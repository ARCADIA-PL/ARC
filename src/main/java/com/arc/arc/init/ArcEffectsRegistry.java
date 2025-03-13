package com.arc.arc.init;
import com.arc.arc.effect.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ArcEffectsRegistry {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, "arc");
    public static final RegistryObject<MobEffect> HIT_COUNTER =
            EFFECTS.register("hit_counter", HitCounterEffect::new);

    public static final RegistryObject<MobEffect> HURT_COUNTER =
            EFFECTS.register("hurt_counter", HurtCounterEffect::new);

    public static final RegistryObject<MobEffect> StellarisInstantDamage =
            EFFECTS.register("stellaris_instantdamage",StellarisInstantDamageEffect::new);

    public static final RegistryObject<MobEffect> StellarisInstantHeal =
            EFFECTS.register("stellaris_instantheal",StellarisInstantHealEffect::new);

    public static final RegistryObject<MobEffect> PercentageDamageEffect =
            EFFECTS.register("stellaris_percentagedamageeffect",StellarisInstantHealEffect::new);

    public static final RegistryObject<MobEffect>StellarisParryCounter =
            EFFECTS.register("stellaris_parrycounter",ParryCounterEffect::new);

    public static final RegistryObject<MobEffect>ArcbladeTransformEffect =
            EFFECTS.register("stellaris_arcbladetransformeffect",ArcbladeTransformEffect::new);

    public static final RegistryObject<MobEffect>CrownEffect =
            EFFECTS.register("crown",CrownEffect::new);

    public static final RegistryObject<MobEffect>StellarisHexagram =
            EFFECTS.register("crown2",StellarisHexagram::new);


    //ARC法阵
    public static final RegistryObject<MobEffect>StellarisHexagram2 =
            EFFECTS.register("crown3",StellarisHexagram2::new);

}