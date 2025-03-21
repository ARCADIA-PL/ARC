package com.arc.arc.gameassets;

import com.arc.arc.ArcMod;
import com.arc.arc.Registries.ArcEffectsRegistry;
import com.arc.arc.Registries.ArcSoundRegistry;
import com.arc.arc.skill.ArcbladeTransformedSkill;
import com.dfdyz.epicacg.registry.MobEffects;
import com.guhao.star.efmex.StarAnimations;
import com.nameless.toybox.ToyBox;
import com.p1nero.invincible.api.events.BiEvent;
import com.p1nero.invincible.api.events.TimeStampedEvent;
import com.p1nero.invincible.conditions.*;
import com.p1nero.invincible.skill.ComboBasicAttack;
import com.p1nero.invincible.skill.api.ComboNode;
import com.p1nero.wukong.client.WuKongSounds;
import com.p1nero.wukong.epicfight.animation.WukongAnimations;
import com.sammy.omnis.core.registry.effects.EffectRegistry;
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
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

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
                .setPriority(4).setConvertTime(0.1F).setPlaySpeed(0.9F)
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
                .addHitEvent(BiEvent.createBiCommandEvent("invincible setPlayerPhase 2", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F,"effect give @s minecraft:slow_falling 1",false))
                .addTimeEvent(new TimeStampedEvent(0.29F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}))
                .addTimeEvent(new TimeStampedEvent(0.3F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}))
                .addTimeEvent(new TimeStampedEvent(0.31F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}))
                .addTimeEvent(new TimeStampedEvent(0.32F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}));//模拟输入

        ComboNode ArcbladeTransformedRevelationAirSecond = ComboNode.createNode(() -> WukongAnimations.JUMP_ATTACK_LIGHT_HIT)
                .setPlaySpeed(1.1F)
                .addCondition(new PlayerPhaseCondition(2,2))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F,"invincible setPlayerPhase 1",false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "playsound minecraft:block.respawn_anchor.deplete ambient @s ~ ~ ~ 100", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "particle minecraft:wax_off ~ ~1 ~ 0 4 0 2 20 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "particle minecraft:wax_off ~-2 ~1 ~ 0 1.5 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "particle minecraft:wax_off ~2 ~1 ~ 0 1.5 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "particle minecraft:wax_off ~ ~1 ~-2 0 1.5 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "particle minecraft:wax_off ~ ~1 ~2 0 1.5 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "particle minecraft:wax_off ~-3 ~1 ~ 0 0.2 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "particle minecraft:wax_off ~3 ~1 ~ 0 0.2 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "particle minecraft:wax_off ~ ~1 ~-3 0 0.2 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "particle minecraft:wax_off ~ ~1 ~3 0 0.2 0 2 10 force", false))//特效音效
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
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "effect give @s arc:strike 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "effect give @s arc:strike 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.8F, "effect give @s arc:strike 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1F, "effect give @s arc:strike 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.2F, "effect give @s arc:strike 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.4F, "effect give @s arc:strike 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(2.3F, "playsound minecraft:block.respawn_anchor.deplete ambient @s ~ ~ ~ 100", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(2.3F, "particle minecraft:explosion ~ ~1.5 ~ 0 1 0 1 1 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(2.7F, "summon minecraft:lightning_bolt ~4 ~ ~", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(2.7F, "summon minecraft:lightning_bolt ~-4 ~ ~", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(2.7F, "summon minecraft:lightning_bolt ~ ~ ~4", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(2.7F, "summon minecraft:lightning_bolt ~ ~ ~-4", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(2.65F, "effect give @s arc:hexagram 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(2.8F, "invincible groundSlam @s 3 false false false", true))//特效指令
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
                }))//特效音效
                .addTimeEvent(new TimeStampedEvent(3.1F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}));;
        ComboNode ArcbladeTransformedRevelationAirEnd = ComboNode.createNode(() -> WOMAnimations.SOLAR_AUTO_4_POLVORA)
                .setConvertTime(0.2F);


        ComboNode ArcbladeTransformedRevelationGroundFirst = ComboNode.createNode(() -> WOMAnimations.SOLAR_AUTO_3_POLVORA)
                .setConvertTime(0.3F).setPlaySpeed(0.7F)
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
                .addHitEvent(BiEvent.createBiCommandEvent("invincible setPlayerPhase 2", false))
                .addTimeEvent(new TimeStampedEvent(0.33F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}))
                .addTimeEvent(new TimeStampedEvent(0.34F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}))
                .addTimeEvent(new TimeStampedEvent(0.35F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}));
        ComboNode ArcbladeTransformedRevelationGroundSecond = ComboNode.createNode(() -> WOMAnimations.SOLAR_AUTO_3_POLVORA)
                .addCondition(new PlayerPhaseCondition(2,2)).setConvertTime(0.3F).setPlaySpeed(0.9F)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "playsound minecraft:block.respawn_anchor.deplete ambient @s ~ ~ ~ 200", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F,"invincible setPlayerPhase 1",false))
                .addTimeEvent(new TimeStampedEvent(0.3F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}))
                .addTimeEvent(new TimeStampedEvent(0.31F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}))
                .addTimeEvent(new TimeStampedEvent(0.32F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}));
        ComboNode ArcbladeTransformedRevelationGroundThird = ComboNode.createNode(() -> StarAnimations.WIND_SLASH)
                .setConvertTime(0.2F).setPlaySpeed(0.8F)
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.BLADE_RUSH_FINISHER, 0, 0);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "effect give @s cofh_core:lightning_resistance 10", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.7F, "summon minecraft:lightning_bolt ~1.5 ~ ~", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.3F, "summon minecraft:lightning_bolt ~-1.5 ~ ~", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.9F, "summon minecraft:lightning_bolt ~1.5 ~ ~", false))
                .addHitEvent(BiEvent.createBiCommandEvent("execute as @s at @s run particle irons_spellbooks:electricity ~ ~1.0 ~ 0.1 0.3 0.1 1.0 32",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle isleofberk:lightning_aoe_emitter ~ ~1.5 ~ 0 2.5 0 0.1 160 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("execute as @p at @s run playsound minecraft:item.trident.thunder block @s ~ ~ ~ 2 2 1",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~ 0 4 0 2 20 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~-2 ~1 ~ 0 1.5 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~2 ~1 ~ 0 1.5 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~-2 0 1.5 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~2 0 1.5 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~-3 ~1 ~ 0 0.2 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~3 ~1 ~ 0 0.2 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~-3 0 0.2 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~3 0 0.2 0 2 10 force",false))
                .addTimeEvent(new TimeStampedEvent(2.2F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}));
        ComboNode ArcbladeTransformedRevelationGroundForth = ComboNode.createNode(() -> WOMAnimations.TORMENT_CHARGED_ATTACK_2)
                .setConvertTime(0.5F).setPlaySpeed(0.8F)
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~ 0 4 0 2 20 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~-2 ~1 ~ 0 1.5 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~2 ~1 ~ 0 1.5 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~-2 0 1.5 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~2 0 1.5 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~-3 ~1 ~ 0 0.2 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~3 ~1 ~ 0 0.2 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~-3 0 0.2 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~3 0 0.2 0 2 10 force",false))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent
                        (0.1F, "playsound minecraft:block.respawn_anchor.deplete ambient @s ~ ~ ~ 200", false))
                .addTimeEvent(new TimeStampedEvent(0.6F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}));
        ComboNode ArcbladeTransformedRevelationGroundEnd = ComboNode.createNode(() -> WukongAnimations.STAFF_AUTO5)
                .setPlaySpeed(0.7F).setConvertTime(0.1F)
                .addTimeEvent(new TimeStampedEvent(0.1F, entityPatch -> {
                    entityPatch.playSound(WuKongSounds.STACK1.get(), 2F, 0, 0);
                }))
                .addTimeEvent(new TimeStampedEvent(0.55F, entityPatch -> {
                    entityPatch.playSound(WuKongSounds.STACK2.get(), 2F, 0, 0);}))

                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "particle isleofberk:lightning_aoe_emitter ~ ~1.5 ~ 0 2.5 0 0.1 160 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "particle minecraft:wax_off ~ ~1 ~ 0 4 0 2 20 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.6F, "particle minecraft:wax_off ~-2 ~1 ~ 0 1.5 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.6F, "particle minecraft:wax_off ~2 ~1 ~ 0 1.5 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.6F, "particle minecraft:wax_off ~ ~1 ~-2 0 1.5 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.6F, "particle minecraft:wax_off ~ ~1 ~2 0 1.5 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.9F, "particle minecraft:wax_off ~-3 ~1 ~ 0 0.2 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.9F, "particle minecraft:wax_off ~3 ~1 ~ 0 0.2 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.9F, "particle minecraft:wax_off ~ ~1 ~-3 0 0.2 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.9F, "particle minecraft:wax_off ~ ~1 ~3 0 0.2 0 2 10 force", false))

                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.1F, "effect give @s arc:hexagram 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.3F, "invincible groundSlam @s 3 false false false", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.4F, "summon minecraft:lightning_bolt ~1.5 ~ ~", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.4F, "summon minecraft:lightning_bolt ~-1.5 ~ ~", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.4F, "summon minecraft:lightning_bolt ~ ~ ~1.5", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.4F, "summon minecraft:lightning_bolt ~ ~ ~-1.5", false))
                .addTimeEvent(new TimeStampedEvent(0.79F, entityPatch -> {
                    entityPatch.playSound(ArcSoundRegistry.ArcSlash.get(), 1F, 0, 0);
                }));


        ComboNode StarCrakerFinalSkillAlaphaAuto1= ComboNode.createNode(() -> WOMAnimations.MOONLESS_LUNAR_ECHO)
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 5 1", true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:levitation 1 7", true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s arc:timer 1", true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s arc:hexagram1 1", false))
                .setStunTypeModifier(StunType.HOLD)
                .addTimeEvent(new TimeStampedEvent(0.9F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}));;
        ComboNode StarCrakerFinalSkillAlaphaAuto2= ComboNode.createNode(() -> WOMAnimations.AGONY_PLUNGE_FORWARD)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent
                        (0.45F, "effect give @s arc:verticalstop 10", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "playsound minecraft:block.respawn_anchor.deplete ambient @s ~ ~ ~ 200", false))
                .addTimeEvent(new TimeStampedEvent(0.5F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}));
        ComboNode StarCrakerFinalSkillAlaphaAuto3= ComboNode.createNode(() -> WOMAnimations.TORMENT_BERSERK_AUTO_1)
                .setConvertTime(0.2F).setPlaySpeed(0.8F)
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 5 1", true))
                .addHitEvent(BiEvent.createBiCommandEvent("execute as @s at @s run particle irons_spellbooks:electricity ~ ~1.0 ~ 0.1 0.3 0.1 1.0 32",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle isleofberk:lightning_aoe_emitter ~ ~1.5 ~ 0 2.5 0 0.1 160 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("execute as @p at @s run playsound minecraft:item.trident.thunder block @s ~ ~ ~ 2 2 1",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~ 0 4 0 2 20 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~-2 ~1 ~ 0 1.5 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~2 ~1 ~ 0 1.5 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~-2 0 1.5 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~2 0 1.5 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~-3 ~1 ~ 0 0.2 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~3 ~1 ~ 0 0.2 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~-3 0 0.2 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~3 0 0.2 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("summon minecraft:lightning_bolt ~ ~ ~-1.5", false))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }))
                .addTimeEvent(new TimeStampedEvent(0.9F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}));
        ComboNode StarCrakerFinalSkillAlaphaAuto4= ComboNode.createNode(() -> WOMAnimations.MOB_ENDERSTEP_OBSCURIS)
                .addTimeEvent(new TimeStampedEvent(0.5F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "playsound minecraft:block.respawn_anchor.deplete ambient @s ~ ~ ~ 200", false));
        ComboNode StarCrakerFinalSkillAlaphaAuto5= ComboNode.createNode(() -> WOMAnimations.TORMENT_BERSERK_AUTO_2)
                .setConvertTime(0.2F).setPlaySpeed(0.8F)
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 5 1", true))
                .addHitEvent(BiEvent.createBiCommandEvent("execute as @s at @s run particle irons_spellbooks:electricity ~ ~1.0 ~ 0.1 0.3 0.1 1.0 32",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle isleofberk:lightning_aoe_emitter ~ ~1.5 ~ 0 2.5 0 0.1 160 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("execute as @p at @s run playsound minecraft:item.trident.thunder block @s ~ ~ ~ 2 2 1",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~ 0 4 0 2 20 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~-2 ~1 ~ 0 1.5 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~2 ~1 ~ 0 1.5 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~-2 0 1.5 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~2 0 1.5 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~-3 ~1 ~ 0 0.2 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~3 ~1 ~ 0 0.2 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~-3 0 0.2 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~3 0 0.2 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("summon minecraft:lightning_bolt ~ ~ ~1.5", false))
                .addTimeEvent(new TimeStampedEvent(0.9F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}));
        ComboNode StarCrakerFinalSkillAlaphaAuto6= ComboNode.createNode(() -> WOMAnimations.MOB_ENDERSTEP_OBSCURIS)
                .addTimeEvent(new TimeStampedEvent(0.5F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "playsound minecraft:block.respawn_anchor.deplete ambient @s ~ ~ ~ 200", false));
        ComboNode StarCrakerFinalSkillAlaphaAuto7= ComboNode.createNode(() -> WOMAnimations.SOLAR_AUTO_2)
                .setConvertTime(-0.18F).setPlaySpeed(0.95F)
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 5 1", true))
                .addHitEvent(BiEvent.createBiCommandEvent("execute as @s at @s run particle irons_spellbooks:electricity ~ ~1.0 ~ 0.1 0.3 0.1 1.0 32",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle isleofberk:lightning_aoe_emitter ~ ~1.5 ~ 0 2.5 0 0.1 160 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("execute as @p at @s run playsound minecraft:item.trident.thunder block @s ~ ~ ~ 2 2 1",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~ 0 4 0 2 20 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~-2 ~1 ~ 0 1.5 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~2 ~1 ~ 0 1.5 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~-2 0 1.5 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~2 0 1.5 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~-3 ~1 ~ 0 0.2 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~3 ~1 ~ 0 0.2 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~-3 0 0.2 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~3 0 0.2 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("summon minecraft:lightning_bolt ~-1.5 ~ ~", false))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.BLADE_RUSH_FINISHER, 0, 0);
                }))
                .addTimeEvent(new TimeStampedEvent(0.77F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}));
        ComboNode StarCrakerFinalSkillAlaphaAuto8= ComboNode.createNode(() -> WOMAnimations.MOB_ENDERSTEP_OBSCURIS)
                .addTimeEvent(new TimeStampedEvent(0.5F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "playsound minecraft:block.respawn_anchor.deplete ambient @s ~ ~ ~ 200", false));
        ComboNode StarCrakerFinalSkillAlaphaAuto9= ComboNode.createNode(() -> WOMAnimations.SOLAR_AUTO_4_POLVORA)
                .setPlaySpeed(0.8F)
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 5 1", true))
                .addHitEvent(BiEvent.createBiCommandEvent("execute as @s at @s run particle irons_spellbooks:electricity ~ ~1.0 ~ 0.1 0.3 0.1 1.0 32",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle isleofberk:lightning_aoe_emitter ~ ~1.5 ~ 0 2.5 0 0.1 160 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("execute as @p at @s run playsound minecraft:item.trident.thunder block @s ~ ~ ~ 2 2 1",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~ 0 4 0 2 20 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~-2 ~1 ~ 0 1.5 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~2 ~1 ~ 0 1.5 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~-2 0 1.5 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~2 0 1.5 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~-3 ~1 ~ 0 0.2 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~3 ~1 ~ 0 0.2 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~-3 0 0.2 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~3 0 0.2 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("summon minecraft:lightning_bolt ~1.5 ~ ~", false))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.BLADE_RUSH_FINISHER, 0, 0);
                }))
                .addTimeEvent(new TimeStampedEvent(0.69F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}));
        ComboNode StarCrakerFinalSkillAlaphaAuto10= ComboNode.createNode(() -> WOMAnimations.MOB_ENDERSTEP_OBSCURIS)
                .addTimeEvent(new TimeStampedEvent(0.5F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "playsound minecraft:block.respawn_anchor.deplete ambient @s ~ ~ ~ 200", false));
        ComboNode StarCrakerFinalSkillAlaphaAuto11= ComboNode.createNode(() -> StarAnimations.WIND_SLASH)
                .setConvertTime(-0.25F)
                .setStunTypeModifier(StunType.HOLD)
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.BLADE_RUSH_FINISHER, 0, 0);
                }))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 5 1", true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s arc:hexagram 1", false))

                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "effect give @s cofh_core:lightning_resistance 10", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.8F, "summon minecraft:lightning_bolt ~1.5 ~ ~", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.5F, "summon minecraft:lightning_bolt ~-1.5 ~ ~", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(2.1F, "summon minecraft:lightning_bolt ~1.5 ~ ~", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(2.3F, "effect clear @s arc:verticalstop", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(2.3F, "effect give @s arc:heavygravity 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(2.3F, "effect clear @s arc:verticalstop", true))
                .addHitEvent(BiEvent.createBiCommandEvent("execute as @s at @s run particle irons_spellbooks:electricity ~ ~1.0 ~ 0.1 0.3 0.1 1.0 32",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle isleofberk:lightning_aoe_emitter ~ ~1.5 ~ 0 2.5 0 0.1 160 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("execute as @p at @s run playsound minecraft:item.trident.thunder block @s ~ ~ ~ 2 2 1",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~ 0 4 0 2 20 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~-2 ~1 ~ 0 1.5 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~2 ~1 ~ 0 1.5 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~-2 0 1.5 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~2 0 1.5 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~-3 ~1 ~ 0 0.2 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~3 ~1 ~ 0 0.2 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~-3 0 0.2 0 2 10 force",false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle minecraft:wax_off ~ ~1 ~3 0 0.2 0 2 10 force",false))
                .addTimeEvent(new TimeStampedEvent(2.35F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}));
        ComboNode StarCrakerFinalSkillAlaphaAuto12= ComboNode.createNode(() -> WukongAnimations.SMASH_CHARGED4)
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
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "effect give @s arc:strike 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "effect give @s arc:strike 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.8F, "effect give @s arc:strike 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1F, "effect give @s arc:strike 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.2F, "effect give @s arc:strike 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.4F, "effect give @s arc:strike 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(2.3F, "playsound minecraft:block.respawn_anchor.deplete ambient @s ~ ~ ~ 100", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(2.3F, "particle minecraft:explosion ~ ~1.5 ~ 0 1 0 1 1 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(2.9F, "summon minecraft:lightning_bolt ~4 ~ ~", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(2.9F, "summon minecraft:lightning_bolt ~-4 ~ ~", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(2.9F, "summon minecraft:lightning_bolt ~ ~ ~4", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(2.9F, "summon minecraft:lightning_bolt ~ ~ ~-4", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(2.65F, "effect give @s arc:hexagram 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(2.8F, "invincible groundSlam @s 3 false false false", true))
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
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.9F, "effect give @s arc:heavygravity 1", false))
                .addTimeEvent(new TimeStampedEvent(2.3F, entityPatch -> {
                    entityPatch.playSound(ArcSoundRegistry.ArcSlash.get(), 1F, 0, 0);
                }))
                .addTimeEvent(new TimeStampedEvent(3.1F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}));
        ComboNode StarCrakerFinalSkillAlaphaAuto13= ComboNode.createNode(() -> WOMAnimations.AGONY_AUTO_2);

        ComboNode StarCrakerFinalSkillBetaAuto= ComboNode.createNode(() -> WOMAnimations.MOONLESS_LUNAR_ECHO)
                .addTimeEvent(new TimeStampedEvent(0.8F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 5 1", true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:levitation 1 5", true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:slow_falling 2", true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:levitation 1", false));;
        ComboNode StarCrakerFinalSkillBetaAuto1= ComboNode.createNode(() -> WOMAnimations.AGONY_AUTO_2)
                .setConvertTime(-0.05F).setPlaySpeed(1.2F)
                .addTimeEvent(new TimeStampedEvent(0.7F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 5 1", true));
        ComboNode StarCrakerFinalSkillBetaAuto2= ComboNode.createNode(() -> WOMAnimations.AGONY_PLUNGE_FORWARD)
                .addTimeEvent(new TimeStampedEvent(0.25F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}));
        ComboNode StarCrakerFinalSkillBetaAuto3= ComboNode.createNode(() -> WOMAnimations.AGONY_AIR_SLASH)
                .setConvertTime(-0.1F).setPlaySpeed(0.7F)
                .addTimeEvent(new TimeStampedEvent(0.6F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 5 1", true));
        ComboNode StarCrakerFinalSkillBetaAuto4= ComboNode.createNode(() -> WOMAnimations.MOB_ENDERSTEP_OBSCURIS)
                .addTimeEvent(new TimeStampedEvent(0.5F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}));
        ComboNode StarCrakerFinalSkillBetaAuto5= ComboNode.createNode(() -> Animations.TSUNAMI_REINFORCED)
                .setStunTypeModifier(StunType.HOLD)
                .setConvertTime(-0.2F)
                .addTimeEvent(new TimeStampedEvent(0.4F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 5 1", true));
        ComboNode StarCrakerFinalSkillBetaAuto6= ComboNode.createNode(() -> WOMAnimations.TORMENT_CHARGED_ATTACK_1)
                .setPlaySpeed(1.2F)
                .setStunTypeModifier(StunType.HOLD)
                .addTimeEvent(new TimeStampedEvent(1.5F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 5 1", true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s epicfight:stun_immunity 2", true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s star:really_stun_immunity 2", true));;
        ComboNode StarCrakerFinalSkillBetaAuto7= ComboNode.createNode(() -> WOMAnimations.MOB_ENDERSTEP_OBSCURIS)
                .addTimeEvent(new TimeStampedEvent(0.5F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}));
        ComboNode StarCrakerFinalSkillBetaAuto8= ComboNode.createNode(() -> Animations.TSUNAMI_REINFORCED)
                .setStunTypeModifier(StunType.HOLD)
                .setConvertTime(-0.2F)
                .addTimeEvent(new TimeStampedEvent(0.45F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 5 1", true));
        ComboNode StarCrakerFinalSkillBetaAuto9= ComboNode.createNode(() -> StarAnimations.WIND_SLASH)
                .setConvertTime(-0.25F)
                .setPlaySpeed(1.2F)
                .setStunTypeModifier(StunType.HOLD)
                .addTimeEvent(new TimeStampedEvent(2F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 5 1", true));;
        ComboNode StarCrakerFinalSkillBetaAuto10=ComboNode.createNode(() -> WOMAnimations.MOB_ENDERSTEP_OBSCURIS)
                .addTimeEvent(new TimeStampedEvent(0.5F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}));
        ComboNode StarCrakerFinalSkillBetaAuto11= ComboNode.createNode(() -> WOMAnimations.TORMENT_BERSERK_AUTO_1)
                .setPlaySpeed(0.9F).setConvertTime(0.2F)
                .setStunTypeModifier(StunType.HOLD)
                .addTimeEvent(new TimeStampedEvent(0.6F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 20 1", true));
        ComboNode StarCrakerFinalSkillBetaAuto12= ComboNode.createNode(() -> WOMAnimations.TORMENT_BERSERK_AUTO_2)
                .setPlaySpeed(0.9F).setConvertTime(0.2F)
                .setStunTypeModifier(StunType.HOLD)
                .addTimeEvent(new TimeStampedEvent(0.6F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 20 1", true));;
        ComboNode StarCrakerFinalSkillBetaAuto13= ComboNode.createNode(() -> WOMAnimations.TORMENT_CHARGED_ATTACK_2)
                .setConvertTime(0.1F).setPlaySpeed(0.9F)
                .setStunTypeModifier(StunType.HOLD)
                .addTimeEvent(new TimeStampedEvent(0.7F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 20 1", true));;
        ComboNode StarCrakerFinalSkillBetaAuto14= ComboNode.createNode(() -> WOMAnimations.AGONY_PLUNGE_FORWARD)
                .setConvertTime(-0.05F)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent
                        (0.55F, "effect give @s arc:verticalstop 10", false))
                .addTimeEvent(new TimeStampedEvent(0.55F, (entityPatch) -> {
                    entityPatch.playSound(EpicFightSounds.ENDER_DRAGON_CRYSTAL_LINK,0,0);}))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.56F, "particle epicfight:force_field ~ ~ ~", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.56F, "particle epicfight:force_field ~ ~0.5 ~", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.56F, "particle epicfight:force_field ~ ~-0.5 ~", false))
                .addTimeEvent(new TimeStampedEvent(0.6F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 5 1", true));;
        ComboNode StarCrakerFinalSkillBetaAuto15= ComboNode.createNode(() -> WOMAnimations.GESETZ_SPRENGKOPF)
                .setConvertTime(8F)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(6.1F, "effect clear @s arc:verticalstop", false))
                .addTimeEvent(new TimeStampedEvent(6.1F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 5 1", true));;
        ComboNode StarCrakerFinalSkillBetaAuto16= ComboNode.createNode(() -> WOMAnimations.AGONY_PLUNGE_FORWARD)
                .setConvertTime(-0.65F)
                .addTimeEvent(new TimeStampedEvent(0.66F, (entityPatch) -> {
                    entityPatch.playSound(EpicFightSounds.ENDER_DRAGON_BREATH_FINALE,1.5F,0,0);}))
                .addTimeEvent(new TimeStampedEvent(0.74F, (entityPatch) -> {
                    entityPatch.playSound(ArcSoundRegistry.ArcSlash.get(),0,0);}))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.66F, "particle epicfight:force_field_end", false))
                .addTimeEvent(new TimeStampedEvent(1.3F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 5 1", true));
        ComboNode StarCrakerFinalSkillBetaAuto17= ComboNode.createNode(() -> WOMAnimations.SOLAR_AUTO_4_POLVORA)
                .setConvertTime(0.1F)
                .addTimeEvent(new TimeStampedEvent(0.9F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 5 1", true));
        ComboNode StarCrakerFinalSkillBetaAuto18= ComboNode.createNode(() -> Animations.WRATHFUL_LIGHTING)
                .setConvertTime(0.1F).setPlaySpeed(0.9F)
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 5 1", true))
                .addTimeEvent(new TimeStampedEvent(0.85F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}));
        ComboNode StarCrakerFinalSkillBetaAuto19= ComboNode.createNode(() -> Animations.WRATHFUL_LIGHTING)
                .setConvertTime(0.15F).setPlaySpeed(0.9F)
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 5 1", true))
                .addTimeEvent(new TimeStampedEvent(0.9F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}));;
        ComboNode StarCrakerFinalSkillBetaAuto20= ComboNode.createNode(() -> Animations.WRATHFUL_LIGHTING)
                .setConvertTime(0.25F).setPlaySpeed(0.8F)
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 1 1", true))
                .addTimeEvent(new TimeStampedEvent(1.3F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}));;



        //初始基础攻击
        ComboNode ArcbladeTransformedBasicAttack = ComboNode.create()
                .addConditionAnimation(ArcbladeTransformedAirStrikeAuto1)
                .addConditionAnimation(ArcbladeTransformedDashSlash)
                .addConditionAnimation(ArcbladeTransformedAuto1);
        //普攻连段
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
        //识破
        ComboNode ArcbladeTransformedRevelation = ComboNode.create()
                .addConditionAnimation(ArcbladeTransformedRevelationAirFirst)
                .addConditionAnimation(ArcbladeTransformedRevelationGroundFirst);
        //大招
        ComboNode StarCrashFinal =ComboNode.create()
                .addConditionAnimation(StarCrakerFinalSkillAlaphaAuto1)
                .addConditionAnimation(StarCrakerFinalSkillBetaAuto);
        //普攻以及重置普攻
        ArcbladeTransformedroot.key1(ArcbladeTransformedBasicAttack);
        ArcbladeTransformedAuto5.key1(ArcbladeTransformedBasicAttack);
        ArcbladeTransformedRevelationAirEnd.key1(ArcbladeTransformedBasicAttack);
        ArcbladeTransformedRevelationAirFirst.key1(ArcbladeTransformedBasicAttack);
        ArcbladeTransformedRevelationGroundEnd.key1(ArcbladeTransformedAutoAttack4);
        ArcbladeTransformedRevelationGroundFirst.key1(ArcbladeTransformedAutoAttack2);
        //常态可释放的技能
        ArcbladeTransformedroot.keyWeaponInnate(ArcbladeTransformedRevelation);//使用识破
        ArcbladeTransformedroot.key3(StarCrakerFinalSkillAlaphaAuto1);
        ArcbladeTransformedroot.key4(StarCrakerFinalSkillBetaAuto);
        //疾跑攻击后续派生
        ArcbladeTransformedDashSlash.key1(ArcbladeTransformedAutoAttack2);
        //跳跃攻击以及后续派生
        ArcbladeTransformedAirStrikeAuto1.key1(ArcbladeTransformedAirStrikeAuto2);
        ArcbladeTransformedAirStrikeAuto1.keyWeaponInnate(ArcbladeTransformedRevelationAirFirst);//空中1A后使用空中识破
        ArcbladeTransformedAirStrikeAuto2.key1(ArcbladeTransformedAutoAttack3);
        //空中识破
        ArcbladeTransformedRevelationAirFirst.keyWeaponInnate(ArcbladeTransformedRevelationAirSecond);
        ArcbladeTransformedRevelationAirSecond.keyWeaponInnate(ArcbladeTransformedRevelationAirThird);
        ArcbladeTransformedRevelationAirThird.keyWeaponInnate(ArcbladeTransformedRevelationAirEnd);
        //地面识破
        ArcbladeTransformedAuto1.keyWeaponInnate(ArcbladeTransformedRevelationGroundFirst);
        ArcbladeTransformedRevelationGroundFirst.keyWeaponInnate(ArcbladeTransformedRevelationGroundSecond);
        ArcbladeTransformedRevelationGroundSecond.keyWeaponInnate(ArcbladeTransformedRevelationGroundThird);
        ArcbladeTransformedRevelationGroundThird.keyWeaponInnate(ArcbladeTransformedRevelationGroundForth);
        ArcbladeTransformedRevelationGroundForth.keyWeaponInnate(ArcbladeTransformedRevelationGroundEnd);
        //大招1
        StarCrakerFinalSkillAlaphaAuto1.keyWeaponInnate(StarCrakerFinalSkillAlaphaAuto2);
        StarCrakerFinalSkillAlaphaAuto2.keyWeaponInnate(StarCrakerFinalSkillAlaphaAuto3);
        StarCrakerFinalSkillAlaphaAuto3.keyWeaponInnate(StarCrakerFinalSkillAlaphaAuto4);
        StarCrakerFinalSkillAlaphaAuto4.keyWeaponInnate(StarCrakerFinalSkillAlaphaAuto5);
        StarCrakerFinalSkillAlaphaAuto5.keyWeaponInnate(StarCrakerFinalSkillAlaphaAuto6);
        StarCrakerFinalSkillAlaphaAuto6.keyWeaponInnate(StarCrakerFinalSkillAlaphaAuto7);
        StarCrakerFinalSkillAlaphaAuto7.keyWeaponInnate(StarCrakerFinalSkillAlaphaAuto8);
        StarCrakerFinalSkillAlaphaAuto8.keyWeaponInnate(StarCrakerFinalSkillAlaphaAuto9);
        StarCrakerFinalSkillAlaphaAuto9.keyWeaponInnate(StarCrakerFinalSkillAlaphaAuto10);
        StarCrakerFinalSkillAlaphaAuto10.keyWeaponInnate(StarCrakerFinalSkillAlaphaAuto11);
        StarCrakerFinalSkillAlaphaAuto11.keyWeaponInnate(StarCrakerFinalSkillAlaphaAuto12);
        StarCrakerFinalSkillAlaphaAuto12.keyWeaponInnate(StarCrakerFinalSkillAlaphaAuto13);
        //大招2
        StarCrakerFinalSkillBetaAuto.keyWeaponInnate(StarCrakerFinalSkillBetaAuto1);
        StarCrakerFinalSkillBetaAuto1.keyWeaponInnate(StarCrakerFinalSkillBetaAuto2);
        StarCrakerFinalSkillBetaAuto2.keyWeaponInnate(StarCrakerFinalSkillBetaAuto3);
        StarCrakerFinalSkillBetaAuto3.keyWeaponInnate(StarCrakerFinalSkillBetaAuto4);
        StarCrakerFinalSkillBetaAuto4.keyWeaponInnate(StarCrakerFinalSkillBetaAuto5);
        StarCrakerFinalSkillBetaAuto5.keyWeaponInnate(StarCrakerFinalSkillBetaAuto6);
        StarCrakerFinalSkillBetaAuto6.keyWeaponInnate(StarCrakerFinalSkillBetaAuto7);
        StarCrakerFinalSkillBetaAuto7.keyWeaponInnate(StarCrakerFinalSkillBetaAuto8);
        StarCrakerFinalSkillBetaAuto8.keyWeaponInnate(StarCrakerFinalSkillBetaAuto9);
        StarCrakerFinalSkillBetaAuto9.keyWeaponInnate(StarCrakerFinalSkillBetaAuto10);
        StarCrakerFinalSkillBetaAuto10.keyWeaponInnate(StarCrakerFinalSkillBetaAuto11);
        StarCrakerFinalSkillBetaAuto11.keyWeaponInnate(StarCrakerFinalSkillBetaAuto12);
        StarCrakerFinalSkillBetaAuto12.keyWeaponInnate(StarCrakerFinalSkillBetaAuto13);
        StarCrakerFinalSkillBetaAuto13.keyWeaponInnate(StarCrakerFinalSkillBetaAuto14);
        StarCrakerFinalSkillBetaAuto14.keyWeaponInnate(StarCrakerFinalSkillBetaAuto15);
        StarCrakerFinalSkillBetaAuto15.keyWeaponInnate(StarCrakerFinalSkillBetaAuto16);
        StarCrakerFinalSkillBetaAuto16.keyWeaponInnate(StarCrakerFinalSkillBetaAuto17);
        StarCrakerFinalSkillBetaAuto17.keyWeaponInnate(StarCrakerFinalSkillBetaAuto18);
        StarCrakerFinalSkillBetaAuto18.keyWeaponInnate(StarCrakerFinalSkillBetaAuto19);
        StarCrakerFinalSkillBetaAuto19.keyWeaponInnate(StarCrakerFinalSkillBetaAuto20);

        //普攻
        ArcbladeTransformedAuto1.key1(ArcbladeTransformedAutoAttack2);
        ArcbladeTransformedAuto2.key1(ArcbladeTransformedAutoAttack3);
        ArcbladeTransformedAuto3.key1(ArcbladeTransformedAutoAttack4);
        ArcbladeTransformedAuto4.key1(ArcbladeTransformedAutoAttack5);

        SkillManager.register(ArcbladeTransformedSkill::new, ArcbladeTransformedSkill.createComboBasicAttack().setCombo(ArcbladeTransformedroot).setShouldDrawGui(true), ArcMod.MOD_ID, "combo1");
    }

    @SubscribeEvent
    public static void BuildSkills(SkillBuildEvent event) {
        ArcbladeTransformed = event.build(ArcMod.MOD_ID, "combo1");
    }
}