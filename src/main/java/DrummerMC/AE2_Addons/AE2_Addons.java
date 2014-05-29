package DrummerMC.AE2_Addons;

import appeng.api.AEApi;
import DrummerMC.AE2_Addons.Block.Reactor.BlockReactorController;
import DrummerMC.AE2_Addons.Block.Reactor.ReactorBase;
import DrummerMC.AE2_Addons.Tile.Reactor.TileReactorBase;
import DrummerMC.AE2_Addons.Tile.Reactor.TileReactorController;
import DrummerMC.AE2_Addons.libs.erogenousbeef.core.multiblock.MultiblockEventHandler;
import DrummerMC.AE2_Addons.network.NetworkHandler;
import DrummerMC.AE2_Addons.proxy.CommonProxy;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.ServerLaunchWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = AE2_Addons.MODID, version = AE2_Addons.VERSION)
public class AE2_Addons
{
    public static final String MODID = "ae2addons";
    public static final String VERSION = "@VERSION@";
    
    public static ReactorBase reactor;
    public static BlockReactorController reactorController;
    
    private MultiblockEventHandler multiblockEventHandler;
    
    public static NetworkHandler network;
    
    @SidedProxy(clientSide = "DrummerMC.AE2_Addons.proxy.ClientProxy", serverSide = "DrummerMC.AE2_Addons.proxy.CommonProxy")
    public static CommonProxy proxy;
    
    @Instance(MODID)
    public static AE2_Addons instance;
    
    public static  CreativeTabs tab;
    
    @EventHandler
    public void preinit(FMLPreInitializationEvent event){
    	tab = new CreativeTabs(MODID) {
    		
    		@SideOnly(Side.CLIENT)
			@Override
			public Item getTabIconItem() {
				return Item.getItemFromBlock(reactor);
			}
    	    
    	};
    	
    	network = new NetworkHandler();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	OreDictionary.registerOre("ingotUranium", Items.apple);
    	proxy.init();
    	this.reactor = new ReactorBase();
    	this.reactor.setCreativeTab(tab);
    	reactorController = new BlockReactorController();
    	reactorController.setCreativeTab(tab);
    	GameRegistry.registerBlock(this.reactor, "reactor");
    	GameRegistry.registerBlock(this.reactorController, "reactorController");
    	GameRegistry.registerTileEntity(TileReactorBase.class, "tileReactor");
    	GameRegistry.registerTileEntity(TileReactorController.class, "tileReactorController");
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	proxy.registerRenderers();
    }
    
    
    @EventHandler
	public void registerServer(FMLServerAboutToStartEvent evt) {
		multiblockEventHandler = new MultiblockEventHandler();
		MinecraftForge.EVENT_BUS.register(multiblockEventHandler);
	}
}
