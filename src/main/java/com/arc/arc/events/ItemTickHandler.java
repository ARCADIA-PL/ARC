package com.arc.arc.events;

import com.arc.arc.ArcItemRegistry;
import com.arc.arc.init.ArcEffectsRegistry;
import com.guhao.star.efmex.StarAnimations;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = "arc", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ItemTickHandler {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<UUID, Long> transformationEndTimes = new HashMap<>();
    private static final Map<UUID, Long> particleEffectEndTimes = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) { // 确保在每 tick 的末尾执行
            Player player = event.player;
            UUID playerUUID = player.getUUID();
            // 检查玩家是否正在变形
            handleTransformation(player, playerUUID);
            // 检查玩家是否在粒子效果持续时间内
            handleParticleEffects(player, playerUUID);
            // 检查玩家手持的物品
            handleHeldItem(player, playerUUID);
        }
    }

    @SubscribeEvent
    public static void onItemPickup(PlayerEvent.ItemPickupEvent event) {
        Player player = event.getPlayer();
        ItemStack pickedUpItem = event.getStack();

        // 如果捡起的物品是 ARCBLADETransformed
        if (pickedUpItem.is(ArcItemRegistry.ARCBLADETransformed.get())) {
            UUID playerUUID = player.getUUID();

            // 如果玩家不在变形倒计时内，立即变回 Arcblade
            if (!transformationEndTimes.containsKey(playerUUID)) {
                revertArcblade(player);
                LOGGER.info("Reverted ARCBLADETransformed to Arcblade for player: " + player.getName().getString());
            }
        }
    }

    @SubscribeEvent
    public static void onPotionEffectAdded(PotionEvent.PotionAddedEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (event.getPotionEffect().getEffect() == ArcEffectsRegistry.ArcbladeTransformEffect.get()) {
                int effectLevel = event.getPotionEffect().getAmplifier() + 1; // 获取 BUFF 等级
                long transformationDuration = effectLevel * 10L * 1000; // 持续时间 = 等级 * 10 秒
                long endTime = System.currentTimeMillis() + transformationDuration;
                transformationEndTimes.put(player.getUUID(), endTime);
                particleEffectEndTimes.put(player.getUUID(), System.currentTimeMillis() + 2000); // 粒子效果持续 1 秒
                // 执行形态切换逻辑
                transformArcblade(player);
                player.addEffect(new MobEffectInstance(ArcEffectsRegistry.StellarisHexagram2.get(), 30, 1, false, false)); // 跳跃提升
                // 播放第一个动画
                playFirstAnimation(player);
                LOGGER.info("Transformed Arcblade for player: " + player.getName().getString() + " for " + transformationDuration / 1000 + " seconds");
                // 在 1 秒后播放第二个动画（打断第一个动画）
                new Thread(() -> {
                    try {
                        Thread.sleep(1000); // 延迟 1 秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    playSecondAnimation(player);
                }).start();
            }
        }
    }

    private static void handleTransformation(Player player, UUID playerUUID) {
        if (transformationEndTimes.containsKey(playerUUID)) {
            long currentTime = System.currentTimeMillis();
            long endTime = transformationEndTimes.get(playerUUID);
            // 如果当前时间超过变形结束时间，执行恢复逻辑
            if (currentTime >= endTime) {
                revertArcblade(player);
                transformationEndTimes.remove(playerUUID);
                LOGGER.info("Reverted Arcblade for player: " + player.getName().getString());
            } else {
                // 在变形期间添加跳跃提升和缓降效果
                player.addEffect(new MobEffectInstance(MobEffects.JUMP, 40, 2, false, false)); // 跳跃提升
                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 40, 0, false, false)); // 缓降
            }
        }
    }

    private static void handleParticleEffects(Player player, UUID playerUUID) {
        if (particleEffectEndTimes.containsKey(playerUUID)) {
            long currentTime = System.currentTimeMillis();
            long endTime = particleEffectEndTimes.get(playerUUID);
            // 如果当前时间超过粒子效果结束时间，移除记录
            if (currentTime >= endTime) {
                particleEffectEndTimes.remove(playerUUID);
            }
        }
    }

    private static void handleHeldItem(Player player, UUID playerUUID) {
        ItemStack heldItem = player.getMainHandItem();
        if (heldItem.is(ArcItemRegistry.ARCBLADETransformed.get())) {
            // 如果玩家手持 ARCBLADETransformed 且不在变形倒计时内，立即变回 Arcblade
            if (!transformationEndTimes.containsKey(playerUUID)) {
                revertArcblade(player);
                LOGGER.info("Reverted ARCBLADETransformed to Arcblade for player: " + player.getName().getString());
            }
        }
    }

    private static void playFirstAnimation(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            ServerPlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(serverPlayer, ServerPlayerPatch.class);
            if (playerPatch != null) {
                playerPatch.playAnimationSynchronized(StarAnimations.YAMATO_COUNTER1, 0.2F);
                player.playSound(EpicFightSounds.WHOOSH_SHARP, 1.0F, 1.0F); // 播放音效
            }
        }
    }

    private static void playSecondAnimation(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            ServerPlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(serverPlayer, ServerPlayerPatch.class);
            if (playerPatch != null) {
                playerPatch.playAnimationSynchronized(WOMAnimations.SOLAR_AUTO_4_POLVORA, 0.2F);
            }
        }
    }

    private static void transformArcblade(Player player) {
        LOGGER.info("Transforming Arcblade for player: " + player.getName().getString());
        Level level = player.level;

        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);

            if (stack.is(ArcItemRegistry.ARCBLADE.get())) {
                ItemStack transformedStack = new ItemStack(ArcItemRegistry.ARCBLADETransformed.get());
                transformedStack.setTag(stack.getTag());
                player.getInventory().setItem(slot, transformedStack);
                LOGGER.info("Successfully transformed ARCBLADE to ARCBLADETransformed for player: " + player.getName().getString());
                break;
            }
        }

        player.inventoryMenu.broadcastChanges();
    }

    private static void revertArcblade(Player player) {
        LOGGER.info("Reverting Arcblade for player: " + player.getName().getString());
        Level level = player.level;

        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);

            if (stack.is(ArcItemRegistry.ARCBLADETransformed.get())) {
                ItemStack normalStack = new ItemStack(ArcItemRegistry.ARCBLADE.get());
                normalStack.setTag(stack.getTag());
                player.getInventory().setItem(slot, normalStack);
                LOGGER.info("Successfully reverted ARCBLADETransformed to ARCBLADE for player: " + player.getName().getString());

                // 播放动画
                if (player instanceof ServerPlayer serverPlayer) {
                    ServerPlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(serverPlayer, ServerPlayerPatch.class);
                    if (playerPatch != null) {
                        playerPatch.playAnimationSynchronized(WOMAnimations.MOONLESS_REWINDER, 0.0F);
                    }
                }
                break;
            }
        }

        player.inventoryMenu.broadcastChanges();
    }
}
