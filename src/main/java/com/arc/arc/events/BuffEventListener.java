//package com.arc.arc.events;
//
//import com.arc.arc.effect.StarWeaponMechanic;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.effect.MobEffectInstance;
//import net.minecraftforge.event.entity.living.PotionEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//
//@Mod.EventBusSubscriber(modid = "your_mod_id", bus = Mod.EventBusSubscriber.Bus.FORGE)
//public class BuffEventListener {
//
//    @SubscribeEvent
//    public static void onPotionAdded(PotionEvent.PotionAddedEvent event) {
//        if (event.getEntity() instanceof Player) {
//            Player player = (Player) event.getEntity();
//            MobEffectInstance effect = event.getPotionEffect();
//            // 检查是否为 StarWeaponMechanic BUFF
//            if (effect.getEffect() instanceof StarWeaponMechanic) {
//                StarWeaponMechanic mechanic = (StarWeaponMechanic) effect.getEffect();
//                System.out.println("玩家获得 StarWeaponMechanic BUFF，开始记录技能。");
//            }
//        }
//    }
//}
