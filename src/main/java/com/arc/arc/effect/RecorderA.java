package com.arc.arc.effect;

import com.arc.arc.Registries.ArcEffectsRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;

public class RecorderA extends MobEffect {
    public static final String HAS_RECORDED_KEY = "hasRecordedA"; // 标志位的键

    public RecorderA() {
        super(MobEffectCategory.BENEFICIAL, 0xFF0000); // 设置 BUFF 类型和颜色
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player) {
            Player player = (Player) entity;

            // 获取玩家的 CompoundTag
            CompoundTag playerData = player.getPersistentData();
            boolean hasRecorded = playerData.getBoolean(HAS_RECORDED_KEY);

            // 检查玩家是否拥有 StarWeaponMechanic BUFF
            if (player.hasEffect(ArcEffectsRegistry.StarWeaponMechanic.get())) {
                StarWeaponMechanic mechanic = (StarWeaponMechanic) player.getEffect(ArcEffectsRegistry.StarWeaponMechanic.get()).getEffect();

                // 如果尚未记录，则记录技能 A
                if (!hasRecorded) {
                    mechanic.recordSkill("A"); // 记录技能 A
                    playerData.putBoolean(HAS_RECORDED_KEY, true); // 设置标志位为 true
                    System.out.println("玩家获得 RecorderA BUFF，记录技能 A。");
                }
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true; // 每 tick 都执行逻辑
    }
}
