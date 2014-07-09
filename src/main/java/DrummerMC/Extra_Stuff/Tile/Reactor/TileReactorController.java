package DrummerMC.Extra_Stuff.Tile.Reactor;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import DrummerMC.Extra_Stuff.AE2_Addons;
import appeng.api.AEApi;
import appeng.api.networking.IGridHost;
import appeng.api.networking.IGridNode;
import appeng.api.util.AECableType;
import appeng.api.util.DimensionalCoord;

public class TileReactorController extends TileEntity implements IGridHost {
	
	IGridNode node =null;
	//The Direction of the Block
	public int dir = 0;
	//Internal only for Ticks
	private int last  = 0;
	//Amount of connected Reactors
	public int conReactor  = 0;
	final GridReactorBlockController grid;
	
	public TileReactorController(){
		this.grid = new GridReactorBlockController(this);
	}
	
	@Override
	public IGridNode getGridNode(ForgeDirection dir) {
		if(this.worldObj.isRemote)
			return null;
		if(node == null){
			node = AEApi.instance().createGridNode(grid);
			node.updateState();
		}
		return node;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag){
		super.writeToNBT(tag);
		tag.setInteger("dir", dir);
		tag.setInteger("conReactors", conReactor);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag){
		super.readFromNBT(tag);
		this.dir = tag.getInteger("dir");
		if(tag.hasKey("conReactors")){
			this.conReactor = tag.getInteger("conReactors");
		}
	}

	@Override
	public AECableType getCableConnectionType(ForgeDirection dir) {
		return AECableType.SMART;
	}

	@Override
	public void securityBreak() {}

	public DimensionalCoord getLocation() {
		return new DimensionalCoord(this);
	}

	public ItemStack getItemStack() {
		return new ItemStack(AE2_Addons.reactorController);
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound packetData = new NBTTagCompound();
		this.writeToNBT(packetData);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, packetData);
	}
	
	@Override
	public void onDataPacket(NetworkManager network, S35PacketUpdateTileEntity packet) {
		this.readFromNBT(packet.func_148857_g());
	}
	
	@Override
	public void updateEntity()
    {
		if(this.worldObj.isRemote)
			return;
		if(last>=200){
			last = 0;
			int newcon = this.getGridNode(ForgeDirection.UNKNOWN).getGrid().getMachines(TileReactorBase.class).size()/27;
			if(newcon != this.conReactor){
				this.conReactor = newcon;
				this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}else{
			last = last +1;
		}
    }
}