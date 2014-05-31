package DrummerMC.AE2_Addons.Block;

import DrummerMC.AE2_Addons.Tile.TileEnergyAutomaticCharger;
import appeng.api.AEApi;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAENormal extends BlockContainer {

	public BlockAENormal() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		switch(meta){
		case 0:
			return new TileEnergyAutomaticCharger();
		default:
			return null;
		}
	}

}