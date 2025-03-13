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


public class StellarisHexagram extends InstantenousMobEffect {
    public StellarisHexagram() {
        super(MobEffectCategory.NEUTRAL, 0x00FF00);
    }


    static int d;
    static int ans=30;
    static int p=1;



    static int I1=0;
    static int I2=1;
    static int J1=0;
    static int J2=0;
    static int I3=0;


    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {

            d=amplifier+1;
            Vec3 vec = player.position().add(0, 0.1, 0);

            //实现呼吸效果
            if(ans==230||ans==30)p=(p^1);
            if(p==0)ans++;
            else ans--;

            spawnHexagramParticles(player,vec);
        }
    }


    private static void liu1(Player player,Vec3 center,int v){
        double r = 1; // 小六芒星的半径

        if (player.level instanceof ServerLevel serverLevel) {
            int points = 6; // 六芒星的顶点数
            double angleIncrement = 2 * Math.PI / points;


            //方位1的六芒星（正前方）
            if(v==0){
                // 生成六芒星的连线（正三角形）
                for (int i = 0; i < points; i += 2) {
                    double angle1 = i * angleIncrement;
                    double angle2 = (i + 2) % points * angleIncrement;
                    double y1 = center.y + r * Math.cos(angle1);
                    double z1 = center.z + r * Math.sin(angle1);
                    double y2 = center.y + r * Math.cos(angle2);
                    double z2 = center.z + r * Math.sin(angle2);
                    // 在两点之间生成粒子
                    int steps = 4; // 两点之间的粒子数量
                    for (int j = 0; j <= steps; j++) {
                        double t = (double) j / steps;
                        double y = y1 + (y2 - y1) * t;
                        double z = z1 + (z2 - z1) * t;
                        serverLevel.sendParticles(ParticleTypes.FIREWORK, center.x, y , z, 1, 0, 0, 0, 0); // 地狱门粒子
                    }
                }
                // 生成六芒星的连线（倒三角形）
                for (int i = 1; i < points; i += 2) {
                    double angle1 = i * angleIncrement;
                    double angle2 = (i + 2) % points * angleIncrement;
                    double y1 = center.y + r * Math.cos(angle1);
                    double z1 = center.z + r * Math.sin(angle1);
                    double y2 = center.y + r * Math.cos(angle2);
                    double z2 = center.z + r * Math.sin(angle2);
                    // 在两点之间生成粒子
                    int steps = 4; // 两点之间的粒子数量
                    for (int j = 0; j <= steps; j++) {
                        double t = (double) j / steps;
                        double y = y1 + (y2 - y1) * t;
                        double z = z1 + (z2 - z1) * t;
                        serverLevel.sendParticles(ParticleTypes.FIREWORK, center.x, y  , z, 1, 0, 0, 0, 0); // 闪电粒子
                    }
                }
                return;
            }


            //方位3的六芒星（侧边）
            if(v==1){
                // 生成六芒星的连线（正三角形）
                for (int i = 0; i < points; i += 2) {
                    double angle1 = i * angleIncrement;
                    double angle2 = (i + 2) % points * angleIncrement;
                    double y1 = center.y + r * Math.cos(angle1);
                    double x1 = center.x + r * Math.sin(angle1);
                    double y2 = center.y + r * Math.cos(angle2);
                    double x2 = center.x + r * Math.sin(angle2);
                    // 在两点之间生成粒子
                    int steps = 4; // 两点之间的粒子数量
                    for (int j = 0; j <= steps; j++) {
                        double t = (double) j / steps;
                        double y = y1 + (y2 - y1) * t;
                        double x = x1 + (x2 - x1) * t;
                        serverLevel.sendParticles(ParticleTypes.FIREWORK, x, y , center.z, 1, 0, 0, 0, 0); // 地狱门粒子
                    }
                }
                // 生成六芒星的连线（倒三角形）
                for (int i = 1; i < points; i += 2) {
                    double angle1 = i * angleIncrement;
                    double angle2 = (i + 2) % points * angleIncrement;
                    double y1 = center.y + r * Math.cos(angle1);
                    double x1 = center.x + r * Math.sin(angle1);
                    double y2 = center.y + r * Math.cos(angle2);
                    double x2 = center.x + r * Math.sin(angle2);
                    // 在两点之间生成粒子
                    int steps = 4; // 两点之间的粒子数量
                    for (int j = 0; j <= steps; j++) {
                        double t = (double) j / steps;
                        double y = y1 + (y2 - y1) * t;
                        double x = x1 + (x2 - x1) * t;
                        serverLevel.sendParticles(ParticleTypes.FIREWORK, x, y, center.z, 1, 0, 0, 0, 0); // 闪电粒子
                    }
                }
                return;
            }



            //方位3的六芒星（正上方）
            // 生成六芒星的连线（正三角形）
            for (int i = 0; i < points; i += 2) {
                double angle1 = i * angleIncrement;
                double angle2 = (i + 2) % points * angleIncrement;
                double x1 = center.x + r * Math.cos(angle1);
                double z1 = center.z + r * Math.sin(angle1);
                double x2 = center.x + r * Math.cos(angle2);
                double z2 = center.z + r * Math.sin(angle2);
                // 在两点之间生成粒子
                int steps = 4; // 两点之间的粒子数量
                for (int j = 0; j <= steps; j++) {
                    double t = (double) j / steps;
                    double x = x1 + (x2 - x1) * t;
                    double z = z1 + (z2 - z1) * t;
                    serverLevel.sendParticles(ParticleTypes.FIREWORK, x, center.y , z, 1, 0, 0, 0, 0); // 地狱门粒子
                }
            }
            // 生成六芒星的连线（倒三角形）
            for (int i = 1; i < points; i += 2) {
                double angle1 = i * angleIncrement;
                double angle2 = (i + 2) % points * angleIncrement;
                double x1 = center.x + r * Math.cos(angle1);
                double z1 = center.z + r * Math.sin(angle1);
                double x2 = center.x + r * Math.cos(angle2);
                double z2 = center.z + r * Math.sin(angle2);
                // 在两点之间生成粒子
                int steps = 4; // 两点之间的粒子数量
                for (int j = 0; j <= steps; j++) {
                    double t = (double) j / steps;
                    double x = x1 + (x2 - x1) * t;
                    double z = z1 + (z2 - z1) * t;
                    serverLevel.sendParticles(ParticleTypes.FIREWORK, x, center.y  , z, 1, 0, 0, 0, 0); // 闪电粒子
                }
            }


        }
    }





    private static void spawnHexagramParticles(Player player, Vec3 center) {
        Map<Integer, SimpleParticleType> map=new HashMap<>();
        map.put(1,ParticleTypes.WAX_OFF);
        map.put(0,ParticleTypes.HEART);



        if (player.level instanceof ServerLevel serverLevel) {


            double[] r = new double[5]; // 三层六芒星的半径
            d=Math.min(d,6);
            r[1]=(double)d;
            r[2]=d*2.0;
            r[3]=d*3.0;
            int h=Math.min(32,1+d*4);
            int points = 6; // 六芒星的顶点数
            double angleIncrement = 2 * Math.PI / points;
            int p=new Random().nextInt(150);

            if(p<=d*2){
                double X=new Random().nextDouble(2*d*3.0);
                double Z=new Random().nextDouble(2*d*3.0);
                X=X-d*3.0;
                Z=Z-d*3.0;
                double Y=new Random().nextDouble((double)h);
                int v=new Random().nextInt(3);
                Vec3 lizi = player.position().add(X, Y, Z);
                for(int q=1;q<=60;q++){
                    liu1(player, lizi,v);
                }
            }

            // 生成多层六芒星0
            for(int k=0;k<=h;k++) {
                if (k != 0 && k != h) continue; //只生成地面和天上两个法阵
                double ringRadius = r[3]; // 粒子环的半径
                int ringSteps = 40 * d; // 粒子环的粒子数量
                //流动感

                int s=2;    //流动的速度
                if(d<3)s=1;
                else if(d==3)s=1;
                else if(d==6)s=4;
                else if(d==4)s=2;
                else if(d==5)s=3;
                for (int u = 1; u <= 3; u++) {
                    int i1 = I1;
                    int i2 = I2;
                    int j1 = J1;
                    int j2 = J2;    //暂存

                    for (; I3 < ringSteps; ) {
                        double angle =   I3 * (2 * Math.PI / ringSteps);
                        double x = center.x + ringRadius * Math.cos(angle);
                        double z = center.z + ringRadius * Math.sin(angle);
                        // 生成粒子环的粒子
                        serverLevel.sendParticles(ParticleTypes.FIREWORK, x, center.y + 0.1 + k, z, 1, 0, 0, 0, 0); // 白色粒子
                        I3--;
                        I3 = (I3+ringSteps)%ringSteps;
                        break;
                    }

                    for (int j = 1; j <= s; j++) {
                        for (; I1 < points; ) {
                            double angle1 = I1 * angleIncrement;
                            double angle2 = (I1 + 2) % points * angleIncrement;
                            double x1 = center.x + r[u] * Math.cos(angle1);
                            double z1 = center.z + r[u] * Math.sin(angle1);
                            double x2 = center.x + r[u] * Math.cos(angle2);
                            double z2 = center.z + r[u] * Math.sin(angle2);
                            //int steps = d * 15; // 两点之间的粒子数量
                            int steps = d*(Math.min(15,Math.max((ans-(40*(u-1)))/6,3)));; // 两点之间的粒子数量
                            for (; J1 <= steps; ) {
                                double t = (double) J1 / steps;
                                double x = x1 + (x2 - x1) * t;
                                double z = z1 + (z2 - z1) * t;
                                serverLevel.sendParticles(ParticleTypes.FIREWORK, x, center.y + 0.1 + k, z, 1, 0, 0, 0, 0); // 地狱门粒子
                                J1++;
                                J1 %= steps + 1;
                                if (J1 == 0) I1 += 2;
                                if (I1 >= points) I1 = 0;
                                break;
                            }
                            break;
                        }

                        for (; I2 < points; ) {
                            double angle1 = I2 * angleIncrement;
                            double angle2 = (I2 + 2) % points * angleIncrement;
                            double x1 = center.x + r[u] * Math.cos(angle1);
                            double z1 = center.z + r[u] * Math.sin(angle1);
                            double x2 = center.x + r[u] * Math.cos(angle2);
                            double z2 = center.z + r[u] * Math.sin(angle2);
                            //int steps = d * 15;
                            int steps = d*(Math.min(15,Math.max((ans-(40*(u-1)))/6,3))); // 两点之间的粒子数量
                            for (; J2 <= steps; ) {
                                double t = (double) J2 / steps;
                                double x = x1 + (x2 - x1) * t;
                                double z = z1 + (z2 - z1) * t;
                                serverLevel.sendParticles(ParticleTypes.FIREWORK, x, center.y + 0.1 + k, z, 1, 0, 0, 0, 0); // 闪电粒子
                                J2++;
                                J2 %= steps + 1;
                                if (J2 == 0) I2 += 2;
                                if (I2 >= points) I2 = 1;
                                break;
                            }
                            break;
                        }
                    }
                    if(u!=3){
                        I1=i1;
                        I2=i2;
                        J1=j1;
                        J2=j2;
                    }


                }

            }
        }
    }

}

