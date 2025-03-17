package com.arc.arc.events;

import com.arc.arc.Registries.ArcEffectsRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "arc", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ArcBGMHandler {
    private static boolean isPlaying = false; // 用于跟踪音乐是否正在播放
    private static float originalRecordsVolume = 1.0f; // 保存唱片机原始音量
    private static float originalMusicVolume = 1.0f; // 保存音乐原始音量

    @SubscribeEvent
    public static void onPotionAdded(PotionEvent.PotionAddedEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            // 检查是否获得了 MagicCircle 的 BUFF
            if (event.getPotionEffect().getEffect() == ArcEffectsRegistry.MagicCircle.get()) {
                if (!isPlaying) {
                    playBGM();
                    isPlaying = true;
                }
            }
        }
    }
    @SubscribeEvent
    public static void onPotionExpiry(PotionEvent.PotionExpiryEvent event) {
        handleBuffRemoval(event);
    }
    @SubscribeEvent
    public static void onPotionRemove(PotionEvent.PotionRemoveEvent event) {
        handleBuffRemoval(event);
    }
    private static void handleBuffRemoval(PotionEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            // 检查 PotionEffect 是否为空
            if (event.getPotionEffect() != null) {
                // 检查是否失去了 MagicCircle 的 BUFF
                if (event.getPotionEffect().getEffect() == ArcEffectsRegistry.MagicCircle.get()) {
                    if (isPlaying) {
                        stopBGM();
                        isPlaying = false;
                    }
                }
            }
        }
    }
    private static void playBGM() {
        // 保存当前唱片机和音乐的音量
        originalRecordsVolume = Minecraft.getInstance().options.getSoundSourceVolume(SoundSource.RECORDS);
        originalMusicVolume = Minecraft.getInstance().options.getSoundSourceVolume(SoundSource.MUSIC);
        // 将唱片机和音乐的音量设置为 0
        Minecraft.getInstance().options.setSoundCategoryVolume(SoundSource.RECORDS, 0.0f);
        Minecraft.getInstance().options.setSoundCategoryVolume(SoundSource.MUSIC, 0.0f);
        // 创建音乐实例
        ResourceLocation soundLocation = new ResourceLocation("arc", "laststardust"); // 声音资源路径
        SoundSource soundSource = SoundSource.PLAYERS; // 声音类别
        float volume = 1.1f; // 音量 (0.0f 到 1.0f)
        float pitch = 1.0f; // 音调 (1.0f 为原始音调)
        boolean looping = false; // 是否循环
        int delay = 0; // 延迟 (ticks)
        SoundInstance.Attenuation attenuation = SoundInstance.Attenuation.NONE; // 衰减类型
        double x = 0.0, y = 4.0, z = 0.0; // 声音位置 (x, y, z)
        boolean relative = false; // 是否相对位置
        SimpleSoundInstance bgm = new SimpleSoundInstance(
                soundLocation, soundSource, volume, pitch, looping, delay, attenuation, x, y, z, relative
        );
        // 播放音乐
        Minecraft.getInstance().getSoundManager().play(bgm);
    }
    private static void stopBGM() {
        // 停止音乐
        Minecraft.getInstance().getSoundManager().stop(
                new ResourceLocation("arc", "laststardust"), // 音乐 ID
                SoundSource.PLAYERS
        );
        // 恢复唱片机和音乐的音量
        Minecraft.getInstance().options.setSoundCategoryVolume(SoundSource.RECORDS, originalRecordsVolume);
        Minecraft.getInstance().options.setSoundCategoryVolume(SoundSource.MUSIC, originalMusicVolume);
    }
}
