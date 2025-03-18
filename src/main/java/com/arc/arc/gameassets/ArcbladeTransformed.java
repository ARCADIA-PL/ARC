package com.arc.arc.gameassets;

import com.arc.arc.ArcMod;
import com.arc.arc.Registries.ArcEffectsRegistry;
import com.arc.arc.skill.ArcbladeTransformedSkill;
import com.guhao.star.efmex.StarAnimations;
import com.p1nero.invincible.api.events.BiEvent;
import com.p1nero.invincible.api.events.TimeStampedEvent;
import com.p1nero.invincible.conditions.CustomCondition;
import com.p1nero.invincible.conditions.MobEffectCondition;
import com.p1nero.invincible.conditions.SprintingCondition;
import com.p1nero.invincible.conditions.UpCondition;
import com.p1nero.invincible.skill.ComboBasicAttack;
import com.p1nero.invincible.skill.api.ComboNode;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.data.reloader.SkillManager;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mod.EventBusSubscriber(modid = ArcMod.MOD_ID)
public class ArcbladeTransformed {
    public static Skill ArcbladeTransformed;

    public static void registerSkills() {
        ComboNode ArcbladeTransformedroot = ComboNode.create();
        ComboNode ArcbladeTransformedAirStrikeAuto1 = ComboNode.createNode(() -> WOMAnimations.SOLAR_AUTO_4_POLVORA)
                .setPriority(4).setConvertTime(0.05F).setPlaySpeed(0.9F)
                .addCondition(new CustomCondition() {
                    @Override
                    public boolean predicate(LivingEntityPatch<?> entityPatch) {
                        // 获取玩家实体
                        LivingEntity livingEntity = entityPatch.getOriginal();
                        boolean isOnGround = livingEntity.isOnGround();//悬空
                        boolean isInWater = livingEntity.isInWater();//水中
                        boolean isOnClimbable = livingEntity.onClimbable();//梯子
                        boolean isRiding = livingEntity.isPassenger();//骑乘
                        boolean isGliding = livingEntity.isFallFlying();//滑翔
                        return !isOnGround && !isInWater && !isOnClimbable && !isRiding && !isGliding;
                    }
                });
        ComboNode ArcbladeTransformedAirStrikeAuto2 = ComboNode.createNode(() -> WOMAnimations.TORMENT_AUTO_4)
                .setPriority(4).setConvertTime(-0.25F).addCondition(new UpCondition())
                .addCondition(new CustomCondition() {
                    @Override
                    public boolean predicate(LivingEntityPatch<?> entityPatch) {
                        // 获取玩家实体
                        LivingEntity livingEntity = entityPatch.getOriginal();
                        boolean isOnGround = livingEntity.isOnGround();//悬空
                        boolean isInWater = livingEntity.isInWater();//水中
                        boolean isOnClimbable = livingEntity.onClimbable();//梯子
                        boolean isRiding = livingEntity.isPassenger();//骑乘
                        boolean isGliding = livingEntity.isFallFlying();//滑翔
                        return !isOnGround && !isInWater && !isOnClimbable && !isRiding && !isGliding;
                    }
                });
        ComboNode ArcbladeTransformedDashSlash = ComboNode.createNode(() -> WOMAnimations.RUINE_DASH)
                .setPriority(4).setConvertTime(0.1F).setPlaySpeed(0.75F).addCondition(new SprintingCondition());

        ComboNode ArcbladeTransformedDashStrikeNormal = ComboNode.createNode(() -> WOMAnimations.TORMENT_CHARGED_ATTACK_2)
                .setPriority(4).setConvertTime(0.2F).setPlaySpeed(0.7F)
                .addCondition(new SprintingCondition())
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:levitation 3 4", true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:slow_falling 3", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "effect give @s minecraft:levitation 1 4", false));

        ComboNode ArcbladeTransformedDashStrikeEnhancedAuto2 = ComboNode.createNode(() -> WOMAnimations.AGONY_PLUNGE_FORWARD)
                .setConvertTime(-0.1F).setPlaySpeed(2F)
                .addTimeEvent(new TimeStampedEvent(1.25F, entityPatch -> {
                    entityPatch.playAnimationSynchronized(Animations.BIPED_WALK_UCHIGATANA, 0);
                }))
                .addTimeEvent(new TimeStampedEvent(1.25F, (entity) -> {
                    if (entity.getOriginal().addEffect(new MobEffectInstance(ArcEffectsRegistry.RecorderA.get(), 1, 0))) ;
                }))
                .addTimeEvent(new TimeStampedEvent(1.25F, (entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.KEY_2);}
                }));

        ComboNode ArcbladeTransformedAuto1 = ComboNode.createNode(() -> StarAnimations.GREATSWORD_OLD_AUTO3)
                .setPriority(1).setPlaySpeed(0.7F)
                .addCondition(new CustomCondition() {
                    @Override
                    public boolean predicate(LivingEntityPatch<?> entityPatch) {
                        LivingEntity livingEntity = entityPatch.getOriginal();
                        // 检测玩家是否处于非滞空状态
                        boolean isOnGround = livingEntity.isOnGround();      // 在地面
                        boolean isInWater = livingEntity.isInWater();       // 在水中
                        boolean isOnClimbable = livingEntity.onClimbable(); // 在梯子上
                        boolean isRiding = livingEntity.isPassenger();      // 骑乘
                        boolean isGliding = livingEntity.isFallFlying();    // 滑翔
                        return isOnGround || isInWater || isOnClimbable || isRiding || isGliding;
                    }
                })
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.35F, "invincible groundSlam @s 1 false true false", true));
        ComboNode ArcbladeTransformedAuto2 = ComboNode.createNode(() -> StarAnimations.GREATSWORD_OLD_AUTO1)
                .setConvertTime(0.1F).setPlaySpeed(0.7F)
                .addCondition(new CustomCondition() {
                    @Override
                    public boolean predicate(LivingEntityPatch<?> entityPatch) {
                        LivingEntity livingEntity = entityPatch.getOriginal();
                        // 检测玩家是否处于非滞空状态
                        boolean isOnGround = livingEntity.isOnGround();      // 在地面
                        boolean isInWater = livingEntity.isInWater();       // 在水中
                        boolean isOnClimbable = livingEntity.onClimbable(); // 在梯子上
                        boolean isRiding = livingEntity.isPassenger();      // 骑乘
                        boolean isGliding = livingEntity.isFallFlying();    // 滑翔
                        return isOnGround || isInWater || isOnClimbable || isRiding || isGliding;
                    }
                })
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.25F, "invincible groundSlam @s 1.5 false true false", true));
        ComboNode ArcbladeTransformedAuto3 = ComboNode.createNode(() -> WOMAnimations.TORMENT_AUTO_4)
                .setConvertTime(-0.2F).setPlaySpeed(0.7F)
                .addCondition(new CustomCondition() {
                    @Override
                    public boolean predicate(LivingEntityPatch<?> entityPatch) {
                        LivingEntity livingEntity = entityPatch.getOriginal();
                        // 检测玩家是否处于非滞空状态
                        boolean isOnGround = livingEntity.isOnGround();      // 在地面
                        boolean isInWater = livingEntity.isInWater();       // 在水中
                        boolean isOnClimbable = livingEntity.onClimbable(); // 在梯子上
                        boolean isRiding = livingEntity.isPassenger();      // 骑乘
                        boolean isGliding = livingEntity.isFallFlying();    // 滑翔
                        return isOnGround || isInWater || isOnClimbable || isRiding || isGliding;
                    }
                });
        ComboNode ArcbladeTransformedAuto4 = ComboNode.createNode(() -> WOMAnimations.TORMENT_AUTO_1)
                .setPlaySpeed(0.6F)
                .addCondition(new CustomCondition() {
                    @Override
                    public boolean predicate(LivingEntityPatch<?> entityPatch) {
                        LivingEntity livingEntity = entityPatch.getOriginal();
                        // 检测玩家是否处于非滞空状态
                        boolean isOnGround = livingEntity.isOnGround();      // 在地面
                        boolean isInWater = livingEntity.isInWater();       // 在水中
                        boolean isOnClimbable = livingEntity.onClimbable(); // 在梯子上
                        boolean isRiding = livingEntity.isPassenger();      // 骑乘
                        boolean isGliding = livingEntity.isFallFlying();    // 滑翔
                        return isOnGround || isInWater || isOnClimbable || isRiding || isGliding;
                    }
                })
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.25F, "invincible groundSlam @s 1 false true false", true));
        ComboNode ArcbladeTransformedAuto5 = ComboNode.createNode(() -> WOMAnimations.RUINE_AUTO_3)
                .setPlaySpeed(0.7F)
                .setConvertTime(-0.1F)
                .addCondition(new CustomCondition() {
                    @Override
                    public boolean predicate(LivingEntityPatch<?> entityPatch) {
                        LivingEntity livingEntity = entityPatch.getOriginal();
                        // 检测玩家是否处于非滞空状态
                        boolean isOnGround = livingEntity.isOnGround();      // 在地面
                        boolean isInWater = livingEntity.isInWater();       // 在水中
                        boolean isOnClimbable = livingEntity.onClimbable(); // 在梯子上
                        boolean isRiding = livingEntity.isPassenger();      // 骑乘
                        boolean isGliding = livingEntity.isFallFlying();    // 滑翔
                        return isOnGround || isInWater || isOnClimbable || isRiding || isGliding;
                    }
                })
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.55F, "invincible groundSlam @s 1 false true false", true));

        ComboNode ArcbladeTransformedBasicAttack = ComboNode.create()
                .addConditionAnimation(ArcbladeTransformedAirStrikeAuto1)
                .addConditionAnimation(ArcbladeTransformedDashSlash)
                .addConditionAnimation(ArcbladeTransformedAuto1);
        ComboNode ArcbladeTransformedBasicStrike = ComboNode.create()
                .addConditionAnimation(ArcbladeTransformedDashStrikeNormal);
        ComboNode ArcbladeTransformedBasicAttack2 = ComboNode.create()
                .addConditionAnimation(ArcbladeTransformedDashSlash)
                .addConditionAnimation(ArcbladeTransformedAirStrikeAuto1)
                .addConditionAnimation(ArcbladeTransformedAuto1);
        ComboNode ArcbladeTransformedBasicAttack3 = ComboNode.create()
                .addConditionAnimation(ArcbladeTransformedDashSlash)
                .addConditionAnimation(ArcbladeTransformedAuto4);

        ComboNode ArcbladeTransformedAutoAttack2 = ComboNode.create()
                .addConditionAnimation(ArcbladeTransformedAuto2)
                .addConditionAnimation(ArcbladeTransformedDashSlash);
        ComboNode ArcbladeTransformedAutoAttack3 = ComboNode.create()
                .addConditionAnimation(ArcbladeTransformedAuto3)
                .addConditionAnimation(ArcbladeTransformedAirStrikeAuto1)
                .addConditionAnimation(ArcbladeTransformedDashSlash);
        ComboNode ArcbladeTransformedAutoAttack4 = ComboNode.create()
                .addConditionAnimation(ArcbladeTransformedAirStrikeAuto1)
                .addConditionAnimation(ArcbladeTransformedAuto4)
                .addConditionAnimation(ArcbladeTransformedDashSlash);
        ComboNode ArcbladeTransformedAutoAttack5 = ComboNode.create()
                .addConditionAnimation(ArcbladeTransformedAirStrikeAuto1)
                .addConditionAnimation(ArcbladeTransformedAuto5)
                .addConditionAnimation(ArcbladeTransformedDashSlash);


        ArcbladeTransformedroot.key1(ArcbladeTransformedBasicAttack);
        ArcbladeTransformedroot.keyWeaponInnate(ArcbladeTransformedBasicStrike);

        ArcbladeTransformedDashSlash.key1(ArcbladeTransformedBasicAttack2);

        ArcbladeTransformedAirStrikeAuto1.key1(ArcbladeTransformedAirStrikeAuto2);
        ArcbladeTransformedAirStrikeAuto2.key1(ArcbladeTransformedBasicAttack3);

        ArcbladeTransformedAuto1.key1(ArcbladeTransformedAutoAttack2);
        ArcbladeTransformedAuto2.key1(ArcbladeTransformedAutoAttack3);
        ArcbladeTransformedAuto3.key1(ArcbladeTransformedAutoAttack4);
        ArcbladeTransformedAuto4.key1(ArcbladeTransformedAutoAttack5);
        ArcbladeTransformedAuto5.key1(ArcbladeTransformedBasicAttack);

        SkillManager.register(ArcbladeTransformedSkill::new, ArcbladeTransformedSkill.createComboBasicAttack().setCombo(ArcbladeTransformedroot).setShouldDrawGui(true), ArcMod.MOD_ID, "combo1");
    }

    @SubscribeEvent
    public static void BuildSkills(SkillBuildEvent event) {
        ArcbladeTransformed = event.build(ArcMod.MOD_ID, "combo1");
    }
}