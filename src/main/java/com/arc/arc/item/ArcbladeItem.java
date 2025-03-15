package com.arc.arc.item;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.ItemStack;

public class ArcbladeItem extends SwordItem {
    public ArcbladeItem(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    // 无限耐久：物品不会被消耗
    @Override
    public boolean isDamageable(ItemStack stack) {
        return false; // 禁止耐久损耗
    }
}