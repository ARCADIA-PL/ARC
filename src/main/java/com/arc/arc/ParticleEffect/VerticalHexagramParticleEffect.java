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

            // 获取玩家的视角方向（yRot），并将其转换为弧度
            double playerYawRadians = Math.toRadians(player.getYRot());

            // 生成多层六芒星
            for (int layer = 0; layer < radii.length; layer++) {
                double layerRadius = radii[layer]; // 当前层的半径
                double layerOffset = 0.3 * layer; // 每层在 X 轴上的偏移

                // 生成六芒星的顶点
                for (int i = 0; i < points; i++) {
                    double angle = i * angleIncrement + playerYawRadians; // 根据玩家视角方向调整角度
                    double y = center.y + layerRadius * Math.cos(angle); // Y 坐标
                    double z = center.z + layerRadius * Math.sin(angle); // Z 坐标
                    serverLevel.sendParticles(ParticleTypes.FIREWORK, center.x + layerOffset, y, z, 5, 0, 0, 0, 0); // 火焰粒子
                    serverLevel.sendParticles(ParticleTypes.ENCHANT, center.x + layerOffset, y, z, 5, 0, 0, 0, 0); // 附魔粒子
                }

                // 生成六芒星的连线（正三角形）
                for (int i = 0; i < points; i += 2) {
                    double angle1 = i * angleIncrement + playerYawRadians; // 根据玩家视角方向调整角度
                    double angle2 = (i + 2) % points * angleIncrement + playerYawRadians; // 根据玩家视角方向调整角度
                    double y1 = center.y + layerRadius * Math.cos(angle1);
                    double z1 = center.z + layerRadius * Math.sin(angle1);
                    double y2 = center.y + layerRadius * Math.cos(angle2);
                    double z2 = center.z + layerRadius * Math.sin(angle2);

                    // 在两点之间生成粒子
                    int steps = 15; // 两点之间的粒子数量
                    for (int j = 0; j <= steps; j++) {
                        double t = (double) j / steps;
                        double y = y1 + (y2 - y1) * t;
                        double z = z1 + (z2 - z1) * t;
                        serverLevel.sendParticles(ParticleTypes.WAX_OFF, center.x + layerOffset, y, z, 1, 0, 0, 0, 0); // 闪电粒子
                        serverLevel.sendParticles(ParticleTypes.PORTAL, center.x + layerOffset, y, z, 1, 0, 0, 0, 0); // 金色粒子
                    }
                }

                // 生成六芒星的连线（倒三角形）
                for (int i = 1; i < points; i += 2) {
                    double angle1 = i * angleIncrement + playerYawRadians; // 根据玩家视角方向调整角度
                    double angle2 = (i + 2) % points * angleIncrement + playerYawRadians; // 根据玩家视角方向调整角度
                    double y1 = center.y + layerRadius * Math.cos(angle1);
                    double z1 = center.z + layerRadius * Math.sin(angle1);
                    double y2 = center.y + layerRadius * Math.cos(angle2);
                    double z2 = center.z + layerRadius * Math.sin(angle2);

                    // 在两点之间生成粒子
                    int steps = 15; // 两点之间的粒子数量
                    for (int j = 0; j <= steps; j++) {
                        double t = (double) j / steps;
                        double y = y1 + (y2 - y1) * t;
                        double z = z1 + (z2 - z1) * t;
                        serverLevel.sendParticles(ParticleTypes.WAX_OFF, center.x + layerOffset, y, z, 1, 0, 0, 0, 0); // 闪电粒子
                        serverLevel.sendParticles(ParticleTypes.PORTAL, center.x + layerOffset, y, z, 1, 0, 0, 0, 0); // 金色粒子
                    }
                }
            }

            // 生成粒子环
            int ringSteps = 15; // 粒子环的粒子数量
            double ringAngle = System.currentTimeMillis() * ringSpeed % (2 * Math.PI); // 根据时间计算旋转角度
            for (int i = 0; i < ringSteps; i++) {
                double angle = ringAngle + i * (2 * Math.PI / ringSteps) + playerYawRadians; // 根据玩家视角方向调整角度
                double y = center.y + radius * Math.cos(angle);
                double z = center.z + radius * Math.sin(angle);

                // 生成粒子环的粒子
                serverLevel.sendParticles(ParticleTypes.FIREWORK, center.x, y, z, 1, 0, 0, 0, 0); // 白色粒子
                serverLevel.sendParticles(ParticleTypes.DRIPPING_OBSIDIAN_TEAR, center.x, y, z, 1, 0, 0, 0, 0); // 灵魂火焰粒子

                // 生成粒子柱
                for (double xOffset = -0.5; xOffset <= 0.5; xOffset += 0.2) { // 粒子柱在 X 轴上的范围
                    serverLevel.sendParticles(ParticleTypes.LANDING_LAVA, center.x + xOffset, y, z, 1, 0, 0, 0, 0); // 紫色粒子
                    serverLevel.sendParticles(ParticleTypes.ENCHANT, center.x + xOffset, y, z, 1, 0, 0, 0, 0); // 金色粒子
                }
            }
        }
    }
}
