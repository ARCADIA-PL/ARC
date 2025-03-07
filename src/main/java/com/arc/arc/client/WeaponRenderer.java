package com.arc.arc.client;

import com.arc.arc.ArcMod;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
@Mod.EventBusSubscriber(modid = ArcMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class WeaponRenderer {
    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event) {
        ItemStack heldItem = null;
        if (Minecraft.getInstance().player != null) {
            heldItem = Minecraft.getInstance().player.getMainHandItem();
        }
        // 检查武器是否有 Glowing 标签
        if (heldItem != null && !heldItem.isEmpty() && heldItem.getTag() != null && heldItem.getTag().getBoolean("Glowing")) {
            // 渲染发光描边效果
            renderGlowingOutline(event.getPoseStack(), event.getMultiBufferSource(), heldItem);
        }
    }
    private static void renderGlowingOutline(PoseStack poseStack, MultiBufferSource bufferSource, ItemStack itemStack) {
        // 获取 Minecraft 的 ItemRenderer
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        // 设置发光描边的颜色和透明度
        int glowColor = 0xFFFFFF; // 白色发光
        float glowAlpha = 0.5F; // 50% 透明度
        // 渲染发光描边
        itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.GROUND, 15728880, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, 0);
    }
}

