/**
 * 
 */
package DrummerMC.AE2_Addons.StorageEnergy;

import java.util.ArrayList;
import java.util.List;

import DrummerMC.AE2_Addons.Api.Grid.IEnergyGrid;
import DrummerMC.AE2_Addons.Api.Grid.IEnergyStorageGrid;
import DrummerMC.AE2_Addons.Api.Util.EnergyType;
import appeng.api.config.Actionable;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridHost;
import appeng.api.networking.IGridNode;
import appeng.api.networking.IGridStorage;


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
	
	public EnergyStorageGrid(IGrid grid){
		this.grid = grid;
		
	}
	
	@Override
	public void onUpdateTick() {}

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
