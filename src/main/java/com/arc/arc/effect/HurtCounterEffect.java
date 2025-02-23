package com.arc.arc.effect;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
@OnlyIn(Dist.CLIENT)

public class HurtCounterEffect extends MobEffect {
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.level.isClientSide) {
            for (int i = 0; i <= amplifier; i++){
                entity.level.addParticle(ParticleTypes.END_ROD,entity.getX(), entity.getY()+1.5, entity.getZ()
                        , 0.0D, 0.0D, 0.0D);
            }
        }
    }
    public HurtCounterEffect() {
        super(MobEffectCategory.NEUTRAL, 0xFFA500); // 橙色效果
    }
}
