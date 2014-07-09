package DrummerMC.AE2_Addons.GridBlock;

import java.util.EnumSet;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import DrummerMC.AE2_Addons.Tile.TileEnergyAutomaticCarger;
import DrummerMC.AE2_Addons.Tile.Reactor.TileReactorBase;
import appeng.api.networking.GridFlags;
import appeng.api.networking.GridNotification;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridBlock;
import appeng.api.networking.IGridHost;
import appeng.api.util.AEColor;
import appeng.api.util.DimensionalCoord;

public class GridBlockEnergyAutomaticLoader implements IGridBlock {
    protected IGrid grid;
    protected int usedChannels;
    protected TileEnergyAutomaticCarger host;

    public GridBlockEnergyAutomaticLoader(TileEnergyAutomaticCarger host) {
    	
        this.host = host;
    }

    @Override
    public double getIdlePowerUsage() {
        return 0D;
    }

    @Override
    public EnumSet<GridFlags> getFlags() {
        return EnumSet.of(GridFlags.REQUIRE_CHANNEL);
    }

    @Override
    public final boolean isWorldAccessable() {
        return true;
    }

    @Override
    public final DimensionalCoord getLocation() {
        return host.getLocation();
    }

    @Override
    public final AEColor getGridColor() {
        return AEColor.Transparent;
    }

    @Override
    public void onGridNotification(GridNotification notification) {
    }

    @Override
    public final void setNetworkStatus(IGrid _grid, int _usedChannels) {
        grid = _grid;
        usedChannels = _usedChannels;
    }

    @Override
    public final EnumSet<ForgeDirection> getConnectableSides() {
        return EnumSet.of(ForgeDirection.DOWN,ForgeDirection.UP,ForgeDirection.NORTH,ForgeDirection.EAST,ForgeDirection.SOUTH,ForgeDirection.WEST);
    }

    @Override
    public IGridHost getMachine() {
        return host;
    }

    @Override
    public void gridChanged() {}

    @Override
    public ItemStack getMachineRepresentation() {
        return host.getItemStack();
    }
}