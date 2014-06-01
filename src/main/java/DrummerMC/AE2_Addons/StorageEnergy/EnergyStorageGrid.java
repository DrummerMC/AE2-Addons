/**
 * 
 */
package DrummerMC.AE2_Addons.StorageEnergy;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import DrummerMC.AE2_Addons.Api.IEnergyCell;
import DrummerMC.AE2_Addons.Api.IEnergyHandler;
import DrummerMC.AE2_Addons.Api.Grid.IEnergyGrid;
import DrummerMC.AE2_Addons.Api.Grid.IEnergyStorageGrid;
import DrummerMC.AE2_Addons.Api.Util.EnergyType;
import appeng.api.AEApi;
import appeng.api.config.Actionable;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridHost;
import appeng.api.networking.IGridNode;
import appeng.api.networking.IGridStorage;
import appeng.api.storage.ICellContainer;
import appeng.api.storage.IMEInventoryHandler;
import appeng.api.storage.StorageChannel;


/**
 * @author DrummerMC
 *
 */
public class EnergyStorageGrid implements IEnergyStorageGrid {
	
	private final IGrid grid;
	private final List<StorageBase> storageMJ = new ArrayList<StorageBase>();
	private final List<StorageBase> storagaeJ = new ArrayList<StorageBase>();
	private final List<StorageBase> storageRF = new ArrayList<StorageBase>();
	private final List<IEnergyGrid> storage = new ArrayList<IEnergyGrid>();
	
	private int lastTick = 0;
	
	public EnergyStorageGrid(IGrid grid){
		this.grid = grid;
		
	}
	
	@Override
	public void onUpdateTick() {
		lastTick = lastTick + 1;
		if(lastTick > 200){
			lastTick = 0;
			try{
				for(StorageBase s : storageMJ){
					s.update(grid);
				}
			}catch(Exception e){
				
			}
		}
	}

	@Override
	public void removeNode(IGridNode gridNode, IGridHost machine) {
		if(storage.contains(machine))
			storage.remove(machine);
	}
	
	@Override
	public void addNode(IGridNode gridNode, IGridHost machine) {
		if(machine instanceof IEnergyGrid)
			storage.add((IEnergyGrid) machine);
	}
	
	@Override
	public void onSplit(IGridStorage destinationStorage) {}
	
	@Override
	public void onJoin(IGridStorage sourceStorage) {}
	
	@Override
	public void populateGridStorage(IGridStorage destinationStorage) {}
	
	@Override
	public double getEnergy(EnergyType type, String ChannelName) {
		switch(type){
		case MJ:
			if(!(this.storageMJ.contains(new StorageBase(type, ChannelName)))){
				StorageBase storage = new StorageBase(type, ChannelName);
				storage.update(grid);
				this.storageMJ.add(storage);
			}
			for(StorageBase base : this.storageMJ){
				if(base.equals(new StorageBase(type, ChannelName))){
					double energy = 0;
					for(ICellContainer container : base.list2){
						List<IMEInventoryHandler> handlers = container.getCellArray(StorageChannel.ITEMS);
						System.out.println("a");
						for(IMEInventoryHandler handler : handlers){
							
							System.out.println(handler);
							if(handler instanceof IEnergyHandler){
								
								System.out.println("c");
								ItemStack stack = ((IEnergyHandler) handler).getCell();
								if(stack.getItem() instanceof IEnergyCell){
									IEnergyCell cell = (IEnergyCell) stack.getItem();
									energy = energy + cell.getEnergy(stack, type, ChannelName);
									
								}
							}
						}
					}
					System.err.println(energy);
					return energy;
				}
			}
			break;
		case RF:
			
			break;
		case J:
			
			break;
		}
		
			
		return 0;
	}
	
	@Override
	public double fillEnergy(double amount, String ChannelName,
			Actionable actionable, EnergyType type) {
		return 0;
	}
	
	@Override
	public double drainEnergy(double amount, String ChannelName,
			Actionable actionable, EnergyType type) {
		return 0;
	}
}