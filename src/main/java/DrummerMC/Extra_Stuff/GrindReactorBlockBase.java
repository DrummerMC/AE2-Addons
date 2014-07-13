package DrummerMC.Extra_Stuff;

import java.util.EnumSet;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.Optional.Interface;
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

@Optional.InterfaceList(value = { @Interface(iface = "appeng.api.networking.IGridBlock", modid = "appliedenergistics2")})
public class GrindReactorBlockBase implements IGridBlock{
	protected AEColor color;
    protected IGrid grid;
    protected int usedChannels;
    protected TileReactorBase host;

    public GrindReactorBlockBase(TileReactorBase _host) {
    	
        host = _host;
    }

    @Optional.Method(modid = "appliedenergistics2")
    @Override
    public double getIdlePowerUsage() {
        return 0D;
    }

    @Optional.Method(modid = "appliedenergistics2")
    @Override
    public EnumSet<GridFlags> getFlags() {
        return EnumSet.of(GridFlags.TIER_2_CAPACITY);
    }

    @Optional.Method(modid = "appliedenergistics2")
    @Override
    public final boolean isWorldAccessable() {
        return true;
    }

    @Optional.Method(modid = "appliedenergistics2")
    @Override
    public final DimensionalCoord getLocation() {
        return host.getLocation();
    }

    @Optional.Method(modid = "appliedenergistics2")
    @Override
    public final AEColor getGridColor() {
        return AEColor.Transparent;
    }

    @Optional.Method(modid = "appliedenergistics2")
    @Override
    public void onGridNotification(GridNotification notification) {
    }

    @Optional.Method(modid = "appliedenergistics2")
    @Override
    public final void setNetworkStatus(IGrid _grid, int _usedChannels) {
        grid = _grid;
        usedChannels = _usedChannels;
    }

    @Optional.Method(modid = "appliedenergistics2")
    @Override
    public final EnumSet<ForgeDirection> getConnectableSides() {
        return EnumSet.of(ForgeDirection.DOWN,ForgeDirection.UP,ForgeDirection.NORTH,ForgeDirection.EAST,ForgeDirection.SOUTH,ForgeDirection.WEST);
    }

    @Optional.Method(modid = "appliedenergistics2")
    @Override
    public IGridHost getMachine() {
        return host;
    }

    @Optional.Method(modid = "appliedenergistics2")
    @Override
    public void gridChanged() {
    	
    }

    @Optional.Method(modid = "appliedenergistics2")
    @Override
    public ItemStack getMachineRepresentation() {
        return host.getItemStack();
    }

    
}