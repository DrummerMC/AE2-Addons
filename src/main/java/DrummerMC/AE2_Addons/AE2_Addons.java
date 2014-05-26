package DrummerMC.AE2_Addons;

import appeng.api.AEApi;
import DrummerMC.AE2_Addons.Block.Reactor.ReactorBase;
import DrummerMC.AE2_Addons.Tile.Reactor.TileReactorBase;
import DrummerMC.AE2_Addons.libs.erogenousbeef.core.multiblock.MultiblockEventHandler;
import DrummerMC.AE2_Addons.network.NetworkHandler;
import DrummerMC.AE2_Addons.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = AE2_Addons.MODID, version = AE2_Addons.VERSION)
public class AE2_Addons
{
    public static final String MODID = "ae2_addons";
    public static final String VERSION = "@VERSION@";
    
    public static ReactorBase reactor;
    
    private MultiblockEventHandler multiblockEventHandler;
    
    public static NetworkHandler network;
    
    @SidedProxy(clientSide = "DrummerMC.AE2_Addons.proxy.ClientProxy", serverSide = "DrummerMC.AE2_Addons.proxy.CommonProxy")
    public static CommonProxy proxy;
    
    @Instance(MODID)
    public static AE2_Addons instance;
    
    @EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
    	network = new NetworkHandler();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.init();
    	this.reactor = new ReactorBase();
    	this.reactor.setCreativeTab(CreativeTabs.tabRedstone);
    	GameRegistry.registerBlock(this.reactor, "reactor");
    	GameRegistry.registerTileEntity(TileReactorBase.class, "tileReactor");
    }
    
    @EventHandler
	public void registerServer(FMLServerAboutToStartEvent evt) {
		multiblockEventHandler = new MultiblockEventHandler();
		MinecraftForge.EVENT_BUS.register(multiblockEventHandler);
	}
}
