package com.arc.arc.events;

import com.arc.arc.Registries.ArcEffectsRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
@Mod.EventBusSubscriber
public class TimerTickHandler {
    private static final double RANGE = 15.0; // 检测范围：15 格
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) { // 在每 tick 的结束时检查
            Player player = event.player;
            Level level = player.level;
            // 获取玩家周围 15 格内的生物
            AABB area = new AABB(player.getX() - RANGE, player.getY() - RANGE, player.getZ() - RANGE,
                                 player.getX() + RANGE, player.getY() + RANGE, player.getZ() + RANGE);
            for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, area)) {
                // 检查生物是否有计时器 BUFF
                MobEffectInstance timerEffect = entity.getEffect(ArcEffectsRegistry.Timer.get());
                if (timerEffect != null && timerEffect.getDuration() <= 1) { // BUFF 即将结束
                    // 移除计时器 BUFF
                    entity.removeEffect(ArcEffectsRegistry.Timer.get());
                    // 给予指定的其它 BUFF
                    MobEffectInstance newEffect = new MobEffectInstance(ArcEffectsRegistry.VerticalStop.get(), 200, 1); // 再生效果，持续 10 秒，等级 2
                    entity.addEffect(newEffect);
                    // 输出日志（可选）
                    if (!level.isClientSide) {
                        System.out.println("Timer BUFF ended! Applied new BUFF to entity: " + entity.getName().getString());
                    }
                }
            }
        }
    }
}


