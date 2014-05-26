package DrummerMC.AE2_Addons.Tile.Reactor;

import appeng.api.AEApi;
import appeng.api.config.AccessRestriction;
import appeng.api.config.Actionable;
import appeng.api.config.PowerMultiplier;
import appeng.api.networking.IGridBlock;
import appeng.api.networking.IGridHost;
import appeng.api.networking.IGridNode;
import appeng.api.networking.energy.IAEPowerStorage;
import appeng.api.util.AECableType;
import appeng.api.util.DimensionalCoord;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import DrummerMC.AE2_Addons.AE2_Addons;
import DrummerMC.AE2_Addons.GrindReactorBlockBase;
import DrummerMC.AE2_Addons.Block.ReactorMultiblockController;
import DrummerMC.AE2_Addons.Tile.TileMultiblockBase;
import DrummerMC.AE2_Addons.libs.erogenousbeef.core.multiblock.MultiblockControllerBase;
import DrummerMC.AE2_Addons.network.ReactorMultiblockUpdate;

public class TileReactorBase extends TileMultiblockBase implements IAEPowerStorage, IGridHost{
	
	public IGridBlock gridBlock;
	public IGridNode node = null;
	
	public TileReactorBase(){
		gridBlock = new GrindReactorBlockBase(this);
		
		
	}
	
	protected double energy = 0D;
	
	
   
  
    @Override
    public void writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        
    }
 
    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
      
    }
  
   

	@Override
	public double extractAEPower(double amt, Actionable mode,
			PowerMultiplier usePowerMultiplier) {
		if(!(this.hasController()))
			return 0;
		if(mode == Actionable.SIMULATE){
			if(this.energy>=amt){
				return amt;
			}else{
				return this.energy;
			}
		}else{
			if(this.energy>=amt){
				this.energy = this.energy -amt;
				return amt;
			}else{
				this.energy = 0D;
				return this.energy;
			}
		}
	}

	@Override
	public double injectAEPower(double amt, Actionable mode) {
		return 0;
	}

	@Override
	public double getAEMaxPower() {
		return 16000;
	}

	@Override
	public double getAECurrentPower() {
		if(this.hasController()){
			return (((ReactorMultiblockController)this.getController()).energy/this.getController().getNumConnectedBlocks());
		}else{
			return 0D;
		}
	}

	@Override
	public boolean isAEPublicPowerStorage() {
		return true;
	}

	@Override
	public AccessRestriction getPowerFlow() {
		if(this.hasController()){
			return AccessRestriction.READ;
		}
		return AccessRestriction.NO_ACCESS;
	}

	@Override
	public IGridNode getGridNode(ForgeDirection dir) {
		if(this.node == null){
			if(!this.worldObj.isRemote){
				node = AEApi.instance().createGridNode(gridBlock);
				node.updateState();
			}
		}
		return this.node;
	}

	@Override
	public AECableType getCableConnectionType(ForgeDirection dir) {
		return AECableType.COVERED;
	}

	@Override
	public void securityBreak() {
		
	}

	public DimensionalCoord getLocation() {
		return new DimensionalCoord(this);
	}

	public ItemStack getItemStack() {
		return new ItemStack(AE2_Addons.reactor);
	}

	@Override
	public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
		return ReactorMultiblockController.class;
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new ReactorMultiblockController(this.worldObj);
	}
}
