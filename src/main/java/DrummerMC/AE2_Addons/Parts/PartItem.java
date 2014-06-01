package DrummerMC.AE2_Addons.Parts;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import DrummerMC.AE2_Addons.AE2_Addons;
import appeng.api.AEApi;
import appeng.api.parts.IPart;
import appeng.api.parts.IPartItem;

public class PartItem extends Item implements IPartItem {
	
	public PartItem(){
		this.setUnlocalizedName("ae2addons.part");
	}

	@Override
	public IPart createPartFromItemStack(ItemStack stack) {
		if(stack.getItemDamage() == 0)
			return new PartBase();
		return null;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getSpriteNumber()
	{
		return 0;
	}
	
	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		return AEApi.instance().partHelper().placeBus( is, x, y, z, side, player, world);
	}

}
