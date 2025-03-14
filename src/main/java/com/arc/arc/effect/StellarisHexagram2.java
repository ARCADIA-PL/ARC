package com.arc.arc.effect;

import com.arc.arc.init.ArcEffectsRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;

public class StellarisHexagram2 extends InstantenousMobEffect {
    static int ans = 0;
    static int p = 1;
    static int p1 = 0;

    public StellarisHexagram2() {
        super(MobEffectCategory.NEUTRAL, 0x00FF00);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            // 每次生成法阵时，基于玩家当前位置动态计算法阵的位置
            Vec3 currentPosition = player.position().add(0, 0.1, 0);
            Vec3 adjustedPosition = adjustPosition(currentPosition, player, 0);

            // 实现呼吸效果
            if (ans == 130 || ans == 0) p = (p ^ 1);
            if (p == 0) ans++;
            else ans--;

            // 生成法阵
            spawnHexagramParticles(player, adjustedPosition, (amplifier + 1) * 3, 0.2);
        }
    }

    /**
     * 动态调整法阵位置
     *
     * @param defaultPosition 默认位置（玩家脚下）
     * @param player          玩家实体
     * @param distance        法阵距离玩家的距离
     * @return 调整后的位置
     */
    public static Vec3 adjustPosition(Vec3 defaultPosition, Player player, double distance) {
        // 获取玩家的朝向
        float yaw = player.getYRot(); // 水平朝向（yaw）
        // 计算玩家前方的偏移量
        double offsetX = -Math.sin(Math.toRadians(yaw)) * distance;
        double offsetZ = Math.cos(Math.toRadians(yaw)) * distance;
        // 调整法阵位置
        return defaultPosition.add(offsetX, 0, offsetZ);
    }

    public static void plough(Player player, Vec3 vec, double radius) {
        if (player.level instanceof ServerLevel serverLevel) {
            // 给玩家 buff 增益
            MobEffectInstance strengthEffect = new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, 2);
            player.addEffect(strengthEffect);

            for (int k = 1; k <= 50; k++) {
                // 生成北斗七星（待补全）
                double x = new Random().nextDouble(radius / 2.0);
                double y = new Random().nextDouble(3.0);
                double z = new Random().nextDouble(radius / 2.0);
                x -= radius / 2.0;
                z -= radius / 2.0;
                serverLevel.sendParticles(ParticleTypes.FIREWORK, vec.x + x, vec.y + y, vec.z + z, 5, 0, 0, 0, 0); // 火焰粒子
                x = new Random().nextDouble(radius / 2.0);
                y = new Random().nextDouble(3.0);
                z = new Random().nextDouble(radius / 2.0);
                x -= radius / 2.0;
                z -= radius / 2.0;
                serverLevel.sendParticles(ParticleTypes.FIREWORK, vec.x + x, vec.y + y, vec.z + z, 5, 0, 0, 0, 0); // 火焰粒子
            }
        }
    }

    public static void spawnHexagramParticles(Player player, Vec3 center, double radius, double ringSpeed) {
        if (player.level instanceof ServerLevel serverLevel) {

            if (ans <= 110 && ans >= 80 && p == 1 && p1 == 0) {
                int p2 = new Random().nextInt(2);
                if (p2 == 1) {
                    plough(player, center, radius);
                }
                p1 = 1;
            }
            if (ans <= 80) p1 = 0;

            double[] radii = {radius * 0.5, radius * 0.75, radius}; // 三层六芒星的半径
            int points = 6; // 六芒星的顶点数
            double angleIncrement = 2 * Math.PI / points;
            // 生成多层六芒星
            for (int layer = 0; layer < radii.length; layer++) {
                double layerRadius = radii[layer]; // 当前层的半径
                double layerHeight = 0.2 * layer; // 每层高度递增
                // 生成六芒星的连线（正三角形）
                for (int i = 0; i < points; i += 2) {
                    double angle1 = i * angleIncrement;
                    double angle2 = (i + 2) % points * angleIncrement;
                    double x1 = center.x + layerRadius * Math.cos(angle1);
                    double z1 = center.z + layerRadius * Math.sin(angle1);
                    double x2 = center.x + layerRadius * Math.cos(angle2);
                    double z2 = center.z + layerRadius * Math.sin(angle2);
                    // 在两点之间生成粒子
                    int steps = ((int) radii[2]) * Math.min(10, Math.max(0, ans / 6 - layer * 4 - 1)); // 两点之间的粒子数量
                    for (int j = 0; j <= steps; j++) {
                        double t = (double) j / steps;
                        double x = x1 + (x2 - x1) * t;
                        double z = z1 + (z2 - z1) * t;
                        serverLevel.sendParticles(ParticleTypes.WAX_OFF, x, center.y + layerHeight, z, 1, 0, 0, 0, 0); // 闪电粒子
                        serverLevel.sendParticles(ParticleTypes.PORTAL, x, center.y + layerHeight, z, 1, 0, 0, 0, 0); // 金色粒子
                    }
                }
                // 生成六芒星的连线（倒三角形）
                for (int i = 1; i < points; i += 2) {
                    double angle1 = i * angleIncrement;
                    double angle2 = (i + 2) % points * angleIncrement;
                    double x1 = center.x + layerRadius * Math.cos(angle1);
                    double z1 = center.z + layerRadius * Math.sin(angle1);
                    double x2 = center.x + layerRadius * Math.cos(angle2);
                    double z2 = center.z + layerRadius * Math.sin(angle2);
                    // 在两点之间生成粒子
                    int steps = ((int) radii[2]) * Math.min(10, Math.max(0, ans / 6 - layer * 4 - 1));
                    for (int j = 0; j <= steps; j++) {
                        double t = (double) j / steps;
                        double x = x1 + (x2 - x1) * t;
                        double z = z1 + (z2 - z1) * t;
                        serverLevel.sendParticles(ParticleTypes.WAX_OFF, x, center.y + layerHeight, z, 1, 0, 0, 0, 0); // 闪电粒子
                        serverLevel.sendParticles(ParticleTypes.PORTAL, x, center.y + layerHeight, z, 1, 0, 0, 0, 0); // 金色粒子
                    }
                }
            }
            // 生成粒子环
            int ringSteps = 20; // 粒子环的粒子数量
            double ringAngle = System.currentTimeMillis() * ringSpeed % (2 * Math.PI); // 根据时间计算旋转角度
            for (int i = 0; i < ringSteps; i++) {
                double angle = ringAngle + i * (2 * Math.PI / ringSteps);
                double x = center.x + radius * Math.cos(angle);
                double z = center.z + radius * Math.sin(angle);
                // 生成粒子环的粒子
                serverLevel.sendParticles(ParticleTypes.FIREWORK, x, center.y + 0.1, z, 1, 0, 0, 0, 0); // 白色粒子
                serverLevel.sendParticles(ParticleTypes.DRIPPING_OBSIDIAN_TEAR, x, center.y + 0.1, z, 1, 0, 0, 0, 0); // 灵魂火焰粒子
                // 生成粒子柱
                for (double y = 0; y < 1.0; y += 0.2) { // 粒子柱高度为 0.8 格
                    serverLevel.sendParticles(ParticleTypes.LANDING_LAVA, x, center.y + y, z, 1, 0, 0, 0, 0); // 紫色粒子
                    serverLevel.sendParticles(ParticleTypes.ENCHANT, x, center.y + y, z, 1, 0, 0, 0, 0); // 金色粒子
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPotionExpire(PotionEvent.PotionExpiryEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            // 重置呼吸效果的状态
            ans = 0;
            p = 1;
            p1 = 0;
        }
    }
}
