package DrummerMC.Extra_Stuff.Item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ChestHolder extends Item {
	public ChestHolder(){
		this.setUnlocalizedName("extrastuff.chestholder");
	}
	
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
		if(world.isRemote)
			return false;
		if(stack.hasTagCompound()){
			if(stack.getTagCompound().hasKey("chest")){
				NBTTagCompound tag = stack.getTagCompound().getCompoundTag("chest");
				ForgeDirection dir = ForgeDirection.getOrientation(side);
				world.setBlock(x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ, Blocks.chest);
				TileEntity t = world.getTileEntity(x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ);
				NBTTagCompound nbt = new NBTTagCompound();
				t.writeToNBT(nbt);
				tag.setString("id", nbt.getString("id"));
				tag.setInteger("x", nbt.getInteger("x"));
				tag.setInteger("y", nbt.getInteger("y"));
				tag.setInteger("z", nbt.getInteger("z"));
				t.readFromNBT(tag);
				stack.setTagCompound(new NBTTagCompound());
				return true;
				
			}
		}
		Block b = world.getBlock(x, y, z);
		if(b == Blocks.chest){
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile != null){
				if(tile instanceof TileEntityChest){
					NBTTagCompound tag = new NBTTagCompound();
					tile.writeToNBT(tag);
					stack.setTagCompound(new NBTTagCompound());
					stack.getTagCompound().setTag("chest", tag);
					NBTTagCompound nbt = new NBTTagCompound();
					nbt.setString("id", tag.getString("id"));
					nbt.setInteger("x", tag.getInteger("x"));
					nbt.setInteger("y", tag.getInteger("y"));
					nbt.setInteger("z", tag.getInteger("z"));
					tile.readFromNBT(nbt);
					world.setBlockToAir(x, y, z);
					return true;
				}
			}
		}
        return false;
    }

}
