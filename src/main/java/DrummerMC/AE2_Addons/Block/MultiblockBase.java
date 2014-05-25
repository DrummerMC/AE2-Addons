package DrummerMC.AE2_Addons.Block;

import DrummerMC.AE2_Addons.Tile.TileMultiblockBase;
import appeng.api.AEApi;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MultiblockBase extends BlockContainer {

	protected MultiblockBase() {
		super(Material.iron);
		
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileMultiblockBase();
	}
	
	public boolean renderAsNormalBlock(){
		return false;
	}

}
