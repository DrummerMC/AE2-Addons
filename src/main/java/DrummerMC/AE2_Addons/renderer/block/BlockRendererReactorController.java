package DrummerMC.AE2_Addons.renderer.block;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import DrummerMC.AE2_Addons.Block.Reactor.BlockReactorController;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockRendererReactorController implements
		ISimpleBlockRenderingHandler {
	
	public final int renderID;
	
	public BlockRendererReactorController(int renderID) {
		this.renderID = renderID;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId,
			RenderBlocks renderer) {}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		if(!(block instanceof BlockReactorController))
			return false;
		
		IIcon icon = block.getIcon(0, 0);
		
		IIcon icon_top = block.getIcon(ForgeDirection.UP.ordinal(), 0);
		
		GL11.glPushMatrix();
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_CULL_FACE);
		Tessellator t = Tessellator.instance;
		t.addTranslation(x, y, z);
		
		t.setColorOpaque_I(block.colorMultiplier(world, x, y, z));
		t.setBrightness(240);
		
		t.setNormal(1.0F, 1.0F, 1.0F);
		
		//top
		t.addVertexWithUV(0, 1, 1, icon_top.getMinU(), icon_top.getMinV());
		t.addVertexWithUV(1, 1, 1, icon_top.getMinU(), icon_top.getMaxV());
		t.addVertexWithUV(1, 1, 0, icon_top.getMaxU(), icon_top.getMaxV());
		t.addVertexWithUV(0, 1, 0, icon_top.getMaxU(), icon_top.getMinV());
		
		//sides
		t.addVertexWithUV(0, 0, 0, icon.getMinU(), icon.getMinV());
		t.addVertexWithUV(0, 1, 0, icon.getMinU(), icon.getMaxV());
		t.addVertexWithUV(1, 1, 0, icon.getMaxU(), icon.getMaxV());
		t.addVertexWithUV(1, 0, 0, icon.getMaxU(), icon.getMinV());
		
		t.addVertexWithUV(1, 0, 1, icon.getMinU(), icon.getMinV());
		t.addVertexWithUV(1, 1, 1, icon.getMinU(), icon.getMaxV());
		t.addVertexWithUV(0, 1, 1, icon.getMaxU(), icon.getMaxV());
		t.addVertexWithUV(0, 0, 1, icon.getMaxU(), icon.getMinV());
		
		t.addVertexWithUV(0, 0, 1, icon.getMinU(), icon.getMinV());
		t.addVertexWithUV(0, 1, 1, icon.getMinU(), icon.getMaxV());
		t.addVertexWithUV(0, 1, 0, icon.getMaxU(), icon.getMaxV());
		t.addVertexWithUV(0, 0, 0, icon.getMaxU(), icon.getMinV());
		
		t.addVertexWithUV(1, 0, 0, icon.getMinU(), icon.getMinV());
		t.addVertexWithUV(1, 1, 0, icon.getMinU(), icon.getMaxV());
		t.addVertexWithUV(1, 1, 1, icon.getMaxU(), icon.getMaxV());
		t.addVertexWithUV(1, 0, 1, icon.getMaxU(), icon.getMinV());
		
		t.draw();
		
		t.startDrawingQuads();
		t.setColorRGBA(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), Color.BLACK.getAlpha());
		//bottom
		t.addVertex(0, 0, 0);
		t.addVertex(1, 0, 0);
		t.addVertex(1, 0, 1);
		t.addVertex(0, 0, 1);
		
		t.draw();
		renderer.minecraftRB.renderEngine.bindTexture(new ResourceLocation("ae2addons:textures/icon.png"));
		t.startDrawingQuads();
		t.setBrightness(125);
		t.setColorRGBA(26, 150, 81, 255);
		t.setNormal(0.1F, 0.1F, 0.0F);
		
		t.addVertex(0.0625, 0.99, 0.9375);
		t.addVertex(0.9375, 0.99, 0.9375);
		t.addVertex(0.9375, 0.99, 0.0625);
		t.addVertex(0.0625, 0.99, 0.0625);
		
		t.draw();
		
		renderer.minecraftRB.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		GL11.glPopMatrix();
		t.addTranslation(-x, -y, -z);
		t.startDrawingQuads();
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return this.renderID;
	}
}