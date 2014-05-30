package DrummerMC.AE2_Addons.Tile;

import DrummerMC.AE2_Addons.libs.erogenousbeef.core.multiblock.MultiblockControllerBase;
import DrummerMC.AE2_Addons.libs.erogenousbeef.core.multiblock.rectangular.RectangularMultiblockTileEntityBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public abstract class TileMultiblockBase extends RectangularMultiblockTileEntityBase{
	
	private MultiblockControllerBase controller = null;
	
	public TileMultiblockBase() {
		super();
		
	}
	NBTTagCompound sData;
	public void readFromNBT(NBTTagCompound tag){
		super.readFromNBT(tag);
		if(tag.hasKey("multiblockData")) {
			this.sData = tag.getCompoundTag("multiblockData");
		}
	}

	public final void setController(MultiblockControllerBase controller){
		this.controller = controller;
		if(this.worldObj.isRemote){
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			
		}else{
			if(this.sData != null && this.hasController()){
				this.controller.readFromNBT(this.sData);
			}else{
			}
		}
	}
	
	public final boolean hasController(){
		return (this.controller != null);
	}
	
	public final MultiblockControllerBase getController(){
		return this.controller;
	}
	
	@Override
	protected void encodeDescriptionPacket(NBTTagCompound packetData) {
		super.encodeDescriptionPacket(packetData);
	}
	
	@Override
	protected void decodeDescriptionPacket(NBTTagCompound packetData) {
		super.decodeDescriptionPacket(packetData);
	}

	@Override
	public abstract MultiblockControllerBase createNewMultiblock();

	///// Game logic methods. In a real game, do real stuff here.
	
	@Override
	public void isGoodForFrame() {}

	@Override
	public void isGoodForSides() {}

	@Override
	public void isGoodForTop() {}

	@Override
	public void isGoodForBottom() {}

	@Override
	public void isGoodForInterior() {}

	@Override
	public void onMachineAssembled(MultiblockControllerBase multiblockControllerBase) {}
	
	@Override
	public void onMachineBroken() {}

	@Override
	public void onMachineActivated() {}

	@Override
	public void onMachineDeactivated() {}

	@Override
	public abstract Class<? extends MultiblockControllerBase> getMultiblockControllerType();

	@Override
	public void onOrphaned(MultiblockControllerBase oldController,
			int oldControllerSize, int newControllerSize) {
	}
}