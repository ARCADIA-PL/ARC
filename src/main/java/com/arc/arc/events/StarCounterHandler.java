package com.arc.arc.events;

import com.arc.arc.Registries.ArcEffectsRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "arc", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StarCounterHandler {

    // 标记键
    public static final ResourceLocation HAS_TRIGGERED_STARGAZING = new ResourceLocation("arc", "has_triggered_stargazing");

    /**
     * 当玩家获得效果时触发
     */
    @SubscribeEvent
    public static void onPotionAdded(PotionEvent.PotionAddedEvent event) {
        LivingEntity entity = event.getEntityLiving();
        MobEffectInstance newEffect = event.getPotionEffect();

        // 检查是否是 StarCounter 效果
        if (newEffect.getEffect() == ArcEffectsRegistry.StarCounter.get()) {
            // 检查是否已经触发过
            if (!entity.getPersistentData().getBoolean(HAS_TRIGGERED_STARGAZING.toString())) {
                // 获取当前的 Stargazing 效果
                MobEffectInstance stargazingEffect = entity.getEffect(ArcEffectsRegistry.Stargazing.get());

                // 计算新的等级
                int newLevel = (stargazingEffect != null) ? stargazingEffect.getAmplifier() + 1 : 0; // 默认等级为 0

                // 移除旧的 Stargazing 效果（如果有）
                entity.removeEffect(ArcEffectsRegistry.Stargazing.get());

                // 添加新的 Stargazing 效果
                entity.addEffect(new MobEffectInstance(
                        ArcEffectsRegistry.Stargazing.get(),
                        600, // 持续时间固定为 30 秒（600 ticks）
                        newLevel, // 新等级
                        false, // 是否显示粒子效果
                        false, // 是否显示图标
                        true   // 是否保存到 NBT
                ));

                // 设置标记，防止重复触发
                entity.getPersistentData().putBoolean(HAS_TRIGGERED_STARGAZING.toString(), true);
            }
        }
    }

    /**
     * 当效果结束时触发
     */
    @SubscribeEvent
    public static void onPotionExpired(PotionEvent.PotionExpiryEvent event) {
        LivingEntity entity = event.getEntityLiving();
        MobEffectInstance expiredEffect = event.getPotionEffect();

        // 检查是否是 StarCounter 效果
        if (expiredEffect.getEffect() == ArcEffectsRegistry.StarCounter.get()) {
            // 重置标记
            entity.getPersistentData().putBoolean(HAS_TRIGGERED_STARGAZING.toString(), false);
        }
    }

    /**
     * 当玩家退出游戏时触发
     */
    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getPlayer();
        player.getPersistentData().putBoolean(HAS_TRIGGERED_STARGAZING.toString(), false);
    }
}
