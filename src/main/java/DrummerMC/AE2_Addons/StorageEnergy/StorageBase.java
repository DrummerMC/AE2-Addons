package DrummerMC.AE2_Addons.StorageEnergy;

import DrummerMC.AE2_Addons.Api.EnergyStorage.IStoredEnergy;
import DrummerMC.AE2_Addons.Api.Util.EnergyType;



public class StorageBase implements IStoredEnergy {
	public final EnergyType type;
	public final String ChannelName;
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

}
