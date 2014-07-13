package DrummerMC.Extra_Stuff.Item;

import java.util.List;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.Optional.Interface;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import DrummerMC.Extra_Stuff.MEInventoryHandlerEnergy;
import DrummerMC.Extra_Stuff.Api.IEnergyCell;
import DrummerMC.Extra_Stuff.Api.Util.EnergyType;
import appeng.api.AEApi;
import appeng.api.config.Actionable;
import appeng.api.implementations.tiles.IChestOrDrive;
import appeng.api.networking.IGridNode;
import appeng.api.storage.ICellContainer;
import appeng.api.storage.ICellHandler;
import appeng.api.storage.IMEInventory;
import appeng.api.storage.IMEInventoryHandler;
import appeng.api.storage.StorageChannel;
import appeng.api.util.AECableType;

@Optional.InterfaceList(value = { @Interface(iface = "appeng.api.storage.ICellHandler", modid = "appliedenergistics2"),
		@Interface(iface = "DrummerMC.Extra_Stuff.Api.IEnergyCell", modid = "appliedenergistics2")})
public class EnergyCell extends Item implements ICellHandler, IEnergyCell{
	
	public EnergyCell(){
		AEApi.instance().registries().cell().addCellHandler(this);
		this.setUnlocalizedName("ae2addons.energyCell");
	}
	
	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public boolean isCell(ItemStack is) {
		if(is.getItem() instanceof IEnergyCell)
			return ((IEnergyCell)is.getItem()).useDefaultEnergyStorageHandler(is);
		return false;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public IMEInventoryHandler getCellInventory(ItemStack is, StorageChannel channel) {
		System.out.println("test");
		if(channel == StorageChannel.FLUIDS || !(is.getItem() instanceof IEnergyCell))
			return null;
		if(!((IEnergyCell)is.getItem()).useDefaultEnergyStorageHandler(is))
			return null;
		return new MEInventoryHandlerEnergy(is);
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public IIcon getTopTexture() {
		return null;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public void openChestGui(EntityPlayer player, IChestOrDrive chest,
			ICellHandler cellHandler, IMEInventoryHandler inv, ItemStack is,
			StorageChannel chan) {

	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public int getStatusForCell(ItemStack is, IMEInventory handler) {
		return 1;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public double cellIdleDrain(ItemStack is, IMEInventory handler) {
		return 1;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public double getEnergy(ItemStack is, EnergyType type, String ChannelName) {
		if(is.hasTagCompound()){
			if(is.getTagCompound().hasKey("energy"))
				return is.getTagCompound().getDouble("energy");
			else
				is.getTagCompound().setDouble("energy", 0);
				
		}else{
			is.setTagCompound(new NBTTagCompound());
			is.getTagCompound().setDouble("energy", 0);
		}
		return 0;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public double getMaxEnergy(ItemStack is, EnergyType type, String ChannelName) {
		return 16000;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public double addEnergy(ItemStack is, EnergyType type, String ChannelName, double amount, Actionable action) {
		if(this.getMaxEnergy(is, type, ChannelName)>=this.getEnergy(is, type, ChannelName)+amount){
			if(action == Actionable.MODULATE)
				is.getTagCompound().setDouble("energy", is.getTagCompound().getDouble("energy")+amount);
			return 0;
		}else{
			double cantAdd = amount - (this.getMaxEnergy(is, type, ChannelName)-is.getTagCompound().getDouble("energy"));
			if(action == Actionable.MODULATE)
				is.getTagCompound().setDouble("energy", this.getMaxEnergy(is, type, ChannelName));
		}
		return 0;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public double removeEnergy(ItemStack is, EnergyType type, String ChannelName, double amount, Actionable action) {
		if(this.getEnergy(is, type, ChannelName)>= amount){
			if(action == Actionable.MODULATE)
				is.getTagCompound().setDouble("energy", is.getTagCompound().getDouble("energy")-amount);
			return amount;
		}else{
			double oldEnergy = is.getTagCompound().getDouble("energy");
			if(action == Actionable.MODULATE)
				is.getTagCompound().setDouble("energy", 0);
			return oldEnergy;
		}
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public boolean useDefaultEnergyStorageHandler(ItemStack is) {
		return true;
	}
	
	public void getSubItems(Item item, CreativeTabs creativeTab, List list) {
		list.add(new ItemStack(item));
		ItemStack stack = new ItemStack(item);
		stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setDouble("energy", 200);
		list.add(stack);
	}
	
	 @Override
	    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		 list.add(""+this.getEnergy(stack, EnergyType.MJ, "default"));
	 }

}
