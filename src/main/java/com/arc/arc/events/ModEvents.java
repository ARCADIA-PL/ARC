//package com.arc.arc.events;
//
//import com.arc.arc.effect.RecorderA;
//import com.arc.arc.effect.RecorderB;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraftforge.event.entity.living.PotionEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//
//@Mod.EventBusSubscriber(modid = "arc")
//public class ModEvents {
//
//    @SubscribeEvent
//    public static void onPotionRemoved(PotionEvent.PotionRemoveEvent event) {
//        // 检查 getPotionEffect() 是否为 null
//        if (event.getPotionEffect() == null) {
//            return; // 如果为 null，直接返回
//        }
//
//        if (event.getEntity() instanceof Player) {
//            Player player = (Player) event.getEntity();
//            CompoundTag playerData = player.getPersistentData();
//
//            // 如果移除的是 RecorderA BUFF，则重置标志位
//            if (event.getPotionEffect().getEffect() instanceof RecorderA) {
//                playerData.putBoolean(RecorderA.HAS_RECORDED_KEY, false); // 通过 getter 访问
//                System.out.println("RecorderA BUFF 结束，重置标志位。");
//            }
//
//            // 如果移除的是 RecorderB BUFF，则重置标志位
//            if (event.getPotionEffect().getEffect() instanceof RecorderB) {
//                playerData.putBoolean(RecorderB.HAS_RECORDED_KEY, false); // 通过 getter 访问
//                System.out.println("RecorderB BUFF 结束，重置标志位。");
//            }
//        }
//    }
//}
