package DrummerMC.Extra_Stuff.Parts;

import java.util.EnumSet;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import appeng.api.networking.GridFlags;
import appeng.api.networking.GridNotification;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridBlock;
import appeng.api.networking.IGridHost;
import appeng.api.util.AEColor;
import appeng.api.util.DimensionalCoord;

public class PartGridBlock implements IGridBlock {
	
	public final PartBase part;
	
	public PartGridBlock(PartBase part){
		this.part = part;
	}

	@Override
	public double getIdlePowerUsage() {
		return 0;
	}

	@Override
	public EnumSet<GridFlags> getFlags() {
		return EnumSet.of(GridFlags.REQUIRE_CHANNEL);
	}

	@Override
	public boolean isWorldAccessable() {
		return false;
	}

	@Override
	public DimensionalCoord getLocation() {
		return part.getLocation();
	}

	@Override
	public AEColor getGridColor() {
		return AEColor.Transparent;
	}

	@Override
	public void onGridNotification(GridNotification notification) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setNetworkStatus(IGrid grid, int channelsInUse) {
		// TODO Auto-generated method stub

	}

	@Override
	public EnumSet<ForgeDirection> getConnectableSides() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IGridHost getMachine() {
		// TODO Auto-generated method stub
		return part;
	}

	@Override
	public void gridChanged() {
		// TODO Auto-generated method stub

	}

	@Override
	public ItemStack getMachineRepresentation() {
		// TODO Auto-generated method stub
		return null;
	}

}
