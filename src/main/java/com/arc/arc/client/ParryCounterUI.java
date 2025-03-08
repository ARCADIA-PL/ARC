package com.arc.arc.client;

import com.arc.arc.ArcMod;
import com.arc.arc.events.ParryCounterEvent;
import com.arc.arc.init.ArcEffectsRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ArcMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ParryCounterUI {
    private static final int TEXT_Y_OFFSET = 50; // 调整文本垂直偏移量（提升高度）
    private static int currentParryCount = 0; // 当前招架计数

    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        // 仅在渲染文本层时执行
        if (event.getType() != RenderGameOverlayEvent.ElementType.TEXT) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;

        // 检查玩家和药水效果是否为 null
        if (player != null && ArcEffectsRegistry.StellarisParryCounter.get() != null) {
            // 检查是否存在药水效果
            MobEffectInstance effect = player.getEffect(ArcEffectsRegistry.StellarisParryCounter.get());
            if (effect != null) {
                // 计算招架进度百分比
                int progressPercent = Math.min(currentParryCount * 10, 100); // 每招架一次增加 10%，上限 100%

                // 渲染招架进度文本
                String text = "招架进度：" + progressPercent + "%";
                int textWidth = minecraft.font.width(text);
                int textX = (event.getWindow().getGuiScaledWidth() / 2) - (textWidth / 2); // 水平居中
                int textY = event.getWindow().getGuiScaledHeight() - TEXT_Y_OFFSET; // 文本位于物品栏上方

                PoseStack poseStack = event.getMatrixStack();
                minecraft.font.draw(poseStack, text, textX, textY, 0xFFFFFF); // 白色文本
            } else {
                // 如果药水效果不存在，清空招架计数
                currentParryCount = 0;
            }
        } else {
            // 如果玩家或药水效果为 null，清空招架计数
            currentParryCount = 0;
        }
    }

    @SubscribeEvent
    public static void onParryCounterEvent(ParryCounterEvent event) {
        // 更新当前招架计数
        currentParryCount = event.getParryCount();
    }
}
