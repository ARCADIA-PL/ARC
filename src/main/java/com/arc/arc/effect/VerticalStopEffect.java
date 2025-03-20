package com.arc.arc.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
public class VerticalStopEffect extends MobEffect {
    // 构造函数
    public VerticalStopEffect() {super(MobEffectCategory.NEUTRAL, 0xFFFFFF);}
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true; // 每 tick 都执行
    }
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // 获取当前实体的速度
        Vec3 currentMotion = entity.getDeltaMovement();
        // 将垂直方向（Y 轴）的速度设置为 0，保留水平方向（X 和 Z 轴）的速度
        Vec3 newMotion = new Vec3(currentMotion.x(), 0, currentMotion.z());
        // 应用新的速度
        entity.setDeltaMovement(newMotion);
        // 将实体的 Y 坐标重置为上一 tick 的位置，防止实体在垂直方向上移动
        entity.setPos(entity.getX(), entity.yOld, entity.getZ());
    }
}

