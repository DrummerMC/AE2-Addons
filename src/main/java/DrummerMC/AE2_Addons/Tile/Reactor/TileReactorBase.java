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
import DrummerMC.AE2_Addons.Tile.TileMultiblockBase;
import DrummerMC.AE2_Addons.network.ReactorMultiblockUpdate;

public class TileReactorBase extends TileMultiblockBase implements IAEPowerStorage, IGridHost{
	
	public IGridBlock gridBlock;
	public IGridNode node = null;
	
	public TileReactorBase(){
		gridBlock = new GrindReactorBlockBase(this);
		
		
	}
	
	protected double energy = 0D;
	
	@Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.worldObj.isRemote) {
            if (hasMaster()) {
                if (isMaster()) {
                    if (checkMultiBlockForm()) {
 
                    } else
                        resetStructure();
                } else {
                    if (checkForMaster())
                        reset();
                }
            } else {
                if (checkMultiBlockForm())
                    setupStructure();
            }
        }
    }
     
    public boolean checkMultiBlockForm() {
        int i = 0;
        for (int x = xCoord - 1; x < xCoord + 2; x++)
            for (int y = yCoord - 1; y < yCoord + 2; y++)
                for (int z = zCoord - 1; z < zCoord + 2; z++) {
                    TileEntity tile = worldObj.getTileEntity(x, y, z);
                    if (tile != null && (tile instanceof TileReactorBase) && !((TileReactorBase)tile).hasMaster())
                        i++;
                }
        
        return i > 26;
    }
  
    public void setupStructure() {
        for (int x = xCoord - 1; x < xCoord + 2; x++)
            for (int y = yCoord - 1; y < yCoord + 2; y++)
                for (int z = zCoord - 1; z < zCoord + 2; z++) {
                	TileEntity tile = worldObj.getTileEntity(x, y, z);
                    boolean master = (x == xCoord && y == yCoord && z == zCoord);
                    if (tile != null && (tile instanceof TileReactorBase)) {
                        ((TileReactorBase) tile).setMasterCoords(xCoord, yCoord, zCoord);
                        ((TileReactorBase) tile).setHasMaster(true);
                        ((TileReactorBase) tile).setIsMaster(master);
                    }
                }
        if(hasMaster&&isMaster){
        	AE2_Addons.network.sendToDimension(new ReactorMultiblockUpdate(this.xCoord, this.yCoord, this.zCoord,
        			this.getWorldObj().getWorldInfo().getWorldName()),
        			this.getWorldObj().provider.dimensionId);
        }
    }
  
    public void reset() {
        masterX = 0;
        masterY = 0;
        masterZ = 0;
        hasMaster = false;
        isMaster = false;
    }
  
    public boolean checkForMaster() {
        TileEntity tile = worldObj.getTileEntity(masterX, masterY, masterZ);
        return (tile != null && (tile instanceof TileReactorBase));
    }
     
    public void resetStructure() {
        for (int x = xCoord - 1; x < xCoord + 2; x++)
            for (int y = yCoord - 1; y < yCoord + 2; y++)
                for (int z = zCoord - 1; z < zCoord + 2; z++) {
                	TileEntity tile = worldObj.getTileEntity(x, y, z);
                    if (tile != null && (tile instanceof TileReactorBase))
                        ((TileReactorBase) tile).reset();
                }
    }
  
    @Override
    public void writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        if (hasMaster() && isMaster()) {
        	data.setDouble("energy", this.energy);
        }
    }
 
    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        if (hasMaster() && isMaster()) {
        	if(data.hasKey("energy"))
        		this.energy = data.getDouble("energy");
        }
    }
  
    public boolean hasMaster() {
        return hasMaster;
    }
  
    public boolean isMaster() {
        return isMaster;
    }
  
    public int getMasterX() {
        return masterX;
    }
  
    public int getMasterY() {
        return masterY;
    }
  
    public int getMasterZ() {
        return masterZ;
    }
  
    public void setHasMaster(boolean bool) {
        hasMaster = bool;
    }
  
    public void setIsMaster(boolean bool) {
        isMaster = bool;
    }
  
    public void setMasterCoords(int x, int y, int z) {
        masterX = x;
        masterY = y;
        masterZ = z;
    }

	@Override
	public double extractAEPower(double amt, Actionable mode,
			PowerMultiplier usePowerMultiplier) {
		if(!(this.hasMaster&&this.isMaster))
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
		return this.energy;
	}

	@Override
	public boolean isAEPublicPowerStorage() {
		return true;
	}

	@Override
	public AccessRestriction getPowerFlow() {
		if(this.hasMaster&&this.isMaster){
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
}
