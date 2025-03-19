package com.arc.arc.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
public class Strike extends MobEffect {
    public Strike() {
        super(MobEffectCategory.BENEFICIAL, 0x00FF00); // 颜色为绿色
    }
}
