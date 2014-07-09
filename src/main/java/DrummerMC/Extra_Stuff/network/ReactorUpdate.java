package DrummerMC.Extra_Stuff.network;

import DrummerMC.Extra_Stuff.Block.Reactor.ReactorMultiblockController;
import DrummerMC.Extra_Stuff.Container.ContainerReactorBase;
import DrummerMC.Extra_Stuff.Tile.Reactor.TileReactorBase;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ReactorUpdate extends AbstractPacket {
	
	public ReactorUpdate(){}
	
	public int x;
	public int y;
	public int z;
	public int dimID;
	public short type;
	public int buttonID = 0;
	public boolean isActivate = false;
	
	//Button Update in Reactor Multiblock Gui
	public ReactorUpdate(int x, int y, int z, int dimID, int buttonID){
		this.type = 0;
		this.x = x;
		this.y = y;
		this.z = z;
		this.dimID = dimID;
		this.buttonID = buttonID;
	}
	
	//Server to Client
	public ReactorUpdate(int x, int y, int z, int dimID, boolean isActivate){
		this.type = 1;
		this.x = x;
		this.y = y;
		this.z = z;
		this.dimID = dimID;
		this.isActivate = isActivate;
	}

	@Override
	public void writeToByteBuf(ChannelHandlerContext ctx, ByteBuf buffer) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setShort("type", type);
		tag.setInteger("x", x);
		tag.setInteger("y", y);
		tag.setInteger("z", z);
		tag.setInteger("dimID", dimID);
		tag.setInteger("buttonID", buttonID);
		tag.setBoolean("isActivate", isActivate);
		ByteBufUtils.writeTag(buffer, tag);
	}

	@Override
	public void readFromByteBuf(ChannelHandlerContext ctx, ByteBuf buffer) {
		NBTTagCompound tag = ByteBufUtils.readTag(buffer);
		this.type = tag.getShort("type");
		this.x = tag.getInteger("x");
		this.y = tag.getInteger("y");
		this.z = tag.getInteger("z");
		this.dimID = tag.getInteger("dimID");
		this.buttonID = tag.getInteger("buttonID");
		this.isActivate = tag.getBoolean("isActivate");
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		if((int)this.type == 1){
			if(player.worldObj.provider.dimensionId == this.dimID){
				TileEntity tile = player.worldObj.getTileEntity(x, y, z);
				if(player.openContainer == null)
					return;
				if(player.openContainer instanceof ContainerReactorBase){
					ContainerReactorBase con = (ContainerReactorBase) player.openContainer;
					if(con.gui != null){
						con.gui.isActivate = this.isActivate;
					}
				}
			}
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		if((int)this.type == 0){
			if(player.worldObj.provider.dimensionId == this.dimID){
				TileEntity tile = player.worldObj.getTileEntity(x, y, z);
				if(tile instanceof TileReactorBase){
					if(((TileReactorBase) tile).hasController()){
						ReactorMultiblockController c = (ReactorMultiblockController) ((TileReactorBase) tile).getController();
						c.changeIsActive();
					}
				}
			}
		}
	}

}