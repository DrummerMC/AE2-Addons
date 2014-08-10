package DrummerMC.Extra_Stuff.Block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import DrummerMC.Extra_Stuff.Tile.TileFluidExporter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFluidExporter extends BlockContainer{

	public BlockFluidExporter() {
		super(Material.iron);
		this.setBlockName("extrastuff.fluid_exporter");
		this.setHardness(0.5F);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileFluidExporter();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
		if(player.isSneaking())
			return false;
		if(world.isRemote)
			return true;
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile == null)
			return false;
		if(player.getCurrentEquippedItem() == null)
			return false;
		if(Block.getBlockFromItem(player.getCurrentEquippedItem().getItem())==null)
			return false;
		if(tile instanceof TileFluidExporter){
			((TileFluidExporter) tile).b = Block.getIdFromBlock(Block.getBlockFromItem(player.getCurrentEquippedItem().getItem()));
			((TileFluidExporter) tile).m = player.getCurrentEquippedItem().getItem().getMetadata(player.getCurrentEquippedItem().getItemDamage());
			world.markBlockForUpdate(x, y, z);
			return true;
		}
		
		return false;
		
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int p_149673_5_)
    {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile == null)
			return null;
		if(tile instanceof TileFluidExporter){
			if(((TileFluidExporter) tile).b == 0)
				return null;
			return Block.getBlockById(((TileFluidExporter) tile).b).getIcon(p_149673_5_, ((TileFluidExporter) tile).m);
		}
		
        return null;
    }

}
