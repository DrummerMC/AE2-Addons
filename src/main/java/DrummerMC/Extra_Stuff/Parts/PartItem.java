package DrummerMC.Extra_Stuff.Parts;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import DrummerMC.Extra_Stuff.Extra_Stuff;
import appeng.api.AEApi;
import appeng.api.parts.IPart;
import appeng.api.parts.IPartItem;

public class PartItem extends Item implements IPartItem {
	
	@SideOnly(Side.CLIENT)
	IIcon exportinterface;
	
	public PartItem(){
		this.setUnlocalizedName("ae2addons.part");
	}

	@Override
	public IPart createPartFromItemStack(ItemStack stack) {
		if(stack.getItemDamage() == 0)
			return new PartBase();
		if(stack.getItemDamage() == 2){
			return new PartExInterface();
		}
			
			
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
	
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 2));
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1)
    {
		if(par1 == 2)
			return this.exportinterface;
        return this.itemIcon;
    }
	
	public int getMetadata(int par1)
    {
        return par1;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        this.exportinterface = register.registerIcon("ae2addons:exportinterface");
    }
	
	@Override
	public String getUnlocalizedName(ItemStack stack){
		if(stack.getItemDamage() == 2)
			return "part.ae2addons.exportinterface";
		return super.getUnlocalizedName(stack);
	}

}
