package com.arc.arc.Registries;
import com.arc.arc.effect.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ArcEffectsRegistry {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, "arc");
    //法阵
    public static final RegistryObject<MobEffect>MagicCircle =
            EFFECTS.register("magic_circle", MagicCircle::new);
    //星星出现后玩家根据星星做出了相应行为
    public static final RegistryObject<MobEffect> Stargazing =
            EFFECTS.register("stargazing", Stargazing::new);
    //北斗七星出现状态
    public static final RegistryObject<MobEffect> StarsTwinklingA =
            EFFECTS.register("stars_twinklinga", StarsTwinklingA::new);
    //六芒星出现状态
    public static final RegistryObject<MobEffect> StarsTwinklingB =
            EFFECTS.register("stars_twinklingb", StarsTwinklingB::new);
    //记录器A
    public static final RegistryObject<MobEffect> RecorderA =
            EFFECTS.register("alhpa", RecorderA::new);
    //记录器B
    public static final RegistryObject<MobEffect> RecorderB =
            EFFECTS.register("beta", RecorderB::new);
    //水平小法阵
    public static final RegistryObject<MobEffect> HEXAGRAMAlapha =
            EFFECTS.register("hexagram", ParticlesAlpha::new);
    //垂直小法阵
    public static final RegistryObject<MobEffect> HEXAGRAMBeta =
            EFFECTS.register("hexagram1", ParticlesBeta::new);
    //
    public static final RegistryObject<MobEffect> Strike =
            EFFECTS.register("strike", Strike::new);
    //
    public static final RegistryObject<MobEffect> Splash =
            EFFECTS.register("splash", Splash::new);
    //
    public static final RegistryObject<MobEffect> Timer =
            EFFECTS.register("timer", TimerEffect::new);
    //
    public static final RegistryObject<MobEffect> VerticalStop =
            EFFECTS.register("verticalstop", VerticalStopEffect::new);
    //
    public static final RegistryObject<MobEffect> HeavyGravity =
            EFFECTS.register("heavygravity", HeavyGravityEffect::new);
    //闪光时调用的buff
    public static final RegistryObject<MobEffect> SuperFlash =
            EFFECTS.register("super_flash", SuperFlash::new);
    //大招buff
    public static final RegistryObject<MobEffect> StarCraker =
            EFFECTS.register("star_craker", StarCraker::new);
}