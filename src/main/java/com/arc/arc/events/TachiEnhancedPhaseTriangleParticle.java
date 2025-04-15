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
public class TachiEnhancedPhaseTriangleParticle {
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
            spawnRotatingTriangle(player);
        }
    }

    private static void spawnRotatingTriangle(Player player) {
        if (player.level.isClientSide) {
            Level world = player.level;
            Vec3 pos = player.position(); // 玩家脚底位置

            // 三角形参数
            int particlesPerSide = 7; // 每条边的粒子数量
            double radius = 2.0; // 三角形外接圆半径
            float baseAlpha = 0.7f; // 基础透明度

            // 动态参数
            long gameTime = world.getGameTime();
            double scale = 1.0 + 0.2 * Math.sin(gameTime * 0.1); // 大小呼吸
            float dynamicAlpha = baseAlpha * (0.5f + 0.5f * (float) Math.sin(gameTime * 0.2)); // 透明度呼吸

            // 旋转和流动参数
            double angleOffset = gameTime * 0.03; // 整体旋转速度
            double flowSpeed = 0.05; // 粒子沿边流动速度

            // 正三角形的三个顶点（初始角度：0°, 120°, 240°）
            for (int side = 0; side < 3; side++) {
                double startAngle = 2 * Math.PI * side / 3 + angleOffset;
                double endAngle = 2 * Math.PI * (side + 1) / 3 + angleOffset;

                // 在每条边上生成粒子
                for (int i = 0; i < particlesPerSide; i++) {
                    double progress = (i + (gameTime * flowSpeed) % 1) / particlesPerSide; // 流动进度
                    double angle = startAngle + progress * (endAngle - startAngle);

                    // 计算粒子位置
                    double x = pos.x + radius * scale * Math.cos(angle);
                    double z = pos.z + radius * scale * Math.sin(angle);

                    // Y轴轻微波动
                    double yOffset = Math.sin(angle + gameTime * 0.15) * 0.1;

                    // 生成粒子（使用 END_ROD）
                    world.addParticle(
                            ParticleTypes.WAX_ON,
                            true,
                            x, pos.y + 0.1 + yOffset, z,
                            0, 0.02, 0
                    );
                }
            }
        }
    }
}
