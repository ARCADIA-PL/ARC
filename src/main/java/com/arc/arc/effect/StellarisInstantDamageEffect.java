package com.arc.arc.effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class StellarisInstantDamageEffect extends InstantenousMobEffect {
    public StellarisInstantDamageEffect() {
        super(MobEffectCategory.NEUTRAL, 0xFF0000);
    }
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // 对任意实体生效
        float maxHealth = entity.getMaxHealth();
        float damage = maxHealth * 0.01f * (amplifier + 1);
        float remainingHealth = entity.getHealth() - damage;
        // 不致死
        if (remainingHealth <= 0) {
            entity.setHealth(1.0f); // 强制保留1点血量
        } else {
            // 虚空伤害源
            entity.hurt(DamageSource.OUT_OF_WORLD,damage);
        }
    }
}


