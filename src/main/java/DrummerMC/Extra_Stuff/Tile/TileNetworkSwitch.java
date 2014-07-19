package DrummerMC.Extra_Stuff.Tile;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.Optional.Interface;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import DrummerMC.Extra_Stuff.GridBlock.GridBlockNetworkSwitch;
import appeng.api.AEApi;
import appeng.api.networking.IGridBlock;
import appeng.api.networking.IGridHost;
import appeng.api.networking.IGridNode;
import appeng.api.util.AECableType;
import appeng.api.util.DimensionalCoord;

@Optional.InterfaceList(value = { @Interface(iface = "appeng.api.networking.IGridHost", modid = "appliedenergistics2")})
public class TileNetworkSwitch extends TileEntity implements IGridHost {

	IGridBlock gridblock;
	IGridNode node = null;
	
	public TileNetworkSwitch(){
		this.gridblock = new GridBlockNetworkSwitch(this);
	}
	
	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public IGridNode getGridNode(ForgeDirection dir) {
		if(this.worldObj.isRemote)
			return null;
		if(node == null){
			node = AEApi.instance().createGridNode(gridblock);
			node.updateState();
		}
		return node;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public AECableType getCableConnectionType(ForgeDirection dir) {
		return AECableType.SMART;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public void securityBreak() {
	}
	
	@Optional.Method(modid = "appliedenergistics2")
	public DimensionalCoord getLocation() {
		return new DimensionalCoord(this);
	}

	public ItemStack getItemStack() {
		return new ItemStack(this.getWorldObj().getBlock(xCoord, yCoord, zCoord),this.getBlockMetadata());
	}

}
