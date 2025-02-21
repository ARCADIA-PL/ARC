package com.arc.arc.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundRegistry {
    // 创建 SOUNDS 注册器
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "arc");

    // 注册 5 级音效
    public static final RegistryObject<SoundEvent> COMBO_5 =
            registerSound("combo_5");

    // 注册 10 级音效
    public static final RegistryObject<SoundEvent> COMBO_10 =
            registerSound("combo_10");

    /**
     * Forge 1.18 兼容的音效注册方法
     */
    private static RegistryObject<SoundEvent> registerSound(String name) {
        // 直接通过 ResourceLocation 构造 SoundEvent
        return SOUNDS.register(name,
                () -> new SoundEvent(new ResourceLocation("arc", name))
        );
    }
}
