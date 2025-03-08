package com.arc.arc.effect;

import com.dfdyz.epicacg.registry.Particles;
import com.guhao.renderers.GuHaoRenderType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import reascer.wom.particle.WOMParticles;

public class ParticleEffect extends MobEffect {
    public ParticleEffect() {
        super(MobEffectCategory.NEUTRAL, 0x00FF00); // 药水效果的颜色
    }
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        Level world = entity.level;
        if (world.isClientSide) { // 仅在客户端生成粒子
            // 获取实际的粒子类型
            SimpleParticleType particleType = WOMParticles.ANTITHEUS_HIT.get();
            // 在实体位置生成粒子
            world.addParticle(particleType, entity.getX(), entity.getY(), entity.getZ(), 0, 0, 0);
        }
    }
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true; // 每 tick 都执行
    }
}


