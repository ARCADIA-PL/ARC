package com.arc.arc.effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class StellarisInstantDamageEffect extends InstantenousMobEffect {
    public StellarisInstantDamageEffect() {
        super(MobEffectCategory.HARMFUL, 0xFF0000);
    }
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            // 伤害值:最大生命值 × 1% × (等级 + 1)
            float maxHealth = player.getMaxHealth();
            float damage = maxHealth * 0.01f * (amplifier + 1);
            // 确保血量不低于1
            float remainingHealth = player.getHealth() - damage;
            if (remainingHealth <= 0) {
                player.setHealth(1.0f); // 强制保留1点血量
            } else {
            // 虚空伤害源
            entity.getLastDamageSource();
            player.hurt(DamageSource.OUT_OF_WORLD, damage);
        }
    }
}
}


