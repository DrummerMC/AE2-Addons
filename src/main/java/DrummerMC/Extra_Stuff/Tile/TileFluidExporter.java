package DrummerMC.Extra_Stuff.Tile;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.Optional.Interface;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.TileFluidHandler;
import li.cil.oc.api.Network;
import li.cil.oc.api.network.Arguments;
import li.cil.oc.api.network.Callback;
import li.cil.oc.api.network.Context;
import li.cil.oc.api.network.Environment;
import li.cil.oc.api.network.Message;
import li.cil.oc.api.network.Node;
import li.cil.oc.api.network.Visibility;


@Optional.InterfaceList(value = { @Interface(iface = "li.cil.oc.api.network.Environment", modid = "OpenComputers")})
public class TileFluidExporter extends TileFluidHandler implements Environment {
	
	public int  b = 0;
	public int m = 0;
	
	protected Node node;

    protected boolean addedToNetwork = false;
    
    
    public TileFluidExporter(){
    	super();
    	tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 100);
    	node = Network.newNode(this, Visibility.Network).
    			withConnector().
    			withComponent("fluidExporter").
    			create();
    }

    @Optional.Method(modid = "OpenComputers")
    @Override
    public Node node() {
        return node;
    }

    @Optional.Method(modid = "OpenComputers")
    @Override
    public void onConnect(final Node node) {
  
    }

    @Optional.Method(modid = "OpenComputers")
    @Override
    public void onDisconnect(final Node node) {

    }

    @Optional.Method(modid = "OpenComputers")
    @Override
    public void onMessage(final Message message) {
    	
    }


    @Override
    public void updateEntity() {
        super.updateEntity();

        if (!addedToNetwork) {
            addedToNetwork = true;
            Network.joinOrCreateNetwork(this);
        }
    }

    @Override
    public void onChunkUnload() {
        super.onChunkUnload();

        if (node != null) node.remove();
    }

    @Override
    public void invalidate() {
        super.invalidate();
        
        if (node != null) node.remove();
    }


    @Override
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        
        if(nbt.hasKey("blockIDtex"))
        	this.b = nbt.getInteger("blockIDtex");
        if(nbt.hasKey("blockMetatex"))
        	this.m = nbt.getInteger("blockMetatex");
        
        if (node != null && node.host() == this) {
           
            node.load(nbt.getCompoundTag("oc:node"));
        }
    }

    @Override
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        
        nbt.setInteger("blockIDtex", b);
        nbt.setInteger("blockMetatex", m);
        
        if (node != null && node.host() == this) {
            final NBTTagCompound nodeNbt = new NBTTagCompound();
            node.save(nodeNbt);
            nbt.setTag("oc:node", nodeNbt);
        }
    }
    
    @Callback
    public Object[] getFluid(Context context, Arguments args) {
    	int amount = args.checkInteger(0);
    	if(this.tank.getFluidAmount() !=0)
    		return new Object[]{false,0};
    	if(amount > FluidContainerRegistry.BUCKET_VOLUME * 100){
    		throw new RuntimeException("The  amount of Fluid is too large. The Maximum is: " + (FluidContainerRegistry.BUCKET_VOLUME * 100));
    	}
    	if(this.getWorldObj().getTileEntity(xCoord, yCoord+1, zCoord) instanceof IFluidHandler){
    		IFluidHandler tanktop = (IFluidHandler) this.getWorldObj().getTileEntity(xCoord, yCoord+1, zCoord);
    		if(tanktop.canDrain(ForgeDirection.DOWN, tanktop.getTankInfo(ForgeDirection.DOWN)[0].fluid.getFluid())){
    			this.tank.setFluid(tanktop.drain(ForgeDirection.DOWN, amount, true));
    			return new Object[]{true,this.tank.getCapacity()};
    		}
    	}
    	
    	
        return new Object[]{false,0};
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

}
