package DrummerMC.AE2_Addons.network;

import DrummerMC.AE2_Addons.Tile.Reactor.TileReactorBase;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ReactorMultiblockUpdate extends AbstractPacket {
	
	public ReactorMultiblockUpdate(){}
	
	public int x;
	public int y;
	public int z;
	public String worldName;
	
	public ReactorMultiblockUpdate(int x, int y, int z, String worldName){
		this.x = x;
		this.y = y;
		this.z = z;
		this.worldName = worldName;
	}

	@Override
	public void writeToByteBuf(ChannelHandlerContext ctx, ByteBuf buffer) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("x", x);
		tag.setInteger("y", y);
		tag.setInteger("z", z);
		tag.setString("worldName", worldName);
		ByteBufUtils.writeTag(buffer, tag);
		

	}

	@Override
	public void readFromByteBuf(ChannelHandlerContext ctx, ByteBuf buffer) {
		NBTTagCompound tag = ByteBufUtils.readTag(buffer);
		this.x = tag.getInteger("x");
		this.y = tag.getInteger("y");
		this.z = tag.getInteger("z");
		this.worldName = tag.getString("worldName");

	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		
	}

	@Override
	public void handleServerSide(EntityPlayer player) {}

}