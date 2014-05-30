package DrummerMC.AE2_Addons.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import DrummerMC.AE2_Addons.AE2_Addons;
import DrummerMC.AE2_Addons.Block.Reactor.BlockReactorController;
import DrummerMC.AE2_Addons.Block.Reactor.ReactorBase;
import DrummerMC.AE2_Addons.Tile.Reactor.TileReactorController;
import DrummerMC.AE2_Addons.libs.erogenousbeef.core.multiblock.MultiblockClientTickHandler;
import DrummerMC.AE2_Addons.renderer.block.BlockRendererReactor;
import DrummerMC.AE2_Addons.renderer.block.BlockRendererReactorController;
import DrummerMC.AE2_Addons.renderer.item.ItemRendererReactor;
import DrummerMC.AE2_Addons.renderer.item.ItemRendererReactorController;
import DrummerMC.AE2_Addons.renderer.tile.TileRendererReactorController;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ClientProxy extends CommonProxy{
	
	@Override
	public void init(){
		super.init();
		FMLCommonHandler.instance().bus().register(new MultiblockClientTickHandler());
	}
	
	@Override
	public void registerRenderers(){
		//Items
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AE2_Addons.reactor), new ItemRendererReactor());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AE2_Addons.reactorController), new ItemRendererReactorController());
		
		//Blocks
		ReactorBase.renderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockRendererReactor(ReactorBase.renderID));
		BlockReactorController.renderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockRendererReactorController(BlockReactorController.renderID));
		
		//TileEntity
		ClientRegistry.bindTileEntitySpecialRenderer(TileReactorController.class, new TileRendererReactorController());
	}

}