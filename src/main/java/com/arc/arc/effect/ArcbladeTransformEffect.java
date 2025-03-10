package com.arc.arc.effect;

import com.arc.arc.item.ArcbladeItem;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ArcbladeTransformEffect extends MobEffect {
    public ArcbladeTransformEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFFA500); // BUFF 类型和颜色
    }
}
