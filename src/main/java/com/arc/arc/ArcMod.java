package com.arc.arc;

import com.arc.arc.Registries.ArcBladeItemRegistry;
import com.arc.arc.Registries.ArcSoundRegistry;
import com.arc.arc.gameassets.Arcblade;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ArcMod.MOD_ID)
public class ArcMod {
    public static final String MOD_ID = "arc";
    public ArcMod() {
        Arcblade.registerSkills();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ArcSoundRegistry.SOUNDS.register(bus);//音效注册表
        ArcBladeItemRegistry.ITEMS.register(bus);}//物品注册表
}

