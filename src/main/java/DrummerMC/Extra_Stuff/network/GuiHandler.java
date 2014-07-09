package DrummerMC.Extra_Stuff.network;

import DrummerMC.Extra_Stuff.Container.ContainerReactorBase;
import DrummerMC.Extra_Stuff.Gui.GuiReactorBase;
import DrummerMC.Extra_Stuff.Tile.Reactor.TileReactorBase;
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