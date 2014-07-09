package DrummerMC.AE2_Addons.Item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockNormal extends ItemBlock {

	public ItemBlockNormal(Block p_i45328_1_) {
		super(p_i45328_1_);
	}
	
	public String getUnlocalizedName(ItemStack stack)
    {
		if(stack.getItemDamage() == 0){
			return "tile.ae2addons.carger";
		}
		
		return this.field_150939_a.getUnlocalizedName();
    }

}
