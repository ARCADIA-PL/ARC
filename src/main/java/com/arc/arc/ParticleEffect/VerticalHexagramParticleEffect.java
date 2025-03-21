package com.arc.arc.ParticleEffect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.Vec3;

public class VerticalHexagramParticleEffect {

    /**
     * 生成垂直六芒星法阵的粒子效果
     *
     * @param player     玩家
     * @param center     法阵的中心点
     * @param radius     法阵的最大半径（六芒星顶点与粒子环的半径）
     * @param ringSpeed  粒子环的旋转速度
     */
    public static void spawnVerticalHexagramParticles(Player player, Vec3 center, double radius, double ringSpeed) {
        if (player.level instanceof ServerLevel serverLevel) {
            double[] radii = {radius * 0.5, radius * 0.75, radius}; // 三层六芒星的半径
            int points = 6; // 六芒星的顶点数
            double angleIncrement = 2 * Math.PI / points;
            // 获取玩家的朝向（Yaw）并左转 90 度
            double yaw = Math.toRadians(player.getYRot() - 90); // 左转 90 度
            double forwardX = -Math.sin(yaw); // 玩家左侧的 X 方向
            double forwardZ = Math.cos(yaw);  // 玩家左侧的 Z 方向
//            // 生成多层六芒星
//            for (int layer = 0; layer < radii.length; layer++) {
//                double layerRadius = radii[layer]; // 当前层的半径
//                // 生成六芒星的顶点
//                for (int i = 0; i < points; i++) {
//                    double angle = i * angleIncrement;
//                    double x = layerRadius * Math.cos(angle); // 水平方向（XZ 平面）
//                    double y = layerRadius * Math.sin(angle); // 垂直方向（Y 轴）
//                    // 将法阵旋转到垂直地面并面向玩家左侧
//                    double rotatedX = x * forwardX;
//                    double rotatedY = y;
//                    double rotatedZ = x * forwardZ;
//                    // 计算最终坐标
//                    double finalX = center.x + rotatedX;
//                    double finalY = center.y + rotatedY;
//                    double finalZ = center.z + rotatedZ;
//                    serverLevel.sendParticles(ParticleTypes.FIREWORK, finalX, finalY, finalZ, 5, 0, 0, 0, 0); // 火焰粒子
//                    serverLevel.sendParticles(ParticleTypes.ENCHANT, finalX, finalY, finalZ, 5, 0, 0, 0, 0); // 附魔粒子
//                }
//            }
            Vec3 []coordinate=new Vec3[10];
            coordinate[1]=new Vec3(0,0,0) ;
            coordinate[2]=coordinate[1].add(0.6,0,0);
            coordinate[3]=coordinate[1].add(0.8,1.0,0);
            coordinate[4]=coordinate[1].add(0.3,1.2,0);
            coordinate[5]=coordinate[1].add(0.2,1.8,0);
            coordinate[6]=coordinate[1].add(0.12,2.28,0);
            coordinate[7]=coordinate[1].add(0.3,2.48,0);
                for (int i = 1; i <= 7; i++) {
                    coordinate[i] = new Vec3(center.x + coordinate[i].x * forwardX+1, coordinate[i].y +1.0+center.y, center.z + coordinate[i].x * forwardZ);
                    serverLevel.sendParticles(ParticleTypes.WAX_ON, coordinate[i].x, coordinate[i].y, coordinate[i].z, 1, 0, 0, 0, 0); // 火焰粒子
                    if(i==7)continue;
                    double x=(coordinate[i+1].x-coordinate[i].x)/6.0;
                    double y=(coordinate[i+1].y-coordinate[i].y)/6.0;
                    double z=(coordinate[i+1].z-coordinate[i].z)/6.0;
                    for (int j = 1; j <= 5; j++) {
                        serverLevel.sendParticles(ParticleTypes.FIREWORK, coordinate[i].x+j*x*1.0, coordinate[i].y+j*y*1.0, coordinate[i].z+j*z*1.0, 1, 0, 0, 0, 0); // 火焰粒子
                    }

                }
        }
    }

    /**
     * 在玩家前方生成垂直六芒星法阵
     *
     * @param player     玩家
     * @param distance   法阵距离玩家的距离
     * @param radius     法阵的最大半径
     * @param ringSpeed  粒子环的旋转速度
     */
    public static void spawnVerticalHexagramInFrontOfPlayer(Player player, double distance, double radius, double ringSpeed) {
        // 计算玩家前方位置
        double forwardX = -Math.sin(Math.toRadians(player.getYRot())) * distance;
        double forwardZ = Math.cos(Math.toRadians(player.getYRot())) * distance;
        Vec3 frontCenter = new Vec3(player.getX() + forwardX, player.getY(), player.getZ() + forwardZ);

        // 生成法阵
        spawnVerticalHexagramParticles(player, frontCenter, radius, ringSpeed);
    }
}
