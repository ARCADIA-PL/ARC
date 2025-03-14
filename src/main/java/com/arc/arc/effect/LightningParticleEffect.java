package com.arc.arc.effect;

import com.arc.arc.ParticleEffect.WeaponParticleEffect;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class LightningParticleEffect extends MobEffect {
    // 构造函数
    public LightningParticleEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFFFFFF); // BUFF 类型和颜色
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // 只在服务端执行
        if (!entity.level.isClientSide && entity instanceof Player player) {
            // 获取当前世界（必须是 ServerLevel）
            if (player.level instanceof ServerLevel serverLevel) {
                // 获取玩家的当前位置
                Vec3 playerPos = player.position();

                // 创建 WeaponParticleEffect 实例
                WeaponParticleEffect effect = WeaponParticleEffect.createExampleEffect(serverLevel, playerPos, player);

                // 触发粒子效果
                effect.execute();
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        // 只在 BUFF 生效的第一刻触发
        return duration == 1;
    }
}
