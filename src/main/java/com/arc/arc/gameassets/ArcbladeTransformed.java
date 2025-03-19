package com.arc.arc.gameassets;

import com.arc.arc.ArcMod;
import com.arc.arc.Registries.ArcEffectsRegistry;
import com.arc.arc.Registries.ArcSoundRegistry;
import com.arc.arc.skill.ArcbladeTransformedSkill;
import com.guhao.star.efmex.StarAnimations;
import com.nameless.toybox.ToyBox;
import com.p1nero.invincible.api.events.BiEvent;
import com.p1nero.invincible.api.events.TimeStampedEvent;
import com.p1nero.invincible.conditions.*;
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
import yesman.epicfight.gameasset.EpicFightSounds;
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
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "playsound minecraft:block.respawn_anchor.deplete ambient @s ~ ~ ~ 100", false))
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
                .addHitEvent(BiEvent.createBiCommandEvent("summon minecraft:lightning_bolt ~3 ~ ~",false))
                .addHitEvent(BiEvent.createBiCommandEvent("summon minecraft:lightning_bolt ~-3 ~ ~",false))
                .addHitEvent(BiEvent.createBiCommandEvent("summon minecraft:lightning_bolt ~ ~ ~3",false))
                .addHitEvent(BiEvent.createBiCommandEvent("summon minecraft:lightning_bolt ~ ~ ~-3",false))
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
                .addTimeEvent(new TimeStampedEvent(0.6F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.WEAPON_INNATE);
                    }}));
        ComboNode ArcbladeTransformedRevelationGroundEnd = ComboNode.createNode(() -> WukongAnimations.STAFF_AUTO5)
                .setPlaySpeed(0.7F).setConvertTime(0.1F)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent
                        (0.0F, "playsound minecraft:block.respawn_anchor.deplete ambient @s ~ ~ ~ 100", false))
                .addTimeEvent(new TimeStampedEvent(0.1F, entityPatch -> {
                    entityPatch.playSound(WuKongSounds.STACK3.get(), 2F, 0, 0);
                }))
                .addTimeEvent(new TimeStampedEvent(0.6F, entityPatch -> {
                    entityPatch.playSound(WuKongSounds.STACK4.get(), 2F, 0, 0);}))
                .addHitEvent(BiEvent.createBiCommandEvent("summon minecraft:lightning_bolt ~1.5 ~ ~",false))
                .addHitEvent(BiEvent.createBiCommandEvent("summon minecraft:lightning_bolt ~-1.5 ~ ~",false))
                .addHitEvent(BiEvent.createBiCommandEvent("summon minecraft:lightning_bolt ~ ~ ~1.5",false))
                .addHitEvent(BiEvent.createBiCommandEvent("summon minecraft:lightning_bolt ~ ~ ~-1.5",false))
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
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(ArcSoundRegistry.ArcSlash.get(), 0, 0);
                }));
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
        //普攻以及重置普攻
        ArcbladeTransformedroot.key1(ArcbladeTransformedBasicAttack);
        ArcbladeTransformedAuto5.key1(ArcbladeTransformedBasicAttack);
        ArcbladeTransformedRevelationAirEnd.key1(ArcbladeTransformedBasicAttack);
        ArcbladeTransformedRevelationAirFirst.key1(ArcbladeTransformedBasicAttack);
        ArcbladeTransformedRevelationGroundEnd.key1(ArcbladeTransformedAutoAttack4);
        ArcbladeTransformedRevelationGroundFirst.key1(ArcbladeTransformedAutoAttack2);
        //常态可释放的技能
        ArcbladeTransformedroot.keyWeaponInnate(ArcbladeTransformedRevelation);//使用识破
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