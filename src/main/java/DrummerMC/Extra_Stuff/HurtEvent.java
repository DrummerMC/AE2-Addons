package DrummerMC.Extra_Stuff;

import DrummerMC.Extra_Stuff.Item.ItemSheld;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HurtEvent {
	
	@SubscribeEvent
	public void onEntityHurt(LivingHurtEvent event){
		if(event.entityLiving instanceof EntityPlayer){
			EntityPlayer p = (EntityPlayer) event.entityLiving;
			if(p.getCurrentEquippedItem() == null)
				return;
			if(p.getCurrentEquippedItem().getItem() == null)
				return;
			if(p.getCurrentEquippedItem().getItem() instanceof ItemSheld && p.getCurrentEquippedItem().stackSize != 0){
				if(event.source.damageType.equals("mob") || event.source.damageType.equals("player")){
					event.ammount = event.ammount * 0.15F;
				}
			}
		}
	}

}
