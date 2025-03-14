package com.arc.arc.effect;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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


    static int ans=0;
    static int p=1;
    static int p1=0;


    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {


            Vec3 vec = player.position().add(0, 0.1, 0);

            //实现呼吸效果
            if(ans==130||ans==0)p=(p^1);
            if(p==0)ans++;
            else ans--;

            spawnHexagramParticles(player,vec,(amplifier+1)*3,0.2);
        }
    }

    public static void plough(Player player, Vec3 vec,double radius) {             //形参为玩家对象，玩家坐标,最大半径
        if (player.level instanceof ServerLevel serverLevel) {
            //给玩家buff增益
            MobEffectInstance strengthEffect = new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, 2);
            player.addEffect(strengthEffect);

            for(int k=1;k<=50;k++){
                //生成北斗七星（待补全）
                double x=new Random().nextDouble(radius/2.0);
                double y=new Random().nextDouble(3.0);
                double z=new Random().nextDouble(radius/2.0);
                x-=radius/2.0;
                z-=radius/2.0;
                serverLevel.sendParticles(ParticleTypes.FIREWORK, vec.x+x, vec.y + y, vec.z+z, 5, 0, 0, 0, 0); // 火焰粒子
                x=new Random().nextDouble(radius/2.0);
                y=new Random().nextDouble(3.0);
                z=new Random().nextDouble(radius/2.0);
                x-=radius/2.0;
                z-=radius/2.0;
                serverLevel.sendParticles(ParticleTypes.FIREWORK, vec.x+x, vec.y + y, vec.z+z, 5, 0, 0, 0, 0); // 火焰粒子
            }
        }


    }
    public static void spawnHexagramParticles(Player player, Vec3 center, double radius, double ringSpeed) {


        if (player.level instanceof ServerLevel serverLevel) {

            if(ans<=110&&ans>=80&&p==1&&p1==0){
                int p2=new Random().nextInt(2);
                if(p2==1){
                    Vec3 vec = player.position().add(0, 0.1, 0);
                    plough(player,vec,radius);
                }
                p1=1;
            }
            if(ans<=80)p1=0;


            double[] radii = {radius * 0.5, radius * 0.75, radius}; // 三层六芒星的半径
            int points = 6; // 六芒星的顶点数
            double angleIncrement = 2 * Math.PI / points;
            // 生成多层六芒星
            for (int layer = 0; layer < radii.length; layer++) {
                double layerRadius = radii[layer]; // 当前层的半径
                double layerHeight = 0.2 * layer; // 每层高度递增
                // 生成六芒星的顶点
//                for (int i = 0; i < points; i++) {
//                    double angle = i * angleIncrement;
//                    double x = center.x + layerRadius * Math.cos(angle);
//                    double z = center.z + layerRadius * Math.sin(angle);
//                    serverLevel.sendParticles(ParticleTypes.FIREWORK, x, center.y + layerHeight, z, 2, 0, 0, 0, 0); // 火焰粒子
//                    serverLevel.sendParticles(ParticleTypes.ENCHANT, x, center.y + layerHeight, z, 2, 0, 0, 0, 0); // 附魔粒子
//                }
                // 生成六芒星的连线（正三角形）
                for (int i = 0; i < points; i += 2) {
                    double angle1 = i * angleIncrement;
                    double angle2 = (i + 2) % points * angleIncrement;
                    double x1 = center.x + layerRadius * Math.cos(angle1);
                    double z1 = center.z + layerRadius * Math.sin(angle1);
                    double x2 = center.x + layerRadius * Math.cos(angle2);
                    double z2 = center.z + layerRadius * Math.sin(angle2);
                    // 在两点之间生成粒子
                    int steps = ((int)radii[2])*Math.min(10,Math.max(0,ans/6-layer*4-1)); // 两点之间的粒子数量
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
                    //int steps = 20; // 两点之间的粒子数量
                    int steps = ((int)radii[2])*Math.min(10,Math.max(0,ans/6-layer*4-1));
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

