package com.arc.arc.effect;

import com.arc.arc.Registries.ArcEffectsRegistry;
import io.redspace.ironsspellbooks.capabilities.magic.PlayerMagicData;
import io.redspace.ironsspellbooks.spells.AbstractSpell;
import io.redspace.ironsspellbooks.spells.lightning.LightningLanceSpell;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public class Lightning extends InstantenousMobEffect {
    public Lightning() {
        super(MobEffectCategory.NEUTRAL, 0x00FF00); // 0x00FF00 是颜色值（绿色）
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            AbstractSpell spell = new LightningLanceSpell(10);
            PlayerMagicData magicData = PlayerMagicData.getPlayerMagicData(player);
            spell.onCast(player.level, player, magicData);
        }
    }

}
