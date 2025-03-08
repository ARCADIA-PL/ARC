package com.arc.arc.client;

import com.arc.arc.ArcMod;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ArcMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ItemGlowRenderer {
    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        // 获取手持物品
        ItemStack heldItem = mc.player.getMainHandItem();

        // 检查手持物品是否有发光描边标记
        if (!heldItem.isEmpty()) {
            CompoundTag nbt = heldItem.getTag();
            if (nbt != null && nbt.getBoolean("GlowingOutline")) {
                // 渲染发光描边效果
                renderGlowingOutline(heldItem, event.getPoseStack(), event.getMultiBufferSource(), event.getPartialTicks());
            }
        }
    }

    private static void renderGlowingOutline(ItemStack itemStack, PoseStack poseStack, MultiBufferSource bufferSource, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        ItemRenderer itemRenderer = mc.getItemRenderer();

        // 设置渲染参数
        poseStack.pushPose();
        poseStack.translate(0.0, 0.0, 0.1); // 轻微偏移，避免与原始物品重叠

        // 渲染物品
        itemRenderer.renderStatic(
                itemStack,
                ItemTransforms.TransformType.GUI, // 渲染模式
                15728880, // 光照值
                OverlayTexture.NO_OVERLAY, // 覆盖纹理
                poseStack,
                bufferSource,
                0
        );

        poseStack.popPose();
    }
}
