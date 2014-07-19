package DrummerMC.Extra_Stuff.Block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import DrummerMC.Extra_Stuff.Tile.TileEnergyAutomaticCarger;
import DrummerMC.Extra_Stuff.Tile.Reactor.TileReactorController;
import appeng.api.AEApi;
import appeng.api.networking.IGridHost;
import appeng.api.networking.IGridNode;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockAENormal extends BlockContainer {

	@SideOnly(Side.CLIENT)
	IIcon carger;
	
	public BlockAENormal() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		switch(meta){
		case 0:
			return new TileEnergyAutomaticCarger();
		default:
			return null;
		}
	}
	
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return this.carger;
    }
	
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        this.carger = register.registerIcon("ae2addons:carger");
    }
	
	@Override
	public void onPostBlockPlaced(World world, int x, int y, int z, int meta) {
		if(meta == 0){
			if(world.isRemote)
				return;
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile != null){
				if(tile instanceof IGridHost){
					((IGridHost) tile).getGridNode(ForgeDirection.UNKNOWN).updateState();
				}
			}
		}
	}
	
	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int meta) {
		if(meta == 0){
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

}