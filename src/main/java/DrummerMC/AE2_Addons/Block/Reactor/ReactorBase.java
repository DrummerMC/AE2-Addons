package DrummerMC.AE2_Addons.Block.Reactor;

import java.util.EnumSet;

import appeng.api.AEApi;
import appeng.api.exceptions.FailedConnection;
import appeng.api.networking.GridFlags;
import appeng.api.networking.GridNotification;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridBlock;
import appeng.api.networking.IGridHost;
import appeng.api.networking.IGridNode;
import appeng.api.util.AEColor;
import appeng.api.util.DimensionalCoord;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import DrummerMC.AE2_Addons.Block.MultiblockBase;
import DrummerMC.AE2_Addons.Tile.TileMultiblockBase;
import DrummerMC.AE2_Addons.Tile.Reactor.TileReactorBase;

public class ReactorBase extends MultiblockBase{
	
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileReactorBase(!world.isRemote);
		
	}
	
	@Override
	public void onPostBlockPlaced(World world, int x, int y, int z, int p_149714_5_) {
		world.getTileEntity(x, y, z);
	}
	
	public void onNeighborBlockChange(World world, int x, int y, int z, Block p_149695_5_) {
		if(!world.isRemote){
			TileEntity n =world.getTileEntity(x, y+1, z);
			TileEntity a = world.getTileEntity(x, y, z);
			if((a!=null)&&(n!=null)){
				if((a instanceof IGridHost)&&(n instanceof IGridHost)){
					System.out.println("test");
					IGridHost aa = (IGridHost) a;
					IGridHost nn = (IGridHost) n;
					IGridNode ab= aa.getGridNode(ForgeDirection.UP);
					IGridNode nb= aa.getGridNode(ForgeDirection.DOWN);
					
					try {
						
						AEApi.instance().createGridConnection(ab, nb);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}


	
}
