package com.arc.arc.init;

import com.arc.arc.ArcMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ParticleRegistry {
    // 使用 ParticleType<?> 作为泛型类型
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, "arc"); // "arc" 是你的 Mod I

    // 注册第一个粒子类型
    public static final RegistryObject<SimpleParticleType> ARCBLADE_HEXAGRAM =
            PARTICLE_TYPES.register("arcblade_hexagram", () -> new SimpleParticleType(true));

    // 注册第二个粒子类型
    public static final RegistryObject<SimpleParticleType> ARCBLADE_HEXAGRAM2 =
            PARTICLE_TYPES.register("arcblade_hexagram2", () -> new SimpleParticleType(true));

    // 注册方法
    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);

    }
}
