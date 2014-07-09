package DrummerMC.Extra_Stuff.Parts;

import net.minecraft.item.ItemStack;
import DrummerMC.Extra_Stuff.AE2_Addons;
import appeng.api.parts.PartItemStack;

public class PartBCStorage extends PartBase {
	
	@Override
	public ItemStack getItemStack(PartItemStack type) {
		return new ItemStack(AE2_Addons.partItem, 1, 1);
	}

}