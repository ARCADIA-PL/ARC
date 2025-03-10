package com.arc.arc.events;

import com.arc.arc.ArcItemRegistry;
import com.arc.arc.init.ArcEffectsRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
            if (transformationEndTimes.containsKey(playerUUID)) {
                long currentTime = System.currentTimeMillis();
                long endTime = transformationEndTimes.get(playerUUID);

                // 如果当前时间超过变形结束时间，执行恢复逻辑
                if (currentTime >= endTime) {
                    revertArcblade(player);
                    transformationEndTimes.remove(playerUUID);
                    LOGGER.info("Reverted Arcblade for player: " + player.getName().getString());
                }
            }

            // 检查玩家是否在粒子效果持续时间内
            if (particleEffectEndTimes.containsKey(playerUUID)) {
                long currentTime = System.currentTimeMillis();
                long endTime = particleEffectEndTimes.get(playerUUID);

                // 如果当前时间超过粒子效果结束时间，移除记录
                if (currentTime >= endTime) {
                    particleEffectEndTimes.remove(playerUUID);
                } else {
                    // 生成粒子柱
                    spawnPentagramParticles(player); // 持续生成五芒星粒子法阵
                }
            }

            // 检查玩家手持的物品
            ItemStack heldItem = player.getMainHandItem();
            if (heldItem.is(ArcItemRegistry.ARCBLADETransformed.get())) {
                // 如果玩家手持 ARCBLADETransformed 且不在变形倒计时内，立即变回 Arcblade
                if (!transformationEndTimes.containsKey(playerUUID)) {
                    revertArcblade(player);
                    LOGGER.info("Reverted ARCBLADETransformed to Arcblade for player: " + player.getName().getString());
                }
            }
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
                long transformationDuration = effectLevel * 4 * 1000; // 持续时间 = 等级 * 10 秒
                long endTime = System.currentTimeMillis() + transformationDuration;

                transformationEndTimes.put(player.getUUID(), endTime);
                particleEffectEndTimes.put(player.getUUID(), System.currentTimeMillis() + 15000); // 粒子效果持续 15 秒

                transformArcblade(player);
                spawnPentagramParticles(player); // 生成五芒星粒子法阵
                LOGGER.info("Transformed Arcblade for player: " + player.getName().getString() + " for " + transformationDuration / 1000 + " seconds");
            }
        }
    }


    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUUID();
        if (transformationEndTimes.containsKey(playerUUID)) {
            revertArcblade(player);
            transformationEndTimes.remove(playerUUID);
            LOGGER.info("Cancelled transformation for player: " + player.getName().getString());
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
                break;
            }
        }

        player.inventoryMenu.broadcastChanges();
    }

    private static void spawnPentagramParticles(Player player) {
        if (player.level instanceof ServerLevel serverLevel) {
            Vec3 center = player.position().add(0, 0.1, 0); // 玩家脚下的中心点，稍微抬高避免被地面遮挡
            double[] radii = {2.0, 4.0, 6.0}; // 三层五芒星的半径
            int points = 5; // 五芒星的顶点数
            double angleIncrement = 2 * Math.PI / points;

            // 生成多层五芒星
            for (int layer = 0; layer < radii.length; layer++) {
                double layerRadius = radii[layer]; // 当前层的半径
                double layerHeight = 0.2 * layer; // 每层高度递增

                // 生成五芒星的顶点
                for (int i = 0; i < points; i++) {
                    double angle = i * angleIncrement;
                    double x = center.x + layerRadius * Math.cos(angle);
                    double z = center.z + layerRadius * Math.sin(angle);
                    serverLevel.sendParticles(ParticleTypes.FIREWORK, x, center.y + layerHeight, z, 5, 0, 0, 0, 0); // 火焰粒子
                    serverLevel.sendParticles(ParticleTypes.ENCHANT, x, center.y + layerHeight, z, 5, 0, 0, 0, 0); // 附魔粒子
                }

                // 生成五芒星的连线
                for (int i = 0; i < points; i++) {
                    double angle1 = i * angleIncrement;
                    double angle2 = (i + 2) % points * angleIncrement; // 五芒星的连线规则
                    double x1 = center.x + layerRadius * Math.cos(angle1);
                    double z1 = center.z + layerRadius * Math.sin(angle1);
                    double x2 = center.x + layerRadius * Math.cos(angle2);
                    double z2 = center.z + layerRadius * Math.sin(angle2);

                    // 在两点之间生成粒子
                    int steps = 30; // 两点之间的粒子数量
                    for (int j = 0; j <= steps; j++) {
                        double t = (double) j / steps;
                        double x = x1 + (x2 - x1) * t;
                        double z = z1 + (z2 - z1) * t;
                        serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, x, center.y + layerHeight, z, 1, 0, 0, 0, 0); // 闪电粒子
                        serverLevel.sendParticles(ParticleTypes.PORTAL, x, center.y + layerHeight, z, 1, 0, 0, 0, 0); // 金色粒子
                    }
                }
            }

            // 生成粒子环
            double ringRadius = 6.0; // 粒子环的半径
            int ringSteps = 30; // 粒子环的粒子数量
            double ringSpeed = 0.2; // 旋转速度
            double ringAngle = System.currentTimeMillis() * ringSpeed % (2 * Math.PI); // 根据时间计算旋转角度

            for (int i = 0; i < ringSteps; i++) {
                double angle = ringAngle + i * (2 * Math.PI / ringSteps);
                double x = center.x + ringRadius * Math.cos(angle);
                double z = center.z + ringRadius * Math.sin(angle);

                // 生成粒子环的粒子
                serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK, x, center.y + 0.1, z, 1, 0, 0, 0, 0); // 白色粒子
                serverLevel.sendParticles(ParticleTypes.DRAGON_BREATH, x, center.y + 0.1, z, 1, 0, 0, 0, 0); // 灵魂火焰粒子

                // 生成粒子柱
                for (double y = 0; y < 0.7; y += 0.2) { // 粒子柱高度为 1 格
                    serverLevel.sendParticles(ParticleTypes.SOUL, x, center.y + y, z, 1, 0, 0, 0, 0); // 紫色粒子
                    serverLevel.sendParticles(ParticleTypes.WAX_OFF, x, center.y + y, z, 1, 0, 0, 0, 0); // 金色粒子
                }
            }

        }
    }
}
