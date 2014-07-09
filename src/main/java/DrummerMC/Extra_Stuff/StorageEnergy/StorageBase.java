package DrummerMC.Extra_Stuff.StorageEnergy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import appeng.api.networking.IGrid;
import appeng.api.networking.IGridHost;
import appeng.api.networking.IGridNode;
import appeng.api.storage.ICellContainer;
import appeng.api.util.IReadOnlyCollection;
import DrummerMC.Extra_Stuff.Api.EnergyStorage.IStoredEnergy;
import DrummerMC.Extra_Stuff.Api.Util.EnergyType;



public class StorageBase implements IStoredEnergy {
	public final EnergyType type;
	public final String ChannelName;
	public List<Class<? extends IGridHost>> list1 = new ArrayList<Class<? extends IGridHost>>();
	public List<ICellContainer> list2 = new ArrayList<ICellContainer>();
	
	public StorageBase(EnergyType type, String ChannelName){
		this.type = type;
		this.ChannelName = ChannelName;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj  == this)
			return true;
		if(obj instanceof StorageBase){
			if(((StorageBase) obj).type  == this.type && ((StorageBase) obj).ChannelName.equals(ChannelName))
				return true;
		}
		return false;
	}

	@Override
	public EnergyType getEnergyType() {
		return this.type;
	}

	@Override
	public String getChannelName() {
		return this.ChannelName;
	}

	@Override
	public void update(IGrid grid) {
		IReadOnlyCollection<Class<? extends IGridHost>> hosts = grid.getMachinesClasses();
		Iterator<Class<? extends IGridHost>> in = hosts.iterator();
		
		list1.clear();
		
		while(in.hasNext()){
			this.list1.add(in.next());
		}
		list2.clear();
		for(Class<? extends IGridHost> clazz : list1){
			for(IGridNode host : grid.getMachines(clazz)){
				IGridHost gridHost= host.getMachine();
				if(gridHost instanceof ICellContainer){
					list2.add((ICellContainer) gridHost);
				}
			}
		}
		
	}

}
