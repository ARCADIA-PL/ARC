package com.arc.arc.events;

import com.arc.arc.ArcMod;
import com.arc.arc.Registries.ArcEffectsRegistry;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
@Mod.EventBusSubscriber(modid = ArcMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerTickHandler {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) { // 确保在每 tick 结束时执行
            Player player = event.player;
            Level world = player.getLevel();
            // 检查玩家是否拥有自定义状态效果
            if (player.hasEffect(ArcEffectsRegistry.Strike.get())) {
                // 计算玩家面前的指定位置
                double spawnDistance = 3.0; // 距离玩家的距离
                Vec3 spawnPos = player.getEyePosition().add(player.getLookAngle().scale(spawnDistance));
                Entity entity = EntityHandler.SUNSTRIKE.get().create(world);
                if (entity != null) {
                    entity.setPos(spawnPos.x, spawnPos.y, spawnPos.z);
                    world.addFreshEntity(entity);
                }
                // 移除状态效果，确保只生成一次
                player.removeEffect(ArcEffectsRegistry.Strike.get());
            }
        }
    }
}

