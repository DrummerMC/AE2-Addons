package DrummerMC.AE2_Addons.Block;

import DrummerMC.AE2_Addons.Tile.TileMultiblockBase;
import DrummerMC.AE2_Addons.Tile.Reactor.TileReactorBase;
import DrummerMC.AE2_Addons.libs.erogenousbeef.core.multiblock.IMultiblockPart;
import DrummerMC.AE2_Addons.libs.erogenousbeef.core.multiblock.MultiblockControllerBase;
import DrummerMC.AE2_Addons.libs.erogenousbeef.core.multiblock.MultiblockValidationException;
import DrummerMC.AE2_Addons.libs.erogenousbeef.core.multiblock.rectangular.RectangularMultiblockControllerBase;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;


public class ReactorMultiblockController extends RectangularMultiblockControllerBase {

	protected static int nextOrdinal = 0;
	public int ordinal;
	
	public double energy = 0;
	
	public ReactorMultiblockController(World world) {
		super(world);
		if(world.isRemote) {
			ordinal = -1;
		}
		else {
			ordinal = nextOrdinal++;
		}
	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {
	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 27;
	}
	
	@Override
	protected void onAssimilate(MultiblockControllerBase assimilated) {
		
	}

	@Override
	protected void onAssimilated(MultiblockControllerBase otherMachine) {
		
	}

	@Override
	public void detachBlock(IMultiblockPart part, boolean chunkUnloading) {
		FMLLog.info("[%s] Controller %d detaching block at %s", (worldObj.isRemote ? "CLIENT" : "SERVER"), ordinal, part.getWorldLocation());
		super.detachBlock(part, chunkUnloading);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound data) {
		data.setDouble("energy", energy);
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		if(data.hasKey("energy")){
			this.energy = data.getDouble("energy");
		}
		
	}

	@Override
	public void formatDescriptionPacket(NBTTagCompound data) {
		data.setInteger("ordinal", ordinal);
	}

	@Override
	public void decodeDescriptionPacket(NBTTagCompound data) {
		if(data.hasKey("ordinal")) {
			ordinal = data.getInteger("ordinal");
		}
	}
	
	@Override
	protected void onMachinePaused() {
		FMLLog.info("Machine %d PAUSED", hashCode());
	}
	
	@Override
	protected void onMachineAssembled() {
		for (IMultiblockPart part : this.connectedParts){
			((TileReactorBase)part).setController(this);
		}
		FMLLog.info("Machine %d ASSEMBLED", hashCode());
	}
	
	@Override
	protected void onMachineDisassembled() {
		FMLLog.info("Machine %d DISASSEMBLED", hashCode());
	}

	@Override
	protected void onMachineRestored() {
		FMLLog.info("Machine %d RESTORED", hashCode());
	}

	@Override
	protected boolean updateServer() {
		return false;
	}
	
	@Override
	protected void updateClient() {
	}

	@Override
	protected int getMaximumXSize() {
		return 3;
	}

	@Override
	protected int getMaximumZSize() {
		return 3;
	}

	@Override
	protected int getMaximumYSize() {
		return 3;
	}
	
	@Override
	protected void isBlockGoodForInterior(World world, int x, int y, int z) {
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part,
			NBTTagCompound data) {
		FMLLog.info("[%s] Controller %d assimilating save data from part @ %s", (worldObj.isRemote ? "CLIENT" : "SERVER"), ordinal, part.getWorldLocation());
		decodeDescriptionPacket(data);
	}



	
}
