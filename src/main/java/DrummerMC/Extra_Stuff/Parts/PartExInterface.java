package DrummerMC.Extra_Stuff.Parts;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;
import DrummerMC.Extra_Stuff.Extra_Stuff;
import DrummerMC.Extra_Stuff.Api.Grid.IEnergyStorageGrid;
import DrummerMC.Extra_Stuff.Api.Util.EnergyType;
import DrummerMC.Extra_Stuff.network.ChatPacket;
import appeng.api.AEApi;
import appeng.api.config.Actionable;
import appeng.api.networking.IGridNode;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.networking.storage.IStorageGrid;
import appeng.api.networking.ticking.IGridTickable;
import appeng.api.networking.ticking.TickRateModulation;
import appeng.api.networking.ticking.TickingRequest;
import appeng.api.parts.IPartRenderHelper;
import appeng.api.parts.PartItemStack;
import appeng.api.storage.data.IAEItemStack;

public class PartExInterface extends PartBase implements IGridTickable{
	
	public ItemStack[] inv = new ItemStack[9];
	
	public int last = 0;
	
	private boolean hasWork = false;
	
	@Override
	public ItemStack getItemStack(PartItemStack type) {
		return new ItemStack(Extra_Stuff.partItem, 1, 2);
	}

	@Override
	public TickingRequest getTickingRequest(IGridNode node) {
		return new TickingRequest(1, 200, false, false);
	}

