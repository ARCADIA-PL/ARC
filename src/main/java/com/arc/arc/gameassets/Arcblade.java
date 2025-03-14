package com.arc.arc.gameassets;

import com.arc.arc.ArcMod;
import com.arc.arc.init.ArcEffectsRegistry;
import com.arc.arc.skill.ArcbladeSkill;
import com.arc.arc.sound.ArcSoundRegistry;
import com.dfdyz.epicacg.registry.MyAnimations;
import com.guhao.star.Star;
import com.guhao.star.efmex.StarAnimations;
import com.guhao.star.regirster.Sounds;
import com.p1nero.invincible.api.events.BiEvent;
import com.p1nero.invincible.api.events.TimeStampedEvent;
import com.p1nero.invincible.conditions.*;
import com.p1nero.invincible.skill.api.ComboNode;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.WOMSounds;
import yesman.epicfight.api.data.reloader.SkillManager;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.damagesource.StunType;

@Mod.EventBusSubscriber(modid = ArcMod.MOD_ID)
public class Arcblade {
    public static Skill Arcblade;

    public static void registerSkills() {
        ComboNode Arcbladeroot = ComboNode.create();
        ComboNode ArcJump = ComboNode.createNode(() -> StarAnimations.YAMATO_AIRSLASH)
                .setConvertTime(-0.1F)
                .setPlaySpeed(1.3F)
                .addCondition(new JumpCondition())
                .setDamageMultiplier(ValueModifier.multiplier(0.5F))
                .setPriority(5);
        ;
        ComboNode ArcDash = ComboNode.createNode(() -> StarAnimations.YAMATO_DASH)
                .setConvertTime(-0.1F)
                .setPlaySpeed(1.2F)
                .addCondition(new SprintingCondition())
                .setDamageMultiplier(ValueModifier.multiplier(0.5F))
                .setPriority(5);
        ;
        ComboNode ArcAuto1 = ComboNode.createNode(() -> StarAnimations.TACHI_TWOHAND_AUTO_1)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "effect give @s irons_spellbooks:rend 2 10", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect clear @s irons_spellbooks:rend", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s epicfight:stun_immunity 2", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s star:really_stun_immunity 2", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:resistance 2 4", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s irons_spellbooks:rend 4 16", true))
                .setDamageMultiplier(ValueModifier.multiplier(0.75F))
                .setPlaySpeed(1.25F)
                .setPriority(1);
        ;
        ComboNode ArcAuto2 = ComboNode.createNode(() -> StarAnimations.YAMATO_AUTO3)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "effect give @s irons_spellbooks:rend 2 10", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect clear @s irons_spellbooks:rend", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s epicfight:stun_immunity 2", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s star:really_stun_immunity 2", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:resistance 2 4", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s irons_spellbooks:rend 4 16", true))
                .setStunTypeModifier(StunType.SHORT)
                .setDamageMultiplier(ValueModifier.multiplier(0.75F))
                .setPlaySpeed(1.17F);
        ;
        ComboNode ArcAuto3 = ComboNode.createNode(() -> StarAnimations.YAMATO_AUTO4)
                .addTimeEvent(new TimeStampedEvent(1.2F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(StarAnimations.YAMATO_STEP_FORWARD, 0.0F);
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }))
                .addTimeEvent(new TimeStampedEvent(0.45F, (entityPatch) -> {
                    entityPatch.playSound(ArcSoundRegistry.VoidSlash.get(),0,0);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "effect give @s irons_spellbooks:rend 4 14", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect clear @s irons_spellbooks:rend", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s epicfight:stun_immunity 6", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s star:really_stun_immunity 6", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:resistance 6 4", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s alexsmobs:soulsteal 8 10", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:instant_health 1 10", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s irons_spellbooks:rend 6 18", true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s irons_spellbooks:blight 10 0", true))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("particle epicacg:dmc_jc_blade_trail ~0 ~0.0 ~0 0.0 1.0 0.0 0.02 1 force @s", false))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("effect give @s cataclysm:stun 3", true))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 1 0.5", true))
                .setPlaySpeed(1.4F)
                .setCanBeInterrupt(false)
                .setDamageMultiplier(ValueModifier.multiplier(1.2F))
                .setConvertTime(-0.28F)
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s irons_spellbooks:rend 3 15", true))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible setPlayerPhase 2", false));
        ;
        ;
        ;
        ComboNode ArcAuto4 = ComboNode.createNode(() -> WOMAnimations.KATANA_AUTO_2)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "effect give @s irons_spellbooks:rend 4 16", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect clear @s irons_spellbooks:rend", false))
                .setDamageMultiplier(ValueModifier.multiplier(0.5F))
                .setConvertTime(-0.1F)
                .setPlaySpeed(1.3F);
        ComboNode ArcPowerAuto4 = ComboNode.createNode(() -> StarAnimations.FATAL_DRAW)
                .setNotCharge(true)
                .setCanBeInterrupt(false)
                .setPlaySpeed(1.05F)
                .setDamageMultiplier(ValueModifier.multiplier(1.2F))
                .addTimeEvent(new TimeStampedEvent(0.8F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(Animations.RUSHING_TEMPO3, 0.0F);
                }))
                .setConvertTime(-0.5F)
                .setPriority(4)
                .addCondition(new PlayerPhaseCondition(2, 2))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.6F, "invincible groundSlam @s 1.5 false false false", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.3F, "invincible setPlayerPhase 1", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:absorption 10 8", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.6F, "invincible consumeStack -1", false))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.2 0.5", true));


        ComboNode ArcAuto5 = ComboNode.createNode(() -> WOMAnimations.ENDERBLASTER_ONEHAND_AUTO_3)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "effect give @s irons_spellbooks:rend 4 16", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect clear @s irons_spellbooks:rend", false))
                .setDamageMultiplier(ValueModifier.multiplier(0.3F))
                .setPlaySpeed(1.1F);
        ;
        ComboNode ArcPowerAuto5 = ComboNode.createNode(() -> WOMAnimations.ENDERBLASTER_ONEHAND_AUTO_4)
                .setNotCharge(true)
                .setPriority(4)
                .setPlaySpeed(1.2F)
                .setConvertTime(-0.2F)
                .setCanBeInterrupt(false)
                .setDamageMultiplier(ValueModifier.multiplier(1.2F))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.0F, "invincible setPlayerPhase 1", false))
                .addTimeEvent(new TimeStampedEvent(0.5F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(StarAnimations.FATAL_DRAW_DASH, -0.5F);
                }))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:absorption 10 8", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "invincible consumeStack -1", false))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.2 0.5", true));
        ;
        ;


        ComboNode ArcAuto6 = ComboNode.createNode(() -> StarAnimations.YAMATO_POWER3_FINISH)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "effect give @s irons_spellbooks:rend 4 16", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect clear @s irons_spellbooks:rend", false))
                .addTimeEvent(new TimeStampedEvent(0.9F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(StarAnimations.YAMATO_STEP_BACKWARD, 0.1F);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "particle epicacg:dmc_jc_blade_trail ~0 ~0.0 ~0 0.0 1.5 0.0 0.00 1 force @s", false))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "effect give @s irons_spellbooks:abyssal_shroud 1 0", false))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStack -1", false))
                .setCanBeInterrupt(false)
                .setDamageMultiplier(ValueModifier.multiplier(0.8F))
                .setConvertTime(0.1F)
                .setPlaySpeed(1.1F);
        ;
        ComboNode ArcPowerAuto6 = ComboNode.createNode(() -> StarAnimations.YAMATO_COUNTER1)
                .addTimeEvent(new TimeStampedEvent(0.9F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(StarAnimations.FATAL_DRAW_DASH, -0.6F);
                }))
                .setCanBeInterrupt(false)
                .setDamageMultiplier(ValueModifier.multiplier(1.2F))
                .setNotCharge(true)
                .setPriority(4)
                .setPlaySpeed(1.5F)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.15F,"particle epicacg:genshin_bow_landing ~0.0 ~0.0 ~0 0.0 0.0 0.0 0.3 5 force @s",false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible consumeStack -1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.0F, "invincible setPlayerPhase 1", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:absorption 10 8", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.4F, "effect give @s irons_spellbooks:abyssal_shroud 1 0", false))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.2 0.5", true))


                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.25F, "particle isleofberk:lightning_aoe_emitter ~ ~1.5 ~ 0 2.5 0 0.1 160 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "particle minecraft:wax_off ~ ~1 ~ 0 4 0 2 20 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "particle minecraft:wax_off ~-2 ~1 ~ 0 1.5 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "particle minecraft:wax_off ~2 ~1 ~ 0 1.5 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "particle minecraft:wax_off ~ ~1 ~-2 0 1.5 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "particle minecraft:wax_off ~ ~1 ~2 0 1.5 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.7F, "particle minecraft:wax_off ~-3 ~1 ~ 0 0.2 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.7F, "particle minecraft:wax_off ~3 ~1 ~ 0 0.2 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.7F, "particle minecraft:wax_off ~ ~1 ~-3 0 0.2 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.7F, "particle minecraft:wax_off ~ ~1 ~3 0 0.2 0 2 10 force", false))

                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "playsound minecraft:block.respawn_anchor.deplete ambient @s ~ ~ ~ 20", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "effect give @s cofh_core:lightning_resistance 5", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.9F, "particle minecraft:explosion ~ ~1.5 ~ 0 1 0 1 1 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.9F, "playsound minecraft:entity.generic.explode ambient @s ~ ~ ~ 5", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.97F, "summon minecraft:lightning_bolt ~ ~ ~", true));
        ;


        ComboNode ArcdashSkill = ComboNode.createNode(() -> StarAnimations.YAMATO_STRIKE1)
                .addCondition(new SprintingCondition())
                .addCondition(new StackCondition(1, 8))
                .setCanBeInterrupt(false)
                .setConvertTime(-0.2F)
                .setPlaySpeed(1.2F)
                .setPriority(7)
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "invincible consumeStack 1", false))
                .setDamageMultiplier(ValueModifier.multiplier(0.6F))
                .setPriority(6);

        ComboNode ArcjumpSkill = ComboNode.createNode(() -> Animations.RUSHING_TEMPO3)
                .addCondition(new JumpCondition())
                .addCondition(new StackCondition(3, 8))
                .setCanBeInterrupt(false)
                .setPriority(7)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible consumeStack 3", false))
                .setDamageMultiplier(ValueModifier.multiplier(3F))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStack -1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "invincible groundSlam @s 3.8 false false false", true))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 2.5 0.5", true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s irons_spellbooks:rend 3 15", true));


        ComboNode Free = ComboNode.createNode(() -> Animations.BIPED_STEP_BACKWARD)
                .addCondition(new StackCondition(1, 8))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack 1", false));

        ComboNode Arctest = ComboNode.createNode(() -> WOMAnimations.SOLAR_HORNO)
                .setConvertTime(0.35F)
                .setPlaySpeed(0.7F)
                .addTimeEvent(new TimeStampedEvent(0.35F, (entityPatch) -> {
                    entityPatch.playSound(WOMSounds.SOLAR_HIT,0,0);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.45F, "particle epicfight:force_field ~ ~0.5 ~ 0 0 0 1 1", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.55F, "invincible groundSlam @s 2 false false false", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.15F, "effect give @s arc:stellaris_arcbladetransformeffect 20 1", false));


        ComboNode ArcbasicAttack = ComboNode.create().addConditionAnimation(ArcJump)
                .addConditionAnimation(ArcDash)
                .addConditionAnimation(ArcAuto1)
                .setPriority(6);

        ComboNode ArcAutoDash2 = ComboNode.create()
                .addConditionAnimation(ArcDash)
                .addConditionAnimation(ArcJump)
                .addConditionAnimation(ArcAuto2);
        ComboNode ArcAutoDash3 = ComboNode.create()
                .addConditionAnimation(ArcDash)
                .addConditionAnimation(ArcJump)
                .addConditionAnimation(ArcAuto3);
        ComboNode ArcAutoDash4 = ComboNode.create()
                .addConditionAnimation(ArcDash)
                .addConditionAnimation(ArcJump)
                .addConditionAnimation(ArcAuto4)
                .addConditionAnimation(ArcPowerAuto4);
        ComboNode ArcAutoDash5 = ComboNode.create()
                .addConditionAnimation(ArcDash)
                .addConditionAnimation(ArcJump)
                .addConditionAnimation(ArcAuto5);
        ComboNode ArcAutoDash6 = ComboNode.create()
                .addConditionAnimation(ArcDash)
                .addConditionAnimation(ArcJump)
                .addConditionAnimation(ArcAuto6);


        Arcbladeroot.key1(ArcbasicAttack);//初始无条件使用1A 疾跑攻击 跳跃攻击
        Arcbladeroot.key4(Arctest);
        ArcDash.key1(ArcAuto1);//疾跑攻击后接1A
        ArcdashSkill.key1(ArcAutoDash2);//疾跑技能后接2A
        ArcJump.key1(ArcAutoDash2);//跳跃攻击后接2A
        ArcjumpSkill.key1(ArcAutoDash2);//跳跃技能后接2A

        ArcAuto1.key1(ArcAutoDash2);//1A后接2A

        ArcAuto2.key1(ArcAutoDash3);//2A后接3A

        ArcAuto3.key1(ArcAutoDash4);//3A后接4A

        ArcAuto4.key1(ArcAutoDash5);//4A后接5A
        ArcPowerAuto4.key1(ArcPowerAuto5);//强化4A后接5A


        ArcAuto5.key1(ArcAutoDash6);//5A后接6A
        ArcPowerAuto5.key1(ArcPowerAuto6);//强化5A后接6A


        ArcAuto6.key1(ArcbasicAttack);//6A后可使用1A 疾跑攻击 跳跃攻击
        ArcPowerAuto6.key1(ArcbasicAttack);//强化6A后可使用1A 疾跑攻击 跳跃攻击


        ComboNode ArcGP1 = ComboNode.createNode(() -> Animations.BIPED_STEP_BACKWARD)
                .setPriority(3)
                .addCondition(new StackCondition(1, 8))
                .setCooldown(140)
                .addCondition(new CooldownCondition(false))
                .setCanBeInterrupt(false)
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("invincible consumeStack -2", false))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("invincible setPlayerPhase 1", false))
                .addDodgeSuccessEvent(new BiEvent(((entityPatch, entity) -> entityPatch.playSound(Sounds.FORESIGHT, 0, 0))))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("invincible entityAfterImage @s", true))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("effect give @s cataclysm:stun 1", true))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 0.3 0.6", true))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("effect give @s irons_spellbooks:abyssal_shroud 2 0", false));
        ;


        ComboNode ArcGP1extendA = ComboNode.createNode(() -> StarAnimations.YAMATO_POWER0_2)
                .addCondition(new StackCondition(1, 8))
                .addCondition(new PlayerPhaseCondition(1,2))
                .setPriority(4)
                .addCondition(new DodgeSuccessCondition())
                .setCanBeInterrupt(false)
                .setPlaySpeed(1.3F)
                .addCondition(new StackCondition(0, 8))
                .addTimeEvent(new TimeStampedEvent(0.35F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(StarAnimations.YAMATO_STRIKE1, -0.07F);
                }))
                .addTimeEvent(new TimeStampedEvent(0.1F, (entityPatch) -> {
                    entityPatch.playSound(ArcSoundRegistry.JudgementCut.get(),0,0);
                }))
                .setDamageMultiplier(ValueModifier.multiplier(0.5F));
        ComboNode ArcGP1extendA1 = ComboNode.createNode(() -> StarAnimations.YAMATO_POWER2)
                .setPlaySpeed(1.3F)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.9F, "effect give @s irons_spellbooks:abyssal_shroud 1 0", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F,"particle epicacg:genshin_bow_landing ~0.0 ~0.0 ~0 0.0 0.0 0.0 0.3 7 force @s",false))
                .setStunTypeModifier(StunType.SHORT)
                .setNotCharge(true)
                .setCanBeInterrupt(false)
                .addTimeEvent(new TimeStampedEvent(1.65F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(StarAnimations.FATAL_DRAW, -0.6F);
                }))
                .addTimeEvent(new TimeStampedEvent(0.28F, (entityPatch) -> {
                    entityPatch.playSound(ArcSoundRegistry.VoidSlash.get(),0,0);
                }));

        ComboNode ArcGP1extendS1 = ComboNode.createNode(() -> StarAnimations.YAMATO_STRIKE1)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.25F, "invincible consumeStack 1", false))
                .addCondition(new StackCondition(1, 8))
                .addCondition(new PlayerPhaseCondition(1,1))
                .setNotCharge(true)
                .setPriority(5)
                .setPlaySpeed(1.2F)
                .setCanBeInterrupt(false)
                .addCondition(new DodgeSuccessCondition())
                .setDamageMultiplier(ValueModifier.multiplier(0.7F));
        ;

        ComboNode ArcGP1extendS2 = ComboNode.createNode(() -> StarAnimations.YAMATO_STRIKE2)
                .setPlaySpeed(1.2F)
                .setNotCharge(true)
                .setDamageMultiplier(ValueModifier.multiplier(0.7F));
        ;

        ComboNode ArcGP1extendS3 = ComboNode.createNode(() -> StarAnimations.YAMATO_AUTO4)
                .addTimeEvent(new TimeStampedEvent(0.45F, (entityPatch) -> {
                    entityPatch.playSound(ArcSoundRegistry.VoidSlash.get(),0,0);
                }))
                .addTimeEvent(new TimeStampedEvent(1F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(Animations.BIPED_WALK_UCHIGATANA, 0.0F);
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.4F, "effect give @s minecraft:haste 3 2", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.4F, "effect @s minecraft:strength 5 3", false))
                .setDamageMultiplier(ValueModifier.multiplier(0.5F))
                .setNotCharge(true)
                .setConvertTime(-0.3F)
                .setPlaySpeed(1.4F);

        ComboNode ArcGP1extendS4 = ComboNode.createNode(() -> StarAnimations.BLADE_RUSH_FINISHER)
                .setPlaySpeed(1.3F)
                .setDamageMultiplier(ValueModifier.multiplier(1.1F))
                .setCanBeInterrupt(false);

        ComboNode ArcGP1After =ComboNode.create()
                .addConditionAnimation(ArcGP1extendA)
                .addConditionAnimation(ArcAuto1);

        Arcbladeroot.keyWeaponInnate(ArcGP1);//常态按技能消耗一层技能后瞬步尝试GP，若为极限闪避则GP成功，则给予敌人1S晕眩
        ArcGP1.key3(Free);//无论GP成功与否，可按key3消耗一层充能使用后跨步重置普攻
        Free.key1(ArcbasicAttack);//key3跨步重置普攻

        ArcGP1.key1(ArcGP1After);//后瞬步GP成功后可按KEY1接A追击1
        ArcGP1extendA.key1(ArcGP1extendA1);//后瞬步GP成功后可按KEY1接A追击2
        ArcGP1extendA1.key1(ArcAutoDash3);//A1追击后接普攻3A

        ArcGP1.keyWeaponInnate(ArcGP1extendS1);//后瞬步GP成功后可消耗技能按keyWeaponInnate接S1追击第一段
        ArcGP1extendS1.keyWeaponInnate(ArcGP1extendS2);//GP成功发动S1追击后衔接S2第二段追击
        ArcGP1extendS2.keyWeaponInnate(ArcGP1extendS3);//S2追击后衔接S3第三段追击
        ArcGP1extendS2.key1(ArcGP1extendS4);//S2追击后提前结算发动S4四段
        ArcGP1extendS3.keyWeaponInnate(ArcGP1extendS4);//S3追击后衔接S4第四段追击
        ArcGP1extendS1.key1(ArcAutoDash3);//S1追击后接普攻3A
        ArcGP1extendS3.key1(ArcAutoDash4);//S3追击后接普攻4A;
        ArcGP1extendS4.key1(ArcAutoDash4);//S4追击后接普攻4A;


        ComboNode Arc1AS = ComboNode.createNode(() -> StarAnimations.YAMATO_POWER1)
                .setConvertTime(-0.1F)
                .setPlaySpeed(1.4F)
                .setNotCharge(true)
                .setCanBeInterrupt(false)
                .addTimeEvent(new TimeStampedEvent(0.3F, entityPatch -> {
                    entityPatch.playSound(EpicFightSounds.WHOOSH_SHARP, 0, 0);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.15F, "invincible groundSlam @s 2 false false false", true))
                .addTimeEvent(new TimeStampedEvent(0.8F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(Animations.RUSHING_TEMPO3, 0.1F);
                }))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s irons_spellbooks:rend 2 6", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "effect give @s minecraft:haste 12 2", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "effect give @s epicfight:stun_immunity 3", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "effect give @s star:really_stun_immunity 3", false))
                .addCondition(new StackCondition(1, 8))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "invincible consumeStack 1", false))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.3 0.5", true))
                .setDamageMultiplier(ValueModifier.multiplier(1.1F))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "effect give @s irons_spellbooks:abyssal_shroud 1 0", false));
        ;
        ;

        ComboNode Arc1AS1 = ComboNode.createNode(() -> Animations.RUSHING_TEMPO3)
                .setConvertTime(0.1F)
                .setPlaySpeed(1.3F)
                .setCanBeInterrupt(false)
                .setNotCharge(true)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "invincible groundSlam @s 3 false false false", true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s epicfight:stun_immunity 4", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s star:really_stun_immunity 4", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:resistance 3 3", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:haste 12 3", false))
                .addCondition(new StackCondition(1, 8))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "invincible consumeStack 1", false))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStack -2", false))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.3 0.5", true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s irons_spellbooks:rend 3 6", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "effect give @s irons_spellbooks:abyssal_shroud 1 0", false))
                .setDamageMultiplier(ValueModifier.multiplier(1.5F));
        ;
        ;
        ;

        ComboNode Arc2ASGP2 = ComboNode.createNode(() -> StarAnimations.YAMATO_STEP_FORWARD)
                .setPriority(4)
                .setPlaySpeed(1.3F)
                .setConvertTime(0F)
                .addCondition(new StackCondition(1, 8))
                .setCanBeInterrupt(false)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible consumeStack 1", false))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("invincible setPlayerPhase 2", false))
                .addDodgeSuccessEvent(new BiEvent(((entityPatch, entity) -> entityPatch.playSound(Sounds.FORESIGHT, 0, 0))))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("invincible entityAfterImage @s", true))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("invincible consumeStack -2", false))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 3 0.5", true))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("effect give @s cataclysm:stun 2", true))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("effect clear @s irons_spellbooks:rend", true));;



        ComboNode GP2 = ComboNode.create().addConditionAnimation(Arc2ASGP2);


        ComboNode Arc3AS1 = ComboNode.createNode(() -> StarAnimations.YAMATO_COUNTER1)
                .setPriority(3)
                .setConvertTime(-0.07F)
                .setPlaySpeed(1.1F)
                .setNotCharge(true)
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F,"particle epicacg:genshin_bow_landing ~0.0 ~0.0 ~0 0.0 0.0 0.0 0.3 5 force @s",false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "effect give @2 minecraft:slowness 2 255", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.73F, "effect give @s irons_spellbooks:abyssal_shroud 1 0", false))
                .addTimeEvent(new TimeStampedEvent(0.73F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(StarAnimations.YAMATO_COUNTER2, 0.0F);
                }))
                .addCondition(new StackCondition(1, 8))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "invincible consumeStack 1", false))
                .setDamageMultiplier(ValueModifier.multiplier(0.7F))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.25F, "particle isleofberk:lightning_aoe_emitter ~ ~1.5 ~ 0 2.5 0 0.1 160 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "particle minecraft:wax_off ~ ~1 ~ 0 4 0 2 20 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "particle minecraft:wax_off ~-2 ~1 ~ 0 1.5 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "particle minecraft:wax_off ~2 ~1 ~ 0 1.5 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "particle minecraft:wax_off ~ ~1 ~-2 0 1.5 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "particle minecraft:wax_off ~ ~1 ~2 0 1.5 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.7F, "particle minecraft:wax_off ~-3 ~1 ~ 0 0.2 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.7F, "particle minecraft:wax_off ~3 ~1 ~ 0 0.2 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.7F, "particle minecraft:wax_off ~ ~1 ~-3 0 0.2 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.7F, "particle minecraft:wax_off ~ ~1 ~3 0 0.2 0 2 10 force", false))

                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "playsound minecraft:block.respawn_anchor.deplete ambient @s ~ ~ ~ 20", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "effect give @s cofh_core:lightning_resistance 5", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.75F, "particle minecraft:explosion ~ ~1.5 ~ 0 1 0 1 1 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.75F, "playsound minecraft:entity.generic.explode ambient @s ~ ~ ~ 5", false));
        ComboNode Arc3AS2 = ComboNode.createNode(() -> StarAnimations.YAMATO_STEP_FORWARD)
                .addCondition(new StackCondition(1, 8))
                .setCanBeInterrupt(false)
                .setNotCharge(true)
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("effect give @s cataclysm:stun 3", true))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("invincible consumeStack -3", true))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 0.8 0.5", true))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("particle epicacg:dmc_jc_blade_trail ~0 ~0.0 ~0 0.0 1.5 0.0 0.02 1 force @s", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack -1", false));
        ;
        ;
        ;

        ComboNode Arc4AS = ComboNode.createNode(() -> WOMAnimations.KATANA_SHEATHED_DASH)
                .setNotCharge(true)
                .addCondition(new StackCondition(1, 8))
                .setConvertTime(-0.1F)
                .setPlaySpeed(1.2F)
                .setDamageMultiplier(ValueModifier.multiplier(2))
                .setDamageMultiplier(ValueModifier.multiplier(0.5F))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 0.3 0.5", true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s irons_spellbooks:rend 2 8", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s minecraft:absorption 10 3", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s star:really_stun_immunity 5 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s epicfight:stun_immunity 5 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s minecraft:resistance 2 4", false))
                .addTimeEvent(new TimeStampedEvent(0.6F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(StarAnimations.YAMATO_COUNTER2, 0.0F);
                }));
        ComboNode Arc4AS1 = ComboNode.createNode(() -> StarAnimations.YAMATO_AUTO4)
                .addTimeEvent(new TimeStampedEvent(1.2F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(StarAnimations.YAMATO_STEP_FORWARD, 0.0F);
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "effect give @s irons_spellbooks:rend 4 14", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect clear @s irons_spellbooks:rend", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s epicfight:stun_immunity 6", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s star:really_stun_immunity 6", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:resistance 6 4", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s alexsmobs:soulsteal 8 10", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:instant_health 1 10", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s irons_spellbooks:rend 6 18", true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s irons_spellbooks:blight 10 0", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "effect give @s irons_spellbooks:abyssal_shroud 1 0", false))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("particle epicacg:dmc_jc_blade_trail ~0 ~0.0 ~0 0.0 1.0 0.0 0.02 1 force @s", false))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("effect give @s cataclysm:stun 3", true))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 1 0.5", true))
                .setPlaySpeed(1.4F)
                .setNotCharge(true)
                .setCanBeInterrupt(false)
                .setDamageMultiplier(ValueModifier.multiplier(1.2F))
                .setConvertTime(-0.28F)
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s irons_spellbooks:rend 3 15", true))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible setPlayerPhase 2", false));
        ;

        ComboNode Arc5As = ComboNode.createNode(() ->  StarAnimations.YAMATO_STRIKE1)
                .setNotCharge(true)
                .addCondition(new StackCondition(1, 8))
                .setDamageMultiplier(ValueModifier.multiplier(1.7F))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "effect give @s cofh_core:lightning_resistance 10", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "execute as @s at @s run particle irons_spellbooks:electricity ~ ~0.1 ~ 0.4 0.2 0.4 0.1 32", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "execute as @p at @s run playsound minecraft:item.trident.thunder block @s ~ ~ ~ 0.5 2.0", false))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 0.2 0.5", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack 1", false))
                .addHitEvent(BiEvent.createBiCommandEvent("summon minecraft:lightning_bolt ~ ~ ~", true));
        ComboNode Arc5As2 = ComboNode.createNode(() -> WOMAnimations.KATANA_AUTO_1)
                .setNotCharge(true)
                .setPlaySpeed(1.3F)
                .addCondition(new StackCondition(1, 8))
                .setDamageMultiplier(ValueModifier.multiplier(1.6F))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "execute as @s at @s run particle irons_spellbooks:electricity ~ ~1.0 ~ 0.1 0.3 0.1 1.0 32", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "particle isleofberk:lightning_aoe_emitter ~ ~1.5 ~ 0 2.5 0 0.1 160 force", false))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack 1", false))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.2 0.5", true))
                .addHitEvent(BiEvent.createBiCommandEvent("summon minecraft:lightning_bolt ~ ~ ~", true));
        ComboNode Arc5As3 = ComboNode.createNode(() -> WOMAnimations.KATANA_AUTO_2)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "execute as @s at @s run particle irons_spellbooks:electricity ~ ~1.0 ~ 0.1 0.3 0.1 1.0 32", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "execute as @p at @s run playsound minecraft:item.trident.thunder block @s ~ ~ ~ 0.5 1.75", false))
                .setNotCharge(true)
                .addCondition(new StackCondition(1, 8))
                .setDamageMultiplier(ValueModifier.multiplier(1.7F))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.4 0.5", true))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack 1", false))
                .addHitEvent(BiEvent.createBiCommandEvent("summon minecraft:lightning_bolt ~ ~ ~", true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s irons_spellbooks:rend 3 6", true));
        ComboNode Arc5As4 = ComboNode.createNode(() -> WOMAnimations.KATANA_AUTO_3)
                .setConvertTime(0.1F)
                .setNotCharge(true)
                .setPlaySpeed(1F)
                .addCondition(new StackCondition(1, 8))
                .setDamageMultiplier(ValueModifier.multiplier(2.5F))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.2 0.5", true))
                .addHitEvent(BiEvent.createBiCommandEvent("summon minecraft:lightning_bolt ~ ~ ~", true))


                .addHitEvent(BiEvent.createBiCommandEvent("particle epicacg:dmc_jc_blade_trail ~0 ~0.0 ~0 0.0 1.5 0.0 0.02 1 force @s", false))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStack -4", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "particle isleofberk:lightning_aoe_emitter ~ ~1.5 ~ 0 2.5 0 0.1 160 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "particle minecraft:wax_off ~ ~1 ~ 0 4 0 2 50 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "particle minecraft:wax_off ~-2 ~1 ~ 0 1.5 0 2 20 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "particle minecraft:wax_off ~2 ~1 ~ 0 1.5 0 2 20 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "particle minecraft:wax_off ~ ~1 ~-2 0 1.5 0 2 20 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "particle minecraft:wax_off ~ ~1 ~2 0 1.5 0 2 20 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "particle minecraft:wax_off ~-3 ~1 ~ 0 0.2 0 2 20 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "particle minecraft:wax_off ~3 ~1 ~ 0 0.2 0 2 20 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "particle minecraft:wax_off ~ ~1 ~-3 0 0.2 0 2 20 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "particle minecraft:wax_off ~ ~1 ~3 0 0.2 0 2 20 force", false))

                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "playsound minecraft:block.respawn_anchor.deplete ambient @s ~ ~ ~ 20", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "effect give @s cofh_core:lightning_resistance 5", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "summon minecraft:lightning_bolt ^ ^0 ^-7", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "summon minecraft:lightning_bolt ^3 ^0 ^-5", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "summon minecraft:lightning_bolt ^-3 ^0 ^-5", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "particle minecraft:explosion ~ ~1.5 ~ 0 1 0 1 1 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.7F, "playsound minecraft:entity.generic.explode ambient @s ~ ~ ~ 5", false));
        ;

        ComboNode Arc6As = ComboNode.createNode(() -> MyAnimations.DMC5_V_JC)
                .addCondition(new StackCondition(8, 8))
                .setCooldown(3600)
                .setNotCharge(true)
                .addCondition(new CooldownCondition(false))
                .setPriority(5)
                .setDamageMultiplier(ValueModifier.multiplier(0.6F))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s cataclysm:stun 3", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack 8", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s irons_spellbooks:abyssal_shroud 5 0", false));
        ;

        ArcAuto1.keyWeaponInnate(Arc1AS);//普攻一段派生一段
        Arc1AS.keyWeaponInnate(Arc1AS1);//派生一段接二段
        Arc1AS.key1(ArcAutoDash2);//一段派生接2A
        Arc1AS1.key1(ArcAutoDash2);//二段派生接2A

        ArcAuto2.keyWeaponInnate(GP2);//普攻二段派生，特殊GP
        ArcPowerAuto4.keyWeaponInnate(GP2);
        ArcPowerAuto5.keyWeaponInnate(GP2);
        ArcPowerAuto6.keyWeaponInnate(GP2);

        GP2.key3(Free);//普攻二段派生失败紧急逃生

        ArcAuto3.keyWeaponInnate(Arc3AS1);//普攻三段派生一段
        ArcPowerAuto4.keyWeaponInnate(Arc3AS2);
        ArcPowerAuto5.keyWeaponInnate(Arc3AS2);
        ArcPowerAuto6.keyWeaponInnate(Arc6As);
        Arc3AS1.keyWeaponInnate(Arc3AS2);//普攻三段派生二段

        Arc3AS1.key1(ArcAutoDash4);//普攻三段派生一段接4A
        Arc3AS2.key1(ArcAutoDash2);//普攻三段派生二段接2A

        ArcAuto4.keyWeaponInnate(Arc4AS);//普攻四段派生一段
        Arc4AS.keyWeaponInnate(Arc4AS1);//普攻四段派生二段
        Arc4AS.key1(ArcAutoDash5);//普攻四段派生一段接5A
        Arc4AS1.key1(ArcAutoDash4);//普攻四段派生二段接4A

        ArcAuto5.keyWeaponInnate(Arc5As);//普攻五段派生1 阎魔刀2A
        Arc5As.keyWeaponInnate(Arc5As2);//普攻五段派生2 人斩1A
        Arc5As2.keyWeaponInnate(Arc5As3);//普攻五段派生3 人斩2A
        Arc5As3.keyWeaponInnate(Arc5As4);//普攻五段派生4 人斩3A
        Arc5As.key1(ArcAutoDash6);//普攻五段派生1接6A
        Arc5As2.key1(ArcAutoDash6);//普攻五段派生2接6A
        Arc5As3.key1(ArcAutoDash6);//普攻五段派生3接6A
        Arc5As4.key1(ArcAutoDash6);//普攻五段派生4接6A

        ArcAuto6.keyWeaponInnate(Arc6As);//普攻六段派生，次元斩绝
        Arc6As.key1(ArcbasicAttack);//普攻六段派生重置平A

        ArcGP1extendA1.keyWeaponInnate(Arc2ASGP2);//GP1A追击后keyWeaponInnate发动GP2
        ArcGP1extendS4.keyWeaponInnate(Arc2ASGP2);//GP1S追击后keyWeaponInnate发动GP2


        ComboNode ArcGP2extendAttack1 = ComboNode.createNode(() -> WOMAnimations.AGONY_CLAWSTRIKE)
                .setPriority(5)
                .setNotCharge(true)
                .setCanBeInterrupt(false)
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStamina 2", false))
                .addCondition(new DodgeSuccessCondition())
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:levitation 1 8", true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:slow_falling 2", true))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 8 0.5", true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s cataclysm:stun 6", true))
                .addCondition(new StackCondition(2, 8))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack 2", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s star:really_stun_immunity 7 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s epicfight:stun_immunity 7 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s minecraft:resistance 7 4", false))
                .setDamageMultiplier(ValueModifier.multiplier(1F));
        ;


        ComboNode ArcGP2extendAttack2 = ComboNode.createNode(() -> WOMAnimations.AGONY_PLUNGE_FORWARD)
                .setNotCharge(true)
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStamina 2", false))
                .addTimeEvent(new TimeStampedEvent(0.43F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(Animations.BIPED_WALK_LONGSWORD, 0.1F);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.45F,"particle epicacg:genshin_bow_landing ~0.0 ~0.0 ~0 0.0 0.0 0.0 1 5 force @s",false))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 2 0.5", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0F, "effect give @s minecraft:slow_falling 2", false))
                .setDamageMultiplier(ValueModifier.multiplier(1F));
        ;
        ;

        ComboNode ArcGP2extendAttack3 = ComboNode.createNode(() ->WOMAnimations.TORMENT_BERSERK_AUTO_1)
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStamina 2", false))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 2 0.5", true))
                .setNotCharge(true)
                .setConvertTime(0.1F)
                .setPlaySpeed(1.45F)
                .setDamageMultiplier(ValueModifier.multiplier(1.7F));

        ComboNode ArcGP2extendAttack4 = ComboNode.createNode(() -> WOMAnimations.TORMENT_BERSERK_AUTO_2)
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStamina 2", false))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 2 0.5", true))
                .setNotCharge(true)
                .setDamageMultiplier(ValueModifier.multiplier(1.7F))
                .setPlaySpeed(1.45F);

        ComboNode ArcGP2extendAttack5 = ComboNode.createNode(() -> WOMAnimations.MOONLESS_FULLMOON)
                .setNotCharge(true)
                .setPlaySpeed(1.6F)
                .setDamageMultiplier(ValueModifier.multiplier(1.3F))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStamina 2", false))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 1 0.5", true))
                .addTimeEvent(new TimeStampedEvent(0.65F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(Animations.BIPED_WALK_LONGSWORD, 0.0F);
                }));

        ComboNode ArcGP2extendAttack6 = ComboNode.createNode(() -> WOMAnimations.RUINE_REDEMPTION)
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStamina 2", false))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 0.5 0.5", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0F, "effect give @s minecraft:slow_falling 2", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.8F,"particle epicacg:genshin_bow_landing ~0.0 ~0.0 ~0 0.0 0.0 0.0 0.3 5 force @s", false))
                .setNotCharge(true)
                .setDamageMultiplier(ValueModifier.multiplier(1F))
                .setConvertTime(0.1F)
                .setPlaySpeed(0.9F);

        ComboNode ArcGP2extendAttack7 = ComboNode.createNode(() -> StarAnimations.YAMATO_COUNTER1)
                .setNotCharge(true)
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStamina 2", false))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 0.3 0.5", true))
                .setDamageMultiplier(ValueModifier.multiplier(1.2F))
                .setPlaySpeed(1.3F);

        ComboNode ArcGP2extendAttack8 = ComboNode.createNode(() -> StarAnimations.FATAL_DRAW_DASH)
                .setConvertTime(-0.65F)
                .setNotCharge(true)
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStamina 2", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "effect give @s irons_spellbooks:abyssal_shroud 1 0", false))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 0.2 0.5", true))
                .setDamageMultiplier(ValueModifier.multiplier(1.3F))
                .setPlaySpeed(1.1F);

        ;
        ;
        ComboNode ArcGP2AttackAfter = ComboNode.create().addConditionAnimation(ArcGP1).addConditionAnimation(ArcGP2extendAttack1);


        Arc2ASGP2.keyWeaponInnate(ArcGP2AttackAfter);//特殊GP成功后消耗一层技能按KEY1挑飞敌人，消耗一层技能,给予漂浮


        Arc2ASGP2.key3(Free);//特殊GP无论成功与否可按key3消耗充能，后撤重置普攻

        ArcGP2extendAttack1.keyWeaponInnate(ArcGP2extendAttack2);//
        ArcGP2extendAttack2.keyWeaponInnate(ArcGP2extendAttack3);//
        ArcGP2extendAttack3.keyWeaponInnate(ArcGP2extendAttack4);//
        ArcGP2extendAttack4.keyWeaponInnate(ArcGP2extendAttack5);//
        ArcGP2extendAttack5.keyWeaponInnate(ArcGP2extendAttack6);//
        ArcGP2extendAttack6.keyWeaponInnate(ArcGP2extendAttack7);//
        ArcGP2extendAttack7.keyWeaponInnate(ArcGP2extendAttack8);//
        ArcGP2extendAttack8.key1(ArcAuto5);//空中下砸落地后KEY1接普攻5A
        ArcGP2extendAttack8.keyWeaponInnate(ArcGP1);//


        ComboNode ArcGP2extendSkill1 = ComboNode.createNode(() -> StarAnimations.LONGSWORD_OLD_AUTO1)
                .setPlaySpeed(1.2F)
                .setPriority(5)
                .addCondition(new PlayerPhaseCondition(2,2))
                .setNotCharge(true)
                .setCanBeInterrupt(false)
                .addCondition(new DodgeSuccessCondition())
                .addCondition(new StackCondition(2, 8))
                .setDamageMultiplier(ValueModifier.multiplier(1.5F))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStamina 2", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s cataclysm:stun 6", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack 2", false))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 2 0.5", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s star:really_stun_immunity 7 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s epicfight:stun_immunity 7 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s minecraft:resistance 7 4", false))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }));

        ComboNode ArcGP2extendSkill2 = ComboNode.createNode(() -> StarAnimations.YAMATO_POWER1)
                .setConvertTime(-0.1F)
                .setPlaySpeed(1.4F)
                .setNotCharge(true)
                .setCanBeInterrupt(false)
                .addTimeEvent(new TimeStampedEvent(0.3F, entityPatch -> {
                    entityPatch.playSound(EpicFightSounds.WHOOSH_SHARP, 0, 0);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.15F, "invincible groundSlam @s 2 false false false", true))
                .addTimeEvent(new TimeStampedEvent(0.8F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(Animations.RUSHING_TEMPO3, 0.1F);
                }))
                .setDamageMultiplier(ValueModifier.multiplier(0.7F))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStamina 2", false))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 2 0.5", true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:levitation 2 2", true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:slow_falling 1", true));

        ComboNode ArcGP2extendSkill3 = ComboNode.createNode(() -> WOMAnimations.AGONY_PLUNGE_FORWARD)
                .setConvertTime(-0.1F)
                .setDamageMultiplier(ValueModifier.multiplier(1.5F))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStamina 2", false))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 2 0.5", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.29F,"effect give @s arc:stellaris_particleeffect 1",true))
                .addTimeEvent(new TimeStampedEvent(1.3F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(StarAnimations.YAMATO_POWER0_2, 0.0F);
                }))
                .setNotCharge(true);

        ComboNode ArcGP2extendSkill4 = ComboNode.createNode(() ->StarAnimations.YAMATO_STRIKE1)
                .setDamageMultiplier(ValueModifier.multiplier(1.5F))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStamina 2", false))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.5 0.5", true))
                .setNotCharge(true)
                .setPlaySpeed(1.4F);
        ComboNode ArcGP2extendSkill5 = ComboNode.createNode(() -> StarAnimations.YAMATO_STRIKE2)
                .setNotCharge(true)
                .setPlaySpeed(1.5F)
                .setDamageMultiplier(ValueModifier.multiplier(1.5F))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStamina 2", false))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.5 0.5", true));

        ComboNode ArcGP2extendSkill6 = ComboNode.createNode(() -> StarAnimations.YAMATO_AUTO4)
                .setNotCharge(true)
                .setDamageMultiplier(ValueModifier.multiplier(2F))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStamina 2", false))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.2 0.5", true))
                .addTimeEvent(new TimeStampedEvent(1F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(Animations.BIPED_WALK_LONGSWORD, 0.0F);
                }))
                .setPlaySpeed(1.4F)
                .setConvertTime(-0.2F);

        ComboNode ArcGP2extendSkill7 = ComboNode.createNode(() -> StarAnimations.BLADE_RUSH_FINISHER)
                .setNotCharge(true)
                .setDamageMultiplier(ValueModifier.multiplier(1.1F))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStamina 2", false))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 0.2 0.5", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "effect give @s irons_spellbooks:abyssal_shroud 1 0", false))
                .setPlaySpeed(1F);


        ComboNode ArcGP2SkillAfter = ComboNode.create()
                .addConditionAnimation(ArcGP2extendSkill1)
                .addConditionAnimation(ArcAuto3);
        Arc2ASGP2.key1(ArcGP2SkillAfter);//

        ArcGP2extendSkill1.key1(ArcGP2extendSkill2);//
        ArcGP2extendSkill2.key1(ArcGP2extendSkill3);//
        ArcGP2extendSkill3.key1(ArcGP2extendSkill4);//
        ArcGP2extendSkill4.key1(ArcGP2extendSkill5);//
        ArcGP2extendSkill5.key1(ArcGP2extendSkill6);//
        ArcGP2extendSkill6.key1(ArcGP2extendSkill7);//
        ArcGP2extendSkill7.key1(ArcAuto4);//
        ArcGP2extendSkill7.keyWeaponInnate(ArcGP1);//

        ComboNode ArcParryStrike1 = ComboNode.createNode(() -> StarAnimations.YAMATO_COUNTER1)
                .setPriority(5)
                .addCondition(new ParrySuccessCondition())
                .addCondition(new StackCondition(1, 8))
                .setCanBeInterrupt(false)
                .setNotCharge(true)
                .setStunTypeModifier(StunType.SHORT)
                .setDamageMultiplier(ValueModifier.multiplier(0.5F))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s minecraft:absorption 5 2", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s epicfight:stun_immunity 5 2", false))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }));


        ComboNode ArcParryStrike2 = ComboNode.createNode(() -> StarAnimations.YAMATO_COUNTER2)
                .setPriority(5)
                .setDamageMultiplier(ValueModifier.multiplier(0.5F))
                .setCanBeInterrupt(false)
                .setStunTypeModifier(StunType.SHORT)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s minecraft:haste 5 2", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s minecraft:resistance 3 3", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s star:really_stun_immunity 3 2", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "effect give @s irons_spellbooks:abyssal_shroud 1 0", false));
        ;

        ComboNode ArcAdvancedAttack = ComboNode
                .create().addConditionAnimation(ArcParryStrike1)
                .addConditionAnimation(ArcdashSkill)
                .addConditionAnimation(ArcjumpSkill)
                .setPriority(5);


        Arcbladeroot.key3(ArcAdvancedAttack);//常态按下key3招架反击
        ArcAuto1.key3(ArcParryStrike1);
        ArcAuto2.key3(ArcParryStrike1);
        ArcAuto4.key3(ArcParryStrike1);
        ArcPowerAuto4.key3(ArcParryStrike1);
        ArcAuto5.key3(ArcParryStrike1);
        ArcPowerAuto5.key3(ArcParryStrike1);
        ArcAuto6.key3(ArcParryStrike1);
        ArcPowerAuto6.key3(ArcParryStrike1);
        Arc1AS.key3(ArcParryStrike1);
        Arc1AS1.key3(ArcParryStrike1);
        Arc2ASGP2.key3(ArcParryStrike1);
        Arc3AS1.key3(ArcParryStrike1);
        Arc3AS2.key3(ArcParryStrike1);
        Arc4AS.key3(ArcParryStrike1);
        Arc4AS1.key3(ArcParryStrike1);
        Arc5As.key3(ArcParryStrike1);
        Arc5As2.key3(ArcParryStrike1);
        Arc5As3.key3(ArcParryStrike1);
        Arc5As4.key3(ArcParryStrike1);
        ArcGP2extendAttack8.key3(ArcParryStrike1);
        ArcGP2extendSkill7.key3(ArcParryStrike1);

        ArcParryStrike1.key3(ArcParryStrike2);//招架反击1段后按下key3使用二段
        ArcParryStrike1.key1(ArcAutoDash2);//招架反击1段后按下key1，接普攻二段
        ArcParryStrike1.keyWeaponInnate(ArcGP1);//招架反击1段后按下keyWeaponInnate，使用GP1
        ArcParryStrike2.key1(ArcAutoDash3);//招架反击2段后按下key1，接普攻三段
        ArcParryStrike2.keyWeaponInnate(Arc2ASGP2);//招架反击2段后按下keyWeaponInnate，使用GP2
        ArcParryStrike2.key3(ArcParryStrike1);//招架反击2段后可继续触发招架反击

        ComboNode ArcGP3Skill1 = ComboNode.createNode(() -> WOMAnimations.AGONY_CLAWSTRIKE)
                .setPriority(5)
                .setNotCharge(true)
                .setCanBeInterrupt(false)
                .addCondition(new DodgeSuccessCondition())
                .setDamageMultiplier(ValueModifier.multiplier(1F))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStamina 2", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:levitation 1 7", true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:slow_falling 3", true))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 2 0.5", true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s cataclysm:stun 7", true))
                .addCondition(new StackCondition(3, 8))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack 3", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s star:really_stun_immunity 7 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s epicfight:stun_immunity 7 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s minecraft:resistance 7 4", false));
        ;


        ComboNode ArcGP3Skill2 = ComboNode.createNode(() -> WOMAnimations.AGONY_PLUNGE_FORWARD)
                .setNotCharge(true)
                .setDamageMultiplier(ValueModifier.multiplier(1F))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStamina 2", false))
                .addTimeEvent(new TimeStampedEvent(0.4F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(Animations.BIPED_WALK_LONGSWORD, 0.1F);
                }))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 2 0.5", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0F, "effect give @s minecraft:slow_falling 4", false));

        ComboNode ArcGP3Skill3 = ComboNode.createNode(() -> StarAnimations.YAMATO_COUNTER1)
                .setDamageMultiplier(ValueModifier.multiplier(1.5F))
                .setConvertTime(0.15F)
                .setPlaySpeed(1.2F)
                .setNotCharge(true)
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStamina 2", false))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }))
                .addHitEvent(BiEvent.createBiCommandEvent("summon minecraft:lightning_bolt ~ ~ ~", true))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 2 0.5", true))
                .addHitEvent(BiEvent.createBiCommandEvent("particle epicacg:dmc_jc_blade_trail ~0 ~0.0 ~0 0.0 1 0.0 1.5 1 force @s", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.15F, "particle isleofberk:lightning_aoe_emitter ~ ~1.5 ~ 0 2.5 0 0.1 160 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "particle minecraft:wax_off ~ ~1 ~ 0 4 0 2 20 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.4F, "particle minecraft:wax_off ~-2 ~1 ~ 0 1.5 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.4F, "particle minecraft:wax_off ~2 ~1 ~ 0 1.5 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.4F, "particle minecraft:wax_off ~ ~1 ~-2 0 1.5 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.4F, "particle minecraft:wax_off ~ ~1 ~2 0 1.5 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.6F, "particle minecraft:wax_off ~-3 ~1 ~ 0 0.2 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.6F, "particle minecraft:wax_off ~3 ~1 ~ 0 0.2 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.6F, "particle minecraft:wax_off ~ ~1 ~-3 0 0.2 0 2 10 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.6F, "particle minecraft:wax_off ~ ~1 ~3 0 0.2 0 2 10 force", false))

                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "playsound minecraft:block.respawn_anchor.deplete ambient @s ~ ~ ~ 20", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "effect give @s cofh_core:lightning_resistance 5", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.8F, "particle minecraft:explosion ~ ~1.5 ~ 0 1 0 1 1 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.8F, "playsound minecraft:entity.generic.explode ambient @s ~ ~ ~ 5", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.0F, "indestructible @s play 'epicfight:biped/living/hold_longsword' 0.1 1", false));

        ComboNode ArcGP3Skill4 = ComboNode.createNode(() -> WOMAnimations.AGONY_PLUNGE_FORWARD)
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStamina 2", false))
                .setDamageMultiplier(ValueModifier.multiplier(1.1F))
                .setConvertTime(-0.3F)
                .addTimeEvent(new TimeStampedEvent(1.3F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(Animations.BIPED_WALK_LONGSWORD, 0.0F);
                }))
                .setNotCharge(true)
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 2 0.5", true));

        ComboNode ArcGP3Skill5 = ComboNode.createNode(() -> WOMAnimations.KATANA_SHEATHED_AUTO_2)
                .setNotCharge(true)
                .setDamageMultiplier(ValueModifier.multiplier(3F))
                .setConvertTime(-0.1F)
                .addHitEvent(BiEvent.createBiCommandEvent("particle epicacg:dmc_jc_blade_trail ~0 ~0.0 ~-4 0.0 1 0.0 1.5 1 force @s", false))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStamina 2", false))
                .addTimeEvent(new TimeStampedEvent(0.5F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(Animations.BIPED_WALK_LONGSWORD, 0.0F);
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }));

        ComboNode ArcGP3Skill6 = ComboNode.createNode(() -> WOMAnimations.KATANA_SHEATHED_AUTO_2)
                .setNotCharge(true)
                .setDamageMultiplier(ValueModifier.multiplier(3F))
                .addHitEvent(BiEvent.createBiCommandEvent("particle epicacg:genshin_bow_landing ~0.0 ~0.0 ~-4 0.0 0.0 0.0 0.3 5 force @s", false))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStamina 2", false))
                .addTimeEvent(new TimeStampedEvent(0.5F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(Animations.BIPED_WALK_LONGSWORD, 0.0F);
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }));
        ;

        ComboNode ArcGP3Skill7 = ComboNode.createNode(() -> StarAnimations.YAMATO_COUNTER2)
                .setNotCharge(true)
                .setDamageMultiplier(ValueModifier.multiplier(2.5F))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 0.2 0.5", true))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }))
                .addHitEvent(BiEvent.createBiCommandEvent("summon minecraft:lightning_bolt ~ ~ ~", true))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStamina 2", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.15F,"particle epicacg:genshin_bow_landing ~0.0 ~0.0 ~0 0.0 0.0 0.0 0.3 5 force @s",false))

                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "particle isleofberk:lightning_aoe_emitter ~ ~1.5 ~ 0 2.5 0 0.1 160 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "particle minecraft:wax_off ~ ~1 ~ 0 4 0 2 50 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "particle minecraft:wax_off ~-2 ~1 ~ 0 1.5 0 2 20 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "particle minecraft:wax_off ~2 ~1 ~ 0 1.5 0 2 20 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "particle minecraft:wax_off ~ ~1 ~-2 0 1.5 0 2 20 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "particle minecraft:wax_off ~ ~1 ~2 0 1.5 0 2 20 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "particle minecraft:wax_off ~-3 ~1 ~ 0 0.2 0 2 20 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "particle minecraft:wax_off ~3 ~1 ~ 0 0.2 0 2 20 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "particle minecraft:wax_off ~ ~1 ~-3 0 0.2 0 2 20 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "particle minecraft:wax_off ~ ~1 ~3 0 0.2 0 2 20 force", false))

                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "playsound minecraft:block.respawn_anchor.deplete ambient @s ~ ~ ~ 20", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "effect give @s cofh_core:lightning_resistance 5", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "summon minecraft:lightning_bolt ^ ^0 ^-7", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "summon minecraft:lightning_bolt ^3 ^0 ^-5", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "summon minecraft:lightning_bolt ^-3 ^0 ^-5", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "particle minecraft:explosion ~ ~1.5 ~ 0 1 0 1 1 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.7F, "playsound minecraft:entity.generic.explode ambient @s ~ ~ ~ 5", false));
        ;


        ComboNode ArcAuto3extend2 = ComboNode.create()
                .addConditionAnimation(Arc3AS1)
                .addConditionAnimation(ArcGP3Skill1);

        ArcAuto3.keyWeaponInnate(ArcAuto3extend2);//普攻三段完美闪避派生S
        Arc3AS2.keyWeaponInnate(ArcGP3Skill1);
        ArcjumpSkill.keyWeaponInnate(ArcGP3Skill1);


        ArcGP3Skill1.keyWeaponInnate(ArcGP3Skill2);
        ArcGP3Skill2.keyWeaponInnate(ArcGP3Skill3);
        ArcGP3Skill3.keyWeaponInnate(ArcGP3Skill4);
        ArcGP3Skill4.keyWeaponInnate(ArcGP3Skill5);
        ArcGP3Skill5.keyWeaponInnate(ArcGP3Skill6);
        ArcGP3Skill6.keyWeaponInnate(ArcGP3Skill7);
        ArcGP3Skill7.key1(ArcAuto5);
        ArcGP3Skill7.keyWeaponInnate(Arc2ASGP2);


        ComboNode ArcGP3Attack1 = ComboNode.createNode(() -> WOMAnimations.AGONY_CLAWSTRIKE)
                .setPriority(5)
                .setNotCharge(true)
                .setCanBeInterrupt(false)
                .addCondition(new DodgeSuccessCondition())
                .setDamageMultiplier(ValueModifier.multiplier(2F))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:levitation 1 7", true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:slow_falling 2", true))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 3 0.5", true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s cataclysm:stun 7", true))
                .addCondition(new StackCondition(4, 8))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack 4", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s star:really_stun_immunity 7 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s epicfight:stun_immunity 7 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s minecraft:resistance 7 4", false));
        ;


        ComboNode ArcGP3Attack2 = ComboNode.createNode(() -> WOMAnimations.AGONY_PLUNGE_FORWARD)
                .setNotCharge(true)
                .setDamageMultiplier(ValueModifier.multiplier(1.5F))
                .addTimeEvent(new TimeStampedEvent(0.4F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(Animations.BIPED_WALK_LONGSWORD, 0.1F);
                }))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 2 0.5", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0F, "effect give @s minecraft:slow_falling 2", false));

        ComboNode ArcGP3Attack3 = ComboNode.createNode(() -> WOMAnimations.MOONLESS_FULLMOON)
                .setDamageMultiplier(ValueModifier.multiplier(2F))
                .setNotCharge(true)
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 2 0.5", true))
                .addHitEvent(BiEvent.createBiCommandEvent("particle epicacg:dmc_jc_blade_trail ~0 ~0.0 ~0 0.0 1.0 0.0 1.5 1 force @s", false))
                .addTimeEvent(new TimeStampedEvent(0.8F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(Animations.BIPED_WALK_LONGSWORD, 0.0F);
                }))
                .setPlaySpeed(1.6F);


        ComboNode ArcGP3Attack4 = ComboNode.createNode(() -> WOMAnimations.AGONY_PLUNGE_FORWARD)
                .setDamageMultiplier(ValueModifier.multiplier(2.5F))
                .setConvertTime(-0.95F)
                .addTimeEvent(new TimeStampedEvent(1.3F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(Animations.BIPED_WALK_LONGSWORD, 0.0F);
                }))
                .setNotCharge(true)
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 2 0.5", true));

        ComboNode ArcGP3Attack5 = ComboNode.createNode(() -> StarAnimations.YAMATO_COUNTER1)
                .setDamageMultiplier(ValueModifier.multiplier(2.5F))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 2 0.5", true))
                .addTimeEvent(new TimeStampedEvent(0.8F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(Animations.BIPED_WALK_LONGSWORD, 0.0F);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "execute as @s at @s run particle irons_spellbooks:electricity ~ ~1.0 ~ 0.1 0.3 0.1 1.0 32", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "execute as @p at @s run playsound minecraft:item.trident.thunder block @s ~ ~ ~ 0.5 1.75", false))
                .setNotCharge(true)
                .setConvertTime(-0.1F)
                .setPlaySpeed(1F);

        ComboNode ArcGP3Attack6 = ComboNode.createNode(() -> StarAnimations.YAMATO_POWER0_2)
                .setDamageMultiplier(ValueModifier.multiplier(2F))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F,"particle epicacg:genshin_bow_landing ~0.0 ~0.0 ~0 0.0 0.0 0.0 1 10 force @s",false))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 3 0.5", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "execute as @s at @s run particle irons_spellbooks:electricity ~ ~1.0 ~ 0.1 0.3 0.1 1.0 32", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "execute as @p at @s run playsound minecraft:item.trident.thunder block @s ~ ~ ~ 0.5 1.75", false))
                .setNotCharge(true)
                .setPlaySpeed(1.3F);

        ComboNode ArcGP3Attack7 = ComboNode.createNode(() -> StarAnimations.YAMATO_STRIKE1)
                .setDamageMultiplier(ValueModifier.multiplier(1.1F))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 1 0.5", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "execute as @s at @s run particle irons_spellbooks:electricity ~ ~1.0 ~ 0.1 0.3 0.1 1.0 32", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "execute as @p at @s run playsound minecraft:item.trident.thunder block @s ~ ~ ~ 0.5 1.75", false))
                .setNotCharge(true)
                .setConvertTime(-0.15F)
                .setPlaySpeed(1.5F);

        ComboNode ArcGP3Attack8 = ComboNode.createNode(() -> StarAnimations.YAMATO_STRIKE2)
                .setDamageMultiplier(ValueModifier.multiplier(1.2F))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 1 0.5", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "execute as @s at @s run particle irons_spellbooks:electricity ~ ~1.0 ~ 0.1 0.3 0.1 1.0 32", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "execute as @p at @s run playsound minecraft:item.trident.thunder block @s ~ ~ ~ 0.5 1.75", false))
                .setNotCharge(true)
                .setConvertTime(-0.15F)
                .setPlaySpeed(1.8F);

        ComboNode ArcGP3Attack9 = ComboNode.createNode(() -> StarAnimations.YAMATO_AUTO4)
                .setNotCharge(true)
                .setDamageMultiplier(ValueModifier.multiplier(2F))
                .addTimeEvent(new TimeStampedEvent(1F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(StarAnimations.BLADE_RUSH_FINISHER, 0.0F);
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.2 0.5", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "execute as @s at @s run particle irons_spellbooks:electricity ~ ~1.0 ~ 0.1 0.3 0.1 1.0 32", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "execute as @p at @s run playsound minecraft:item.trident.thunder block @s ~ ~ ~ 0.5 1.75", false))
                .setConvertTime(-0.3F)
                .setPlaySpeed(1.4F);

        ComboNode ArcGP3Attack10 = ComboNode.createNode(() -> WOMAnimations.RUINE_REDEMPTION)
                .setNotCharge(true)
                .setDamageMultiplier(ValueModifier.multiplier(2.2F))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }))
                .setDamageMultiplier(ValueModifier.multiplier(2.5F))
                .setConvertTime(-0.95F)
                .addTimeEvent(new TimeStampedEvent(1.3F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(Animations.BIPED_WALK_LONGSWORD, 0.0F);
                }))
                .setNotCharge(true)
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 2 0.5", true))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.5 0.5", true))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStamina -3", false))
                .addHitEvent(BiEvent.createBiCommandEvent("summon minecraft:lightning_bolt ~ ~ ~", true))

                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F,"particle epicacg:genshin_bow_landing ~0.0 ~0.0 ~0 0.0 0.0 0.0 1 10 force @s",false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "particle isleofberk:lightning_aoe_emitter ~ ~1.5 ~ 0 2.5 0 0.1 160 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "particle minecraft:wax_off ~ ~1 ~ 0 4 0 2 50 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "particle minecraft:wax_off ~-2 ~1 ~ 0 1.5 0 2 20 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "particle minecraft:wax_off ~2 ~1 ~ 0 1.5 0 2 20 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "particle minecraft:wax_off ~ ~1 ~-2 0 1.5 0 2 20 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "particle minecraft:wax_off ~ ~1 ~2 0 1.5 0 2 20 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "particle minecraft:wax_off ~-3 ~1 ~ 0 0.2 0 2 20 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "particle minecraft:wax_off ~3 ~1 ~ 0 0.2 0 2 20 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "particle minecraft:wax_off ~ ~1 ~-3 0 0.2 0 2 20 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "particle minecraft:wax_off ~ ~1 ~3 0 0.2 0 2 20 force", false))

                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "playsound minecraft:block.respawn_anchor.deplete ambient @s ~ ~ ~ 20", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "effect give @s cofh_core:lightning_resistance 5", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "summon minecraft:lightning_bolt ^ ^0 ^-7", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "summon minecraft:lightning_bolt ^3 ^0 ^-5", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "summon minecraft:lightning_bolt ^-3 ^0 ^-5", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "particle minecraft:explosion ~ ~1.5 ~ 0 1 0 1 1 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.6F, "playsound minecraft:entity.generic.explode ambient @s ~ ~ ~ 5", false));


        ComboNode ArcAuto3extend1 = ComboNode.create()
                .addConditionAnimation(ArcGP3Attack1)
                .addConditionAnimation(ArcParryStrike1);

        ArcAuto3.key3(ArcAuto3extend1);//普攻三段完美闪避派生A
        Arc3AS2.key3(ArcAuto3extend1);
        ArcjumpSkill.key3(ArcAuto3extend1);


        ArcGP3Attack1.key3(ArcGP3Attack2);
        ArcGP3Attack2.key3(ArcGP3Attack3);
        ArcGP3Attack3.key3(ArcGP3Attack4);
        ArcGP3Attack4.key3(ArcGP3Attack5);
        ArcGP3Attack5.key3(ArcGP3Attack6);
        ArcGP3Attack6.key3(ArcGP3Attack7);
        ArcGP3Attack7.key3(ArcGP3Attack8);
        ArcGP3Attack8.key3(ArcGP3Attack9);
        ArcGP3Attack9.key3(ArcGP3Attack10);
        ArcGP3Attack10.key1(ArcAuto5);
        ArcGP3Attack10.keyWeaponInnate(Arc2ASGP2);
        ArcGP3Attack7.key1(ArcAuto5);
        ArcGP3Attack8.key1(ArcAuto5);
        ArcGP3Attack9.key1(ArcAuto5);
        ArcGP3Attack10.key1(ArcAuto5);
        SkillManager.register(ArcbladeSkill::new, ArcbladeSkill.createComboBasicAttack().setCombo(Arcbladeroot).setShouldDrawGui(true), ArcMod.MOD_ID, "combo2");
    }

    @SubscribeEvent
    public static void BuildSkills(SkillBuildEvent event) {
        Arcblade = event.build(ArcMod.MOD_ID, "combo2");
    }
}

