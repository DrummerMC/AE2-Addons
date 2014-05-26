package DrummerMC.AE2_Addons.proxy;

import DrummerMC.AE2_Addons.libs.erogenousbeef.core.multiblock.MultiblockServerTickHandler;
import cpw.mods.fml.common.FMLCommonHandler;

public class CommonProxy {
	
	public void init(){
		FMLCommonHandler.instance().bus().register(new MultiblockServerTickHandler());
	}

}
