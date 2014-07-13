package DrummerMC.Extra_Stuff;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.Optional.Interface;
import net.minecraft.item.ItemStack;
import DrummerMC.Extra_Stuff.Api.IEnergyHandler;
import appeng.api.config.AccessRestriction;
import appeng.api.config.Actionable;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.storage.IMEInventoryHandler;
import appeng.api.storage.StorageChannel;
import appeng.api.storage.data.IAEStack;
import appeng.api.storage.data.IItemList;

@Optional.InterfaceList(value = { @Interface(iface = "appeng.api.storage.IMEInventoryHandler", modid = "appliedenergistics2"),
		@Interface(iface = "DrummerMC.Extra_Stuff.Api.IEnergyHandler", modid = "appliedenergistics2")})
public class MEInventoryHandlerEnergy implements IMEInventoryHandler, IEnergyHandler{
	
	public final ItemStack stack;
	
	public MEInventoryHandlerEnergy(ItemStack stack) {
		this.stack = stack;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public IAEStack injectItems(IAEStack input, Actionable type,
			BaseActionSource src) {

		return input;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public IAEStack extractItems(IAEStack request, Actionable mode,
			BaseActionSource src) {
		return null;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public IItemList getAvailableItems(IItemList out) {
		return out;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public StorageChannel getChannel() {
		return StorageChannel.ITEMS;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public AccessRestriction getAccess() {
		return AccessRestriction.READ_WRITE;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public boolean isPrioritized(IAEStack input) {
		return false;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public boolean canAccept(IAEStack input) {
		return false;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public int getPriority() {
		return 0;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public int getSlot() {
		return 0;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public ItemStack getCell() {
		return this.stack;
	}

}
