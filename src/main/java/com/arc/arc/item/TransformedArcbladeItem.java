package com.arc.arc.item;

import com.arc.arc.ArcItemRegistry;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class TransformedArcbladeItem extends SwordItem {
    public TransformedArcbladeItem(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }
    // 无限耐久：物品不会被消耗
    @Override
    public boolean isDamageable(ItemStack stack) {
        return false; // 禁止耐久损耗
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return true;
    }

    // 控制可接受的附魔类型
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.category == EnchantmentCategory.WEAPON;
    }

    // 设置附魔强度（影响附魔等级）
    @Override
    public int getEnchantmentValue() {
        return 15; // 原版钻石剑为 10
    }
}

