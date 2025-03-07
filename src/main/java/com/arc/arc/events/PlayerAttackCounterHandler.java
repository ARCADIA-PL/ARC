package com.arc.arc.events;

import com.arc.arc.ArcMod;
import com.arc.arc.init.ArcEffectsRegistry;
import com.arc.arc.network.ComboSoundPacket;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerAttackCounterHandler {
    @SubscribeEvent
    public static void onPlayerAttack(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            if (player.hasEffect(ArcEffectsRegistry.HIT_COUNTER.get())) {
                MobEffectInstance effect = player.getEffect(ArcEffectsRegistry.HIT_COUNTER.get());
                int newLevel = 0;
                if (effect != null) {
                    newLevel = effect.getAmplifier() + 1;
                }
                // 仅在服务端处理
                if (player.level.isClientSide) return;
                // 获取玩家当前攻击计数BUFF
                MobEffectInstance currentEffect = player.getEffect(ArcEffectsRegistry.HIT_COUNTER.get());
                int currentLevel = (currentEffect != null) ? currentEffect.getAmplifier() : 1;

                int duration = (currentEffect != null) ? currentEffect.getDuration() : 30 * 20;

                MobEffectInstance newEffect = new MobEffectInstance(
                        ArcEffectsRegistry.HIT_COUNTER.get(),
                        10 * 20,
                        currentLevel + 1,
                        false,
                        true,
                        true
                );

                player.removeEffect(ArcEffectsRegistry.HIT_COUNTER.get());
                player.addEffect(newEffect);

                // 检测等级触发条件
                if (newLevel == 4 || newLevel == 9) {
                    ArcMod.CHANNEL.sendToServer(new ComboSoundPacket(newLevel));
                }

            }
        }
    }
}
