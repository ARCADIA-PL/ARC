package com.arc.arc.effect;

import com.arc.arc.ParticleEffect.WeaponParticleEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class LightningParticleEffect extends MobEffect {
    // 构造函数
    public LightningParticleEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x00FF00); // 设置 BUFF 类型和颜色
    }
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            Level level = player.getLevel();
            Vec3 pos = player.position(); // 获取玩家位置
            // 创建并执行粒子效果
            WeaponParticleEffect effect = WeaponParticleEffect.createExampleEffect(level, pos, player);
            effect.execute();
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        // 只在 BUFF 的第一帧触发效果
        return duration == 1;
    }
}


