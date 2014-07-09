package DrummerMC.Extra_Stuff.Block.Reactor;

import java.util.EnumSet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import appeng.api.AEApi;
import appeng.api.exceptions.FailedConnection;
import appeng.api.networking.GridFlags;
import appeng.api.networking.GridNotification;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridBlock;
import appeng.api.networking.IGridHost;
import appeng.api.networking.IGridNode;
import appeng.api.networking.IMachineSet;
import appeng.api.util.AEColor;
import appeng.api.util.DimensionalCoord;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import DrummerMC.Extra_Stuff.Extra_Stuff;
import DrummerMC.Extra_Stuff.Block.MultiblockBase;
import DrummerMC.Extra_Stuff.Tile.TileMultiblockBase;
import DrummerMC.Extra_Stuff.Tile.Reactor.TileReactorBase;
import DrummerMC.Extra_Stuff.Tile.Reactor.TileReactorController;
import DrummerMC.Extra_Stuff.network.ChatPacket;
import DrummerMC.Extra_Stuff.network.ReactorUpdate;

public class ReactorBase extends MultiblockBase{
	
	public ReactorBase(){
		super();
		this.setBlockName("ae2addons.reactor.base");
	}
	
	public static int renderID = 0;
	
	@SideOnly(Side.CLIENT)
	IIcon icon;
	@SideOnly(Side.CLIENT)
	IIcon icon2;
	
	@Override
	public int getRenderType(){
        return renderID;
    }
	
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileReactorBase();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register){
		this.icon = register.registerIcon("ae2addons:reactor");
		this.icon2 = register.registerIcon("ae2addons:reactor2");
	}
	
	@Override
	public void onPostBlockPlaced(World world, int x, int y, int z, int p_149714_5_) {
		world.getTileEntity(x, y, z);
	}
	
	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int meta) {
		if(world.isRemote)
			return;
		TileEntity tile  = world.getTileEntity(x, y, z);
		if(tile != null){
			if(tile instanceof TileReactorBase){
				IGridNode  node =((TileReactorBase) tile).getGridNode(ForgeDirection.UNKNOWN);
				if(node != null){
					node.destroy();
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
        TileEntity tile =world.getTileEntity(x, y, z);
        if(tile instanceof TileReactorBase){
        	if(((TileReactorBase) tile).hasController())
        		if(((TileReactorBase) tile).getController().isAssembled()){
        			return icon2;
        		}
        		
        }
        return this.getIcon(side, world.getBlockMetadata(x, y, z));
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side,int meta){
		return this.icon;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
		if(world.isRemote)
			return true;
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile != null){
			if(tile instanceof TileReactorBase){
				if(((TileReactorBase) tile).hasController() == false)
					return false;
				IGridNode node = ((TileReactorBase) tile).getGridNode(ForgeDirection.UNKNOWN);
				if(node == null)
					return true;
				IGrid grid = node.getGrid();
				if(grid == null)
					return true;
				IMachineSet m = grid.getMachines(TileReactorController.class);
				if(m.size()==0){
					if(player instanceof EntityPlayerMP)
						Extra_Stuff.network.sendTo(new ChatPacket("chat.ae2addons.noController"), (EntityPlayerMP) player);
					return true;
				}else if(m.size()>1){
					if(player instanceof EntityPlayerMP)
						Extra_Stuff.network.sendTo(new ChatPacket("chat.ae2addons.toManyController"), (EntityPlayerMP) player);
					return true;
				}
				player.openGui(Extra_Stuff.instance, 0, world, x, y, z);
				Extra_Stuff.network.sendTo(new ReactorUpdate(x,y,z,world.provider.dimensionId,((ReactorMultiblockController)((TileReactorBase) tile).getController()).isActive), (EntityPlayerMP) player);
				return true;
			}
		}
        return false;
    }
	
}