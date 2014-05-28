package DrummerMC.AE2_Addons.Block.Reactor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import appeng.api.networking.IGridNode;
import DrummerMC.AE2_Addons.Tile.Reactor.TileReactorBase;
import DrummerMC.AE2_Addons.Tile.Reactor.TileReactorController;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockReactorController extends BlockContainer {
	
	@SideOnly(Side.CLIENT)
	protected IIcon icon;
	
	public BlockReactorController() {
		super(Material.iron);
		this.setBlockName("ae2addons.reactorcontroller");
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileReactorController();
	}
	
	@Override
	public void onPostBlockPlaced(World world, int x, int y, int z, int p_149714_5_) {
		if(world.isRemote)
			return;
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile != null){
			if(tile instanceof TileReactorController){
				((TileReactorController) tile).getGridNode(ForgeDirection.UNKNOWN).updateState();
			}
		}
	}
	
	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int meta) {
		if(world.isRemote)
			return;
		TileEntity tile  = world.getTileEntity(x, y, z);
		if(tile != null){
			if(tile instanceof TileReactorController){
				IGridNode  node =((TileReactorController) tile).getGridNode(ForgeDirection.UNKNOWN);
				if(node != null){
					node.destroy();
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register){
		this.icon = register.registerIcon("ae2addons:reactor_controller");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side,int meta){
		return this.icon;
	}
}
