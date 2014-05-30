package DrummerMC.AE2_Addons.renderer.item;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemRendererReactorController implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		IIcon icon = Block.getBlockFromItem(stack.getItem()).getIcon(0, 0);
		IIcon icon_top = Block.getBlockFromItem(stack.getItem()).getIcon(ForgeDirection.UP.ordinal(), 0);
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_CULL_FACE);
		Tessellator t = Tessellator.instance;
		
		t.startDrawingQuads();
		
		t.setNormal(0.0F, 1.0F, 0.0F);
		
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
		t.setNormal(0.0F, 1.0F, 0.0F);
		t.setColorRGBA(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), Color.BLACK.getAlpha());
		//bottom
		t.addVertex(0, 0, 0);
		t.addVertex(1, 0, 0);
		t.addVertex(1, 0, 1);
		t.addVertex(0, 0, 1);
		
		t.draw();
		
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("ae2addons:textures/icon.png"));
		t.startDrawingQuads();
		t.setBrightness(125);
		t.setColorRGBA(26, 150, 81, 255);
		t.setNormal(0.1F, 0.1F, 0.0F);
		
		t.addVertex(0.0625, 0.99, 0.9375);
		t.addVertex(0.9375, 0.99, 0.9375);
		t.addVertex(0.9375, 0.99, 0.0625);
		t.addVertex(0.0625, 0.99, 0.0625);
		
		t.draw();
		
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		GL11.glPopMatrix();

	}
}