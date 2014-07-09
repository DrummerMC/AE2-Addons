package DrummerMC.Extra_Stuff.Block.Reactor;

import java.util.ArrayList;

import appeng.api.networking.IGridNode;
import appeng.api.networking.events.MENetworkPowerStorage;
import DrummerMC.Extra_Stuff.AE2_Addons;
import DrummerMC.Extra_Stuff.Tile.TileMultiblockBase;
import DrummerMC.Extra_Stuff.Tile.Reactor.TileReactorBase;
import DrummerMC.Extra_Stuff.Tile.Reactor.TileReactorController;
import DrummerMC.Extra_Stuff.libs.erogenousbeef.core.multiblock.IMultiblockPart;
import DrummerMC.Extra_Stuff.libs.erogenousbeef.core.multiblock.MultiblockControllerBase;
import DrummerMC.Extra_Stuff.libs.erogenousbeef.core.multiblock.MultiblockValidationException;
import DrummerMC.Extra_Stuff.libs.erogenousbeef.core.multiblock.rectangular.RectangularMultiblockControllerBase;
import DrummerMC.Extra_Stuff.network.ReactorUpdate;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

public class ReactorMultiblockController extends
		RectangularMultiblockControllerBase implements IInventory {

	protected static int nextOrdinal = 0;
	public int ordinal;

	public double energy = 0;
	public int uran = 0;
	public boolean isActive = false;

	private ItemStack[] inv = new ItemStack[1];

	public ReactorMultiblockController(World world) {
		super(world);
		inv = new ItemStack[1];
		if (world.isRemote) {
			ordinal = -1;
		} else {
			ordinal = nextOrdinal++;
		}
	}
	
	public void changeIsActive(){
		this.isActive = !this.isActive;
		AE2_Addons.network.sendToDimension(new ReactorUpdate(this.getMaximumXSize(),
				this.getMaximumYSize(),
				this.getMaximumZSize(),
				this.worldObj.provider.dimensionId,
				this.isActive),
				this.worldObj.provider.dimensionId);
	}
	
	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {
	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 27;
	}

	@Override
	protected void onAssimilate(MultiblockControllerBase assimilated) {

	}

	@Override
	protected void onAssimilated(MultiblockControllerBase otherMachine) {

	}

	@Override
	public void detachBlock(IMultiblockPart part, boolean chunkUnloading) {
		FMLLog.info("[%s] Controller %d detaching block at %s",
				(worldObj.isRemote ? "CLIENT" : "SERVER"), ordinal,
				part.getWorldLocation());
		super.detachBlock(part, chunkUnloading);
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		NBTTagCompound slot = new NBTTagCompound();
		try {
			ItemStack s = inv[0];
			if (s != null) {
				s.writeToNBT(slot);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		data.setTag("slot", slot);
		data.setDouble("energy", energy);
		data.setInteger("uran", uran);
		data.setBoolean("isActive", isActive);
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		if (data.hasKey("energy")) {
			this.energy = data.getDouble("energy");
		}
		if (data.hasKey("slot")) {
			this.inv[0] = ItemStack.loadItemStackFromNBT((NBTTagCompound) data
					.getTag("slot"));
		}
		if (data.hasKey("uran")) {
			this.uran = data.getInteger("uran");
		}
		if (data.hasKey("isActive")) {
			this.isActive = data.getBoolean("isActive");
		}

	}

	@Override
	public void formatDescriptionPacket(NBTTagCompound data) {
		data.setInteger("ordinal", ordinal);
	}

	@Override
	public void decodeDescriptionPacket(NBTTagCompound data) {
		if (data.hasKey("ordinal")) {
			ordinal = data.getInteger("ordinal");
		}
	}

	@Override
	protected void onMachinePaused() {
		FMLLog.info("Machine %d PAUSED", hashCode());
	}

	@Override
	protected void onMachineAssembled() {

		for (IMultiblockPart part : this.connectedParts) {
			((TileReactorBase) part).setController(this);
			IGridNode node = ((TileReactorBase) part)
					.getGridNode(ForgeDirection.UNKNOWN);
			if (node != null) {
				node.updateState();
			}
		}
		FMLLog.info("Machine %d ASSEMBLED", hashCode());
	}

	@Override
	protected void onMachineDisassembled() {
		for (IMultiblockPart part : this.connectedParts) {
			IGridNode node = ((TileReactorBase) part)
					.getGridNode(ForgeDirection.UNKNOWN);
			if (node != null) {
				node.destroy();
			}
			((TileReactorBase) part).setController(null);
		}
		FMLLog.info("Machine %d DISASSEMBLED", hashCode());
	}

	@Override
	protected void onMachineRestored() {
		FMLLog.info("Machine %d RESTORED", hashCode());
	}

	@Override
	protected boolean updateServer() {
		if (!this.isAssembled())
			return true;
		boolean u = false;
		TileReactorBase tile = null;
		for (IMultiblockPart part : this.connectedParts) {
			tile = (TileReactorBase) part;
			break;
		}
		if (tile != null && this.isActive) {

			if (tile.getGridNode(ForgeDirection.UNKNOWN).getGrid() == null)
				return true;
			if (tile.getGridNode(ForgeDirection.UNKNOWN).getGrid().isEmpty())
				return true;
			if (tile.getGridNode(ForgeDirection.UNKNOWN).getGrid()
					.getMachines(TileReactorController.class).size() != 1)
				return true;
			if (this.uran == 0 && this.getStackInSlot(0) != null) {
				for (ItemStack s : OreDictionary.getOres("ingotUranium")) {
					if (s.isItemEqual(this.getStackInSlot(0))) {
						if (this.inv[0].stackSize == 0) {
							this.inv[0] = null;
							u = true;
							this.uran = 1800000;
							break;
						}
						this.inv[0].stackSize = this.inv[0].stackSize - 1;
						this.uran = 1800000;
						break;
					}
				}
			}
			if(this.energy == 0 && this.uran != 0){
				u = true;
			}
			if (this.uran >= 100
					&& ((this.energy + 100) <= ((double) (16000 * this
							.getNumConnectedBlocks())))) {
				this.uran = this.uran - 100;
				this.energy = this.energy + (double) 100;
			} else if (uran > 0
					&& ((this.energy + uran) <= ((double) (16000 * this
							.getNumConnectedBlocks())))) {
				this.energy = this.energy + (double) uran;
				uran = 0;
			}
			if(u){
				for (IMultiblockPart part : this.connectedParts) {
					((TileReactorBase)part).getGridNode(ForgeDirection.UNKNOWN).getGrid().
						postEvent(new MENetworkPowerStorage(tile, MENetworkPowerStorage.PowerEventType.PROVIDE_POWER));
				}
			}
		}
		return true;
	}

	@Override
	protected void updateClient() {
	}

	@Override
	protected int getMaximumXSize() {
		return 3;
	}

	@Override
	protected int getMaximumZSize() {
		return 3;
	}

	@Override
	protected int getMaximumYSize() {
		return 3;
	}

	@Override
	protected void isBlockGoodForInterior(World world, int x, int y, int z) {
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part,
			NBTTagCompound data) {
		FMLLog.info("[%s] Controller %d assimilating save data from part @ %s",
				(worldObj.isRemote ? "CLIENT" : "SERVER"), ordinal,
				part.getWorldLocation());
		decodeDescriptionPacket(data);
	}

	@Override
	public int getSizeInventory() {
		return inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inv[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amt) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			if (stack.stackSize <= amt) {
				setInventorySlotContents(slot, null);
			} else {
				stack = stack.splitStack(amt);
				if (stack.stackSize == 0) {
					setInventorySlotContents(slot, null);
				}
			}
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			setInventorySlotContents(slot, null);
		}
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inv[slot] = stack;
		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return "ae2addons.reactor.inventory";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {

	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		ArrayList<ItemStack> list = OreDictionary.getOres("ingotUranium");
		for (ItemStack s : list) {
			if (s.isItemEqual(stack))
				return true;
		}
		return false;
	}
}