package com.arc.arc.events;

import com.arc.arc.ArcMod;
import com.arc.arc.init.ArcEffectsRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = ArcMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ParryCounterEffectHandler {
    // 存储玩家的招架次数
    private static final Map<UUID, Integer> playerParryCounts = new HashMap<>();
    // 存储已经注册监听器的玩家
    private static final Map<UUID, Boolean> registeredPlayers = new HashMap<>();
    // 存储玩家上一次手持的物品
    private static final Map<UUID, ItemStack> lastHeldItems = new HashMap<>();
    // 药水效果的持续时间
    private static final int EFFECT_DURATION = 240; // 12 秒
    @SubscribeEvent
    public static void onHurtEvent(LivingHurtEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            UUID playerId = player.getUUID();
            // 获取玩家的 PlayerPatch
            PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
            if (playerPatch != null) {
                // 检查是否已经为该玩家注册过监听器
                if (!registeredPlayers.containsKey(playerId)) {
                    // 获取玩家的 PlayerEventListener
                    PlayerEventListener listener = playerPatch.getEventListener();
                    // 监听 HURT_EVENT_PRE 事件
                    listener.addEventListener(PlayerEventListener.EventType.HURT_EVENT_PRE, UUID.randomUUID(), (hurtEvent) -> {
                        // 检查玩家是否有药水效果
                        MobEffectInstance effect = player.getEffect(ArcEffectsRegistry.StellarisParryCounter.get());
                        if (effect != null) {
                            if (hurtEvent.isParried()) {
                                // 获取当前招架次数
                                int count = playerParryCounts.getOrDefault(playerId, 0);
                                // 如果招架次数未达到上限（10 次），则增加计数
                                if (count < 10) {
                                    count++;
                                    playerParryCounts.put(playerId, count);
                                    // 更新药水效果等级（等级 = 招架次数）
                                    int level = count; // 等级与招架次数一致
                                    // 更新药水效果（刷新持续时间）
                                    player.addEffect(new MobEffectInstance(ArcEffectsRegistry.StellarisParryCounter.get(), EFFECT_DURATION, level - 1));
                                } else {
                                    // 如果招架次数已达到上限（10 次），仅刷新持续时间
                                    player.addEffect(new MobEffectInstance(ArcEffectsRegistry.StellarisParryCounter.get(), EFFECT_DURATION, 9)); // 等级固定为 10（9 是等级索引）
                                }
                            }
                        }
                    });
                    // 标记该玩家已经注册过监听器
                    registeredPlayers.put(playerId, true);
                }
            }
        }
    }
    // 监听玩家 Tick 事件
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.player instanceof ServerPlayer player) {
            UUID playerId = player.getUUID();
            // 获取玩家当前手持的物品
            ItemStack currentItem = player.getMainHandItem();
            // 获取上一次手持的物品
            ItemStack lastItem = lastHeldItems.getOrDefault(playerId, ItemStack.EMPTY);
            // 检查物品是否发生变化
            if (!ItemStack.matches(currentItem, lastItem)) {
                // 如果物品发生变化，触发药水效果移除逻辑
                onPlayerChangeItem(player);
                // 更新上一次手持的物品
                lastHeldItems.put(playerId, currentItem.copy());
            }
        }
    }
    // 处理玩家切换物品的逻辑
    private static void onPlayerChangeItem(ServerPlayer player) {
        UUID playerId = player.getUUID();
        // 检查玩家是否有指定的药水效果
        MobEffectInstance effect = player.getEffect(ArcEffectsRegistry.StellarisParryCounter.get());
        if (effect != null) {
            // 移除药水效果
            player.removeEffect(ArcEffectsRegistry.StellarisParryCounter.get());
            // 重置招架计数
            playerParryCounts.remove(playerId);
        }
    }

    // 监听玩家死亡或退出游戏事件
    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getPlayer() instanceof ServerPlayer player) {
            UUID playerId = player.getUUID();
            // 重置玩家的招架次数
            playerParryCounts.remove(playerId);
            registeredPlayers.remove(playerId);
            lastHeldItems.remove(playerId);
        }
    }
    @SubscribeEvent
    public static void onPotionRemove(PotionEvent.PotionRemoveEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            UUID playerId = player.getUUID();
            if (event.getPotionEffect() != null && event.getPotionEffect().getEffect() == ArcEffectsRegistry.StellarisParryCounter.get()) {
                // 清空招架计数
                playerParryCounts.remove(playerId);
            }
        }
    }
    @SubscribeEvent
    public static void onPotionExpire(PotionEvent.PotionExpiryEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            UUID playerId = player.getUUID();
            if (event.getPotionEffect() != null && event.getPotionEffect().getEffect() == ArcEffectsRegistry.StellarisParryCounter.get()) {
                // 清空招架计数
                playerParryCounts.remove(playerId);
            }
        }
}}


