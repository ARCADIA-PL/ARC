package com.arc.arc.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class TimerEffect extends MobEffect {
    // 自定义计时器 BUFF
    public TimerEffect() {
        super(MobEffectCategory.NEUTRAL, 0xFFFFFF); // 中性效果，颜色为白色
    }
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // 在每 tick 调用，这里不需要额外逻辑
    }
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true; // 每 tick 都调用 applyEffectTick
    }
}
