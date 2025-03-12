package com.arc.arc.client;

import com.arc.arc.ArcMod;
import com.arc.arc.Renderer.MagicCircleRenderer;
import com.arc.arc.init.ModEntities;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = "arc", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // 注册法阵实体的渲染器
        EntityRenderers.register(ModEntities.MAGIC_CIRCLE.get(), MagicCircleRenderer::new);
    }
}



