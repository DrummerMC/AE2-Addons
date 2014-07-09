package DrummerMC.Extra_Stuff.Block.Reactor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import appeng.api.networking.IGridNode;
import DrummerMC.Extra_Stuff.Tile.Reactor.TileReactorBase;
import DrummerMC.Extra_Stuff.Tile.Reactor.TileReactorController;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockReactorController extends BlockContainer {
	
	@SideOnly(Side.CLIENT)
	protected IIcon icon;
	@SideOnly(Side.CLIENT)
	protected IIcon icon_top;
	
	public static int renderID = 0;
	
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
		this.icon_top = register.registerIcon("ae2addons:reactor_controller_top");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side,int meta){
		if(ForgeDirection.UP.ordinal() == side){
			return this.icon_top;
		}
		return this.icon;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
    {
        int l = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        
        TileEntity tile = world.getTileEntity(x, y, z);
        if(tile instanceof TileReactorController){
        	if(l == 0){
        		((TileReactorController) tile).dir = 2;
        	}else if(l == 2){
        		((TileReactorController) tile).dir = 0;
        	}else{
        		((TileReactorController) tile).dir = l;
        	}
        }
        
    }
	
	@Override
	public boolean isOpaqueCube(){
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock(){
		return false;
	}
	
	@Override
	public int getRenderType(){
        return renderID;
    }
}