package com.arc.arc.events;

import com.arc.arc.ArcMod;
import com.arc.arc.Registries.ArcEffectsRegistry;
import com.arc.arc.ParticleEffect.VerticalHexagramParticleEffect; // 导入法阵生成类
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
            if (player.hasEffect(ArcEffectsRegistry.Splash.get())) {
                // 计算玩家面前两格的位置
                double yawRadians = Math.toRadians(player.getYRot()); // 将玩家的 yaw 角度转换为弧度
                double xOffset = -Math.sin(yawRadians) * 2; // 计算 X 轴偏移量
                double zOffset = Math.cos(yawRadians) * 2; // 计算 Z 轴偏移量

                // 法阵中心点位置
                Vec3 center = new Vec3(
                        player.getX() + xOffset, // X 坐标
                        player.getY() + 2.3,    // Y 坐标（玩家脚部高度 + 0.5）
                        player.getZ() + zOffset  // Z 坐标
                );

                // 生成垂直于地面的法阵
                VerticalHexagramParticleEffect.spawnVerticalHexagramParticles(player, center, 2.5, 0.01);

                // 移除玩家的 BUFF
                player.removeEffect(ArcEffectsRegistry.Splash.get());
            }
        }
    }
}
