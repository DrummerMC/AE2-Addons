package DrummerMC.AE2_Addons.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;

public class ChatPacket extends AbstractPacket {
	
	public String message;
	
	public ChatPacket(){}
	
	public ChatPacket(String message){
		this.message = message;
	}
	
	@Override
	public void writeToByteBuf(ChannelHandlerContext ctx, ByteBuf buffer) {
		ByteBufUtils.writeUTF8String(buffer, this.message);

	}

	@Override
	public void readFromByteBuf(ChannelHandlerContext ctx, ByteBuf buffer) {
		this.message = ByteBufUtils.readUTF8String(buffer);

	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		player.addChatMessage(new ChatComponentTranslation(this.message));
	}

	@Override
	public void handleServerSide(EntityPlayer player) {}

}
