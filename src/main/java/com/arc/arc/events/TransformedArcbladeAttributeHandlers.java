package com.arc.arc.events;

import com.arc.arc.Registries.ArcBladeItemRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "arc")
public class TransformedArcbladeAttributeHandlers {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        Player player = event.player;
        // 仅在服务端处理
        if (!player.level.isClientSide) {
            ItemStack mainHandItem = player.getMainHandItem();
            // 检查玩家是否手持 TransformedArcbladeItem
            boolean isHoldingArcblade = mainHandItem.getItem() == ArcBladeItemRegistry.ARCBLADETRANSFORMED.get();
            // 如果手持 TransformedArcbladeItem，添加 BUFF
            if (isHoldingArcblade) {
                // 添加跳跃提升效果（等级 1，持续 2 秒）
                player.addEffect(new MobEffectInstance(MobEffects.JUMP, 40, 2, true, true));
                // 添加缓降效果（等级 0，持续 2 秒）
                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 40, 0, true, true));
            } else {
                // 如果不手持 TransformedArcbladeItem，移除 BUFF
                player.removeEffect(MobEffects.JUMP);
                player.removeEffect(MobEffects.SLOW_FALLING);
            }
        }
    }
}


