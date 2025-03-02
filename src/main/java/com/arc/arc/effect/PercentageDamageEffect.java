package com.arc.arc.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;

public class PercentageDamageEffect extends MobEffect {
    public PercentageDamageEffect() {
        super(MobEffectCategory.BENEFICIAL,0x00FF00);
    }
}
