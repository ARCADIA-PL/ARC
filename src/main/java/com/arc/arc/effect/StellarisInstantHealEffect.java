package com.arc.arc.effect;

import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class StellarisInstantHealEffect extends InstantenousMobEffect {
    public StellarisInstantHealEffect() {
        super(MobEffectCategory.NEUTRAL, 0x00FF00);
    }
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            // 计算治疗量：最大生命值 × 1% × (等级 + 1)
            float maxHealth = player.getMaxHealth();
            float healAmount = maxHealth * 0.01f * (amplifier + 1);
            player.heal(healAmount);
        }
    }
}

