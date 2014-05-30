package DrummerMC.AE2_Addons.renderer.tile;

import org.lwjgl.opengl.GL11;

import DrummerMC.AE2_Addons.Tile.Reactor.TileReactorController;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.gen.structure.MapGenNetherBridge.Start;

public class TileRendererReactorController extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity t, double x, double y,
			double z, float var8) {
		if(!(t instanceof TileReactorController)){
			return;
		}
		GL11.glPushMatrix();
		TileReactorController tile = (TileReactorController) t;
		GL11.glTranslated(x+.5, y+1.0001, z+.5);
		GL11.glRotated(90 * tile.dir, 0, 1, 0);
		for (int i=0; i<7; i++) {
			GL11.glScalef(.5F, .5F, .5F);
		}
		GL11.glScalef(.8F, .8F, .8F);
		GL11.glRotatef(90, 1, 0, 0);
		GL11.glTranslated(-63, -63, 0);
		FontRenderer font = this.func_147498_b();
		font.drawSplitString(StatCollector.translateToLocal("render.ae2addons.blockReactorController.ConectedReactors") +
				" " + tile.conReactor , 0, 0, 127, 1);
		GL11.glPopMatrix();
	}

}