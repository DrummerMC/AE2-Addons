package DrummerMC.AE2_Addons.Gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiReactorBase extends GuiContainer {

	public GuiReactorBase(Container par1Container) {
		super(par1Container);
		
	}

	 @Override
     protected void drawGuiContainerForegroundLayer(int param1, int param2) {
             fontRendererObj.drawString(StatCollector.translateToLocal("ae2addons.gui.reactor.base"), 8, 6, 4210752);
             fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
     }

     @Override
     protected void drawGuiContainerBackgroundLayer(float par1, int par2,
                     int par3) {
             GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
             
             this.mc.renderEngine.bindTexture(new ResourceLocation("ae2addons:/gui/reactor.png"));
             
             int x = (width - xSize) / 2;
             int y = (height - ySize) / 2;
             this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
     }

}
