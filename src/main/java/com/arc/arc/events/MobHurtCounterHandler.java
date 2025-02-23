package com.arc.arc.events;
import com.arc.arc.init.ArcEffectsRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "arc")
public class MobHurtCounterHandler {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity target = (LivingEntity) event.getEntity();

        // 仅在服务端处理 且 目标有BUFF
        if (target.level.isClientSide || !target.hasEffect(ArcEffectsRegistry.HURT_COUNTER.get())) {
            return;
        }

        // 获取当前效果
        MobEffectInstance currentHurtEffect = target.getEffect(ArcEffectsRegistry.HURT_COUNTER.get());
        int currentHurtLevel = 0;
        if (currentHurtEffect != null) {
            currentHurtLevel = currentHurtEffect.getAmplifier();
        }
        if (currentHurtEffect != null) {
            int duration = currentHurtEffect.getDuration(); // 继承剩余时间
        }

        // 创建新效果（等级+1）
        MobEffectInstance newHurtEffect = new MobEffectInstance(
                ArcEffectsRegistry.HURT_COUNTER.get(),
                10 * 20, //
                currentHurtLevel + 1,
                false,
                true,
                true
        );

        // 更新效果
        target.removeEffect(ArcEffectsRegistry.HURT_COUNTER.get());
        target.addEffect(newHurtEffect);

    }
}

