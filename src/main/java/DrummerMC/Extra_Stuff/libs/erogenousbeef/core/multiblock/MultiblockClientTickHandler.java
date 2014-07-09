package DrummerMC.Extra_Stuff.libs.erogenousbeef.core.multiblock;

import java.util.EnumSet;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;

public class MultiblockClientTickHandler{

	
	private void tickStart(Object event) {
		MultiblockRegistry.tickStart(Minecraft.getMinecraft().theWorld);
	}

	
	private void tickEnd(Object event) {
		MultiblockRegistry.tickEnd(Minecraft.getMinecraft().theWorld);
	}
	
	@SubscribeEvent
	public void tick(TickEvent.ClientTickEvent event){
		if(event.phase == TickEvent.Phase.START){
			this.tickStart(event);
		}else if(event.phase == TickEvent.Phase.END){
			this.tickEnd(event);
		}
	}

}