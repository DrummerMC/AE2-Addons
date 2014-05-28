package DrummerMC.AE2_Addons.Gui;

import org.lwjgl.opengl.GL11;

import DrummerMC.AE2_Addons.AE2_Addons;
import DrummerMC.AE2_Addons.Block.Reactor.ReactorMultiblockController;
import DrummerMC.AE2_Addons.Container.ContainerReactorBase;
import DrummerMC.AE2_Addons.network.ReactorUpdate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiReactorBase extends GuiContainer {
	
	final Container con;
	
	public boolean isActivate = false;
	
	public GuiReactorBase(Container container) {
		super(container);
		((ContainerReactorBase) container).gui = this;
		this.con = container;
	}
	
	@Override
	public void initGui(){
		super.initGui();
		int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
		
		//the parameter number 6 is ignoreged because I override the method which draw the string
		buttonList.add(new GuiButton(1, x+10, y+40, 60, 20, ""){
			@Override
			public void drawCenteredString(FontRenderer par1FontRenderer, String par2Str, int par3, int par4, int par5){
		        if(!isActivate){
		        	super.drawCenteredString(par1FontRenderer, StatCollector.translateToLocal("gui.ae2addons.reactor.button.activate"), par3, par4, par5);
		        }else{
		        	super.drawCenteredString(par1FontRenderer, StatCollector.translateToLocal("gui.ae2addons.reactor.button.deActivate"), par3, par4, par5);
		        }
		        
		    }
		});
	}
	
	protected void actionPerformed(GuiButton guibutton) {
		ContainerReactorBase c = (ContainerReactorBase) con;
		AE2_Addons.network.sendToServer(new ReactorUpdate(c.tile.xCoord,c.tile.yCoord,c.tile.zCoord,c.tile.getWorldObj().provider.dimensionId,guibutton.id));
	}

	 @Override
     protected void drawGuiContainerForegroundLayer(int param1, int param2) {
             fontRendererObj.drawString(StatCollector.translateToLocal("gui.ae2addons.reactor.base"), 8, 6, 4210752);
             fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
     }

     @Override
     protected void drawGuiContainerBackgroundLayer(float par1, int par2,
                     int par3) {
             GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
             
             this.mc.renderEngine.bindTexture(new ResourceLocation("ae2addons:/textures/gui/reactor.png"));
             
             int x = (width - xSize) / 2;
             int y = (height - ySize) / 2;
             this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
     }

}
