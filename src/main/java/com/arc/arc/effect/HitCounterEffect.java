package com.arc.arc.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class HitCounterEffect extends MobEffect {
    public HitCounterEffect() {
        super(MobEffectCategory.NEUTRAL, 0xFFFFFF); // 颜色（白色）
    }
}
