package DrummerMC.AE2_Addons.libs.erogenousbeef.core.multiblock;

import java.util.EnumSet;

import net.minecraft.world.World;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

/**
 * This is a generic multiblock tick handler. If you are using this code on your own,
 * you will need to register this with the Forge TickRegistry on both the
 * client AND server sides.
 * Note that different types of ticks run on different parts of the system.
 * CLIENT ticks only run on the client, at the start/end of each game loop.
 * SERVER and WORLD ticks only run on the server.
 * WORLDLOAD ticks run only on the server, and only when worlds are loaded.
 */
public class MultiblockServerTickHandler{

	private void tickStart(Object event) {

		MultiblockRegistry.tickStart(((TickEvent.WorldTickEvent)event).world);
	}

	private void tickEnd(Object event) {
		MultiblockRegistry.tickEnd(((TickEvent.WorldTickEvent)event).world);

	}

	@SubscribeEvent
	public void tick(TickEvent.WorldTickEvent event){
		if(event.phase == TickEvent.Phase.START){
			this.tickStart(event);
		}else if(event.phase == TickEvent.Phase.END){
			this.tickEnd(event);
		}
	}
}