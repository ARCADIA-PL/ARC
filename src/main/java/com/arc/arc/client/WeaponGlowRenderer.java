package com.arc.arc.client;

import com.arc.arc.ArcMod;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ArcMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WeaponGlowRenderer {
    @SubscribeEvent
    public static void onRenderItem(RenderTooltipEvent.Pre event) {
        ItemStack stack = event.getItemStack();
        // 检查物品是否有 Glowing 标签
        if (stack.getTag() != null && stack.hasTag() && stack.getTag().getBoolean("Glowing")) {
            // 渲染发光描边效果
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.5F); // 设置发光颜色
            RenderSystem.setShaderTexture(0, new ResourceLocation("textures/misc/glow.png")); // 使用自定义发光纹理
            // 渲染发光描边
            // 这里需要根据具体需求实现渲染逻辑
            RenderSystem.disableBlend();
        }
    }
}

