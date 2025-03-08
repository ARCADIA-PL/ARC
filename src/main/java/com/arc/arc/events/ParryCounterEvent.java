package com.arc.arc.events;

import com.arc.arc.ArcMod;
import com.arc.arc.init.ArcEffectsRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.phys.Vec3;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraftforge.common.MinecraftForge;
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
public class ParryCounterEvent extends PlayerEvent {
    private static final Map<UUID, Integer> playerParryCounts = new HashMap<>(); // 存储玩家招架次数的映射
    private static final Map<UUID, Boolean> registeredPlayers = new HashMap<>(); // 存储已注册监听器的玩家
    private static final Map<UUID, Boolean> particleEffectActive = new HashMap<>(); // 存储玩家是否激活粒子效果的映射

    private final int parryCount; // 当前的招架计数

    public ParryCounterEvent(ServerPlayer player, int parryCount) {
        super(player);
        this.parryCount = parryCount;
    }

    public int getParryCount() {
        return parryCount;
    }

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
                        if (player != null && ArcEffectsRegistry.StellarisParryCounter.get() != null) {
                            MobEffectInstance effect = player.getEffect(ArcEffectsRegistry.StellarisParryCounter.get());
                            if (effect != null) {
                                if (hurtEvent.isParried()) {
                                    // 获取当前招架次数
                                    int count = playerParryCounts.getOrDefault(playerId, 0);
                                    count++; // 增加招架计数
                                    playerParryCounts.put(playerId, count);

                                    // 触发招架计数事件
                                    MinecraftForge.EVENT_BUS.post(new ParryCounterEvent(player, count));

                                    // 检查招架计数是否达到 10
                                    if (count >= 10) {
                                        // 激活粒子效果
                                        particleEffectActive.put(playerId, true);
                                    }
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

    @SubscribeEvent
    public static void onPotionExpire(PotionEvent.PotionExpiryEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            // 检查药水效果是否是 StellarisParryCounter
            if (event.getPotionEffect() != null && event.getPotionEffect().getEffect() == ArcEffectsRegistry.StellarisParryCounter.get()) {
                // 清空该玩家的招架计数和粒子效果状态
                playerParryCounts.remove(player.getUUID());
                particleEffectActive.remove(player.getUUID());
            }
        }
    }

    @SubscribeEvent
    public static void onPotionRemove(PotionEvent.PotionRemoveEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            // 检查药水效果是否是 StellarisParryCounter
            if (event.getPotionEffect() != null && event.getPotionEffect().getEffect() == ArcEffectsRegistry.StellarisParryCounter.get()) {
                // 清空该玩家的招架计数和粒子效果状态
                playerParryCounts.remove(player.getUUID());
                particleEffectActive.remove(player.getUUID());
            }
        }
    }

    // 生成绿色粒子效果圈
    public static void spawnParticleCircle(ServerPlayer player) {
        if (particleEffectActive.getOrDefault(player.getUUID(), false)) {
            ServerLevel level = (ServerLevel) player.level;
            Vec3 playerPos = player.position();
            double radius = 0.5; // 粒子圈的半径
            int particleCount = 10; // 粒子数量

            for (int i = 0; i < particleCount; i++) {
                double angle = 2 * Math.PI * i / particleCount;
                double x = playerPos.x + radius * Math.cos(angle);
                double z = playerPos.z + radius * Math.sin(angle);
                level.sendParticles(ParticleTypes.END_ROD, x, playerPos.y, z, 1, 0, 0, 0, 0);
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.PlayerTickEvent.Phase.END && event.player instanceof ServerPlayer player) {
            // 检查玩家是否激活了粒子效果
            if (particleEffectActive.getOrDefault(player.getUUID(), false)) {
                // 生成绿色粒子效果圈
                spawnParticleCircle(player);
            }
        }
    }


}
