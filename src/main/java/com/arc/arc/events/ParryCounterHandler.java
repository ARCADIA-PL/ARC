package com.arc.arc.events;

import com.arc.arc.ArcMod;
import com.arc.arc.init.ArcEffectsRegistry;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Mod.EventBusSubscriber(modid = ArcMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ParryCounterHandler {
    // 存储玩家的招架次数
    private static final Map<UUID, Integer> playerParryCounts = new HashMap<>();
    // 存储已经注册监听器的玩家
    private static final Map<UUID, Boolean> registeredPlayers = new HashMap<>();
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
                    listener.addEventListener(EventType.HURT_EVENT_PRE, UUID.randomUUID(), (hurtEvent) -> {
                        // 检查玩家是否有药水效果
                        MobEffectInstance effect = player.getEffect(ArcEffectsRegistry.StellarisParryCounter.get());
                        if (effect != null) {
                            if (hurtEvent.isParried()) {
                                // 获取当前招架次数
                                int count = playerParryCounts.getOrDefault(playerId, 0) + 1;
                                playerParryCounts.put(playerId, count);
                                // 获取当前药水效果等级
                                int level = effect.getAmplifier(); // 当前等级（默认 0 级）
                                if (level < 9) { // 如果当前等级小于 9，可以继续增加等级
                                    // 增加等级并刷新持续时间为 10 秒
                                    player.addEffect(new MobEffectInstance(ArcEffectsRegistry.StellarisParryCounter.get(), 200, level + 1)); // 10 秒 = 200 ticks
                                } else {
                                    // 如果等级达到 9（即第 10 次招架），清除效果并重置计数
                                    player.removeEffect(ArcEffectsRegistry.StellarisParryCounter.get());
                                    playerParryCounts.remove(playerId);
                                    player.sendMessage(new TextComponent("招架计数已重置！"), player.getUUID());
                                }
                                // 发送招架成功消息
                                player.sendMessage(new TextComponent("成功招架 " + count + " 次"), player.getUUID());
                            }
                        }
                    });
                    // 标记该玩家已经注册过监听器
                    registeredPlayers.put(playerId, true);
                }
            }
        }
    }
}







