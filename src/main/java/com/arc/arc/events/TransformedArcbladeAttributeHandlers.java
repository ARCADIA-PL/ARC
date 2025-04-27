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
        ItemStack mainHandItem = player.getMainHandItem();
        // 仅在服务端处理且手持 ArcbladeTransformed 时生效
        if (!player.level.isClientSide && mainHandItem.getItem() == ArcBladeItemRegistry.ARCBLADETRANSFORMED.get()) {
            // 检查玩家是否手持 TransformedArcbladeItem
            boolean isHoldingArcblade = mainHandItem.getItem() == ArcBladeItemRegistry.ARCBLADETRANSFORMED.get();
            // 如果手持 TransformedArcbladeItem，添加 BUFF
            if (isHoldingArcblade) {
                // 添加跳跃提升效果（等级 1，持续 2 秒）
                player.addEffect(new MobEffectInstance(MobEffects.JUMP, 40, 1, true, true));
                // 添加缓降效果（等级 0，持续 2 秒）
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, 0, true, true));
            } else {
                // 如果不手持 TransformedArcbladeItem，移除 BUFF
                player.removeEffect(MobEffects.JUMP);
            }
        }
    }
}


