package com.arc.arc.gameassets;
import com.arc.arc.ArcMod;
import com.arc.arc.Registries.ArcEffectsRegistry;
import com.arc.arc.skill.TachiSkill;
import com.guhao.star.efmex.StarAnimations;
import com.guhao.star.regirster.Sounds;
import com.p1nero.invincible.api.events.BiEvent;
import com.p1nero.invincible.api.events.TimeStampedEvent;
import com.p1nero.invincible.conditions.*;
import com.p1nero.invincible.skill.ComboBasicAttack;
import com.p1nero.invincible.skill.api.ComboNode;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.data.reloader.SkillManager;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

import static java.lang.Integer.MAX_VALUE;

@Mod.EventBusSubscriber(modid = ArcMod.MOD_ID)
public class StellairsComboTachi {
    public static Skill StellairsComboTachi;
    public static void registerSkills(){
        ComboNode Tachiroot = ComboNode.create();
        ComboNode Jump = ComboNode.createNode(() -> StarAnimations.YAMATO_AIRSLASH)
                .addCondition(new JumpCondition())
                .setPriority(5)
                .setConvertTime(-0.08F).setPlaySpeed(1.3F)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible consumeStamina 2", false));
        ComboNode Dash = ComboNode.createNode(() -> StarAnimations.YAMATO_DASH)
                .addCondition(new SprintingCondition())
                .setPriority(5)
                .setConvertTime(-0.08F).setPlaySpeed(1.3F);

        ComboNode Auto1 = ComboNode.createNode(() -> StarAnimations.TACHI_TWOHAND_AUTO_1)
                .setConvertTime(0.05F).setPriority(1);
        ComboNode Auto2 = ComboNode.createNode(() -> StarAnimations.YAMATO_AUTO3)
                .setStunTypeModifier(StunType.SHORT).setConvertTime(-0.1F).setPlaySpeed(1.2F)
                //更改后摇
                .addTimeEvent(new TimeStampedEvent(1.65F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(Animations.TACHI_AUTO1, -0.9F);
                }));
        ComboNode Auto3 = ComboNode.createNode(() -> StarAnimations.YAMATO_AUTO4)
                .setPlaySpeed(1.2F).setConvertTime(-0.2F).setStunTypeModifier(StunType.HOLD).setImpactMultiplier(2F)
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s star:slowtime 1",true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s epicacg:stop 1",true))
                .addTimeEvent(new TimeStampedEvent(1.35F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.KEY_4);
                    }}));
        ComboNode Auto3_1 = ComboNode.createNode(() -> Animations.TACHI_AUTO2)
                .setConvertTime(-0.9F).setPlaySpeed(0.7F)
                .addTimeEvent(new TimeStampedEvent(0.95F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(Animations.BIPED_HOLD_LONGSWORD, 0F);
                }));

        ComboNode Roll = ComboNode.createNode(() -> WOMAnimations.KNIGHT_ROLL_BACKWARD)
                .addCondition(new StackCondition(2, 3))
                .addCondition(new MobEffectCondition(false, (ArcEffectsRegistry.TACHIFINALSKILLB), 0, 10))
                .setPriority(4)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "effect clear @s arc:tachifinalskillb", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible consumeStack 2", false))
                .addDodgeSuccessEvent(new BiEvent(((entityPatch, entity) -> entityPatch.playSound(Sounds.FORESIGHT, 0, 0))))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("invincible entityAfterImage @s", true))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("effect give @s epicacg:stop 2", true))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("effect give @s star:slowtime 2", true))
                .addTimeEvent(new TimeStampedEvent(0.27F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.KEY_4);
                    }}));;

        ComboNode Skill_LethalSlicing_Start = ComboNode.createNode(() -> StarAnimations.LETHAL_SLICING_START)
                .addCondition(new StackCondition(1, 3))
                .setPlaySpeed(0.8F).setConvertTime(0.15F).setPlaySpeed(0.6F).setNotCharge(true).setPriority(2).setStunTypeModifier(StunType.LONG)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.15F,"invincible consumeStack 1", false))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible setPlayerPhase 2", false))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.getOriginal().addEffect(new MobEffectInstance(ArcEffectsRegistry.TACHI_ENHANCED_PHASE.get(), MAX_VALUE));
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {if (entityPatch.getOriginal()instanceof ServerPlayer serverPlayer) {
                    ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.KEY_4);
                }}));
        ComboNode Skill_LethalSlicing_Once = ComboNode.createNode(() -> StarAnimations.LETHAL_SLICING_ONCE)
                .setNotCharge(true).setConvertTime(0.35F).setPlaySpeed(0.2F).setStunTypeModifier(StunType.HOLD).setDamageMultiplier(ValueModifier.multiplier(0.5F))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStack -1", false))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.BLADE_RUSH_FINISHER,  0,0);
                }))
                .addTimeEvent(new TimeStampedEvent(0.4F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.KEY_4);
                    }}));

        ComboNode Skill_UpperSlash_Once = ComboNode.createNode(() -> StarAnimations.YAMATO_POWER1)
                .addCondition(new PlayerPhaseCondition(2,2))
                .addCondition(new StackCondition(1, 3))
                .setPriority(3).setNotCharge(true).setConvertTime(-0.15F).setPlaySpeed(1.3F).setNewPhase(1).setStunTypeModifier(StunType.LONG)
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.getOriginal().addEffect(new MobEffectInstance(ArcEffectsRegistry.TACHIFINALSKILLA.get(), 400));
                }))
                .addTimeEvent(new TimeStampedEvent(0.2F,entityPatch -> {
                    entityPatch.getOriginal().removeEffect(new MobEffectInstance(ArcEffectsRegistry.TACHI_ENHANCED_PHASE.get()).getEffect());
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.BLADE_RUSH_FINISHER,  0,0);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "invincible consumeStack 1", false))
                .addTimeEvent(new TimeStampedEvent(0.7F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.KEY_4);
                    }}));
        ComboNode Skill_UpperSlash_Twice = ComboNode.createNode(() -> Animations.RUSHING_TEMPO3)
                .setNotCharge(true).setConvertTime(0.1F).setPlaySpeed(0.9F)
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.getOriginal().addEffect(new MobEffectInstance(ArcEffectsRegistry.TACHIFINALSKILLA.get(), 400));
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE,  0,0);
                }));

        ComboNode Skill_DodgeCounter_Dodge = ComboNode.createNode(() -> WOMAnimations.KNIGHT_ROLL_BACKWARD)
                .addCondition(new PlayerPhaseCondition(2,2))
                .addCondition(new StackCondition(1, 3))
                .setNotCharge(true).setPriority(3).setNewPhase(1)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible consumeStack 1", false))
                .addTimeEvent(new TimeStampedEvent(0.2F,entityPatch -> {
                    entityPatch.getOriginal().removeEffect(new MobEffectInstance(ArcEffectsRegistry.TACHI_ENHANCED_PHASE.get()).getEffect());
                }))
                .addDodgeSuccessEvent(new BiEvent(((entityPatch, entity) -> entityPatch.playSound(Sounds.FORESIGHT, 0, 0))))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("invincible entityAfterImage @s", true))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("effect give @s epicacg:stop 2", true))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("effect give @s star:slowtime 2", true))
                .addDodgeSuccessEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.getOriginal().addEffect(new MobEffectInstance(ArcEffectsRegistry.TACHIFINALSKILLB.get(), 400));
                }))
                .addDodgeSuccessEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.getOriginal().addEffect(new MobEffectInstance(ArcEffectsRegistry.Success.get(), 20));
                }))
                .addTimeEvent(new TimeStampedEvent(0.35F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.KEY_4);
                    }}));
        ComboNode Skill_DodgeCounter_Counter_1 = ComboNode.createNode(() -> StarAnimations.FATAL_DRAW_DASH)
                .setNotCharge(true).setCanBeInterrupt(false).setConvertTime(-0.6F).setStunTypeModifier(StunType.HOLD)
                .addCondition(new MobEffectCondition(false, (ArcEffectsRegistry.Success), 0, 10))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.BLADE_RUSH_FINISHER,  0,0);
                }));

        ComboNode Skill_LethalSlicing_Start_Combo = ComboNode.createNode(() -> StarAnimations.LETHAL_SLICING_START)
                .addCondition(new StackCondition(2, 3))
                .addCondition(new PlayerPhaseCondition(2,2))
                .setNotCharge(true).setPriority(3).setStunTypeModifier(StunType.LONG)
                .addHitEvent(BiEvent.createBiCommandEvent("invincible setPlayerPhase 3", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack 2", false))
                .addTimeEvent(new TimeStampedEvent(0.0F,entityPatch -> {
                    entityPatch.getOriginal().removeEffect(new MobEffectInstance(ArcEffectsRegistry.TACHI_ENHANCED_PHASE.get()).getEffect());
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {if (entityPatch.getOriginal()instanceof ServerPlayer serverPlayer) {
                    ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.KEY_4);
                }}));
        ComboNode Skill_LethalSlicing_Once_Combo = ComboNode.createNode(() -> StarAnimations.LETHAL_SLICING_ONCE)
                .addCondition(new PlayerPhaseCondition(3,3))
                .setNotCharge(true).setConvertTime(0.25F).setPlaySpeed(0.2F).setNewPhase(1).setDamageMultiplier(ValueModifier.multiplier(1.5F)).setImpactMultiplier(2F).setStunTypeModifier(StunType.HOLD)
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s star:slowtime 1",true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s epicacg:stop 1",true))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.BLADE_RUSH_FINISHER,  0,0);
                }))
                .addTimeEvent(new TimeStampedEvent(0.35F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.KEY_4);
                    }}));
        ComboNode Skill_LethalSlicing_Twice_Combo = ComboNode.createNode(() -> Animations.RUSHING_TEMPO2)
                .setNotCharge(true).setConvertTime(-0.05F).setDamageMultiplier(ValueModifier.multiplier(1.5F)).setImpactMultiplier(2F)
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s star:slowtime 1",true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s epicacg:stop 1",true))
                .addHitEvent(BiEvent.createBiCommandEvent("particle biomesoplenty:dripping_blood ~ ~1 ~ 1.9 1 1.9 1 45",false))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.BLADE_RUSH_FINISHER,  0,0);
                }))
                .addTimeEvent(new TimeStampedEvent(0.4F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.KEY_4);
                    }}));

        ComboNode Final_Skill_RushingTempo_Once = ComboNode.createNode(()-> StarAnimations.YAMATO_POWER1)
                .addCondition(new ParrySuccessCondition())
                .addCondition(new StackCondition(2, 3))
                .addCondition(new MobEffectCondition(false, (ArcEffectsRegistry.TACHIFINALSKILLA), 0, 10))
                .setPriority(6).setNotCharge(true).setStunTypeModifier(StunType.LONG)
                .setImpactMultiplier(3).setConvertTime(-0.15F).setPlaySpeed(1.3F).setCanBeInterrupt(false)
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE,  0,0);
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(entityPatch.getTarget(), LivingEntityPatch.class);
                    if(targetPatch != null){
                        targetPatch.getOriginal().addEffect(new MobEffectInstance(ArcEffectsRegistry.Success.get(),600));}
                }))
                .addTimeEvent(new TimeStampedEvent(0.3F,entityPatch -> {
                    entityPatch.getOriginal().removeEffect(new MobEffectInstance(ArcEffectsRegistry.TACHIFINALSKILLA.get()).getEffect());
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "invincible consumeStack 2", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.35F, "invincible groundSlam @s 1.5 false true false", true))
                .addTimeEvent(new TimeStampedEvent(0.65F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.KEY_4);
                    }}));
        ComboNode Final_Skill_RushingTempo_Twice = ComboNode.createNode(()->Animations.RUSHING_TEMPO3)
                .setNotCharge(true).setStunTypeModifier(StunType.LONG).setImpactMultiplier(3).setConvertTime(0.2F).setPlaySpeed(0.8F).setCanBeInterrupt(false)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "invincible groundSlam @s 2.5 false false false", true))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(entityPatch.getTarget(), LivingEntityPatch.class);
                    if(targetPatch != null){
                        targetPatch.getOriginal().addEffect(new MobEffectInstance(ArcEffectsRegistry.Success.get(),600));}
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE,  0,0);
                }))
                .addTimeEvent(new TimeStampedEvent(0.4F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.KEY_4);
                    }}));
        ComboNode Final_Skill_RushingTempo_Thrid = ComboNode.createNode(()->Animations.RUSHING_TEMPO3)
                .setNotCharge(true).setStunTypeModifier(StunType.LONG).setImpactMultiplier(3).setConvertTime(0.15F).setPlaySpeed(0.95F).setCanBeInterrupt(false)
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(entityPatch.getTarget(), LivingEntityPatch.class);
                    if(targetPatch != null){
                        targetPatch.getOriginal().addEffect(new MobEffectInstance(ArcEffectsRegistry.Success.get(),600));}
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.BLADE_RUSH_FINISHER,  0,0);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "invincible groundSlam @s 3.5 false false false", true));

        ComboNode Final_Skill_CrossSlash_Once = ComboNode.createNode(()->StarAnimations.YAMATO_STRIKE1)
                .addCondition(new DodgeSuccessCondition())
                .setNotCharge(true).setStunTypeModifier(StunType.HOLD).setPriority(4).setConvertTime(-0.1F).setStunTypeModifier(StunType.LONG).setCanBeInterrupt(false)
                .addTimeEvent(new TimeStampedEvent(0.3F,entityPatch -> {
                    entityPatch.getOriginal().removeEffect(new MobEffectInstance(ArcEffectsRegistry.TACHIFINALSKILLB.get()).getEffect());
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.6F, "invincible groundSlam @s 2 false false false", true))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(entityPatch.getTarget(), LivingEntityPatch.class);
                    if(targetPatch != null){
                        targetPatch.getOriginal().addEffect(new MobEffectInstance(ArcEffectsRegistry.Success.get(),600));}
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE,  0,0);
                }))
                .addTimeEvent(new TimeStampedEvent(1F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.KEY_4);
                    }}));
        ComboNode Final_Skill_CrossSlash_Twice = ComboNode.createNode(()->StarAnimations.FATAL_DRAW)
                .setNotCharge(true).setStunTypeModifier(StunType.HOLD).setConvertTime(-0.4F).setStunTypeModifier(StunType.LONG).setCanBeInterrupt(false)
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(entityPatch.getTarget(), LivingEntityPatch.class);
                    if(targetPatch != null){
                        targetPatch.getOriginal().addEffect(new MobEffectInstance(ArcEffectsRegistry.Success.get(),600));}
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.BLADE_RUSH_FINISHER,  0,0);
                }))
                .addTimeEvent(new TimeStampedEvent(0.8F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.KEY_4);
                    }}));
        ComboNode Final_Skill_CrossSlash_Thrid = ComboNode.createNode(()->Animations.RUSHING_TEMPO2)
                .setNotCharge(true).setStunTypeModifier(StunType.LONG).setImpactMultiplier(3).setConvertTime(0.2F).setCanBeInterrupt(false)
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(entityPatch.getTarget(), LivingEntityPatch.class);
                    if(targetPatch != null){
                        targetPatch.getOriginal().addEffect(new MobEffectInstance(ArcEffectsRegistry.Success.get(),600));}
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.BLADE_RUSH_FINISHER,  0,0);
                }));

        ComboNode Final_Skill_Execute = ComboNode.createNode(()-> StarAnimations.TACHI_EXECUTE)
                .addCondition(new MobEffectCondition(true, (ArcEffectsRegistry.Success), 0, 10))
                .setPriority(10).setNotCharge(true).setDamageMultiplier(ValueModifier.multiplier(0.8f))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "particle biomesoplenty:dripping_blood ~ ~1 ~ 1.9 1 1.9 1 45", false))
                .addTimeEvent(new TimeStampedEvent(0.05F,entityPatch -> {
                    entityPatch.getOriginal().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,50,5));
                }))
                .addTimeEvent(new TimeStampedEvent(0.1F,entityPatch -> {
                    LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(entityPatch.getTarget(), LivingEntityPatch.class);
                    if(targetPatch != null){
                        targetPatch.getOriginal().removeEffect(new MobEffectInstance(ArcEffectsRegistry.Success.get()).getEffect());}
                }))
                .addTimeEvent(new TimeStampedEvent(0.1F, (entityPatch) -> {
                    entityPatch.playSound(Sounds.SEKIRO,  0, 0);
                }))
                .addTimeEvent(new TimeStampedEvent(0.0F, livingEntityPatch -> {
                    LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(livingEntityPatch.getTarget(), LivingEntityPatch.class);
                    if(targetPatch != null){
                        targetPatch.playAnimationSynchronized(StarAnimations.TACHI_EXECUTED, 0);
                    }
                }));

        ComboNode BasicAttack = ComboNode.create()
                .addConditionAnimation(Dash)
                .addConditionAnimation(Jump)
                .addConditionAnimation(Auto1);
        ComboNode Attack2 = ComboNode.create()
                .addConditionAnimation(Dash)
                .addConditionAnimation(Jump)
                .addConditionAnimation(Auto2);
        ComboNode Attack3 = ComboNode.create()
                .addConditionAnimation(Dash)
                .addConditionAnimation(Jump)
                .addConditionAnimation(Auto3);
        ComboNode Attack4 = ComboNode.create()
                .addConditionAnimation(Dash)
                .addConditionAnimation(Jump)
                .addConditionAnimation(Auto3_1);

        ComboNode DefeatSkills = ComboNode.create()
                .addConditionAnimation(Skill_LethalSlicing_Start)
                .addConditionAnimation(Final_Skill_CrossSlash_Once)
                .addConditionAnimation(Final_Skill_RushingTempo_Once)
                .addConditionAnimation(Roll);

        ComboNode ComboSkill_1 = ComboNode.create()
                .addConditionAnimation(Skill_LethalSlicing_Start)
                .addConditionAnimation(Skill_UpperSlash_Once)
                .addConditionAnimation(Roll)
                .addConditionAnimation(Final_Skill_RushingTempo_Once);

        ComboNode ComboSkill_2 = ComboNode.create()
                .addConditionAnimation(Skill_LethalSlicing_Start)
                .addConditionAnimation(Skill_DodgeCounter_Dodge)
                .addConditionAnimation(Roll)
                .addConditionAnimation(Final_Skill_RushingTempo_Once);

        ComboNode ComboSkill_3_A = ComboNode.create()
                .addConditionAnimation(Skill_LethalSlicing_Start)
                .addConditionAnimation(Skill_LethalSlicing_Start_Combo)
                .addConditionAnimation(Roll)
                .addConditionAnimation(Final_Skill_RushingTempo_Once);

        //Root
        Tachiroot.key1(BasicAttack);
        Tachiroot.keyWeaponInnate(DefeatSkills);
        Dash.keyWeaponInnate(DefeatSkills);
        Jump.keyWeaponInnate(DefeatSkills);
        //技能命中二段
        Skill_LethalSlicing_Start.key4(Skill_LethalSlicing_Once);
        Skill_LethalSlicing_Start_Combo.key4(Skill_LethalSlicing_Once_Combo);
        Skill_LethalSlicing_Once_Combo.key4(Skill_LethalSlicing_Twice_Combo);
        Skill_LethalSlicing_Twice_Combo.key4(Final_Skill_Execute);
        Skill_UpperSlash_Once.key4(Skill_UpperSlash_Twice);
        Skill_DodgeCounter_Dodge.key4(Skill_DodgeCounter_Counter_1);
        Final_Skill_RushingTempo_Once.key4(Final_Skill_RushingTempo_Twice);
        Final_Skill_RushingTempo_Twice.key4(Final_Skill_RushingTempo_Thrid);
        Roll.key4(Final_Skill_CrossSlash_Once);
        Final_Skill_CrossSlash_Once.key4(Final_Skill_CrossSlash_Twice);
        Final_Skill_CrossSlash_Twice.key4(Final_Skill_CrossSlash_Thrid);
        //闭环
        Dash.key1(Auto1);
        Jump.key1(Auto1);
        Roll.key1(BasicAttack);
        Skill_LethalSlicing_Start.key1(BasicAttack);
        Skill_LethalSlicing_Start.keyWeaponInnate(DefeatSkills);
        Skill_LethalSlicing_Once.key1(BasicAttack);
        Skill_LethalSlicing_Once.keyWeaponInnate(DefeatSkills);
        Skill_UpperSlash_Twice.key1(Attack3);
        Skill_UpperSlash_Twice.keyWeaponInnate(DefeatSkills);
        Skill_DodgeCounter_Dodge.key1(BasicAttack);
        Skill_DodgeCounter_Dodge.keyWeaponInnate(DefeatSkills);
        Skill_DodgeCounter_Counter_1.key1(BasicAttack);
        Skill_DodgeCounter_Counter_1.keyWeaponInnate(DefeatSkills);
        Skill_LethalSlicing_Start_Combo.key1(BasicAttack);
        Skill_LethalSlicing_Start_Combo.keyWeaponInnate(DefeatSkills);
        Skill_LethalSlicing_Twice_Combo.key1(BasicAttack);
        Skill_LethalSlicing_Twice_Combo.keyWeaponInnate(DefeatSkills);
        Final_Skill_RushingTempo_Twice.key1(BasicAttack);
        Final_Skill_RushingTempo_Twice.keyWeaponInnate(DefeatSkills);
        Final_Skill_RushingTempo_Thrid.key1(BasicAttack);
        Final_Skill_RushingTempo_Thrid.keyWeaponInnate(DefeatSkills);
        Final_Skill_CrossSlash_Thrid.key1(BasicAttack);
        Final_Skill_CrossSlash_Thrid.keyWeaponInnate(DefeatSkills);
        Final_Skill_Execute.key1(BasicAttack);
        Final_Skill_Execute.keyWeaponInnate(DefeatSkills);
        //普通攻击一段及其派生
        Auto1.key1(Attack2);
        Auto1.keyWeaponInnate(ComboSkill_1);
        //普通攻击二段及其派生
        Auto2.key1(Attack3);
        Auto2.keyWeaponInnate(ComboSkill_2);
        //普通攻击三段及其派生
        Auto3.key4(Attack4);
        Auto3.keyWeaponInnate(ComboSkill_3_A);
        Auto3_1.keyWeaponInnate(ComboSkill_3_A);


        SkillManager.register(TachiSkill::new, TachiSkill.createComboBasicAttack().setCombo(Tachiroot).setShouldDrawGui(true), ArcMod.MOD_ID, "combo2");
    }
    @SubscribeEvent
    public static void BuildSkills(SkillBuildEvent event) {
        StellairsComboTachi = event.build(ArcMod.MOD_ID, "combo2");
    }
}
