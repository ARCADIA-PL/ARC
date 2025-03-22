package com.arc.arc.events;

import com.arc.arc.Registries.ArcEffectsRegistry;
import com.arc.arc.effect.RecorderA;
import com.arc.arc.effect.RecorderB;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.Util;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = "arc", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RecorderHandler {

    // 用于存储玩家获得 RecorderA 和 RecorderB 的次数
    private static final Map<UUID, int[]> playerRecorderCounts = new HashMap<>();

    // 用于存储第三和第四次的组合
    private static final Map<UUID, MobEffect[]> playerCombination = new HashMap<>();

    /**
     * 当玩家获得效果时触发
     */
    @SubscribeEvent
    public static void onPotionAdded(PotionEvent.PotionAddedEvent event) {
        // 检查事件是否有效
        if (event == null || event.getPotionEffect() == null || event.getEntityLiving() == null) {
            return;
        }

        LivingEntity entity = event.getEntityLiving();
        MobEffectInstance newEffect = event.getPotionEffect();

        // 检查是否是 RecorderA 或 RecorderB
        if (newEffect.getEffect() instanceof RecorderA || newEffect.getEffect() instanceof RecorderB) {
            if (entity instanceof Player) {
                Player player = (Player) entity;
                UUID playerId = player.getUUID();

                // 获取效果的标记
                boolean hasBeenCounted = false;
                if (newEffect.getEffect() instanceof RecorderA) {
                    RecorderA recorderA = (RecorderA) newEffect.getEffect();
                    hasBeenCounted = recorderA.hasBeenCounted();
                } else if (newEffect.getEffect() instanceof RecorderB) {
                    RecorderB recorderB = (RecorderB) newEffect.getEffect();
                    hasBeenCounted = recorderB.hasBeenCounted();
                }

                // 如果效果未被计数，则进行计数
                if (!hasBeenCounted) {
                    // 获取玩家的 Recorder 计数
                    int[] counts = playerRecorderCounts.getOrDefault(playerId, new int[]{0, 0});

                    // 更新计数
                    if (newEffect.getEffect() instanceof RecorderA) {
                        counts[0]++; // RecorderA 计数
                        player.sendMessage(new TextComponent("§a获得 RecorderA！当前 RecorderA 计数: " + counts[0]), Util.NIL_UUID);
                        ((RecorderA) newEffect.getEffect()).setCounted(true); // 设置标记为已计数
                    } else if (newEffect.getEffect() instanceof RecorderB) {
                        counts[1]++; // RecorderB 计数
                        player.sendMessage(new TextComponent("§b获得 RecorderB！当前 RecorderB 计数: " + counts[1]), Util.NIL_UUID);
                        ((RecorderB) newEffect.getEffect()).setCounted(true); // 设置标记为已计数
                    }

                    // 更新计数到 Map
                    playerRecorderCounts.put(playerId, counts);

                    // 获取总计数
                    int totalCount = counts[0] + counts[1];

                    // 提升 Stargazing 的等级
                    if (totalCount > 0 && totalCount <= 4) {
                        // 移除旧的 Stargazing 效果（如果有）
                        player.removeEffect(ArcEffectsRegistry.Stargazing.get());

                        // 添加新的 Stargazing 效果
                        player.addEffect(new MobEffectInstance(
                                ArcEffectsRegistry.Stargazing.get(),
                                600, // 持续时间固定为 30 秒（600 ticks）
                                totalCount - 1, // 等级从 0 开始
                                false, // 是否显示粒子效果
                                false, // 是否显示图标
                                true   // 是否保存到 NBT
                        ));

                        // 发送 Stargazing 等级提升消息
                        player.sendMessage(new TextComponent("§eStargazing 等级提升至 " + totalCount + " 级！"), Util.NIL_UUID);
                    }

                    // 记录第三和第四次的组合
                    MobEffect[] combination = playerCombination.getOrDefault(playerId, new MobEffect[2]);
                    if (totalCount == 3) {
                        combination[0] = newEffect.getEffect(); // 记录第三次的效果
                        playerCombination.put(playerId, combination);
                    } else if (totalCount == 4) {
                        combination[1] = newEffect.getEffect(); // 记录第四次的效果
                        playerCombination.put(playerId, combination);

                        // 根据第三和第四次的组合给予 StarCraker
                        int starCrakerLevel = getStarCrakerLevel(combination);
                        if (starCrakerLevel > 0) {
                            // 移除旧的 StarCraker 效果（如果有）
                            player.removeEffect(ArcEffectsRegistry.StarCraker.get());

                            // 添加新的 StarCraker 效果
                            player.addEffect(new MobEffectInstance(
                                    ArcEffectsRegistry.StarCraker.get(),
                                    600, // 持续时间固定为 30 秒（600 ticks）
                                    starCrakerLevel - 1, // 等级从 0 开始
                                    false, // 是否显示粒子效果
                                    false, // 是否显示图标
                                    true   // 是否保存到 NBT
                            ));

                            // 发送组合结果消息
                            String combinationDescription = getCombinationDescription(combination);
                            player.sendMessage(new TextComponent("§6组合完成！组合结果: " + combinationDescription + "，获得 " + starCrakerLevel + " 级 StarCraker！"), Util.NIL_UUID);
                        }

                        // 重置计数和组合
                        playerRecorderCounts.put(playerId, new int[]{0, 0});
                        playerCombination.put(playerId, new MobEffect[2]);
                    }
                }
            }
        }
    }

    /**
     * 当玩家失去效果时触发
     */
    @SubscribeEvent
    public static void onPotionRemoved(PotionEvent.PotionRemoveEvent event) {
        // 检查事件是否有效
        if (event == null || event.getPotionEffect() == null || event.getEntityLiving() == null) {
            return;
        }

        LivingEntity entity = event.getEntityLiving();
        MobEffectInstance removedEffect = event.getPotionEffect();

        // 检查是否是 RecorderA 或 RecorderB
        if (removedEffect.getEffect() instanceof RecorderA) {
            ((RecorderA) removedEffect.getEffect()).setCounted(false); // 重置标记
        } else if (removedEffect.getEffect() instanceof RecorderB) {
            ((RecorderB) removedEffect.getEffect()).setCounted(false); // 重置标记
        }
    }

    /**
     * 根据第三和第四次的组合计算 StarCraker 的等级
     */
    private static int getStarCrakerLevel(MobEffect[] combination) {
        if (combination[0] instanceof RecorderA && combination[1] instanceof RecorderA) {
            return 2; // 第三次和第四次都是 RecorderA -> 2 级 StarCraker
        } else if (combination[0] instanceof RecorderB && combination[1] instanceof RecorderB) {
            return 3; // 第三次和第四次都是 RecorderB -> 3 级 StarCraker
        } else if (combination[0] instanceof RecorderA && combination[1] instanceof RecorderB) {
            return 1; // 第三次 RecorderA，第四次 RecorderB -> 1 级 StarCraker
        } else if (combination[0] instanceof RecorderB && combination[1] instanceof RecorderA) {
            return 4; // 第三次 RecorderB，第四次 RecorderA -> 4 级 StarCraker
        }
        return 0; // 默认不给予 StarCraker
    }

    /**
     * 获取组合的描述
     */
    private static String getCombinationDescription(MobEffect[] combination) {
        if (combination[0] instanceof RecorderA && combination[1] instanceof RecorderA) {
            return "RecorderA + RecorderA";
        } else if (combination[0] instanceof RecorderB && combination[1] instanceof RecorderB) {
            return "RecorderB + RecorderB";
        } else if (combination[0] instanceof RecorderA && combination[1] instanceof RecorderB) {
            return "RecorderA + RecorderB";
        } else if (combination[0] instanceof RecorderB && combination[1] instanceof RecorderA) {
            return "RecorderB + RecorderA";
        }
        return "未知组合";
    }
}
