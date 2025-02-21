package com.arc.arc.events;
import com.arc.arc.ArcMod;
import com.arc.arc.init.ArcEffectsRegistry;
import com.arc.arc.network.ComboSoundPacket;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "arc")
public class AttackEventHandler {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        // 检查攻击者是否为玩家
        if (event.getSource().getEntity() instanceof Player player) {
            // 检查玩家是否拥有 BUFF
            if (player.hasEffect(ArcEffectsRegistry.HIT_COUNTER.get())) {
                // 获取当前效果等级
                MobEffectInstance effect = player.getEffect(ArcEffectsRegistry.HIT_COUNTER.get());
                int currentLevel = effect.getAmplifier();
                int newLevel = effect.getAmplifier() + 1;

                // 创建新效果（等级 +1，持续时间重置为 30 秒）
                MobEffectInstance newEffect = new MobEffectInstance(
                        ArcEffectsRegistry.HIT_COUNTER.get(),
                        10 * 20,  // 持续时间（30 秒 * 20 tick/秒）
                        currentLevel + 1,
                        false,     // 粒子效果
                        false,     // 显示图标
                        true       // 在 HUD 显示
                );

                // 移除旧效果并应用新效果
                player.removeEffect(ArcEffectsRegistry.HIT_COUNTER.get());
                player.addEffect(newEffect);

                // 检测等级触发条件
                if (newLevel == 5 || newLevel == 10) {
                    // 发送带等级参数的音效包
                    ArcMod.CHANNEL.sendToServer(new ComboSoundPacket(newLevel));
                }
            }
        }
    }
}