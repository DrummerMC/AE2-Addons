package DrummerMC.Extra_Stuff.Parts;

import java.util.EnumSet;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.Optional.Interface;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import appeng.api.networking.GridFlags;
import appeng.api.networking.GridNotification;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridBlock;
import appeng.api.networking.IGridHost;
import appeng.api.util.AEColor;
import appeng.api.util.DimensionalCoord;

@Optional.InterfaceList(value = { @Interface(iface = "appeng.api.networking.IGridBlock", modid = "appliedenergistics2")})
public class PartGridBlock implements IGridBlock {
	
	public final PartBase part;
	
	public PartGridBlock(PartBase part){
		this.part = part;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public double getIdlePowerUsage() {
		return 0;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public EnumSet<GridFlags> getFlags() {
		return EnumSet.of(GridFlags.REQUIRE_CHANNEL);
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public boolean isWorldAccessable() {
		return false;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public DimensionalCoord getLocation() {
		return part.getLocation();
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public AEColor getGridColor() {
		return AEColor.Transparent;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public void onGridNotification(GridNotification notification) {
		// TODO Auto-generated method stub

	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public void setNetworkStatus(IGrid grid, int channelsInUse) {
		// TODO Auto-generated method stub

	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public EnumSet<ForgeDirection> getConnectableSides() {
		// TODO Auto-generated method stub
		return null;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public IGridHost getMachine() {
		// TODO Auto-generated method stub
		return part;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public void gridChanged() {
		// TODO Auto-generated method stub

	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public ItemStack getMachineRepresentation() {
		// TODO Auto-generated method stub
		return null;
	}

}
