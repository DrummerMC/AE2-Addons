package DrummerMC.Extra_Stuff.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import net.minecraft.entity.player.EntityPlayer;



public abstract class AbstractPacket {

    
    public abstract void writeToByteBuf(ChannelHandlerContext ctx, ByteBuf buffer);

   
    public abstract void readFromByteBuf(ChannelHandlerContext ctx, ByteBuf buffer);


    public abstract void handleClientSide(EntityPlayer player);

  
    public abstract void handleServerSide(EntityPlayer player);
}