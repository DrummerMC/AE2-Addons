package DrummerMC.AE2_Addons.network;

import DrummerMC.AE2_Addons.Container.ContainerReactorBase;
import DrummerMC.AE2_Addons.Gui.GuiReactorBase;
import DrummerMC.AE2_Addons.Tile.Reactor.TileReactorBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return new ContainerReactorBase(player.inventory, (TileReactorBase) world.getTileEntity(x, y, z));
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return new GuiReactorBase(new ContainerReactorBase(player.inventory, (TileReactorBase) world.getTileEntity(x, y, z)));
	}

}