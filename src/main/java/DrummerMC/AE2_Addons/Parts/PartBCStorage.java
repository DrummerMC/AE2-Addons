package DrummerMC.AE2_Addons.Parts;

import net.minecraft.item.ItemStack;
import DrummerMC.AE2_Addons.AE2_Addons;
import appeng.api.parts.PartItemStack;

public class PartBCStorage extends PartBase {
	
	@Override
	public ItemStack getItemStack(PartItemStack type) {
		// TODO Auto-generated method stub
		return new ItemStack(AE2_Addons.partItem, 1, 1);
	}

}
