package com.arc.arc.effect;

import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class Stargaze extends InstantenousMobEffect {
    public Stargaze() {
        super(MobEffectCategory.NEUTRAL, 0x00FF00);
    }

}

