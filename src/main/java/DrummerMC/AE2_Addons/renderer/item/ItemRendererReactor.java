package DrummerMC.AE2_Addons.renderer.item;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRendererReactor implements IItemRenderer {
	
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
		GL11.glEnable(GL11.GL_BLEND);
		//GL11.glDisable(GL11.GL_CULL_FACE);
		Tessellator t = Tessellator.instance;
		
		t.startDrawingQuads();
		
		t.setNormal(0.0F, 1.0F, 0.0F);
		
		//top
		t.addVertexWithUV(0, 1, 1, icon.getMinU(), icon.getMinV());
		t.addVertexWithUV(1, 1, 1, icon.getMinU(), icon.getMaxV());
		t.addVertexWithUV(1, 1, 0, icon.getMaxU(), icon.getMaxV());
		t.addVertexWithUV(0, 1, 0, icon.getMaxU(), icon.getMinV());
		
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
		
		//bottom
		t.addVertexWithUV(0, 0, 0, icon.getMinU(), icon.getMinV());
		t.addVertexWithUV(1, 0, 0, icon.getMinU(), icon.getMaxV());
		t.addVertexWithUV(1, 0, 1, icon.getMaxU(), icon.getMaxV());
		t.addVertexWithUV(0, 0, 1, icon.getMaxU(), icon.getMinV());
		
		t.draw();
	}

}
