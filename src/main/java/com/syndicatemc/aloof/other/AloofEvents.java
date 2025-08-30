package com.syndicatemc.aloof.other;

import com.syndicatemc.aloof.Aloof;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Aloof.MOD_ID)
public class AloofEvents {
    @SubscribeEvent
    public static void itemOnBlock(LivingDamageEvent event) {

    }
}
