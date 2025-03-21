package com.arc.arc.Registries;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ArcSoundRegistry {
    // 创建SOUNDS注册器
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "arc");
    public static final RegistryObject<SoundEvent> JudgementCut =
            registerSound("judgementcut");
    public static final RegistryObject<SoundEvent> ArcSlash =
            registerSound("arcslash");
    public static final RegistryObject<SoundEvent> LastStarDust =
            registerSound("laststardust");
    private static RegistryObject<SoundEvent> registerSound(String name) {
        // 直接通过ResourceLocation构造SoundEvent
        return SOUNDS.register(name,
                               () -> new SoundEvent(new ResourceLocation("arc", name))
        );
    }
}