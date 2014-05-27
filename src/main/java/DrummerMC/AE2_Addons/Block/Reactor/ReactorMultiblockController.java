package DrummerMC.AE2_Addons.Block.Reactor;

import java.util.ArrayList;

import appeng.api.networking.IGridNode;
import DrummerMC.AE2_Addons.Tile.TileMultiblockBase;
import DrummerMC.AE2_Addons.Tile.Reactor.TileReactorBase;
import DrummerMC.AE2_Addons.libs.erogenousbeef.core.multiblock.IMultiblockPart;
import DrummerMC.AE2_Addons.libs.erogenousbeef.core.multiblock.MultiblockControllerBase;
import DrummerMC.AE2_Addons.libs.erogenousbeef.core.multiblock.MultiblockValidationException;
import DrummerMC.AE2_Addons.libs.erogenousbeef.core.multiblock.rectangular.RectangularMultiblockControllerBase;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;


public class ReactorMultiblockController extends RectangularMultiblockControllerBase implements IInventory{

	protected static int nextOrdinal = 0;
	public int ordinal;
	
	public double energy = 0;
	
	private ItemStack[] inv = new ItemStack[1];
	
	public ReactorMultiblockController(World world) {
		super(world);
		inv = new ItemStack[1];
		if(world.isRemote) {
			ordinal = -1;
		}
		else {
			ordinal = nextOrdinal++;
		}
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
		FMLLog.info("[%s] Controller %d detaching block at %s", (worldObj.isRemote ? "CLIENT" : "SERVER"), ordinal, part.getWorldLocation());
		super.detachBlock(part, chunkUnloading);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound data) {
		NBTTagCompound slot = new NBTTagCompound();
		try{
			ItemStack s = inv[0];
			if(s!= null){
				s.writeToNBT(slot);
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		data.setTag("slot", slot);
		data.setDouble("energy", energy);
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		if(data.hasKey("energy")){
			this.energy = data.getDouble("energy");
		}
		if(data.hasKey("slot")){
			
			this.inv[0] = ItemStack.loadItemStackFromNBT((NBTTagCompound) data.getTag("slot"));
		}
		
	}

	@Override
	public void formatDescriptionPacket(NBTTagCompound data) {
		data.setInteger("ordinal", ordinal);
	}

	@Override
	public void decodeDescriptionPacket(NBTTagCompound data) {
		if(data.hasKey("ordinal")) {
			ordinal = data.getInteger("ordinal");
		}
	}
	
	@Override
	protected void onMachinePaused() {
		FMLLog.info("Machine %d PAUSED", hashCode());
	}
	
	@Override
	protected void onMachineAssembled() {
		
		for (IMultiblockPart part : this.connectedParts){
			((TileReactorBase)part).setController(this);
			IGridNode node =((TileReactorBase)part).getGridNode(ForgeDirection.UNKNOWN);
			if(node != null){
				node.updateState();
			}
		}
		FMLLog.info("Machine %d ASSEMBLED", hashCode());
	}
	
	@Override
	protected void onMachineDisassembled() {
		for (IMultiblockPart part : this.connectedParts){
			IGridNode node =((TileReactorBase)part).getGridNode(ForgeDirection.UNKNOWN);
			if(node != null){
				node.destroy();
			}
			((TileReactorBase)part).setController(null);
		}
		FMLLog.info("Machine %d DISASSEMBLED", hashCode());
	}

	@Override
	protected void onMachineRestored() {
		FMLLog.info("Machine %d RESTORED", hashCode());
	}

	@Override
	protected boolean updateServer() {
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
		FMLLog.info("[%s] Controller %d assimilating save data from part @ %s", (worldObj.isRemote ? "CLIENT" : "SERVER"), ordinal, part.getWorldLocation());
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
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		ArrayList<ItemStack> list =OreDictionary.getOres("ingotUranium");
		for(ItemStack s :list){
			if(s.isItemEqual(stack))
				return true;
		}
		return false;
	}



	
}
