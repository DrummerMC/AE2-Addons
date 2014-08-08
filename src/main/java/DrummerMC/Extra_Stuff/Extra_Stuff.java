package DrummerMC.Extra_Stuff;

import appeng.api.AEApi;
import appeng.api.parts.IPartItem;
import DrummerMC.Extra_Stuff.Api.Grid.IEnergyStorageGrid;
import DrummerMC.Extra_Stuff.Block.BlockAENormal;
import DrummerMC.Extra_Stuff.Block.BlockNetworkSwitch;
import DrummerMC.Extra_Stuff.Block.BlockSolarFurnace;
import DrummerMC.Extra_Stuff.Block.Reactor.BlockReactorController;
import DrummerMC.Extra_Stuff.Block.Reactor.ReactorBase;
import DrummerMC.Extra_Stuff.Item.ChestHolder;
import DrummerMC.Extra_Stuff.Item.EnergyCell;
import DrummerMC.Extra_Stuff.Item.ItemBlockNormal;
import DrummerMC.Extra_Stuff.Item.ItemSheld;
import DrummerMC.Extra_Stuff.Parts.PartItem;
import DrummerMC.Extra_Stuff.StorageEnergy.EnergyStorageGrid;
import DrummerMC.Extra_Stuff.Tile.TileEnergyAutomaticCarger;
import DrummerMC.Extra_Stuff.Tile.TileNetworkSwitch;
import DrummerMC.Extra_Stuff.Tile.TileSolarFurnace;
import DrummerMC.Extra_Stuff.Tile.Reactor.TileReactorBase;
import DrummerMC.Extra_Stuff.Tile.Reactor.TileReactorController;
import DrummerMC.Extra_Stuff.libs.erogenousbeef.core.multiblock.MultiblockEventHandler;
import DrummerMC.Extra_Stuff.network.NetworkHandler;
import DrummerMC.Extra_Stuff.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.ServerLaunchWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = Extra_Stuff.MODID, version = Extra_Stuff.VERSION)
public class Extra_Stuff
{
    public static final String MODID = "extrastuff";
    public static final String VERSION = "@VERSION@";
    
    public static Item chestHolder;
    
    public static Block solarFurnaceBurn;
    public static Block solarFurnaceIdle;
    
    public static ReactorBase reactor;
    public static BlockReactorController reactorController;
    public static BlockAENormal aeNormalBlock;
    public static Block networkSwitch;
    
    
    public static Item partItem;
    public static EnergyCell energyCell;
    
    public static Item sheld;
    
    private MultiblockEventHandler multiblockEventHandler;
    
    public static NetworkHandler network;
    
    @SidedProxy(clientSide = "DrummerMC.Extra_Stuff.proxy.ClientProxy", serverSide = "DrummerMC.Extra_Stuff.proxy.CommonProxy")
    public static CommonProxy proxy;
    
    @Instance(MODID)
    public static Extra_Stuff instance;
    
    public static  CreativeTabs tab;
    
    @EventHandler
    public void preinit(FMLPreInitializationEvent event){
    	chestHolder = new ChestHolder();
    	GameRegistry.registerItem(chestHolder, "chestHolder");
    	
    	
    	
    	API.instance = new APIInstance();
    	tab = new CreativeTabs(MODID) {
    		
    		@SideOnly(Side.CLIENT)
			@Override
			public Item getTabIconItem() {
				return Item.getItemFromBlock(solarFurnaceIdle);
			}
    	    
    	};
    	sheld = new ItemSheld().setCreativeTab(tab).setUnlocalizedName("extrastuff.sheld");
    	GameRegistry.registerItem(sheld, "sheld");
    	
    	
    	this.solarFurnaceBurn = new BlockSolarFurnace(true).setHardness(3.5F).setStepSound(Block.soundTypePiston).setBlockName("extrastuff.solarfurnace.burn");
    	this.solarFurnaceIdle = new BlockSolarFurnace(false).setHardness(3.5F).setStepSound(Block.soundTypePiston).setBlockName("extrastuff.solarfurnace.idle").setCreativeTab(tab);
    	GameRegistry.registerBlock(solarFurnaceBurn, "solarfurnaceburn");
    	GameRegistry.registerBlock(solarFurnaceIdle, "solarfurnaceidle");
    	
    	GameRegistry.registerTileEntity(TileSolarFurnace.class, "tilesolarfurnace");
    	
    	chestHolder.setCreativeTab(tab);
    	
    	network = new NetworkHandler();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event){
    	
    	proxy.init();
    	//AE2 Stuff
    	if(Loader.isModLoaded("appliedenergistics2")){
    		AEApi.instance().registries().gridCache().registerGridCache(IEnergyStorageGrid.class, EnergyStorageGrid.class);
        	OreDictionary.registerOre("ingotUranium", Items.apple);
        	
        	partItem = new PartItem();
        	partItem.setCreativeTab(tab);
        	energyCell = new EnergyCell();
        	energyCell.setCreativeTab(tab);
        	AEApi.instance().partHelper().setItemBusRenderer((IPartItem) partItem);
        	reactor = new ReactorBase();
        	reactor.setCreativeTab(tab);
        	reactorController = new BlockReactorController();
        	reactorController.setCreativeTab(tab);
        	aeNormalBlock = new BlockAENormal();
        	aeNormalBlock.setCreativeTab(tab);
        	networkSwitch = new BlockNetworkSwitch().setCreativeTab(tab);
        	GameRegistry.registerBlock(reactor, "reactor");
        	GameRegistry.registerBlock(reactorController, "reactorController");
        	GameRegistry.registerBlock(aeNormalBlock, ItemBlockNormal.class, "aeNormalBlock");
        	GameRegistry.registerBlock(networkSwitch, "networkSwitch");
        	GameRegistry.registerTileEntity(TileReactorBase.class, "tileReactor");
        	GameRegistry.registerTileEntity(TileReactorController.class, "tileReactorController");
        	GameRegistry.registerTileEntity(TileEnergyAutomaticCarger.class, "tileEnergyAutomaticCharger");
        	GameRegistry.registerTileEntity(TileNetworkSwitch.class, "tileNetworkSwitch");
        	GameRegistry.registerItem(partItem, "partItem");
        	GameRegistry.registerItem(energyCell, "energyCell");
        	
        	ItemStack controler = AEApi.instance().blocks().blockController.stack(1);
        	GameRegistry.addShapelessRecipe(new ItemStack(networkSwitch), controler, AEApi.instance().materials().materialLogicProcessor.stack(1));
        	
    	}
    	
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
		MinecraftForge.EVENT_BUS.register(new HurtEvent());
	}
}