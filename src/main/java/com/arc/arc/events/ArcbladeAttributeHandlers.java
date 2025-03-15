package com.arc.arc.events;
import com.arc.arc.Registries.ArcBladeItemRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "arc")
public class ArcbladeAttributeHandlers {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        Player player = event.player;
        ItemStack mainHandItem = player.getMainHandItem();
        // 仅在服务端处理且手持 Arcblade 时生效
        if (!player.level.isClientSide && mainHandItem.getItem() == ArcBladeItemRegistry.ARCBLADE.get()) {
            // 获取当前最大生命值
            float maxHealth = player.getMaxHealth();
            // 计算目标生命值（50%）
            float targetHealth = maxHealth * 0.5f;
            // 强制设置当前生命值
            if (player.getHealth() > targetHealth) {
                player.setHealth(targetHealth);
            }
        }
    }
}