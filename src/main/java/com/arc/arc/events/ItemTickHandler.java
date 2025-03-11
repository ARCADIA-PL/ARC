package com.arc.arc.events;

import com.arc.arc.ArcItemRegistry;
import com.arc.arc.init.ArcEffectsRegistry;
import com.guhao.star.efmex.StarAnimations;
import com.p1nero.invincible.api.events.TimeStampedEvent;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.WOMSounds;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = "arc", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ItemTickHandler {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<UUID, Long> transformationEndTimes = new HashMap<>();
    private static final Map<UUID, Long> particleEffectEndTimes = new HashMap<>();
    private static final Map<UUID, Vec3> hexagramCenters = new HashMap<>(); // 存储每个玩家的法阵中心点

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
                    hexagramCenters.remove(playerUUID); // 移除法阵中心点
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
                    hexagramCenters.remove(playerUUID); // 移除法阵中心点
                } else {
                    // 生成粒子柱
                    Vec3 center = hexagramCenters.get(playerUUID);
                    if (center != null) {
                        spawnHexagramParticles(player, center); // 使用固定中心点生成六芒星粒子法阵
                    }
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
                long transformationDuration = effectLevel * 10L * 1000; // 持续时间 = 等级 * 10 秒
                long endTime = System.currentTimeMillis() + transformationDuration;
                transformationEndTimes.put(player.getUUID(), endTime);
                particleEffectEndTimes.put(player.getUUID(), System.currentTimeMillis() + 2000); // 粒子效果持续 1 秒

                // 记录法阵的中心点
                Vec3 center = player.position().add(0, 0.1, 0);
                hexagramCenters.put(player.getUUID(), center);


                // 执行形态切换逻辑
                transformArcblade(player);

                // 生成六芒星粒子法阵
                spawnHexagramParticles(player, center);

                // 播放动画
                if (player instanceof ServerPlayer serverPlayer) {
                    ServerPlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(serverPlayer, ServerPlayerPatch.class);
                    if (playerPatch != null) {
                        playerPatch.playAnimationSynchronized(WOMAnimations.SOLAR_AUTO_4_POLVORA, 0.2F);
                    }
                }

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

    private static void spawnHexagramParticles(Player player, Vec3 center) {
        if (player.level instanceof ServerLevel serverLevel) {
            double[] radii = {2.0, 4.0, 6.0}; // 三层六芒星的半径
            int points = 6; // 六芒星的顶点数
            double angleIncrement = 2 * Math.PI / points;
            // 生成多层六芒星
            for (int layer = 0; layer < radii.length; layer++) {
                double layerRadius = radii[layer]; // 当前层的半径
                double layerHeight = 0.2 * layer; // 每层高度递增
                // 生成六芒星的顶点
                for (int i = 0; i < points; i++) {
                    double angle = i * angleIncrement;
                    double x = center.x + layerRadius * Math.cos(angle);
                    double z = center.z + layerRadius * Math.sin(angle);
                    serverLevel.sendParticles(ParticleTypes.FIREWORK, x, center.y + layerHeight, z, 5, 0, 0, 0, 0); // 火焰粒子
                    serverLevel.sendParticles(ParticleTypes.ENCHANT, x, center.y + layerHeight, z, 5, 0, 0, 0, 0); // 附魔粒子
                }
                // 生成六芒星的连线（正三角形）
                for (int i = 0; i < points; i += 2) {
                    double angle1 = i * angleIncrement;
                    double angle2 = (i + 2) % points * angleIncrement;
                    double x1 = center.x + layerRadius * Math.cos(angle1);
                    double z1 = center.z + layerRadius * Math.sin(angle1);
                    double x2 = center.x + layerRadius * Math.cos(angle2);
                    double z2 = center.z + layerRadius * Math.sin(angle2);
                    // 在两点之间生成粒子
                    int steps = 20; // 两点之间的粒子数量
                    for (int j = 0; j <= steps; j++) {
                        double t = (double) j / steps;
                        double x = x1 + (x2 - x1) * t;
                        double z = z1 + (z2 - z1) * t;
                        serverLevel.sendParticles(ParticleTypes.WAX_OFF, x, center.y + layerHeight, z, 1, 0, 0, 0, 0); // 闪电粒子
                        serverLevel.sendParticles(ParticleTypes.PORTAL, x, center.y + layerHeight, z, 1, 0, 0, 0, 0); // 金色粒子
                    }
                }
                // 生成六芒星的连线（倒三角形）
                for (int i = 1; i < points; i += 2) {
                    double angle1 = i * angleIncrement;
                    double angle2 = (i + 2) % points * angleIncrement;
                    double x1 = center.x + layerRadius * Math.cos(angle1);
                    double z1 = center.z + layerRadius * Math.sin(angle1);
                    double x2 = center.x + layerRadius * Math.cos(angle2);
                    double z2 = center.z + layerRadius * Math.sin(angle2);
                    // 在两点之间生成粒子
                    int steps = 20; // 两点之间的粒子数量
                    for (int j = 0; j <= steps; j++) {
                        double t = (double) j / steps;
                        double x = x1 + (x2 - x1) * t;
                        double z = z1 + (z2 - z1) * t;
                        serverLevel.sendParticles(ParticleTypes.WAX_OFF, x, center.y + layerHeight, z, 1, 0, 0, 0, 0); // 闪电粒子
                        serverLevel.sendParticles(ParticleTypes.PORTAL, x, center.y + layerHeight, z, 1, 0, 0, 0, 0); // 金色粒子
                    }
                }
            }
            // 生成粒子环
            double ringRadius = 6.0; // 粒子环的半径
            int ringSteps = 20; // 粒子环的粒子数量
            double ringSpeed = 0.2; // 旋转速度
            double ringAngle = System.currentTimeMillis() * ringSpeed % (2 * Math.PI); // 根据时间计算旋转角度
            for (int i = 0; i < ringSteps; i++) {
                double angle = ringAngle + i * (2 * Math.PI / ringSteps);
                double x = center.x + ringRadius * Math.cos(angle);
                double z = center.z + ringRadius * Math.sin(angle);
                // 生成粒子环的粒子
                serverLevel.sendParticles(ParticleTypes.FIREWORK, x, center.y + 0.1, z, 1, 0, 0, 0, 0); // 白色粒子
                serverLevel.sendParticles(ParticleTypes.DRIPPING_OBSIDIAN_TEAR, x, center.y + 0.1, z, 1, 0, 0, 0, 0); // 灵魂火焰粒子
                // 生成粒子柱
                for (double y = 0; y < 1.3; y += 0.2) { // 粒子柱高度为 1 格
                    serverLevel.sendParticles(ParticleTypes.LANDING_LAVA, x, center.y + y, z, 1, 0, 0, 0, 0); // 紫色粒子
                    serverLevel.sendParticles(ParticleTypes.ENCHANT, x, center.y + y, z, 1, 0, 0, 0, 0); // 金色粒子
                }
            }
        }
    }

}
