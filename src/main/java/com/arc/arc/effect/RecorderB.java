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
    private boolean hasBeenCounted = false;

    public RecorderB() {
        super(MobEffectCategory.BENEFICIAL, 0x0000FF); // 颜色可以自定义
    }

    public boolean hasBeenCounted() {
        return hasBeenCounted;
    }

    public void setCounted(boolean counted) {
        this.hasBeenCounted = counted;
    }
}

