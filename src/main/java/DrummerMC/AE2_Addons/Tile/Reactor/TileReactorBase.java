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
import DrummerMC.AE2_Addons.Block.Reactor.ReactorMultiblockController;
import DrummerMC.AE2_Addons.Tile.TileMultiblockBase;
import DrummerMC.AE2_Addons.libs.erogenousbeef.core.multiblock.MultiblockControllerBase;

public class TileReactorBase extends TileMultiblockBase implements IAEPowerStorage, IGridHost{
	
	public IGridBlock gridBlock;
	public IGridNode node = null;
	
	public TileReactorBase(){
		gridBlock = new GrindReactorBlockBase(this);
		
		
	}
	
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
			if(((ReactorMultiblockController)this.getController()).energy>=amt){
				return amt;
			}else{
				return ((ReactorMultiblockController)this.getController()).energy;
			}
		}else{
			if(((ReactorMultiblockController)this.getController()).energy>=amt){
				((ReactorMultiblockController)this.getController()).energy = ((ReactorMultiblockController)this.getController()).energy -amt;
				return amt;
			}else{
				double oldEnergy = ((ReactorMultiblockController)this.getController()).energy+0D;
				((ReactorMultiblockController)this.getController()).energy = 0D;
				return oldEnergy;
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
		return AccessRestriction.READ;
	}

	@Override
	public IGridNode getGridNode(ForgeDirection dir) {
		if(this.node == null){
			if(!this.hasController())
				return null;
			else if(!this.getController().isAssembled())
				return null;
			if(!this.worldObj.isRemote){
				node = AEApi.instance().createGridNode(gridBlock);
				node.updateState();
			}
		}
		return this.node;
	}

	@Override
	public AECableType getCableConnectionType(ForgeDirection dir) {
		return AECableType.DENSE;
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
