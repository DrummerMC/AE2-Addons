package DrummerMC.AE2_Addons.proxy;

import DrummerMC.AE2_Addons.AE2_Addons;
import DrummerMC.AE2_Addons.libs.erogenousbeef.core.multiblock.MultiblockServerTickHandler;
import DrummerMC.AE2_Addons.network.GuiHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class CommonProxy {
	
	public void init(){
		FMLCommonHandler.instance().bus().register(new MultiblockServerTickHandler());
		NetworkRegistry.INSTANCE.registerGuiHandler(AE2_Addons.instance, new GuiHandler());
	}
	
	//Client Side Only
	public void registerRenderers(){}

}
