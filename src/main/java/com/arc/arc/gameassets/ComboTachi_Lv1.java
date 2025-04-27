package com.arc.arc.gameassets;
import com.arc.arc.Animation.ArcAnimations;
import com.arc.arc.ArcMod;
import com.arc.arc.Registries.ArcEffectsRegistry;
import com.arc.arc.skill.TachiSkill_Lv1;
import com.guhao.star.efmex.StarAnimations;
import com.guhao.star.regirster.Effect;
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
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.effect.EpicFightMobEffects;
import static java.lang.Integer.MAX_VALUE;

@Mod.EventBusSubscriber(modid = ArcMod.MOD_ID)
public class ComboTachi_Lv1 {
    public static Skill ComboTachi_Lv1;
    public static void registerSkills(){
        ComboNode Tachiroot = ComboNode.create();

        ComboNode Test_4 = ComboNode.createNode(() -> ArcAnimations.Gesshoku);

        ComboNode Jump = ComboNode.createNode(() -> StarAnimations.YAMATO_AIRSLASH)
                .addCondition(new JumpCondition())
                .setPriority(5)
                .setConvertTime(-0.1F).setPlaySpeed(1.3F);
        ComboNode Dash = ComboNode.createNode(() -> StarAnimations.LONGSWORD_OLD_DASH)
                .addCondition(new SprintingCondition())
                .setPriority(5)
                .setConvertTime(-0.1F).setPlaySpeed(1.1F);

        ComboNode Auto1 = ComboNode.createNode(() -> StarAnimations.TACHI_TWOHAND_AUTO_1)
                .setConvertTime(0.1F).setPriority(1);
        ComboNode Auto2 = ComboNode.createNode(() -> StarAnimations.YAMATO_AUTO3)
                .setStunTypeModifier(StunType.HOLD).setConvertTime(-0.1F).setPlaySpeed(1.2F).setHurtDamageMultiplier(1.3F)
                .addTimeEvent(new TimeStampedEvent(1F,entityPatch -> {
                    entityPatch.getOriginal().addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 20));
                }))
                .addTimeEvent(new TimeStampedEvent(1F,entityPatch -> {
                    entityPatch.getOriginal().addEffect(new MobEffectInstance(Effect.REALLY_STUN_IMMUNITY.get(), 20));
                }))
                //更改后摇
                .addTimeEvent(new TimeStampedEvent(1.7F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(Animations.TACHI_AUTO1, -0.9F);
                }));
        ComboNode Auto3 = ComboNode.createNode(() -> StarAnimations.TACHI_TWOHAND_AUTO_4)
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }))
                .setStunTypeModifier(StunType.HOLD).setConvertTime(0.1F).setPlaySpeed(0.9F);

        ComboNode Auto3_Enhanced = ComboNode.createNode(() -> StarAnimations.YAMATO_AUTO4)
                .addCondition(new MobEffectCondition(false,(ArcEffectsRegistry.TACHI_ENHANCED_PHASE),0,10))
                .setPriority(2).setConvertTime(-0.15F).setPlaySpeed(1.2F).setDamageMultiplier(ValueModifier.multiplier(2)).setStunTypeModifier(StunType.HOLD).setHurtDamageMultiplier(1.3F)
                .addTimeEvent(new TimeStampedEvent(0.5F,entityPatch -> {
                    entityPatch.getOriginal().addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 20));
                }))
                .addTimeEvent(new TimeStampedEvent(0.5F,entityPatch -> {
                    entityPatch.getOriginal().addEffect(new MobEffectInstance(Effect.REALLY_STUN_IMMUNITY.get(), 20));
                }))
                .addTimeEvent(new TimeStampedEvent(1.3F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.KEY_4);
                    }}))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.BLADE_RUSH_FINISHER, 0, 0);
                }));
        ComboNode Auto3_1_Enhanced = ComboNode.createNode(() -> Animations.AXE_DASH)
                .setHurtDamageMultiplier(1.3F).setDamageMultiplier(ValueModifier.multiplier(2)).setPlaySpeed(1.1F).setConvertTime(-0.1F).setStunTypeModifier(StunType.HOLD).setHurtDamageMultiplier(1.3F)
                .addTimeEvent(new TimeStampedEvent(0.0F,entityPatch -> {
                    entityPatch.getOriginal().addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 20));
                }))
                .addTimeEvent(new TimeStampedEvent(0.0F,entityPatch -> {
                    entityPatch.getOriginal().addEffect(new MobEffectInstance(Effect.REALLY_STUN_IMMUNITY.get(), 20));
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.BLADE_RUSH_FINISHER, 0, 0);
                }));


        ComboNode Skill_LethalSlicing_Start = ComboNode.createNode(() -> StarAnimations.LETHAL_SLICING_START)
                .addCondition(new StackCondition(1, MAX_VALUE))
                .setPlaySpeed(0.8F).setConvertTime(0.15F).setPlaySpeed(0.6F).setNotCharge(true).setPriority(2).setStunTypeModifier(StunType.HOLD).setImpactMultiplier(3)
                .addTimeEvent(new TimeStampedEvent(0.15F, (entityPatch -> {
                    if (entityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                        SkillContainer container = serverPlayerPatch.getSkill(SkillSlots.WEAPON_INNATE);
                        container.getSkill().setStackSynchronize(serverPlayerPatch, container.getStack() - 1);
                        container.getSkill().setConsumptionSynchronize(serverPlayerPatch, 1);
                    }
                })))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.getOriginal().addEffect(new MobEffectInstance(ArcEffectsRegistry.TACHI_ENHANCED_PHASE.get(), 400));
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.getOriginal().addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20,2));
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {if (entityPatch.getOriginal()instanceof ServerPlayer serverPlayer) {
                    ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.KEY_4);
                }}));
        ComboNode Skill_LethalSlicing_Once = ComboNode.createNode(() -> StarAnimations.LETHAL_SLICING_ONCE)
                .setNotCharge(true).setConvertTime(0.35F).setPlaySpeed(0.2F).setStunTypeModifier(StunType.LONG).setDamageMultiplier(ValueModifier.multiplier(0.5F))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    if (entityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                        SkillContainer container = serverPlayerPatch.getSkill(SkillSlots.WEAPON_INNATE);
                        container.getSkill().setStackSynchronize(serverPlayerPatch, container.getStack() + 1);
                        container.getSkill().setConsumptionSynchronize(serverPlayerPatch, 1);
                    }
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.BLADE_RUSH_FINISHER,  0,0);
                }))
                .addTimeEvent(new TimeStampedEvent(0.4F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.KEY_4);
                    }}));

        ComboNode Skill_UpperSlash_Once = ComboNode.createNode(() -> StarAnimations.YAMATO_POWER1)
                .addCondition(new MobEffectCondition(false,(ArcEffectsRegistry.TACHI_ENHANCED_PHASE),0,10))
                .addCondition(new StackCondition(1, MAX_VALUE))
                .addCondition(new DownCondition())
                .setPriority(3).setNotCharge(true).setCanBeInterrupt(false).setConvertTime(-0.05F).setPlaySpeed(1.2F).setStunTypeModifier(StunType.LONG)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.15F, "invincible groundSlam @s 1 false true false", true))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.getOriginal().addEffect(new MobEffectInstance(ArcEffectsRegistry.TACHIFINALSKILLA.get(), 400));
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.BLADE_RUSH_FINISHER,  0,0);
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(entityPatch.getTarget(), LivingEntityPatch.class);
                    if(targetPatch != null){
                        targetPatch.getOriginal().removeEffect(new MobEffectInstance(Effect.REALLY_STUN_IMMUNITY.get()).getEffect());}
                }))
                .addTimeEvent(new TimeStampedEvent(0.2F, (entityPatch -> {
                    if (entityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                        SkillContainer container = serverPlayerPatch.getSkill(SkillSlots.WEAPON_INNATE);
                        container.getSkill().setStackSynchronize(serverPlayerPatch, container.getStack() - 1);
                        container.getSkill().setConsumptionSynchronize(serverPlayerPatch, 1);
                    }
                })))
                .addTimeEvent(new TimeStampedEvent(0.59F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.KEY_4);
                    }}))
                .addTimeEvent(new TimeStampedEvent(0.6F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.KEY_4);
                    }}))
                .addTimeEvent(new TimeStampedEvent(0.61F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.KEY_4);
                    }}));
        ComboNode Skill_UpperSlash_Twice = ComboNode.createNode(() -> Animations.RUSHING_TEMPO3)
                .setNotCharge(true).setCanBeInterrupt(false).setConvertTime(0.15F).setPlaySpeed(0.85F).setStunTypeModifier(StunType.LONG)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "invincible groundSlam @s 1.5 false true false", true))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.getOriginal().addEffect(new MobEffectInstance(ArcEffectsRegistry.TACHIFINALSKILLA.get(), 400));
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE,  0,0);
                }));

        ComboNode Skill_DodgeCounter_Dodge = ComboNode.createNode(() -> WOMAnimations.KNIGHT_ROLL_BACKWARD)
                .addCondition(new MobEffectCondition(false,(ArcEffectsRegistry.TACHI_ENHANCED_PHASE),0,10))
                .addCondition(new StackCondition(1, MAX_VALUE))
                .addCondition(new DownCondition())
                .setNotCharge(true).setPriority(3)
                .addTimeEvent(new TimeStampedEvent(0.1F, (entityPatch -> {
                    if (entityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                        SkillContainer container = serverPlayerPatch.getSkill(SkillSlots.WEAPON_INNATE);
                        container.getSkill().setStackSynchronize(serverPlayerPatch, container.getStack() - 2);
                        container.getSkill().setConsumptionSynchronize(serverPlayerPatch, 1);
                    }
                })))
                .addDodgeSuccessEvent(new BiEvent(((entityPatch, entity) -> entityPatch.playSound(Sounds.FORESIGHT, 0, 0))))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("invincible entityAfterImage @s", true))
                .addDodgeSuccessEvent(new BiEvent(((entityPatch, entity) -> {
                    LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(entityPatch.getTarget(), LivingEntityPatch.class);
                    if(targetPatch != null){
                        targetPatch.playAnimationSynchronized(Animations.BIPED_HIT_LONG, 2);
                    }
                })))
                .addDodgeSuccessEvent(new BiEvent((entityPatch, entity) -> {
                    LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(entityPatch.getTarget(), LivingEntityPatch.class);
                    if(targetPatch != null){
                        targetPatch.getOriginal().addEffect(new MobEffectInstance(com.dfdyz.epicacg.registry.MobEffects.STOP.get(),40));}
                }))
                .addDodgeSuccessEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.getOriginal().addEffect(new MobEffectInstance(ArcEffectsRegistry.Success.get(), 30));
                }))
                .addTimeEvent(new TimeStampedEvent(0.13F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.KEY_4);
                    }}));

        ComboNode Skill_LethalSlicing_Start_Combo = ComboNode.createNode(() -> StarAnimations.LETHAL_SLICING_START)
                .addCondition(new StackCondition(1, MAX_VALUE))
                .addCondition(new MobEffectCondition(false,(ArcEffectsRegistry.TACHI_ENHANCED_PHASE),0,10))
                .setNotCharge(true).setPriority(3).setStunTypeModifier(StunType.HOLD).setImpactMultiplier(3)
                .addHitEvent(BiEvent.createBiCommandEvent("invincible setPlayerPhase 3", false))
                .addTimeEvent(new TimeStampedEvent(0.0F, (entityPatch -> {
                    if (entityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                        SkillContainer container = serverPlayerPatch.getSkill(SkillSlots.WEAPON_INNATE);
                        container.getSkill().setStackSynchronize(serverPlayerPatch, container.getStack() - 1);
                        container.getSkill().setConsumptionSynchronize(serverPlayerPatch, 1);
                    }
                })))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.getOriginal().removeEffect(new MobEffectInstance(ArcEffectsRegistry.TACHI_ENHANCED_PHASE.get()).getEffect());
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {if (entityPatch.getOriginal()instanceof ServerPlayer serverPlayer) {
                    ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.KEY_4);
                }}));
        ComboNode Skill_LethalSlicing_Once_Combo = ComboNode.createNode(() -> StarAnimations.LETHAL_SLICING_ONCE)
                .addCondition(new PlayerPhaseCondition(3,3))
                .setNotCharge(true).setConvertTime(0.2F).setStunTypeModifier(StunType.LONG).setPlaySpeed(0.2F).setNewPhase(1).setArmorNegation(80).setDamageMultiplier(ValueModifier.multiplier(2F)).setImpactMultiplier(2F).setStunTypeModifier(StunType.HOLD)
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.getOriginal().removeEffect(new MobEffectInstance(ArcEffectsRegistry.TACHI_ENHANCED_PHASE.get()).getEffect());
                }))
                .addHitEvent(new BiEvent(((entityPatch, entity) -> {
                    LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(entityPatch.getTarget(), LivingEntityPatch.class);
                    if(targetPatch != null){
                        targetPatch.playAnimationSynchronized(Animations.BIPED_HIT_LONG, 1);
                    }
                })))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.BLADE_RUSH_FINISHER,  0,0);
                }))
                .addTimeEvent(new TimeStampedEvent(0.35F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.KEY_4);
                    }}));
        ComboNode Skill_LethalSlicing_Twice_Combo = ComboNode.createNode(() -> Animations.RUSHING_TEMPO2)
                .setNotCharge(true).setConvertTime(-0.05F).setNewPhase(1).setArmorNegation(80).setDamageMultiplier(ValueModifier.multiplier(2F))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.getOriginal().removeEffect(new MobEffectInstance(ArcEffectsRegistry.TACHI_ENHANCED_PHASE.get()).getEffect());
                }))
                .addHitEvent(new BiEvent(((entityPatch, entity) -> {
                    LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(entityPatch.getTarget(), LivingEntityPatch.class);
                    if(targetPatch != null){
                        targetPatch.playAnimationSynchronized(Animations.BIPED_HIT_LONG, 1);
                    }
                })))
                .addHitEvent(BiEvent.createBiCommandEvent("particle biomesoplenty:dripping_blood ~ ~1 ~ 1.9 1 1.9 1 45",false))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.BLADE_RUSH_FINISHER,  0,0);
                }))
                .addTimeEvent(new TimeStampedEvent(0.4F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.KEY_4);
                    }}));

        ComboNode Final_Skill_Fushigiri_Once = ComboNode.createNode(()-> WOMAnimations.KNIGHT_ROLL_BACKWARD)
                .addCondition(new ParrySuccessCondition())
                .addCondition(new StackCondition(2, 4))
                .addCondition(new MobEffectCondition(false, (ArcEffectsRegistry.TACHIFINALSKILLA), 0,MAX_VALUE))
                .setPriority(6).setNotCharge(true).setCanBeInterrupt(false).setStunTypeModifier(StunType.LONG)
                .setCanBeInterrupt(false)
                .addTimeEvent(new TimeStampedEvent(0.05F,entityPatch -> {entityPatch.playSound(Sounds.FORESIGHT,0,0);}))
                .addTimeEvent(new TimeStampedEvent(0.1F,entityPatch -> {
                    entityPatch.getOriginal().removeEffect(new MobEffectInstance(ArcEffectsRegistry.TACHIFINALSKILLA.get()).getEffect());
                }))
                .addTimeEvent(new TimeStampedEvent(0.3F, (entityPatch -> {
                    if (entityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                        SkillContainer container = serverPlayerPatch.getSkill(SkillSlots.WEAPON_INNATE);
                        container.getSkill().setStackSynchronize(serverPlayerPatch, container.getStack() - 2);
                        container.getSkill().setConsumptionSynchronize(serverPlayerPatch, 1);
                    }
                })))
                .addTimeEvent(new TimeStampedEvent(0.2F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.KEY_4);
                    }}));
        ComboNode Final_Skill_Fushigiri_Twice = ComboNode.createNode(()->ArcAnimations.FUSHIGIRI_HAIRUI_SLASH_1)
                .setNotCharge(true).setCanBeInterrupt(false).setHurtDamageMultiplier(3F).setConvertTime(0.1F).setPlaySpeed(1.25F).setStunTypeModifier(StunType.LONG).setImpactMultiplier(5).setArmorNegation(100)
                .addTimeEvent(new TimeStampedEvent(0.0F,entityPatch -> {
                    entityPatch.getOriginal().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 34));
                }))
                .addTimeEvent(new TimeStampedEvent(0.1F,entityPatch -> {
                    entityPatch.getOriginal().addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 65));
                }))
                .addTimeEvent(new TimeStampedEvent(0.1F,entityPatch -> {
                    entityPatch.getOriginal().addEffect(new MobEffectInstance(Effect.REALLY_STUN_IMMUNITY.get(), 65));
                }))
                .addTimeEvent(new TimeStampedEvent(0.1F,entityPatch -> {
                    entityPatch.getOriginal().addEffect(new MobEffectInstance(ArcEffectsRegistry.InstantDamage.get(),1,30));
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.7F, "invincible groundSlam @s 5 false false false", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "execute as @p at @s run playsound cataclysm:flame_burst block @s ~ ~ ~ 0.75 0.75", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.7F, "execute as @s at @s run particle minecraft:falling_dust minecraft:crimson_hyphae ^ ^1.0 ^3 2.5 1.0 2.5 0.01 180", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.7F, "execute as @p at @s run playsound cataclysm:sword_stomp block @s ~ ~ ~ 0.75 1.5", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.7F, "execute as @p at @s run playsound cataclysm:sword_stomp block @s ~ ~ ~ 3.0 0.5", false))
                .addHitEvent(BiEvent.createBiCommandEvent("execute as @p at @s run playsound epicfight:entity.hit.blade_rush_last block @s ~ ~ ~ 0.3 0.8", false))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(entityPatch.getTarget(), LivingEntityPatch.class);
                    if(targetPatch != null){
                        targetPatch.getOriginal().addEffect(new MobEffectInstance(ArcEffectsRegistry.Success.get(),400));}
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.BLADE_RUSH_FINISHER,  0,0);
                }))
                .addHitEvent(new BiEvent(((entityPatch, entity) -> {
                    LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(entityPatch.getTarget(), LivingEntityPatch.class);
                    if(targetPatch != null){
                        targetPatch.playAnimationSynchronized(Animations.BIPED_HIT_LONG, 0.3F);
                    }
                })))
                .addTimeEvent(new TimeStampedEvent(3F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.KEY_4);
                    }}));
        ComboNode Final_Skill_Fushigiri_Thrid = ComboNode.createNode(()->ArcAnimations.FUSHIGIRI_HAIRUI_SLASH_2)
                .setNotCharge(true).setCanBeInterrupt(false).setHurtDamageMultiplier(3F).setConvertTime(0.1F).setStunTypeModifier(StunType.LONG).setDamageMultiplier(ValueModifier.multiplier(1.5F)).setImpactMultiplier(5).setArmorNegation(100)
                .addTimeEvent(new TimeStampedEvent(0.1F,entityPatch -> {
                    entityPatch.getOriginal().addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 65));
                }))
                .addTimeEvent(new TimeStampedEvent(0.1F,entityPatch -> {
                    entityPatch.getOriginal().addEffect(new MobEffectInstance(Effect.REALLY_STUN_IMMUNITY.get(), 65));
                }))
                .addTimeEvent(new TimeStampedEvent(0.1F,entityPatch -> {
                    entityPatch.getOriginal().addEffect(new MobEffectInstance(ArcEffectsRegistry.InstantDamage.get(),1,30));
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "execute as @p at @s run playsound cataclysm:flame_burst block @s ~ ~ ~ 0.75 0.75", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.4F, "execute as @s at @s run particle minecraft:falling_dust minecraft:crimson_hyphae ^ ^2.0 ^3 2.5 1.0 2.5 0.01 250", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.4F, "execute as @p at @s run playsound cataclysm:sword_stomp block @s ~ ~ ~ 3.0 1.5", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.4F, "execute as @p at @s run playsound cataclysm:sword_stomp block @s ~ ~ ~ 3.0 0.5", false))
                .addHitEvent(BiEvent.createBiCommandEvent("execute as @p at @s run playsound epicfight:entity.hit.blade_rush_last block @s ~ ~ ~ 0.3 0.8", false))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(entityPatch.getTarget(), LivingEntityPatch.class);
                    if(targetPatch != null){
                        targetPatch.getOriginal().addEffect(new MobEffectInstance(ArcEffectsRegistry.Success.get(),600));}
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.BLADE_RUSH_FINISHER,  0,0);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.4F, "invincible groundSlam @s 6 false false false", true));

        ComboNode Final_Skill_CrossSlash_Once = ComboNode.createNode(()->StarAnimations.YAMATO_STRIKE1)
                .addCondition(new MobEffectCondition(false,(ArcEffectsRegistry.Success),0,10))
                .addCondition(new DodgeSuccessCondition())
                .setNotCharge(true).setStunTypeModifier(StunType.LONG).setArmorNegation(70).setDamageMultiplier(ValueModifier.multiplier(1.2F)).setPriority(4).setConvertTime(-0.1F).setStunTypeModifier(StunType.LONG).setCanBeInterrupt(false)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.6F, "invincible groundSlam @s 2 false false false", true))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE,  0,0);
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(entityPatch.getTarget(), LivingEntityPatch.class);
                    if(targetPatch != null){
                        targetPatch.getOriginal().addEffect(new MobEffectInstance(ArcEffectsRegistry.Success.get(), 400));}
                }))
                .addHitEvent(new BiEvent(((entityPatch, entity) -> {
                    LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(entityPatch.getTarget(), LivingEntityPatch.class);
                    if(targetPatch != null){
                        targetPatch.playAnimationSynchronized(Animations.BIPED_HIT_LONG, 0);
                    }
                })))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(entityPatch.getTarget(), LivingEntityPatch.class);
                    if(targetPatch != null){
                        targetPatch.getOriginal().removeEffect(new MobEffectInstance(Effect.REALLY_STUN_IMMUNITY.get()).getEffect());}
                }))
                .addTimeEvent(new TimeStampedEvent(1F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.KEY_4);
                    }}));
        ComboNode Final_Skill_CrossSlash_Twice = ComboNode.createNode(()->StarAnimations.FATAL_DRAW)
                .setNotCharge(true).setStunTypeModifier(StunType.LONG).setConvertTime(-0.4F).setArmorNegation(70).setDamageMultiplier(ValueModifier.multiplier(1.2F)).setStunTypeModifier(StunType.LONG).setCanBeInterrupt(false)
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.BLADE_RUSH_FINISHER,  0,0);
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(entityPatch.getTarget(), LivingEntityPatch.class);
                    if(targetPatch != null){
                        targetPatch.getOriginal().addEffect(new MobEffectInstance(ArcEffectsRegistry.Success.get(), 400));}
                }))
                .addHitEvent(new BiEvent(((entityPatch, entity) -> {
                    LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(entityPatch.getTarget(), LivingEntityPatch.class);
                    if(targetPatch != null){
                        targetPatch.playAnimationSynchronized(Animations.BIPED_HIT_LONG, 0);
                    }
                })))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(entityPatch.getTarget(), LivingEntityPatch.class);
                    if(targetPatch != null){
                        targetPatch.getOriginal().removeEffect(new MobEffectInstance(Effect.REALLY_STUN_IMMUNITY.get()).getEffect());}
                }))
                .addTimeEvent(new TimeStampedEvent(0.8F,(entity) -> {
                    if (entity.getOriginal() instanceof ServerPlayer serverPlayer) {
                        ComboBasicAttack.executeOnServer(serverPlayer, ComboNode.ComboTypes.KEY_4);
                    }}));
        ComboNode Final_Skill_CrossSlash_Thrid = ComboNode.createNode(()->Animations.RUSHING_TEMPO2)
                .setNotCharge(true).setStunTypeModifier(StunType.LONG).setArmorNegation(70).setDamageMultiplier(ValueModifier.multiplier(1.2F)).setImpactMultiplier(2F).setConvertTime(0.2F).setCanBeInterrupt(false)
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(entityPatch.getTarget(), LivingEntityPatch.class);
                    if(targetPatch != null){
                        targetPatch.getOriginal().addEffect(new MobEffectInstance(ArcEffectsRegistry.Success.get(), 400));}
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(entityPatch.getTarget(), LivingEntityPatch.class);
                    if(targetPatch != null){
                        targetPatch.getOriginal().removeEffect(new MobEffectInstance(Effect.REALLY_STUN_IMMUNITY.get()).getEffect());}
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.BLADE_RUSH_FINISHER,  0,0);
                }));

        ComboNode Final_Skill_Execute = ComboNode.createNode(()-> StarAnimations.TACHI_EXECUTE)
                .addCondition(new MobEffectCondition(true, (ArcEffectsRegistry.Success), 0,MAX_VALUE))
                .setPriority(10).setNotCharge(true).setArmorNegation(150).setDamageMultiplier(ValueModifier.multiplier(0.7F)).setNewPhase(1)
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(entityPatch.getTarget(), LivingEntityPatch.class);
                    if(targetPatch != null){
                        targetPatch.getOriginal().addEffect(new MobEffectInstance(Effect.SLOW_TIME.get(),10));}
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(entityPatch.getTarget(), LivingEntityPatch.class);
                    if(targetPatch != null){
                        targetPatch.getOriginal().addEffect(new MobEffectInstance(com.dfdyz.epicacg.registry.MobEffects.STOP.get(), 10));}
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(entityPatch.getTarget(), LivingEntityPatch.class);
                    if(targetPatch != null){
                        targetPatch.getOriginal().removeEffect(new MobEffectInstance(Effect.REALLY_STUN_IMMUNITY.get()).getEffect());}
                }))
                .addTimeEvent(new TimeStampedEvent(0.1F, (entityPatch -> {
                    if (entityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                        SkillContainer container = serverPlayerPatch.getSkill(SkillSlots.WEAPON_INNATE);
                        container.getSkill().setStackSynchronize(serverPlayerPatch, container.getStack() +2);
                        container.getSkill().setConsumptionSynchronize(serverPlayerPatch, 1);
                    }
                })))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "particle biomesoplenty:dripping_blood ~ ~1 ~ 1.9 1 1.9 1 45", false))
                .addTimeEvent(new TimeStampedEvent(0.1F,entityPatch -> {
                    entityPatch.getOriginal().addEffect(new MobEffectInstance(MobEffects.REGENERATION,40,10));
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
                .addConditionAnimation(Auto3)
                .addConditionAnimation(Auto3_Enhanced);

        ComboNode DefeatSkills = ComboNode.create()
                .addConditionAnimation(Skill_LethalSlicing_Start)
                .addConditionAnimation(Final_Skill_CrossSlash_Once)
                .addConditionAnimation(Final_Skill_Fushigiri_Once);

        ComboNode ComboSkill_1 = ComboNode.create()
                .addConditionAnimation(Skill_LethalSlicing_Start)
                .addConditionAnimation(Skill_UpperSlash_Once)
                .addConditionAnimation(Final_Skill_Fushigiri_Once);

        ComboNode ComboSkill_2 = ComboNode.create()
                .addConditionAnimation(Skill_LethalSlicing_Start)
                .addConditionAnimation(Skill_DodgeCounter_Dodge)
                .addConditionAnimation(Final_Skill_Fushigiri_Once);

        ComboNode ComboSkill_3_A = ComboNode.create()
                .addConditionAnimation(Skill_LethalSlicing_Start)
                .addConditionAnimation(Skill_LethalSlicing_Start_Combo)
                .addConditionAnimation(Final_Skill_Fushigiri_Once);

        //test
        Tachiroot.key4(Test_4);
        //Root
        Tachiroot.key1(BasicAttack);
        Tachiroot.keyWeaponInnate(DefeatSkills);
        Dash.keyWeaponInnate(DefeatSkills);
        Jump.keyWeaponInnate(DefeatSkills);
        //技能二段
        Skill_LethalSlicing_Start.key4(Skill_LethalSlicing_Once);
        Skill_LethalSlicing_Start_Combo.key4(Skill_LethalSlicing_Once_Combo);
        Skill_LethalSlicing_Once_Combo.key4(Skill_LethalSlicing_Twice_Combo);
        Skill_LethalSlicing_Twice_Combo.key4(Final_Skill_Execute);
        Skill_UpperSlash_Once.key4(Skill_UpperSlash_Twice);
        Skill_DodgeCounter_Dodge.key4(Final_Skill_CrossSlash_Once);
        Final_Skill_CrossSlash_Once.key4(Final_Skill_CrossSlash_Twice);
        Final_Skill_CrossSlash_Twice.key4(Final_Skill_CrossSlash_Thrid);
        Final_Skill_Fushigiri_Once.key4(Final_Skill_Fushigiri_Twice);
        Final_Skill_Fushigiri_Twice.key4(Final_Skill_Fushigiri_Thrid);
        //闭环
        Test_4.key1(BasicAttack);
        Test_4.keyWeaponInnate(DefeatSkills);
        Auto3_1_Enhanced.key1(BasicAttack);
        Auto3.key1(BasicAttack);
        Dash.key1(Auto1);
        Jump.key1(Auto1);
        Skill_LethalSlicing_Start.key1(BasicAttack);
        Skill_LethalSlicing_Start.keyWeaponInnate(DefeatSkills);
        Skill_LethalSlicing_Once.key1(BasicAttack);
        Skill_LethalSlicing_Once.keyWeaponInnate(DefeatSkills);
        Skill_UpperSlash_Twice.key1(BasicAttack);
        Skill_UpperSlash_Twice.keyWeaponInnate(DefeatSkills);
        Skill_DodgeCounter_Dodge.key1(BasicAttack);
        Skill_DodgeCounter_Dodge.keyWeaponInnate(DefeatSkills);
        Skill_LethalSlicing_Start_Combo.key1(BasicAttack);
        Skill_LethalSlicing_Start_Combo.keyWeaponInnate(DefeatSkills);
        Skill_LethalSlicing_Twice_Combo.key1(BasicAttack);
        Skill_LethalSlicing_Twice_Combo.keyWeaponInnate(DefeatSkills);
        Final_Skill_Fushigiri_Twice.key1(BasicAttack);
        Final_Skill_Fushigiri_Twice.keyWeaponInnate(DefeatSkills);
        Final_Skill_Fushigiri_Thrid.key1(BasicAttack);
        Final_Skill_Fushigiri_Thrid.keyWeaponInnate(DefeatSkills);
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
        Auto3.keyWeaponInnate(ComboSkill_3_A);
        Auto3_Enhanced.key4(Auto3_1_Enhanced);
        Auto3_Enhanced.keyWeaponInnate(ComboSkill_3_A);
        Auto3_1_Enhanced.keyWeaponInnate(ComboSkill_3_A);
        SkillManager.register(TachiSkill_Lv1::new, TachiSkill_Lv1.createComboBasicAttack().setCombo(Tachiroot).setShouldDrawGui(true), ArcMod.MOD_ID, "combo2_lv1");
    }
    @SubscribeEvent
    public static void BuildSkills(SkillBuildEvent event) {
        ComboTachi_Lv1 = event.build(ArcMod.MOD_ID, "combo2_lv1");
    }
}
