package com.arc.arc.events;

import com.arc.arc.ArcMod;
import com.arc.arc.init.ArcEffectsRegistry;
import com.arc.arc.network.ComboSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = ArcMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerAttackCounterHandler {

    // 存储玩家上一次手持的物品
    private static final Map<UUID, ItemStack> lastHeldItems = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerAttack(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            if (player.hasEffect(ArcEffectsRegistry.HIT_COUNTER.get())) {
                MobEffectInstance effect = player.getEffect(ArcEffectsRegistry.HIT_COUNTER.get());
                int newLevel = 0;
                if (effect != null) {
                    newLevel = effect.getAmplifier() + 1;
                }
                // 仅在服务端处理
                if (player.level.isClientSide) return;
                // 获取玩家当前攻击计数BUFF
                MobEffectInstance currentEffect = player.getEffect(ArcEffectsRegistry.HIT_COUNTER.get());
                int currentLevel = (currentEffect != null) ? currentEffect.getAmplifier() : 1;

                int duration = (currentEffect != null) ? currentEffect.getDuration() : 30 * 20;

                MobEffectInstance newEffect = new MobEffectInstance(
                        ArcEffectsRegistry.HIT_COUNTER.get(),
                        10 * 20,
                        currentLevel + 1,
                        false,
                        true,
                        true
                );

                player.removeEffect(ArcEffectsRegistry.HIT_COUNTER.get());
                player.addEffect(newEffect);

                // 检测等级触发条件
                if (newLevel == 4 || newLevel == 9) {
                    ArcMod.CHANNEL.sendToServer(new ComboSoundPacket(newLevel));
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
                // 如果物品发生变化，触发 BUFF 移除逻辑
                onPlayerChangeItem(player);

                // 更新上一次手持的物品
                lastHeldItems.put(playerId, currentItem.copy());
            }
        }
    }

    // 处理玩家切换物品的逻辑
    private static void onPlayerChangeItem(Player player) {
        // 检查玩家是否有指定的 BUFF
        if (player.hasEffect(ArcEffectsRegistry.HIT_COUNTER.get())) {
            // 移除 BUFF
            player.removeEffect(ArcEffectsRegistry.HIT_COUNTER.get());

            }
        }
    }

