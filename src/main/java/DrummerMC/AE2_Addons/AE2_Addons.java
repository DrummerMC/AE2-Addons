package DrummerMC.AE2_Addons;

import appeng.api.AEApi;
import DrummerMC.AE2_Addons.Block.Reactor.ReactorBase;
import DrummerMC.AE2_Addons.Tile.Reactor.TileReactorBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = AE2_Addons.MODID, version = AE2_Addons.VERSION)
public class AE2_Addons
{
    public static final String MODID = "ae2_addons";
    public static final String VERSION = "@VERSION@";
    
    public static ReactorBase reactor;
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	this.reactor = new ReactorBase();
    	this.reactor.setCreativeTab(CreativeTabs.tabRedstone);
    	GameRegistry.registerBlock(this.reactor, "reactor");
    	GameRegistry.registerTileEntity(TileReactorBase.class, "tileReactor");
    }
}
