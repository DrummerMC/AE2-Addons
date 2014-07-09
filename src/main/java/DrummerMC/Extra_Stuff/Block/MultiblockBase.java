package DrummerMC.Extra_Stuff.Block;

import DrummerMC.Extra_Stuff.Tile.TileMultiblockBase;
import DrummerMC.Extra_Stuff.libs.erogenousbeef.core.multiblock.BlockMultiblockBase;
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