package com.arc.arc.events;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "arc", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PotionEffectHandler {

    @SubscribeEvent
    public static void onPotionEffectAdded(PotionEvent.PotionAddedEvent event) {
        if (event.getEntity() instanceof Player) {
            checkAndTransformItems((Player) event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onPotionEffectRemoved(PotionEvent.PotionRemoveEvent event) {
        if (event.getEntity() instanceof Player) {
            checkAndTransformItems((Player) event.getEntity());
        }
    }
    // 检查并转换物品
    private static void checkAndTransformItems(Player player) {
        Level level = player.level;
}
}

