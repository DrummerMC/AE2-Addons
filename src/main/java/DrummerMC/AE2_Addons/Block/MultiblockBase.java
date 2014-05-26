package DrummerMC.AE2_Addons.Block;

import DrummerMC.AE2_Addons.Tile.TileMultiblockBase;
import DrummerMC.AE2_Addons.libs.erogenousbeef.core.multiblock.BlockMultiblockBase;
import appeng.api.AEApi;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class MultiblockBase extends BlockMultiblockBase {

	protected MultiblockBase() {
		super(Material.iron);
		
	}

	@Override
	public abstract TileEntity createNewTileEntity(World var1, int var2);
	
	public boolean renderAsNormalBlock(){
		return false;
	}

}
