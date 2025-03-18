package com.arc.arc.effect;

import com.arc.arc.Registries.ArcEffectsRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;

public class RecorderB extends MobEffect {
    public static final String HAS_RECORDED_KEY = "hasRecordedB"; // 标志位的键

    public RecorderB() {
        super(MobEffectCategory.BENEFICIAL, 0x0000FF); // 设置 BUFF 类型和颜色
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true; // 每 tick 都执行逻辑
    }
}
