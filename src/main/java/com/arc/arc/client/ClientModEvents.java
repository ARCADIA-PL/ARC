package com.arc.arc.client;

import com.arc.arc.Renderer.ArcbladeHexagramRenderer;
import com.arc.arc.Renderer.MagicCircleRenderer;
import com.arc.arc.init.ArcModEntities;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.arc.arc.init.ArcModEntities.ARCBLADE_HEXAGRAM;

@Mod.EventBusSubscriber(modid = "arc", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // 注册法阵实体的渲染器
        EntityRenderers.register(ArcModEntities.MAGIC_CIRCLE.get(), MagicCircleRenderer::new);
        EntityRenderers.register(ARCBLADE_HEXAGRAM.get(), ArcbladeHexagramRenderer::new);
    }

}



