package DrummerMC.Extra_Stuff.Container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import DrummerMC.Extra_Stuff.Gui.GuiReactorBase;
import DrummerMC.Extra_Stuff.Tile.Reactor.TileReactorBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerReactorBase extends Container{
	
	public TileReactorBase tile;
	
	@SideOnly(Side.CLIENT)
	public GuiReactorBase gui = null;
	
    public ContainerReactorBase (InventoryPlayer inventoryPlayer, TileReactorBase tile){
            this.tile = tile;

            addSlotToContainer(new Slot((IInventory) tile.getController(), 0, 20, 21));
            
            for (int i = 0; i < 3; i++) {
            	for (int j = 0; j < 9; j++) {
            		addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
            				8 + j * 18, 84 + i * 18));
            	}
            }

            for (int i = 0; i < 9; i++) {
            	addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
            }
            
    }
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
            ItemStack stack = null;
            Slot slotObject = (Slot) inventorySlots.get(slot);
            
            if (slotObject != null && slotObject.getHasStack()) {
                    ItemStack stackInSlot = slotObject.getStack();
                    stack = stackInSlot.copy();

                    if (slot < 9) {
                            if (!this.mergeItemStack(stackInSlot, 0, 35, true)) {
                                    return null;
                            }
                    }
                    else if (!this.mergeItemStack(stackInSlot, 0, 9, false)) {
                            return null;
                    }

                    if (stackInSlot.stackSize == 0) {
                            slotObject.putStack(null);
                    } else {
                            slotObject.onSlotChanged();
                    }

                    if (stackInSlot.stackSize == stack.stackSize) {
                            return null;
                    }
                    slotObject.onPickupFromSlot(player, stackInSlot);
            }
            return stack;
    }

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}
}