package com.arc.arc.events;

import com.arc.arc.init.ArcEffectsRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PercentageDamageHandler {
    private static final float BASE_PERCENT = 0.01f; // 1%

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        DamageSource source = event.getSource();
        if (!(source.getEntity() instanceof Player player)) return;

        LivingEntity target = (LivingEntity) event.getEntity();
        ItemStack weapon = player.getMainHandItem();

        // 替换原有的武器等级检测逻辑
        int level = 0;
        if (player.hasEffect(ArcEffectsRegistry.PercentageDamageEffect.get())) {
            level = player.getEffect(ArcEffectsRegistry.PercentageDamageEffect.get()).getAmplifier() + 1;
        }

        // 计算额外伤害
        float maxHealth = target.getMaxHealth();
        float extraDamage = maxHealth * BASE_PERCENT * level;
        if (extraDamage <= 0) return; // 伤害值无效

        // 创建伤害源（绕过护甲和抗性）
        DamageSource damageSource = new EntityDamageSource("percentage_damage.player", player)
                .bypassArmor()
                .bypassMagic();

        // 施加伤害（确保目标存活）
        if (target.isAlive()) {
            target.hurt(damageSource, extraDamage);
        }
    }
}


