package DrummerMC.AE2_Addons.proxy;

import DrummerMC.AE2_Addons.libs.erogenousbeef.core.multiblock.MultiblockClientTickHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ClientProxy extends CommonProxy{
	
	@Override
	public void init(){
		super.init();
		FMLCommonHandler.instance().bus().register(new MultiblockClientTickHandler());
	}
	
	@Override
	public void loadLaguage(){
		
	}

}
