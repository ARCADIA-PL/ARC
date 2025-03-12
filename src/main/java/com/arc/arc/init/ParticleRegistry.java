package com.arc.arc.init;

import com.arc.arc.ArcMod;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ParticleRegistry {
    // 创建 DeferredRegister 来管理粒子类型
    public static final DeferredRegister<SimpleParticleType> PARTICLE_TYPES =
            DeferredRegister.create((ResourceLocation) ForgeRegistries.PARTICLE_TYPES, ArcMod.MOD_ID);
    // 注册粒子类型
    public static final RegistryObject<SimpleParticleType> TEXTURE_PARTICLE =
            PARTICLE_TYPES.register("texture_particle", () -> new SimpleParticleType(true));
}
