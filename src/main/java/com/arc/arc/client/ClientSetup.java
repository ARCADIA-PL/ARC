package com.arc.arc.client;

import com.arc.arc.ArcMod;
import com.arc.arc.ParticleEffect.ArcbladeHexagram;
import com.arc.arc.ParticleEffect.ArcbladeHexagram2;
import com.arc.arc.init.ParticleRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ArcMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void onParticleFactoryRegistration(ParticleFactoryRegisterEvent event) {
        ParticleEngine particleEngine = Minecraft.getInstance().particleEngine;

        // 注册第一个粒子工厂
        particleEngine.register(
                ParticleRegistry.TEXTURE_PARTICLE_EXAMPLE.get(),
                ArcbladeHexagram.Factory::new
        );

        // 注册第二个粒子工厂
        particleEngine.register(
                ParticleRegistry.ANOTHER_TEXTURE_PARTICLE.get(),
                ArcbladeHexagram2.Factory::new
        );
    }
}
