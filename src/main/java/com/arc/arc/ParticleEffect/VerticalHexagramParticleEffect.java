package com.arc.arc.ParticleEffect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.Vec3;

public class VerticalHexagramParticleEffect {

    /**
     * 生成垂直于地面的六芒星法阵的粒子效果
     *
     * @param player    玩家
     * @param center    法阵的中心点
     * @param radius    法阵的最大半径（六芒星顶点与粒子环的半径）
     * @param ringSpeed 粒子环的旋转速度
     */
    public static void spawnVerticalHexagramParticles(Player player, Vec3 center, double radius, double ringSpeed) {
        if (player.level instanceof ServerLevel serverLevel) { // 检查是否为服务器端
            double[] radii = {radius * 0.5, radius * 0.75, radius}; // 三层六芒星的半径
            int points = 6; // 六芒星的顶点数
            double angleIncrement = 2 * Math.PI / points;

            // 获取玩家的视角方向（yRot 和 xRot），并将其转换为弧度
            double playerYawRadians = Math.toRadians(player.getYRot()); // 水平旋转角度
            double playerPitchRadians = Math.toRadians(player.getXRot()); // 垂直旋转角度

            // 计算法阵的旋转矩阵
            double cosYaw = Math.cos(playerYawRadians);
            double sinYaw = Math.sin(playerYawRadians);
            double cosPitch = Math.cos(playerPitchRadians);
            double sinPitch = Math.sin(playerPitchRadians);

            // 生成多层六芒星
            for (int layer = 0; layer < radii.length; layer++) {
                double layerRadius = radii[layer]; // 当前层的半径
                double layerOffset = 0.3 * layer; // 每层在 X 轴上的偏移

                // 生成六芒星的顶点
                for (int i = 0; i < points; i++) {
                    double angle = i * angleIncrement; // 六芒星的角度
                    double x = layerRadius * Math.cos(angle); // 初始 X 坐标
                    double y = layerRadius * Math.sin(angle); // 初始 Y 坐标
                    double z = 0; // 初始 Z 坐标

                    // 应用旋转矩阵
                    double rotatedX = x * cosYaw - y * sinYaw;
                    double rotatedY = y * cosYaw + x * sinYaw;
                    double rotatedZ = z * cosPitch - rotatedY * sinPitch;
                    rotatedY = rotatedY * cosPitch + z * sinPitch;

                    // 调整坐标到法阵中心
                    double finalX = center.x + layerOffset + rotatedX;
                    double finalY = center.y + rotatedY;
                    double finalZ = center.z + rotatedZ;

                    // 生成粒子
                    serverLevel.sendParticles(ParticleTypes.FIREWORK, finalX, finalY, finalZ, 5, 0, 0, 0, 0); // 火焰粒子
                    serverLevel.sendParticles(ParticleTypes.ENCHANT, finalX, finalY, finalZ, 5, 0, 0, 0, 0); // 附魔粒子
                }

                // 生成六芒星的连线（正三角形）
                for (int i = 0; i < points; i += 2) {
                    double angle1 = i * angleIncrement; // 第一个角度
                    double angle2 = (i + 2) % points * angleIncrement; // 第二个角度

                    // 计算第一个点的坐标
                    double x1 = layerRadius * Math.cos(angle1);
                    double y1 = layerRadius * Math.sin(angle1);
                    double z1 = 0;
                    double rotatedX1 = x1 * cosYaw - y1 * sinYaw;
                    double rotatedY1 = y1 * cosYaw + x1 * sinYaw;
                    double rotatedZ1 = z1 * cosPitch - rotatedY1 * sinPitch;
                    rotatedY1 = rotatedY1 * cosPitch + z1 * sinPitch;
                    double finalX1 = center.x + layerOffset + rotatedX1;
                    double finalY1 = center.y + rotatedY1;
                    double finalZ1 = center.z + rotatedZ1;

                    // 计算第二个点的坐标
                    double x2 = layerRadius * Math.cos(angle2);
                    double y2 = layerRadius * Math.sin(angle2);
                    double z2 = 0;
                    double rotatedX2 = x2 * cosYaw - y2 * sinYaw;
                    double rotatedY2 = y2 * cosYaw + x2 * sinYaw;
                    double rotatedZ2 = z2 * cosPitch - rotatedY2 * sinPitch;
                    rotatedY2 = rotatedY2 * cosPitch + z2 * sinPitch;
                    double finalX2 = center.x + layerOffset + rotatedX2;
                    double finalY2 = center.y + rotatedY2;
                    double finalZ2 = center.z + rotatedZ2;

                    // 在两点之间生成粒子
                    int steps = 15; // 两点之间的粒子数量
                    for (int j = 0; j <= steps; j++) {
                        double t = (double) j / steps;
                        double finalX = finalX1 + (finalX2 - finalX1) * t;
                        double finalY = finalY1 + (finalY2 - finalY1) * t;
                        double finalZ = finalZ1 + (finalZ2 - finalZ1) * t;
                        serverLevel.sendParticles(ParticleTypes.WAX_OFF, finalX, finalY, finalZ, 1, 0, 0, 0, 0); // 闪电粒子
                        serverLevel.sendParticles(ParticleTypes.PORTAL, finalX, finalY, finalZ, 1, 0, 0, 0, 0); // 金色粒子
                    }
                }

                // 生成六芒星的连线（倒三角形）
                for (int i = 1; i < points; i += 2) {
                    double angle1 = i * angleIncrement; // 第一个角度
                    double angle2 = (i + 2) % points * angleIncrement; // 第二个角度

                    // 计算第一个点的坐标
                    double x1 = layerRadius * Math.cos(angle1);
                    double y1 = layerRadius * Math.sin(angle1);
                    double z1 = 0;
                    double rotatedX1 = x1 * cosYaw - y1 * sinYaw;
                    double rotatedY1 = y1 * cosYaw + x1 * sinYaw;
                    double rotatedZ1 = z1 * cosPitch - rotatedY1 * sinPitch;
                    rotatedY1 = rotatedY1 * cosPitch + z1 * sinPitch;
                    double finalX1 = center.x + layerOffset + rotatedX1;
                    double finalY1 = center.y + rotatedY1;
                    double finalZ1 = center.z + rotatedZ1;

                    // 计算第二个点的坐标
                    double x2 = layerRadius * Math.cos(angle2);
                    double y2 = layerRadius * Math.sin(angle2);
                    double z2 = 0;
                    double rotatedX2 = x2 * cosYaw - y2 * sinYaw;
                    double rotatedY2 = y2 * cosYaw + x2 * sinYaw;
                    double rotatedZ2 = z2 * cosPitch - rotatedY2 * sinPitch;
                    rotatedY2 = rotatedY2 * cosPitch + z2 * sinPitch;
                    double finalX2 = center.x + layerOffset + rotatedX2;
                    double finalY2 = center.y + rotatedY2;
                    double finalZ2 = center.z + rotatedZ2;

                    // 在两点之间生成粒子
                    int steps = 15; // 两点之间的粒子数量
                    for (int j = 0; j <= steps; j++) {
                        double t = (double) j / steps;
                        double finalX = finalX1 + (finalX2 - finalX1) * t;
                        double finalY = finalY1 + (finalY2 - finalY1) * t;
                        double finalZ = finalZ1 + (finalZ2 - finalZ1) * t;
                        serverLevel.sendParticles(ParticleTypes.WAX_OFF, finalX, finalY, finalZ, 1, 0, 0, 0, 0); // 闪电粒子
                        serverLevel.sendParticles(ParticleTypes.PORTAL, finalX, finalY, finalZ, 1, 0, 0, 0, 0); // 金色粒子
                    }
                }
            }

            // 生成粒子环
            int ringSteps = 15; // 粒子环的粒子数量
            double ringAngle = System.currentTimeMillis() * ringSpeed % (2 * Math.PI); // 根据时间计算旋转角度
            for (int i = 0; i < ringSteps; i++) {
                double angle = ringAngle + i * (2 * Math.PI / ringSteps); // 粒子环的角度
                double x = radius * Math.cos(angle);
                double y = radius * Math.sin(angle);
                double z = 0;

                // 应用旋转矩阵
                double rotatedX = x * cosYaw - y * sinYaw;
                double rotatedY = y * cosYaw + x * sinYaw;
                double rotatedZ = z * cosPitch - rotatedY * sinPitch;
                rotatedY = rotatedY * cosPitch + z * sinPitch;

                // 调整坐标到法阵中心
                double finalX = center.x + rotatedX;
                double finalY = center.y + rotatedY;
                double finalZ = center.z + rotatedZ;

                // 生成粒子环的粒子
                serverLevel.sendParticles(ParticleTypes.FIREWORK, finalX, finalY, finalZ, 1, 0, 0, 0, 0); // 白色粒子
                serverLevel.sendParticles(ParticleTypes.DRIPPING_OBSIDIAN_TEAR, finalX, finalY, finalZ, 1, 0, 0, 0, 0); // 灵魂火焰粒子

                // 生成粒子柱
                for (double xOffset = -0.5; xOffset <= 0.5; xOffset += 0.2) { // 粒子柱在 X 轴上的范围
                    serverLevel.sendParticles(ParticleTypes.LANDING_LAVA, finalX + xOffset, finalY, finalZ, 1, 0, 0, 0, 0); // 紫色粒子
                    serverLevel.sendParticles(ParticleTypes.ENCHANT, finalX + xOffset, finalY, finalZ, 1, 0, 0, 0, 0); // 金色粒子
                }
            }
        }
    }
}
