package com.arc.arc.ParticleEffect;

import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class VerticalStellarisParticleEffect {

    /**
     * 生成垂直六芒星法阵的粒子效果
     *
     * @param player     玩家
     * @param center     法阵的中心点
     * @param radius     法阵的最大半径（六芒星顶点与粒子环的半径）
     * @param ringSpeed  粒子环的旋转速度
     * @param height     法阵的生成高度（Y 坐标）
     */
    public static void spawnVerticalStellarisParticles(Player player, Vec3 center, double radius, double ringSpeed, double height) {
        if (player.level instanceof ServerLevel serverLevel) {
            double[] radii = {radius * 0.5, radius * 0.75, radius}; // 三层六芒星的半径
            int points = 6; // 六芒星的顶点数
            double angleIncrement = 2 * Math.PI / points;

            // 获取玩家的朝向（Yaw）并左转 90 度
            double yaw = Math.toRadians(player.getYRot() - 90); // 左转 90 度
            double forwardX = -Math.sin(yaw); // 玩家左侧的 X 方向
            double forwardZ = Math.cos(yaw);  // 玩家左侧的 Z 方向

            // 生成多层六芒星
            for (int layer = 0; layer < radii.length; layer++) {
                double layerRadius = radii[layer]; // 当前层的半径

                // 生成六芒星的顶点
                for (int i = 0; i < points; i++) {
                    double angle = i * angleIncrement;
                    double x = layerRadius * Math.cos(angle); // 水平方向（XZ 平面）
                    double y = layerRadius * Math.sin(angle); // 垂直方向（Y 轴）

                    // 将法阵旋转到垂直地面并面向玩家左侧
                    double rotatedX = x * forwardX;
                    double rotatedY = y;
                    double rotatedZ = x * forwardZ;

                    // 计算最终坐标，并应用高度
                    double finalX = center.x + rotatedX;
                    double finalY = height + rotatedY; // 使用传入的高度值
                    double finalZ = center.z + rotatedZ;

                    // 生成粒子，设置速度为 0 防止下落
                    serverLevel.sendParticles(ParticleTypes.END_ROD, finalX, finalY, finalZ, 5, 0, 0, 0, 0); // 火焰粒子
                    serverLevel.sendParticles(ParticleTypes.WAX_ON, finalX, finalY, finalZ, 5, 0, 0, 0, 0); // 灵魂火焰粒子
                }

                // 生成六芒星的连线（正三角形）
                for (int i = 0; i < points; i += 2) {
                    double angle1 = i * angleIncrement;
                    double angle2 = (i + 2) % points * angleIncrement;

                    double x1 = layerRadius * Math.cos(angle1);
                    double y1 = layerRadius * Math.sin(angle1);
                    double rotatedX1 = x1 * forwardX;
                    double rotatedY1 = y1;
                    double rotatedZ1 = x1 * forwardZ;

                    double x2 = layerRadius * Math.cos(angle2);
                    double y2 = layerRadius * Math.sin(angle2);
                    double rotatedX2 = x2 * forwardX;
                    double rotatedY2 = y2;
                    double rotatedZ2 = x2 * forwardZ;

                    // 在两点之间生成粒子
                    int steps = 15; // 两点之间的粒子数量
                    for (int j = 0; j <= steps; j++) {
                        double t = (double) j / steps;
                        double x = rotatedX1 + (rotatedX2 - rotatedX1) * t;
                        double y = rotatedY1 + (rotatedY2 - rotatedY1) * t;
                        double z = rotatedZ1 + (rotatedZ2 - rotatedZ1) * t;

                        double finalX = center.x + x;
                        double finalY = height + y; // 使用传入的高度值
                        double finalZ = center.z + z;

                        // 生成粒子，设置速度为 0 防止下落
                        serverLevel.sendParticles(ParticleTypes.WAX_OFF, finalX, finalY, finalZ, 1, 0, 0, 0, 0); // 灵魂粒子
                        serverLevel.sendParticles(ParticleTypes.ENCHANT, finalX, finalY, finalZ, 1, 0, 0, 0, 0); // 发光粒子
                    }
                }

                // 生成六芒星的连线（倒三角形）
                for (int i = 1; i < points; i += 2) {
                    double angle1 = i * angleIncrement;
                    double angle2 = (i + 2) % points * angleIncrement;

                    double x1 = layerRadius * Math.cos(angle1);
                    double y1 = layerRadius * Math.sin(angle1);
                    double rotatedX1 = x1 * forwardX;
                    double rotatedY1 = y1;
                    double rotatedZ1 = x1 * forwardZ;

                    double x2 = layerRadius * Math.cos(angle2);
                    double y2 = layerRadius * Math.sin(angle2);
                    double rotatedX2 = x2 * forwardX;
                    double rotatedY2 = y2;
                    double rotatedZ2 = x2 * forwardZ;

                    // 在两点之间生成粒子
                    int steps = 15; // 两点之间的粒子数量
                    for (int j = 0; j <= steps; j++) {
                        double t = (double) j / steps;
                        double x = rotatedX1 + (rotatedX2 - rotatedX1) * t;
                        double y = rotatedY1 + (rotatedY2 - rotatedY1) * t;
                        double z = rotatedZ1 + (rotatedZ2 - rotatedZ1) * t;

                        double finalX = center.x + x;
                        double finalY = height + y; // 使用传入的高度值
                        double finalZ = center.z + z;

                        // 生成粒子，设置速度为 0 防止下落
                        serverLevel.sendParticles(ParticleTypes.WAX_OFF, finalX, finalY, finalZ, 1, 0, 0, 0, 0); // 灵魂粒子
                        serverLevel.sendParticles(ParticleTypes.ENCHANT, finalX, finalY, finalZ, 1, 0, 0, 0, 0); // 发光粒子
                    }
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
     * @param height     法阵的生成高度（Y 坐标）
     */
    public static void spawnVerticalHexagramInFrontOfPlayer(Player player, double distance, double radius, double ringSpeed, double height) {
        // 计算玩家前方位置
        double forwardX = -Math.sin(Math.toRadians(player.getYRot())) * distance;
        double forwardZ = Math.cos(Math.toRadians(player.getYRot())) * distance;
        Vec3 frontCenter = new Vec3(player.getX() + forwardX, height, player.getZ() + forwardZ); // 使用传入的高度值

        // 生成法阵
        spawnVerticalStellarisParticles(player, frontCenter, radius, ringSpeed, height);
    }
}
