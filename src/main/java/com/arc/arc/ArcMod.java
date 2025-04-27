package com.arc.arc;

import com.arc.arc.Registries.ArcBladeItemRegistry;
import com.arc.arc.Registries.ArcEffectsRegistry;
import com.arc.arc.Registries.ArcEntities;
import com.arc.arc.Registries.ArcSoundRegistry;
import com.arc.arc.events.ArcbladeAttributeHandlers;
//import com.arc.arc.events.StarWeaponMechanic;
import com.arc.arc.gameassets.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.ArrayList;
import java.util.List;

@Mod(ArcMod.MOD_ID)
public class ArcMod {
    public static final String MOD_ID = "arc";
    public ArcMod() {
        //Forge库注册;事件注册
        List<PlayerEventListener> PLAYER_EVENT_LISTENERS = new ArrayList<>();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(ArcbladeAttributeHandlers.class);
//        MinecraftForge.EVENT_BUS.register(StarWeaponMechanic.class);
//        无坚不摧技能注册
        Arcblade.registerSkills();//注册Arcblade的无坚不摧技能
        ArcbladeTransformed.registerSkills();//注册ArcbladeTransformed的无坚不摧技能
        ComboTachi_Lv0.registerSkills();
        ComboTachi_Lv1.registerSkills();
        ComboTachi_Lv2.registerSkills();
        ComboTachi_Lv3.registerSkills();
        //注册表
        ArcSoundRegistry.SOUNDS.register(bus);//音效注册表
        ArcBladeItemRegistry.ITEMS.register(bus);//物品注册表
        ArcEffectsRegistry.EFFECTS.register(bus);//药水注册表
        ArcEntities.register(bus);      //实体注册表
    }
}



