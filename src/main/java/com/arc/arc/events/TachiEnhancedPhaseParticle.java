package com.arc.arc.events;

import com.arc.arc.Registries.ArcEffectsRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "arc", value = Dist.CLIENT)
public class TachiEnhancedPhaseParticle {
    private static final int PARTICLE_INTERVAL = 40; // 2秒（20 ticks = 1秒）
    private static int timer = 0;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player != Minecraft.getInstance().player) {
            return;
        }

        Player player = event.player;
        MobEffectInstance effect = player.getEffect(ArcEffectsRegistry.TACHI_ENHANCED_PHASE.get());
        if (effect == null) {
            timer = 0; // 重置计时器
            return;
        }

        // 每2秒触发一次
        if (++timer >= PARTICLE_INTERVAL) {
            timer = 0;
            spawnBreathingRing(player);
        }
    }

    private static void spawnBreathingRing(Player player) {
        if (player.level.isClientSide) {
            Level world = player.level;
            Vec3 pos = player.position(); // 玩家脚底位置

            // 圆环参数
            int particles = 20; // 粒子数量（增加平滑度）
            double baseRadius = 2.0; // 基础半径
            float baseAlpha = 0.7f; // 基础透明度

            // 动态参数
            long gameTime = world.getGameTime();
            double scale = 1.0 + 0.3 * Math.sin(gameTime * 0.1); // 半径呼吸
            float dynamicAlpha = baseAlpha * (0.5f + 0.5f * (float) Math.sin(gameTime * 0.2)); // 透明度呼吸

            // 旋转和流动参数
            double angleOffset = gameTime * 0.05; // 旋转速度（值越大越快）
            double flowSpeed = 0.02; // 流动速度（粒子轻微偏移模拟流动）

            // 生成圆环
            for (int i = 0; i < particles; i++) {
                double angle = 2 * Math.PI * i / particles + angleOffset;
                double x = pos.x + baseRadius * scale * Math.cos(angle);
                double z = pos.z + baseRadius * scale * Math.sin(angle);

                // 流动效果：Y轴轻微波动
                double yOffset = Math.sin(angle + gameTime * 0.1) * 0.2;

                // 使用 END_ROD 粒子（可替换为自定义粒子）
                world.addParticle(
                        ParticleTypes.END_ROD,
                        true, // 强制渲染
                        x, pos.y + 0.1 + yOffset, z, // Y轴波动
                        flowSpeed * Math.cos(angle), // X方向流动
                        0.05, // 轻微上升
                        flowSpeed * Math.sin(angle)  // Z方向流动
                );
            }
        }
    }
}
