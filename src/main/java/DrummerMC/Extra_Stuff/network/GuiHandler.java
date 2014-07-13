package DrummerMC.Extra_Stuff.network;

import DrummerMC.Extra_Stuff.Container.ContainerReactorBase;
import DrummerMC.Extra_Stuff.Container.ContainerSolarFurnace;
import DrummerMC.Extra_Stuff.Gui.GuiReactorBase;
import DrummerMC.Extra_Stuff.Gui.GuiSolarFurnace;
import DrummerMC.Extra_Stuff.Tile.Reactor.TileReactorBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		try{
			if(ID == 1)
				return new ContainerSolarFurnace(player.inventory, (TileEntityFurnace) world.getTileEntity(x, y, z));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return new ContainerReactorBase(player.inventory, (TileReactorBase) world.getTileEntity(x, y, z));
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		try{
			if(ID == 1)
				return new GuiSolarFurnace(player.inventory, (TileEntityFurnace) world.getTileEntity(x, y, z));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return new GuiReactorBase(new ContainerReactorBase(player.inventory, (TileReactorBase) world.getTileEntity(x, y, z)));
	}

}