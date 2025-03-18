package com.arc.arc.gameassets;

import com.arc.arc.ArcMod;
import com.arc.arc.Registries.ArcEffectsRegistry;
import com.arc.arc.Registries.ArcSoundRegistry;
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
import com.p1nero.wukong.client.WuKongSounds;
import com.p1nero.wukong.epicfight.animation.WukongAnimations;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
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
        ComboNode ArcbladeTransformedAirStrikeAuto1 = ComboNode.createNode(() -> Animations.SPEAR_TWOHAND_AIR_SLASH)
                .setPriority(4).setConvertTime(0.15F).setPlaySpeed(0.9F)
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
                })
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F,"effect give @s minecraft:slow_falling 2",false));
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
                .setPriority(4).setConvertTime(0.05F).setPlaySpeed(0.9F).addCondition(new SprintingCondition());

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

        ComboNode ArcbladeTransformedRevelationAirFirst = ComboNode.createNode(() -> WOMAnimations.ENDERBLASTER_ONEHAND_AUTO_1)
                .setPriority(4).setPlaySpeed(1.1F)
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
                })
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s irons_spellbooks:rend 4 10", true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:levitation 1 1", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F,"effect give @s minecraft:slow_falling 1",false))
                .addTimeEvent(new TimeStampedEvent(0.3F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}));
        ComboNode ArcbladeTransformedRevelationAirSecond = ComboNode.createNode(() -> WukongAnimations.JUMP_ATTACK_LIGHT_HIT)
                .setPlaySpeed(1.1F)
                .addCondition(new MobEffectCondition(true,(MobEffectRegistry.REND),0,100))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "playsound minecraft:block.respawn_anchor.deplete ambient @s ~ ~ ~ 100", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "particle minecraft:wax_off ~ ~1 ~ 0 4 0 2 20 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "particle minecraft:wax_off ~-2 ~1 ~ 0 1.5 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "particle minecraft:wax_off ~2 ~1 ~ 0 1.5 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "particle minecraft:wax_off ~ ~1 ~-2 0 1.5 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "particle minecraft:wax_off ~ ~1 ~2 0 1.5 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "particle minecraft:wax_off ~-3 ~1 ~ 0 0.2 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "particle minecraft:wax_off ~3 ~1 ~ 0 0.2 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "particle minecraft:wax_off ~ ~1 ~-3 0 0.2 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "particle minecraft:wax_off ~ ~1 ~3 0 0.2 0 2 10 force", false))
                .addTimeEvent(new TimeStampedEvent(0.75F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}));
        ComboNode ArcbladeTransformedRevelationAirThird = ComboNode.createNode(() -> WukongAnimations.SMASH_CHARGED4)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0F, "invincible groundSlam @s 1 false false false", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "particle isleofberk:lightning_aoe_emitter ~ ~1.5 ~ 0 2.5 0 0.1 160 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "particle minecraft:wax_off ~ ~1 ~ 0 4 0 2 20 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.7F, "particle minecraft:wax_off ~-2 ~1 ~ 0 1.5 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.7F, "particle minecraft:wax_off ~2 ~1 ~ 0 1.5 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.7F, "particle minecraft:wax_off ~ ~1 ~-2 0 1.5 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.7F, "particle minecraft:wax_off ~ ~1 ~2 0 1.5 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1F, "particle minecraft:wax_off ~-3 ~1 ~ 0 0.2 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1F, "particle minecraft:wax_off ~3 ~1 ~ 0 0.2 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1F, "particle minecraft:wax_off ~ ~1 ~-3 0 0.2 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1F, "particle minecraft:wax_off ~ ~1 ~3 0 0.2 0 2 10 force", false))

                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "effect give @s cofh_core:lightning_resistance 5", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(2.3F, "playsound minecraft:block.respawn_anchor.deplete ambient @s ~ ~ ~ 100", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(2.3F, "particle minecraft:explosion ~ ~1.5 ~ 0 1 0 1 1 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(2.7F, "summon minecraft:lightning_bolt ~3 ~ ~", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(2.7F, "summon minecraft:lightning_bolt ~-3 ~ ~", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(2.7F, "summon minecraft:lightning_bolt ~ ~ ~3", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(2.7F, "summon minecraft:lightning_bolt ~ ~ ~-3", false))
                .addTimeEvent(new TimeStampedEvent(0.0F, entityPatch -> {
                    entityPatch.playSound(WuKongSounds.STACK1.get(), 1F, 0, 0);
                }))
                .addTimeEvent(new TimeStampedEvent(0.7F, entityPatch -> {
                    entityPatch.playSound(WuKongSounds.STACK2.get(), 1F, 0, 0);
                }))
                .addTimeEvent(new TimeStampedEvent(1.4F, entityPatch -> {
                    entityPatch.playSound(WuKongSounds.STACK3.get(), 1F, 0, 0);
                }))
                .addTimeEvent(new TimeStampedEvent(2F, entityPatch -> {
                    entityPatch.playSound(WuKongSounds.STACK4.get(), 1F, 0, 0);
                }))
                .addTimeEvent(new TimeStampedEvent(2.3F, entityPatch -> {
                    entityPatch.playSound(ArcSoundRegistry.ArcSlash.get(), 1F, 0, 0);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(2.9F, "invincible groundSlam @s 3 false false false", true))
                .addTimeEvent(new TimeStampedEvent(3.1F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}));;
        ComboNode ArcbladeTransformedRevelationAirEnd = ComboNode.createNode(() -> WOMAnimations.SOLAR_AUTO_4_POLVORA)
                .setConvertTime(0.2F);

        ComboNode ArcbladeTransformedBasicAttack = ComboNode.create()
                .addConditionAnimation(ArcbladeTransformedAirStrikeAuto1)
                .addConditionAnimation(ArcbladeTransformedDashSlash)
                .addConditionAnimation(ArcbladeTransformedAuto1);
        ComboNode ArcbladeTransformedAutoAttack2 = ComboNode.create()
                .addConditionAnimation(ArcbladeTransformedAuto2)
                .addConditionAnimation(ArcbladeTransformedAirStrikeAuto1)
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

        ComboNode ArcbladeTransformedRevelation = ComboNode.create()
                .addConditionAnimation(ArcbladeTransformedRevelationAirFirst);

        ArcbladeTransformedroot.key1(ArcbladeTransformedBasicAttack);
        ArcbladeTransformedroot.keyWeaponInnate(ArcbladeTransformedRevelation);

        ArcbladeTransformedDashSlash.key1(ArcbladeTransformedAutoAttack2);

        ArcbladeTransformedAirStrikeAuto1.key1(ArcbladeTransformedAirStrikeAuto2);
        ArcbladeTransformedAirStrikeAuto1.keyWeaponInnate(ArcbladeTransformedRevelationAirFirst);
        ArcbladeTransformedRevelationAirFirst.keyWeaponInnate(ArcbladeTransformedRevelationAirSecond);
        ArcbladeTransformedRevelationAirSecond.keyWeaponInnate(ArcbladeTransformedRevelationAirThird);
        ArcbladeTransformedRevelationAirThird.keyWeaponInnate(ArcbladeTransformedRevelationAirEnd);
        ArcbladeTransformedAirStrikeAuto2.key1(ArcbladeTransformedAutoAttack3);

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