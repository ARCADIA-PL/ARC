package com.arc.arc.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
public class HeavyGravityEffect extends MobEffect {
    public HeavyGravityEffect() {
        super(MobEffectCategory.NEUTRAL, 0xFFFFFF);
    }
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true; // 每 tick 都执行
    }
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // 如果实体在空中（未接触地面）
        if (!entity.isOnGround()) {
            // 获取当前实体的速度
            Vec3 currentMotion = entity.getDeltaMovement();
            // 增加垂直方向（Y 轴）的下落速度
            double gravityMultiplier = 1.5 + (amplifier * 0.5); // 根据等级增加重力倍数
            Vec3 newMotion = new Vec3(currentMotion.x(), currentMotion.y() - (0.05 * gravityMultiplier), currentMotion.z());
            // 应用新的速度
            entity.setDeltaMovement(newMotion);
        }
    }
}

