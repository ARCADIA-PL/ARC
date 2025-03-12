package com.arc.arc.client;

import com.arc.arc.ArcMod;
import com.arc.arc.ParticleEffect.ArcbladeHexagram;
import com.arc.arc.ParticleEffect.ArcbladeHexagram2;
import com.arc.arc.init.ModEntities;
import com.arc.arc.init.ParticleRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = "arc", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void onParticleFactoryRegistration(ParticleFactoryRegisterEvent event) {
        ParticleEngine particleEngine = Minecraft.getInstance().particleEngine;
        // 注册粒子工厂
        particleEngine.register(
                ParticleRegistry.ARCBLADE_HEXAGRAM.get(),
                ArcbladeHexagram.Factory::new
        );
        // 注册第二个粒子工厂
        particleEngine.register(
                ParticleRegistry.ARCBLADE_HEXAGRAM2.get(),
                ArcbladeHexagram2.Factory::new
        );
    }
    public class ClientModEvents {
        @SubscribeEvent
        public static void onTextureStitch(TextureStitchEvent.Pre event) {
            if (event.getAtlas().location().equals(TextureAtlas.LOCATION_PARTICLES)) {
                event.addSprite(new ResourceLocation("arc", "particle/arcblade_hexagram"));
            }
        }
        @SubscribeEvent
        public static void onClientSetup(EntityRenderersEvent.RegisterRenderers event) {
            // 注册 Arcblade_Hexagram_Entity 的渲染器
            event.registerEntityRenderer(ModEntities.ARCBLADE_HEXAGRAM.get(), Arcblade_Hexagram_Renderer::new);
        }
    }

}
