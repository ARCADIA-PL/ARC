package com.arc.arc.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = "arc")
public class TransformedArcbladeItem extends SwordItem {
    public TransformedArcbladeItem(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }
    // 无限耐久
    @Override
    public boolean isDamageable(ItemStack stack) {
        return false; // 禁止耐久损耗
    }
    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return true;
    }
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.category == EnchantmentCategory.WEAPON;
    }
    // 设置附魔强度
    @Override
    public int getEnchantmentValue() {
        return 15; // 原版钻石剑为 10
    }
}