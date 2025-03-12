package com.arc.arc.events;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "arc") // 替换为你的 Mod ID
public class PlayerAirborneParticleEvent {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        // 确保只在服务端运行
        if (event.phase == TickEvent.Phase.END || event.player.getLevel().isClientSide()) {
            return;
        }

        // 获取玩家实体
        Player player = event.player;

        // 检测玩家是否处于滞空状态
        if (isPlayerAirborne(player)) {
            // 获取玩家所在的世界
            if (player.level instanceof ServerLevel serverLevel) {
                // 获取玩家脚底位置
                double x = player.getX();
                double y = player.getY() - 0.5; // 粒子生成在脚下
                double z = player.getZ();

                // 生成紫色粒子
                serverLevel.sendParticles(ParticleTypes.PORTAL, x, y, z, 5, 0.5, 0.1, 0.5, 0.02);
            }
        }
    }

    /**
     * 检测玩家是否处于滞空状态。
     *
     * @param player 玩家实体
     * @return 如果玩家处于滞空状态，返回 true；否则返回 false
     */
    private static boolean isPlayerAirborne(Player player) {
        // 检测玩家是否在地面、水中、梯子上、骑乘或滑翔
        boolean isOnGround = player.isOnGround();      // 在地面
        boolean isInWater = player.isInWater();       // 在水中
        boolean isOnClimbable = player.onClimbable(); // 在梯子上
        boolean isRiding = player.isPassenger();      // 骑乘
        boolean isGliding = player.isFallFlying();     // 滑翔

        // 如果玩家不处于这些状态，则处于滞空状态
        return !isOnGround && !isInWater && !isOnClimbable && !isRiding && !isGliding;
    }
}
