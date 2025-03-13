package com.arc.arc.effect;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class StellarisHexagram2 extends InstantenousMobEffect {
    public StellarisHexagram2() {
        super(MobEffectCategory.NEUTRAL, 0x00FF00);
    }


    static int d;
    static int ans=30;
    static int p=1;



    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {

            d=amplifier+1;
            Vec3 vec = player.position().add(0, 0.1, 0);

            //实现呼吸效果
            if(ans==230||ans==30)p=(p^1);
            if(p==0)ans++;
            else ans--;

            spawnHexagramParticles(player,vec,6,0.2);
        }
    }


    public static void spawnHexagramParticles(Player player, Vec3 center, double radius, double ringSpeed) {


        if (player.level instanceof ServerLevel serverLevel) {
            double[] radii = {radius * 0.5, radius * 0.75, radius}; // 三层六芒星的半径
            int points = 6; // 六芒星的顶点数
            double angleIncrement = 2 * Math.PI / points;
            // 生成多层六芒星
            for (int layer = 0; layer < radii.length; layer++) {
                double layerRadius = radii[layer]; // 当前层的半径
                double layerHeight = 0.2 * layer; // 每层高度递增
                // 生成六芒星的顶点
                for (int i = 0; i < points; i++) {
                    double angle = i * angleIncrement;
                    double x = center.x + layerRadius * Math.cos(angle);
                    double z = center.z + layerRadius * Math.sin(angle);
                    serverLevel.sendParticles(ParticleTypes.FIREWORK, x, center.y + layerHeight, z, 5, 0, 0, 0, 0); // 火焰粒子
                    serverLevel.sendParticles(ParticleTypes.ENCHANT, x, center.y + layerHeight, z, 5, 0, 0, 0, 0); // 附魔粒子
                }
                // 生成六芒星的连线（正三角形）
                for (int i = 0; i < points; i += 2) {
                    double angle1 = i * angleIncrement;
                    double angle2 = (i + 2) % points * angleIncrement;
                    double x1 = center.x + layerRadius * Math.cos(angle1);
                    double z1 = center.z + layerRadius * Math.sin(angle1);
                    double x2 = center.x + layerRadius * Math.cos(angle2);
                    double z2 = center.z + layerRadius * Math.sin(angle2);
                    // 在两点之间生成粒子
                    int steps = 20; // 两点之间的粒子数量
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
                    int steps = 20; // 两点之间的粒子数量
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
                for (double y = 0; y < 1.3; y += 0.2) { // 粒子柱高度为 1 格
                    serverLevel.sendParticles(ParticleTypes.LANDING_LAVA, x, center.y + y, z, 1, 0, 0, 0, 0); // 紫色粒子
                    serverLevel.sendParticles(ParticleTypes.ENCHANT, x, center.y + y, z, 1, 0, 0, 0, 0); // 金色粒子
                }
            }
        }
    }

}

