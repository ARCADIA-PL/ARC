package com.arc.arc.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class Stargazing extends MobEffect {
    public Stargazing() {
        super(MobEffectCategory.NEUTRAL, 0x00FF00); // 0x00FF00 是颜色值（绿色）
    }
}
