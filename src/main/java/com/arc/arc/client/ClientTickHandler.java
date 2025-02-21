package com.arc.arc.client;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = "yourmodid")
public class ClientTickHandler {

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent event) {
        if (event.phase == ClientTickEvent.Phase.END) {
            Player player = Minecraft.getInstance().player;
            if (player != null && ClientEffectTracker.isTracking(player)) {
                // 每 tick 生成粒子（20次/秒）
                ClientParticleUtils.spawnParticlesAroundEntity(
                        player,
                        ParticleTypes.FLAME,
                        5,  // 每次生成5个粒子
                        2  // 环绕半径
                );

            }

        }
    }
}
