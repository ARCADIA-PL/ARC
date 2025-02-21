package com.arc.arc.client;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@OnlyIn(Dist.CLIENT)
public class ClientEffectTracker {
    private static final Set<UUID> playersWithParticles = new HashSet<>();

    // 添加玩家到跟踪列表
    public static void startTracking(Player player) {
        playersWithParticles.add(player.getUUID());
    }

    // 移除玩家并停止粒子
    public static void stopTracking(Player player) {
        playersWithParticles.remove(player.getUUID());
    }

    // 检查是否正在跟踪
    public static boolean isTracking(Player player) {
        return playersWithParticles.contains(player.getUUID());
    }

}
