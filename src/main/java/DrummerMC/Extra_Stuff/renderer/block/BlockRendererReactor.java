package DrummerMC.Extra_Stuff.renderer.block;

import org.lwjgl.opengl.GL11;

import appeng.api.AEApi;
import DrummerMC.Extra_Stuff.Extra_Stuff;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockRendererReactor implements ISimpleBlockRenderingHandler {
	
	private final int ID;
	public BlockRendererReactor(int ID){
		this.ID = ID;
	}
	
	//I use the IItemRenderer
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId,
			RenderBlocks renderer) {}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		if(Extra_Stuff.reactor.getIcon(0, 0) == Extra_Stuff.reactor.getIcon(world, x, y, z, 0)){
			return renderer.renderStandardBlock(block, x, y, z);
		}else{
			renderer.renderStandardBlock(AEApi.instance().blocks().blockQuartz.block(), x, y, z);
			return renderer.renderStandardBlock(block, x, y, z);
		}
	}
	
	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return ID;
	}

}