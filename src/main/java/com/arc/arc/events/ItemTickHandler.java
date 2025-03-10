package com.arc.arc.events;

import com.arc.arc.ArcItemRegistry;
import com.arc.arc.init.ArcEffectsRegistry;
import com.arc.arc.item.ArcbladeItem;
import com.arc.arc.item.TransformedArcbladeItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "arc", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ItemTickHandler {

    // 监听玩家每刻状态变化
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            checkAndTransformItems(event.player);
        }
    }

    // 监听药水效果变化
    @SubscribeEvent
    public static void onPotionEffectExpired(PotionEvent.PotionExpiryEvent event) {
        if (event.getEntity() instanceof Player player) {
            // 当药水效果过期时，强制检查并更新物品栏
            checkAndTransformItems(player);
        }
    }

    // 监听药水效果移除
    @SubscribeEvent
    public static void onPotionEffectRemoved(PotionEvent.PotionRemoveEvent event) {
        if (event.getEntity() instanceof Player player) {
            // 当药水效果被移除时，强制检查并更新物品栏
            checkAndTransformItems(player);
        }
    }

    // 监听物品拾取
    @SubscribeEvent
    public static void onItemPickup(PlayerEvent.ItemPickupEvent event) {
        checkAndTransformItems(event.getPlayer());
    }

    // 监听物品合成
    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        checkAndTransformItems(event.getPlayer());
    }

    // 监听物品熔炼
    @SubscribeEvent
    public static void onItemSmelted(PlayerEvent.ItemSmeltedEvent event) {
        checkAndTransformItems(event.getPlayer());
    }

    // 监听容器内物品移动
    @SubscribeEvent
    public static void onContainerItemMove(PlayerContainerEvent event) {
        checkAndTransformItems(event.getPlayer());
    }

    // 检查并转换物品
    private static void checkAndTransformItems(Player player) {
        Level level = player.level;

        // 遍历玩家的物品栏
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);

            // 检查当前物品是否为 Arcblade
            if (stack.getItem() instanceof ArcbladeItem) {
                // 检查玩家是否具有特定的药水效果
                if (player.hasEffect(ArcEffectsRegistry.ArcbladeTransformEffect.get())) {
                    // 将物品替换为变形 Arcblade
                    ItemStack transformedStack = new ItemStack(ArcItemRegistry.ARCBLADETransformed.get());
                    transformedStack.setTag(stack.getTag()); // 保留 NBT 数据
                    player.getInventory().setItem(slot, transformedStack);
                }
            }

            // 检查当前物品是否为变形 Arcblade
            if (stack.getItem() instanceof TransformedArcbladeItem) {
                // 检查玩家是否不再具有特定的药水效果
                if (!player.hasEffect(ArcEffectsRegistry.ArcbladeTransformEffect.get())) {
                    // 将物品替换回普通 Arcblade
                    ItemStack normalStack = new ItemStack(ArcItemRegistry.ARCBLADE.get());
                    normalStack.setTag(stack.getTag()); // 保留 NBT 数据
                    player.getInventory().setItem(slot, normalStack);
                }
            }
        }
    }
}
