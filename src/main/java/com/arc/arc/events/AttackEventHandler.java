package com.arc.arc.events;
import com.arc.arc.ArcMod;
import com.arc.arc.init.ArcEffectsRegistry;
import com.arc.arc.network.ComboSoundPacket;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "arc")
public class AttackEventHandler {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity target = (LivingEntity) event.getEntity();

        // 仅在服务端处理 且 目标有BUFF
        if (target.level.isClientSide || !target.hasEffect(ArcEffectsRegistry.HURT_COUNTER.get())) {
            return;
        }

        // 获取当前效果
        MobEffectInstance currentEffect = target.getEffect(ArcEffectsRegistry.HURT_COUNTER.get());
        int currentLevel = currentEffect.getAmplifier();
        int duration = currentEffect.getDuration(); // 继承剩余时间

        // 创建新效果（等级+1）
        MobEffectInstance newEffect = new MobEffectInstance(
                ArcEffectsRegistry.HURT_COUNTER.get(),
                duration, // 保持剩余时间不变
                currentLevel + 1,
                false,
                true,
                true
        );

        // 更新效果
        target.removeEffect(ArcEffectsRegistry.HURT_COUNTER.get());
        target.addEffect(newEffect);




            if (event.getSource().getEntity() instanceof Player player) {

                // 检查玩家是否拥有 BUFF
                if (player.hasEffect(ArcEffectsRegistry.HIT_COUNTER.get())) {
                    // 获取当前效果等级
                    MobEffectInstance effect = player.getEffect(ArcEffectsRegistry.HIT_COUNTER.get());
                    int currentHitLevel = 0;
                    if (effect != null) {
                        currentHitLevel = effect.getAmplifier();
                    }
                    int newHitLevel = 0;
                    if (effect != null) {
                        newHitLevel = effect.getAmplifier() + 1;
                    }

                    // 创建新效果（等级 +1，持续时间重置为 30 秒）
                    MobEffectInstance newHitEffect = new MobEffectInstance(
                            ArcEffectsRegistry.HIT_COUNTER.get(),
                            10 * 20,  // 持续时间（30 秒 * 20 tick/秒）
                            currentHitLevel + 1,
                            false,     // 粒子效果
                            false,     // 显示图标
                            true       // 在 HUD 显示
                    );

                    // 移除旧效果并应用新效果
                    player.removeEffect(ArcEffectsRegistry.HIT_COUNTER.get());
                    player.addEffect(newHitEffect);

                    // 检测等级触发条件
                    if (newHitLevel == 4 || newHitLevel == 9) {
                        // 发送带等级参数的音效包
                        ArcMod.CHANNEL.sendToServer(new ComboSoundPacket(newHitLevel));
                    }


                }
            }

        }
    }
