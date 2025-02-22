package com.arc.arc.client;

import com.arc.arc.sound.SoundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT) // 确保仅客户端加载此类
public class ClientSoundHandler {

    // 播放 5 级音效
    public static void playCombo5() {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player; //
        Minecraft.getInstance().player.playSound(
                SoundRegistry.COMBO_5.get(),
                1.0f, // 音量
                1.0f  // 音调
        );
        // 开始持续粒子效果
        ClientEffectTracker.startTracking(player);
        // 生成环绕粒子
        ClientParticleUtils.spawnParticlesAroundEntity(
                Minecraft.getInstance().player,
                ParticleTypes.END_ROD,
                50,
                2
        );
    }

    // 播放 10 级音效
    public static void playCombo10() {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player; //
        Minecraft.getInstance().player.playSound(
                SoundRegistry.COMBO_10.get(),
                1.2f, // 更大音量
                0.9f  // 更低音调
        );
        // 开始持续粒子效果
        ClientEffectTracker.startTracking(player);
        // 生成环绕粒子
        ClientParticleUtils.spawnParticlesAroundEntity(
                Minecraft.getInstance().player,
                ParticleTypes.WAX_OFF,
                50,
                2
        );
    }
}


