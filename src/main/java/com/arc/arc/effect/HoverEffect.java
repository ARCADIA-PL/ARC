package com.arc.arc.effect;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class HoverEffect extends MobEffect {
    public HoverEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFFFFFF); // 颜色为白色
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // 每等级悬浮 2 格高度
        double targetHeight = entity.getY() + (amplifier + 1) * 2.0;

        // 如果实体低于目标高度，则将其提升到目标高度
        if (entity.getY() < targetHeight) {
            entity.setPos(entity.getX(), targetHeight, entity.getZ());
        }

        // 在实体脚下生成云粒子
        Level level = entity.level;
        if (level instanceof ServerLevel serverLevel) {
            double x = entity.getX();
            double y = entity.getY() - 0.5; // 粒子生成在脚下
            double z = entity.getZ();
            serverLevel.sendParticles(ParticleTypes.CLOUD, x, y, z, 10, 0.5, 0.1, 0.5, 0.02);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true; // 每 tick 都执行效果
    }
}


