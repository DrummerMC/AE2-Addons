package DrummerMC.Extra_Stuff.Item;

import li.cil.oc.api.Network;
import li.cil.oc.api.driver.Container;
import li.cil.oc.api.driver.Slot;
import li.cil.oc.api.machine.Robot;
import li.cil.oc.api.network.*;
import li.cil.oc.api.prefab.DriverItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import appeng.api.AEApi;
import appeng.api.implementations.tiles.IWirelessAccessPoint;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridHost;
import appeng.api.networking.IGridNode;
import appeng.api.networking.storage.IStorageGrid;
import appeng.api.util.WorldCoord;

public class ItemOCUpgrade extends DriverItem {
    protected ItemOCUpgrade() {
        super(null);
    }

    // We want our item to be a card component, i.e. it can be placed into
    // computers' card slots.

    @Override
    public Slot slot(ItemStack stack) {
        return Slot.Upgrade;
    }

    @Override
    public ManagedEnvironment createEnvironment(ItemStack stack, Container container) {
        return new Environment(container);
    }

    public class Environment extends li.cil.oc.api.prefab.ManagedEnvironment {
        protected final Container container;

        public Environment(Container container) {
            this.container = container;
            node = Network.newNode(this, Visibility.Neighbors).
                    withComponent("ae_remote_link").
                    create();
        }

        @Callback
        public Object[] getItems(Context context, Arguments args) {
        	/*TileEntity tile = container.world().getTileEntity((int)container.xPosition(), (int)container.yPosition(), (int)container.zPosition());
        	if(tile instanceof Robot){
        		if(((Robot) tile).getStackInSlot(((Robot) tile).selectedSlot()) == null){
        			IGridHost securityTerminal = (IGridHost) AEApi.instance().registries().locateable().findLocateableBySerial();
        	        if (securityTerminal == null)
        	            return null;
        	        IGridNode gridNode = securityTerminal.getGridNode(ForgeDirection.UNKNOWN);
        	        if (gridNode == null)
        	            return null;
        	        IGrid grid = gridNode.getGrid();
        	        if (grid == null)
        	            return null;
        	        for (IGridNode node : grid.getMachines((Class<? extends IGridHost>) AEApi.instance().blocks().blockWireless.entity())) {
        	            IWirelessAccessPoint accessPoint = (IWirelessAccessPoint) node.getMachine();
        	            WorldCoord distance = accessPoint.getLocation().subtract((int)container.xPosition(), (int)container.yPosition(), (int)container.zPosition());
        	            int squaredDistance = distance.x * distance.x + distance.y * distance.y + distance.z * distance.z;
        	            if (squaredDistance <= accessPoint.getRange() * accessPoint.getRange()) {
        	                IStorageGrid gridCache = grid.getCache(IStorageGrid.class);
        	                if (gridCache != null) {
        	                    IMEMonitor<IAEFluidStack> fluidInventory = gridCache.getFluidInventory();
        	                    if (fluidInventory != null) {
        	                        GuiHandler.launchGui(GuiHandler.getGuiId(1), entityPlayer, fluidInventory);
        	                    }
        	                }
        	            }
        	        }
        	        return itemStack;
        		}
        	}*/
			return null;
            
        }
    }
}