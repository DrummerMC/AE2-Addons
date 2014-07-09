package DrummerMC.Extra_Stuff.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import DrummerMC.Extra_Stuff.Extra_Stuff;
import DrummerMC.Extra_Stuff.Block.Reactor.BlockReactorController;
import DrummerMC.Extra_Stuff.Block.Reactor.ReactorBase;
import DrummerMC.Extra_Stuff.Tile.Reactor.TileReactorController;
import DrummerMC.Extra_Stuff.libs.erogenousbeef.core.multiblock.MultiblockClientTickHandler;
import DrummerMC.Extra_Stuff.renderer.block.BlockRendererReactor;
import DrummerMC.Extra_Stuff.renderer.block.BlockRendererReactorController;
import DrummerMC.Extra_Stuff.renderer.item.ItemRendererReactor;
import DrummerMC.Extra_Stuff.renderer.item.ItemRendererReactorController;
import DrummerMC.Extra_Stuff.renderer.tile.TileRendererReactorController;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ClientProxy extends CommonProxy{
	
	@Override
	public void init(){
		super.init();
		FMLCommonHandler.instance().bus().register(new MultiblockClientTickHandler());
	}
	
	@Override
	public void registerRenderers(){
		if(Loader.isModLoaded("appliedenergistics2")){
			//Items
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Extra_Stuff.reactor), new ItemRendererReactor());
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Extra_Stuff.reactorController), new ItemRendererReactorController());
			
			//Blocks
			ReactorBase.renderID = RenderingRegistry.getNextAvailableRenderId();
			RenderingRegistry.registerBlockHandler(new BlockRendererReactor(ReactorBase.renderID));
			BlockReactorController.renderID = RenderingRegistry.getNextAvailableRenderId();
			RenderingRegistry.registerBlockHandler(new BlockRendererReactorController(BlockReactorController.renderID));
			
			//TileEntity
			ClientRegistry.bindTileEntitySpecialRenderer(TileReactorController.class, new TileRendererReactorController());
		}
		
	}

}