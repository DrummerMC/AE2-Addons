package DrummerMC.Extra_Stuff.proxy;

import DrummerMC.Extra_Stuff.Extra_Stuff;
import DrummerMC.Extra_Stuff.libs.erogenousbeef.core.multiblock.MultiblockServerTickHandler;
import DrummerMC.Extra_Stuff.network.GuiHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class CommonProxy {
	
	public void init(){
		FMLCommonHandler.instance().bus().register(new MultiblockServerTickHandler());
		NetworkRegistry.INSTANCE.registerGuiHandler(Extra_Stuff.instance, new GuiHandler());
	}
	
	//Client Side Only
	public void registerRenderers(){}

}