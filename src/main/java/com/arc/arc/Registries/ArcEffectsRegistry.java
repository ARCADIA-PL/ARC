package com.arc.arc.Registries;
import com.arc.arc.effect.MagicCircle;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ArcEffectsRegistry {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, "arc");
    //法阵
    public static final RegistryObject<MobEffect>MagicCircle =
            EFFECTS.register("magic_circle", com.arc.arc.effect.MagicCircle::new);

}