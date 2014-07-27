package DrummerMC.Extra_Stuff.Block;

import appeng.api.networking.IGridHost;
import appeng.api.networking.IGridNode;
import DrummerMC.Extra_Stuff.Tile.TileNetworkSwitch;
import DrummerMC.Extra_Stuff.Tile.Reactor.TileReactorController;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockNetworkSwitch extends BlockContainer {

	public BlockNetworkSwitch() {
		super(Material.iron);
		this.setBlockName("extrastuff.networkswitch");
		this.setBlockTextureName("extrastuff:networkswitch");
		this.setHardness(0.3F);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileNetworkSwitch();
	}
	
	@Override
	public void onPostBlockPlaced(World world, int x, int y, int z, int p_149714_5_) {
		if(world.isRemote)
			return;
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile != null){
			if(tile instanceof IGridHost){
				((IGridHost) tile).getGridNode(ForgeDirection.UNKNOWN).updateState();
			}
		}
	}
	
	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int meta) {
		if(world.isRemote)
			return;
		TileEntity tile  = world.getTileEntity(x, y, z);
		if(tile != null){
			if(tile instanceof IGridHost){
				IGridNode  node =((IGridHost) tile).getGridNode(ForgeDirection.UNKNOWN);
				if(node != null){
					node.destroy();
				}
			}
		}
	}

}
