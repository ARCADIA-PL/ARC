package com.arc.arc.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
public class ParryCounterEffect extends MobEffect {
    public ParryCounterEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x00FF00); // 药水效果的颜色
    }
}

