package com.arc.arc.events;

import com.arc.arc.init.ModCapabilities;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AttackTrackerHandler {
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player attacker) {
            LivingEntity target = (LivingEntity) event.getEntity();

            // 获取Capability并更新最后攻击者
            target.getCapability(ModCapabilities.LAST_ATTACKER).ifPresent(cap -> {
                cap.setLastAttackerUUID(attacker.getUUID());
            });
        }
    }
}
