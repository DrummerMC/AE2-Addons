package DrummerMC.Extra_Stuff;

import appeng.api.AEApi;
import DrummerMC.Extra_Stuff.Api.Grid.IEnergyStorageGrid;
import DrummerMC.Extra_Stuff.Block.BlockAENormal;
import DrummerMC.Extra_Stuff.Block.Reactor.BlockReactorController;
import DrummerMC.Extra_Stuff.Block.Reactor.ReactorBase;
import DrummerMC.Extra_Stuff.Item.EnergyCell;
import DrummerMC.Extra_Stuff.Item.ItemBlockNormal;
import DrummerMC.Extra_Stuff.Parts.PartItem;
import DrummerMC.Extra_Stuff.StorageEnergy.EnergyStorageGrid;
import DrummerMC.Extra_Stuff.Tile.TileEnergyAutomaticCarger;
import DrummerMC.Extra_Stuff.Tile.Reactor.TileReactorBase;
import DrummerMC.Extra_Stuff.Tile.Reactor.TileReactorController;
import DrummerMC.Extra_Stuff.libs.erogenousbeef.core.multiblock.MultiblockEventHandler;
import DrummerMC.Extra_Stuff.network.NetworkHandler;
import DrummerMC.Extra_Stuff.proxy.CommonProxy;
import net.minecraft.block.BlockFurnace;
import net.minecraft.client.Minecraft;
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
    public static BlockAENormal aeNormalBlock;
    
    public static PartItem partItem;
    public static EnergyCell energyCell;
    
    private MultiblockEventHandler multiblockEventHandler;
    
    public static NetworkHandler network;
    
    @SidedProxy(clientSide = "DrummerMC.AE2_Addons.proxy.ClientProxy", serverSide = "DrummerMC.AE2_Addons.proxy.CommonProxy")
    public static CommonProxy proxy;
    
    @Instance(MODID)
    public static AE2_Addons instance;
    
    public static  CreativeTabs tab;
    
    @EventHandler
    public void preinit(FMLPreInitializationEvent event){
    	API.instance = new APIInstance();
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
    public void init(FMLInitializationEvent event){
    	AEApi.instance().registries().gridCache().registerGridCache(IEnergyStorageGrid.class, EnergyStorageGrid.class);
    	OreDictionary.registerOre("ingotUranium", Items.apple);
    	proxy.init();
    	partItem = new PartItem();
    	partItem.setCreativeTab(tab);
    	energyCell = new EnergyCell();
    	energyCell.setCreativeTab(tab);
    	AEApi.instance().partHelper().setItemBusRenderer(partItem);
    	reactor = new ReactorBase();
    	reactor.setCreativeTab(tab);
    	reactorController = new BlockReactorController();
    	reactorController.setCreativeTab(tab);
    	aeNormalBlock = new BlockAENormal();
    	aeNormalBlock.setCreativeTab(tab);
    	GameRegistry.registerBlock(reactor, "reactor");
    	GameRegistry.registerBlock(reactorController, "reactorController");
    	GameRegistry.registerBlock(aeNormalBlock, ItemBlockNormal.class, "aeNormalBlock");
    	GameRegistry.registerTileEntity(TileReactorBase.class, "tileReactor");
    	GameRegistry.registerTileEntity(TileReactorController.class, "tileReactorController");
    	GameRegistry.registerTileEntity(TileEnergyAutomaticCarger.class, "tileEnergyAutomaticCharger");
    	GameRegistry.registerItem(partItem, "partItem");
    	GameRegistry.registerItem(energyCell, "energyCell");
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