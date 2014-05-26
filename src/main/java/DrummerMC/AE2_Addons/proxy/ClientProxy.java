package DrummerMC.AE2_Addons.proxy;

import DrummerMC.AE2_Addons.libs.erogenousbeef.core.multiblock.MultiblockClientTickHandler;
import cpw.mods.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy{
	
	public void init(){
		super.init();
		FMLCommonHandler.instance().bus().register(new MultiblockClientTickHandler());
	}

}