	@Override
	public TickRateModulation tickingRequest(IGridNode node, int TicksSinceLastCall) {
		if(this.getGridNode() != null){
			IGridNode gridNode = this.getGridNode();
			IStorageGrid storage = gridNode.getGrid().getCache(IStorageGrid.class);
			if(this.tile.getWorldObj().getBlock(this.tile.xCoord + side.offsetX, this.tile.yCoord + side.offsetY, this.tile.zCoord + side.offsetZ).hasTileEntity(this.tile.getWorldObj().getBlockMetadata(this.tile.xCoord + side.offsetX, this.tile.yCoord + side.offsetY, this.tile.zCoord + side.offsetZ))){
				TileEntity tileEntity = this.tile.getWorldObj().getTileEntity(this.tile.xCoord + side.offsetX, this.tile.yCoord + side.offsetY, this.tile.zCoord + side.offsetZ);
				if(tileEntity instanceof IInventory){
					int i = ((IInventory) tileEntity).getSizeInventory();
					
					ItemStack[] invCopy = new ItemStack[9];
					{
						for(int num=0; num<9; num++)
							if(inv[num]!=null)
								invCopy[num] = inv[num].copy();
					}
					while(i>0){
						
						i = i-1;
						ItemStack stack = ((IInventory) tileEntity).getStackInSlot(i);
						int i2 = 9;
						while(i2>0){
							i2 = i2 - 1;
							if(!(invCopy[i2] == null || stack == null)){
								if(invCopy[i2].isItemEqual(stack)){
									if(invCopy[i2].stackSize > stack.stackSize){
										invCopy[i2].stackSize = invCopy[i2].stackSize - stack.stackSize;
									}else{
										invCopy[i2] = null;
									}
								}
							}
						}
					}
					int a = 9;
					while(a>0){
						a = a -1;
						int m = ((IInventory) tileEntity).getSizeInventory();;
						while(m>0){
							m = m - 1;
							System.out.println(m+" "+ invCopy[a]);
							if(((IInventory) tileEntity).isItemValidForSlot(m, invCopy[a])){
								if(((IInventory) tileEntity).getStackInSlot(m)!= null && invCopy[a] != null){
									if(((IInventory) tileEntity).getStackInSlot(m).isItemEqual(invCopy[a])&&((IInventory) tileEntity).getStackInSlot(m).getMaxStackSize()>((IInventory) tileEntity).getStackInSlot(m).stackSize&&((IInventory) tileEntity).getStackInSlot(m).stackSize<((IInventory) tileEntity).getInventoryStackLimit()){
										int max = Math.min(((IInventory) tileEntity).getStackInSlot(m).getMaxStackSize(), ((IInventory) tileEntity).getInventoryStackLimit());
										if(max-((IInventory) tileEntity).getStackInSlot(m).stackSize>=invCopy[a].stackSize){
											IAEItemStack AEs = storage.getItemInventory().extractItems(AEApi.instance().storage().createItemStack(invCopy[a]), Actionable.MODULATE, new BaseActionSource());
											if(AEs!=null){
												ItemStack s = ((IInventory) tileEntity).getStackInSlot(m).copy();
												s.stackSize = (int) (s.stackSize + AEs.getStackSize());
												((IInventory) tileEntity).setInventorySlotContents(m, s);
												if(invCopy[a].stackSize == (int) s.stackSize)
													invCopy[a] = null;
												else
													invCopy[a].stackSize = invCopy[a].stackSize - s.stackSize;
											}
										}else{
											int add = invCopy[a].stackSize - (max-((IInventory) tileEntity).getStackInSlot(m).stackSize);
											ItemStack toRem = invCopy[a].copy();
											toRem.stackSize = add;
											IAEItemStack AEs = storage.getItemInventory().extractItems(AEApi.instance().storage().createItemStack(toRem), Actionable.MODULATE, new BaseActionSource());
											if(AEs!=null){
												ItemStack s = ((IInventory) tileEntity).getStackInSlot(m).copy();
												s.stackSize = (int) (s.stackSize + AEs.getStackSize());
												((IInventory) tileEntity).setInventorySlotContents(m, s);
												if(invCopy[a].stackSize == (int) s.stackSize)
													invCopy[a] = null;
												else
													invCopy[a].stackSize = invCopy[a].stackSize - s.stackSize;
											}
										}
									}
								}else if(invCopy[a] != null){
									if(0<((IInventory) tileEntity).getInventoryStackLimit()){
										int max = Math.min(64, ((IInventory) tileEntity).getInventoryStackLimit());
										if(max>=invCopy[a].stackSize){
											IAEItemStack AEs = storage.getItemInventory().extractItems(AEApi.instance().storage().createItemStack(invCopy[a]), Actionable.MODULATE, new BaseActionSource());
											if(AEs!=null){
												ItemStack s = AEs.getItemStack();
												s.stackSize = (int) (AEs.getStackSize());
												((IInventory) tileEntity).setInventorySlotContents(m, s);
												if(invCopy[a].stackSize == (int) s.stackSize)
													invCopy[a] = null;
												else
													invCopy[a].stackSize = invCopy[a].stackSize - s.stackSize;
											}
										}else{
											int add = invCopy[a].stackSize - (max-((IInventory) tileEntity).getStackInSlot(m).stackSize);
											ItemStack toRem = invCopy[a].copy();
											toRem.stackSize = add;
											IAEItemStack AEs = storage.getItemInventory().extractItems(AEApi.instance().storage().createItemStack(toRem), Actionable.MODULATE, new BaseActionSource());
											if(AEs!=null){
												ItemStack s = AEs.getItemStack();
												s.stackSize = (int) (AEs.getStackSize());
												((IInventory) tileEntity).setInventorySlotContents(m, s);
												if(invCopy[a].stackSize == (int) s.stackSize)
													invCopy[a] = null;
												else
													invCopy[a].stackSize = invCopy[a].stackSize - s.stackSize;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		if(hasWork)
			return TickRateModulation.URGENT;
		return TickRateModulation.IDLE;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound data) {
		data.setInteger("last", last);
		int a = 9;
		while (a>0){
			a = a-1;
			if(inv[a] == null){
				NBTTagCompound tag = new NBTTagCompound();
				tag.setBoolean("hasItem", false);
				data.setTag("inv"+a, tag);
			}else{
				NBTTagCompound tag = new NBTTagCompound();
				tag.setBoolean("hasItem", true);
				data.setTag("inv"+a, inv[a].writeToNBT(tag));
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound data) {
		if(data.hasKey("last"))
			last = data.getInteger("last");
		int a = 9;
		while (a>0){
			a = a-1;
			if(data.hasKey("inv"+a)){
				if(data.getCompoundTag("inv"+a).getBoolean("hasItem"))
					this.inv[a] = ItemStack.loadItemStackFromNBT(data.getCompoundTag("inv"+a));
			}else
				this.inv[a] = null;
		}
	}
	
	@Override
	public boolean onActivate(EntityPlayer player, Vec3 pos) {
		if(player.worldObj.isRemote)
			return !player.isSneaking();
		if(player.isSneaking())
			return false;
		if(player.getHeldItem()!=null)
			this.inv[last] = player.getHeldItem().copy();
		else
			this.inv[last] = null;
		last = last + 1;
		Extra_Stuff.network.sendTo(new ChatPacket("chat.ae2addons.setItem", last), (EntityPlayerMP) player);
		if(last == 9)
			last = 0;
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void renderInventory(IPartRenderHelper rh, RenderBlocks renderer) {
		rh.setTexture(Extra_Stuff.partItem.getIconFromDamage(2));
		rh.setBounds(2, 2, 14, 14, 14, 16);
		rh.renderInventoryBox(renderer);

	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void renderStatic(int x, int y, int z, IPartRenderHelper rh,
			RenderBlocks renderer) {
		rh.setTexture(Extra_Stuff.partItem.getIconFromDamage(2));
		rh.setBounds(2, 2, 14, 14, 14, 16);
		rh.renderBlock(x, y, z, renderer);

	}
}