package com.arc.arc.client;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientParticleUtils {

    /**
     * 在实体周围生成环绕粒子
     * @param entity 目标实体
     * @param particleType 粒子类型
     * @param count 粒子数量
     * @param radius 环绕半径
     */
    public static void spawnParticlesAroundEntity(Entity entity, ParticleType<?> particleType, int count, double radius) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level != null) {
            for (int i = 0; i < count; i++) {
                double angle = Math.PI * 2 * i / count;
                double x = entity.getX() + radius * Math.cos(angle);
                double y = entity.getY() + entity.getBbHeight() * 0.5;
                double z = entity.getZ() + radius * Math.sin(angle);
                minecraft.level.addParticle(
                        (ParticleOptions) particleType,
                        x, y, z, // 粒子位置
                        0, 0, 0  // 速度（静止）
                );
            }
        }
    }
}
