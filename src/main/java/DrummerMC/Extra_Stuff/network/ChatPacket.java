package DrummerMC.Extra_Stuff.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;

public class ChatPacket extends AbstractPacket {
	
	public String message;
	
	public boolean hasnumber = false;
	
	public int number = 0;
	
	public ChatPacket(){}
	
	public ChatPacket(String message){
		this.message = message;
	}
	
	public ChatPacket(String message, int number){
		this.message = message;
		this.number = number;
		this.hasnumber = true;
	}
	
	@Override
	public void writeToByteBuf(ChannelHandlerContext ctx, ByteBuf buffer) {
		ByteBufUtils.writeUTF8String(buffer, this.message);
		ItemStack stack = new ItemStack(Blocks.diamond_block);
		stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setBoolean("a", hasnumber);
		stack.getTagCompound().setInteger("b", number);
		ByteBufUtils.writeItemStack(buffer, stack);
	}

	@Override
	public void readFromByteBuf(ChannelHandlerContext ctx, ByteBuf buffer) {
		this.message = ByteBufUtils.readUTF8String(buffer);
		ItemStack stack = ByteBufUtils.readItemStack(buffer);
		this.hasnumber = stack.getTagCompound().getBoolean("a");
		this.number = stack.getTagCompound().getInteger("b");

	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		if(hasnumber){
			player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(this.message).replaceAll("%number%", this.number+"")));
		}else
			player.addChatMessage(new ChatComponentTranslation(this.message));
	}

	@Override
	public void handleServerSide(EntityPlayer player) {}

}