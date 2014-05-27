package DrummerMC.AE2_Addons.Tile.Reactor;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import appeng.api.AEApi;
import appeng.api.networking.IGridHost;
import appeng.api.networking.IGridNode;
import appeng.api.util.AECableType;
import appeng.api.util.DimensionalCoord;

public class TileReactorController extends TileEntity implements IGridHost {
	
	IGridNode node =null;
	final GridReactorBlockController grid;
	
	public TileReactorController(){
		this.grid = new GridReactorBlockController(this);
	}
	
	@Override
	public IGridNode getGridNode(ForgeDirection dir) {
		if(this.worldObj.isRemote)
			return null;
		if(node == null){
			node = AEApi.instance().createGridNode(grid);
			node.updateState();
		}
		return node;
	}

	@Override
	public AECableType getCableConnectionType(ForgeDirection dir) {
		return AECableType.SMART;
	}

	@Override
	public void securityBreak() {}

	public DimensionalCoord getLocation() {
		return new DimensionalCoord(this);
	}

	public ItemStack getItemStack() {
		return new ItemStack(this.blockType);
	}

}
