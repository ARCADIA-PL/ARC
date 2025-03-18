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

    //星星出现状态
    public static final RegistryObject<MobEffect> StarsTwinkling =
            EFFECTS.register("stars_twinkling", StarsTwinkling::new);

    //记录器A
    public static final RegistryObject<MobEffect> RecorderA =
            EFFECTS.register("stars_recordera", RecorderA::new);

    //记录器B
    public static final RegistryObject<MobEffect> RecorderB =
            EFFECTS.register("stars_recorderb", RecorderB::new);
    //记录器
    public static final RegistryObject<MobEffect> StarWeaponMechanic =
            EFFECTS.register("recorder", StarWeaponMechanic::new);

    //闪光时调用的buff
    public static final RegistryObject<MobEffect> SuperFlash =
            EFFECTS.register("super_flash", SuperFlash::new);



}