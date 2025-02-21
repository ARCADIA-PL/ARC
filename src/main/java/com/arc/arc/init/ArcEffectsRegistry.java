package com.arc.arc.init;
import com.arc.arc.effect.HitCounterEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ArcEffectsRegistry {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, "arc");

    public static final RegistryObject<MobEffect> HIT_COUNTER =
            EFFECTS.register("hit_counter", HitCounterEffect::new);
}