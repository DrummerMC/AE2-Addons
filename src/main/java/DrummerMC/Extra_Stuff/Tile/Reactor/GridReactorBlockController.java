package DrummerMC.Extra_Stuff.Tile.Reactor;

import java.util.EnumSet;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import DrummerMC.Extra_Stuff.Block.Reactor.ReactorBase;
import DrummerMC.Extra_Stuff.Tile.Reactor.TileReactorBase;
import appeng.api.AEApi;
import appeng.api.networking.GridFlags;
import appeng.api.networking.GridNotification;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridBlock;
import appeng.api.networking.IGridHost;
import appeng.api.networking.IGridNode;
import appeng.api.networking.storage.IStorageGrid;
import appeng.api.parts.PartItemStack;
import appeng.api.util.AEColor;
import appeng.api.util.DimensionalCoord;

public class GridReactorBlockController implements IGridBlock{
	protected AEColor color;
    protected IGrid grid;
    protected int usedChannels;
    protected TileReactorController host;
    
    public GridReactorBlockController(TileReactorController _host) {
        host = _host;
    }

    @Override
    public double getIdlePowerUsage() {
        return 8D;
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
    public void onGridNotification(GridNotification notification) {}

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