package com.arc.arc.events;

import com.arc.arc.Registries.ArcEffectsRegistry;
import com.arc.arc.ParticleEffect.HorizontalHexagramParticleEffect; // 导入封装好的法阵粒子效果类
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "arc")
public class WeaponParticlesAlpha {

    @SubscribeEvent
    public static void onEffectAdded(PotionEvent.PotionAddedEvent event) {
        // 确保是玩家且是自定义 Buff
        if (event.getEntity() instanceof Player player && event.getPotionEffect().getEffect() == ArcEffectsRegistry.HEXAGRAMAlapha.get()) {
            if (!player.getLevel().isClientSide()) { // 确保在服务端运行
                // 计算玩家前方两格的位置
                double distance = 0.5; // 距离玩家两格
                double forwardX = -Math.sin(Math.toRadians(player.getYRot())) * distance; // 计算 X 方向偏移
                double forwardZ = Math.cos(Math.toRadians(player.getYRot())) * distance; // 计算 Z 方向偏移
                Vec3 frontCenter = new Vec3(player.getX() + forwardX, player.getY(), player.getZ() + forwardZ); // 新的中心点
                // 生成六芒星法阵
                HorizontalHexagramParticleEffect.spawnHexagramParticles(player, frontCenter, 5, 0.2); // 调用封装好的法阵粒子效果
            }
        }
    }
}