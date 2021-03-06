package DrummerMC.Extra_Stuff.Tile;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.Optional.Interface;
import DrummerMC.Extra_Stuff.GridBlock.GridBlockEnergyAutomaticLoader;
import appeng.api.AEApi;
import appeng.api.config.Actionable;
import appeng.api.config.PowerMultiplier;
import appeng.api.implementations.items.IAEItemPowerStorage;
import appeng.api.networking.IGridBlock;
import appeng.api.networking.IGridCache;
import appeng.api.networking.IGridHost;
import appeng.api.networking.IGridNode;
import appeng.api.networking.energy.IEnergyGrid;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.networking.storage.IStorageGrid;
import appeng.api.networking.ticking.IGridTickable;
import appeng.api.networking.ticking.TickRateModulation;
import appeng.api.networking.ticking.TickingRequest;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IItemList;
import appeng.api.util.AECableType;
import appeng.api.util.DimensionalCoord;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList(value = { @Interface(iface = "appeng.api.networking.ticking.IGridTickable", modid = "appliedenergistics2"),
		@Interface(iface = "appeng.api.networking.IGridHost", modid = "appliedenergistics2")})
public class TileEnergyAutomaticCarger extends TileEntity implements IGridHost, IGridTickable{
	
	private boolean doWork = false;
	IAEItemStack stackToCharge = null;
	
	IGridBlock gridblock;
	IGridNode node = null;
	public TileEnergyAutomaticCarger(){
		gridblock = new GridBlockEnergyAutomaticLoader(this);
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public IGridNode getGridNode(ForgeDirection dir) {
		if(this.worldObj.isRemote)
			return null;
		if(node == null){
			node = AEApi.instance().createGridNode(gridblock);
			node.updateState();
		}
		return node;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public AECableType getCableConnectionType(ForgeDirection dir) {
		return AECableType.SMART;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public void securityBreak() {}

	@Optional.Method(modid = "appliedenergistics2")
	public DimensionalCoord getLocation() {
		return new DimensionalCoord(this);
	}

	public ItemStack getItemStack() {
		return new ItemStack(this.getWorldObj().getBlock(xCoord, yCoord, zCoord),this.getBlockMetadata());
	}
	
	public void tick(){
		if(this.getWorldObj().isRemote)
			return;
		if(this.doWork){
			if(this.getGridNode(ForgeDirection.UNKNOWN) == null)
				return;
			if(this.getGridNode(ForgeDirection.UNKNOWN).getGrid() == null)
				return;
			IStorageGrid g = this.getGridNode(ForgeDirection.UNKNOWN).getGrid().getCache(IStorageGrid.class);
			IEnergyGrid gEnergy = this.getGridNode(ForgeDirection.UNKNOWN).getGrid().getCache(IEnergyGrid.class);
			if(g == null || gEnergy == null || this.stackToCharge == null)
				return;
			this.stackToCharge.setStackSize(1L);
			try{
				if(g.getItemInventory().extractItems(this.stackToCharge, Actionable.MODULATE, new BaseActionSource(){
					public boolean isMachine()
					{
						return true;
					}
				}).getStackSize() == 1){
					double power = 0;
					if((power = gEnergy.extractAEPower(400D, Actionable.MODULATE, PowerMultiplier.ONE))>0){
						ItemStack stack = this.stackToCharge.getItemStack();
						stack.stackSize = 1;
						if(!(0D<((IAEItemPowerStorage) this.stackToCharge.getItem()).injectAEPower(stack, 400D))){
							this.stackToCharge = AEApi.instance().storage().createItemStack(stack);
							g.getItemInventory().injectItems(this.stackToCharge.copy(), Actionable.MODULATE, new BaseActionSource(){
								public boolean isMachine()
								{
									return true;
								}
							});
						}else{
							this.stackToCharge = AEApi.instance().storage().createItemStack(stack);
							g.getItemInventory().injectItems(this.stackToCharge.copy(), Actionable.MODULATE, new BaseActionSource(){
								public boolean isMachine()
								{
									return true;
								}
							});
							this.stackToCharge = null;
							this.doWork = false;
						}
					}
				}else{
					this.stackToCharge = null;
					this.doWork = false;
				}
			}catch (Exception e){
				this.stackToCharge = null;
				this.doWork = false;
			}
		}
		if(!doWork){
			if(this.stackToCharge == null){
				IGridNode node = this.getGridNode(ForgeDirection.UNKNOWN);
				if(node != null){
					if (node.getGrid() != null){
						
						IStorageGrid g = node.getGrid().getCache(IStorageGrid.class);
						for(IAEItemStack stack : g.getItemInventory().getStorageList()){
							Item item = stack.getItem();
							if(item instanceof IAEItemPowerStorage){
								if(((IAEItemPowerStorage) item).getAECurrentPower(stack.getItemStack())<((IAEItemPowerStorage) item).getAEMaxPower(stack.getItemStack())){
									this.stackToCharge = stack.copy();
									this.doWork = true;
									break;
								}
							}
						}
					}
				}
			}
		}
		
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public TickingRequest getTickingRequest(IGridNode node) {
		return new TickingRequest(20, 200, false, false);
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public TickRateModulation tickingRequest(IGridNode node, int TicksSinceLastCall) {
		tick();
		if(this.doWork)
			return TickRateModulation.URGENT;
		return TickRateModulation.IDLE;
	}
}