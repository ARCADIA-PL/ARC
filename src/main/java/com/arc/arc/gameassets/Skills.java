package com.arc.arc.gameassets;

import com.arc.arc.ArcMod;
import com.arc.arc.init.ArcEffectsRegistry;
import com.guhao.star.efmex.StarAnimations;
import com.guhao.star.regirster.Sounds;
import com.p1nero.invincible.api.events.BiEvent;
import com.p1nero.invincible.api.events.TimeStampedEvent;
import com.p1nero.invincible.conditions.*;
import com.p1nero.invincible.skill.api.ComboNode;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.data.reloader.SkillManager;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.skill.BattojutsuPassive;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.StunType;

@Mod.EventBusSubscriber(modid = ArcMod.MOD_ID)
public class Skills {
    public static Skill StellarisTachi;
    public static Skill UchigatanaPower;
    public static Skill Arcblade;
    public static Skill ArcbladeMini;

    public static void registerSkills() {
        ComboNode Tachiroot = ComboNode.create();
        ComboNode TachiAirSlash = ComboNode.createNode(() -> Animations.LONGSWORD_AIR_SLASH)
                .setPriority(4)
                .addCondition(new JumpCondition());
        ComboNode TachiDashAttack = ComboNode.createNode(() -> Animations.TACHI_DASH)
                .setPriority(4)
                .addCondition(new SprintingCondition());
        ComboNode TachiAuto1 = ComboNode.createNode(() -> StarAnimations.TACHI_TWOHAND_AUTO_3)
                .setPriority(1)
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s arc:stellaris_instantdamage 1 100", true));
        ComboNode TachiAuto2 = ComboNode.createNode(() -> StarAnimations.LONGSWORD_OLD_AUTO4)
                .setPriority(1)
                .setConvertTime(0.1F)
                .setPlaySpeed(0.9F);
        ComboNode TachiAuto3 = ComboNode.createNode(() -> StarAnimations.TACHI_TWOHAND_AUTO_1)
                .setPriority(1);
        ComboNode TachiPowerAuto3Success = ComboNode.createNode(() -> StarAnimations.YAMATO_AUTO2)
                .setPriority(3)
                .setPlaySpeed(0.75F)
                .addCondition(new PlayerPhaseCondition(2, 2))
                .addCondition(new StackCondition(1, 4))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible consumeStack 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.5F, "invincible setPlayerPhase 1", false));
        ComboNode TachiPowerAuto3Fail = ComboNode.createNode(() -> StarAnimations.YAMATO_AUTO2)
                .setPriority(2)
                .setPlaySpeed(0.75F)
                .addCondition(new StackCondition(1, 4))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible consumeStack 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible consumeStamina 5", false));

        ComboNode TachiAuto4 = ComboNode.createNode(() -> StarAnimations.TACHI_TWOHAND_AUTO_2)
                .setPriority(1);
        ComboNode TachiPowerAuto4Success = ComboNode.createNode(() -> StarAnimations.LETHAL_SLICING_ONCE)
                .setPriority(3)
                .addCondition(new MobEffectCondition(true, (MobEffectRegistry.REND), 1, 9999999))
                .setStunTypeModifier(StunType.HOLD)
                .addCondition(new StackCondition(1, 4))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible consumeStack 1", false));
        ComboNode TachiPowerAuto4Fail = ComboNode.createNode(() -> StarAnimations.LETHAL_SLICING_ONCE)
                .setPriority(2)
                .setConvertTime(0.1F)
                .setPlaySpeed(0.8F)
                .setStunTypeModifier(StunType.SHORT)
                .addCondition(new StackCondition(1, 4))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible consumeStack 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible consumeStamina 2.5", false));

        ComboNode TachiAuto5 = ComboNode.createNode(() -> StarAnimations.LONGSWORD_OLD_DASH)
                .setPriority(1)
                .setConvertTime(-0.1F)
                .setPlaySpeed(0.75F)
                .setStunTypeModifier(StunType.SHORT)
                .addTimeEvent(new TimeStampedEvent(0.22F, (entityPatch) -> {
                    entityPatch.playSound(EpicFightSounds.WHOOSH_SHARP, 0, 0);
                }));
        ComboNode TachiPowerAuto5 = ComboNode.createNode(() -> StarAnimations.BLADE_RUSH_FINISHER)
                .setPriority(2)
                .setConvertTime(0.2F)
                .addCondition(new StackCondition(1, 4))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible consumeStack 1", false))
                .addCondition(new MobEffectCondition(true, (MobEffectRegistry.REND), 1, 9999999));


        ComboNode TachiMartialArtsAuto1 = ComboNode.createNode(() -> WOMAnimations.ENDERBLASTER_ONEHAND_AUTO_3)
                .setPriority(2)
                .setPlaySpeed(0.8F)
                .addCondition(new StackCondition(1, 4))
                .setDamageMultiplier(ValueModifier.multiplier(1.5F))
                .setImpactMultiplier(1.1F)
                .setArmorNegation(0.1F)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible consumeStamina 2", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible consumeStack 1", false))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible setPlayerPhase 2", false));
        ComboNode TachiMartialArtsAuto2 = ComboNode.createNode(() -> WOMAnimations.ENDERBLASTER_ONEHAND_AUTO_2)
                .setPriority(2)
                .setImpactMultiplier(1.5F)
                .setArmorNegation(0.1F)
                .setConvertTime(-0.4F)
                .addCondition(new StackCondition(1, 4))
                .setDamageMultiplier(ValueModifier.multiplier(1.5F))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible consumeStamina 2", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible consumeStack 1", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s irons_spellbooks:rend 1 3", true));
        ComboNode TachiMartialArtsAuto3 = ComboNode.createNode(() -> StarAnimations.LETHAL_SLICING_START)
                .setPriority(2)
                .setPlaySpeed(0.8F)
                .setImpactMultiplier(1.3F)
                .setArmorNegation(0.1F)
                .addCondition(new StackCondition(1, 4))
                .setDamageMultiplier(ValueModifier.multiplier(1.5F))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible consumeStamina 2", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible consumeStack 1", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s irons_spellbooks:rend 1 3", true));


        ComboNode TachiBasicAttack = ComboNode.create()
                .addConditionAnimation(TachiAirSlash)
                .addConditionAnimation(TachiDashAttack)
                .addConditionAnimation(TachiAuto1);

        ComboNode TachiAttack2 = ComboNode.create()
                .addConditionAnimation(TachiAuto2)
                .addConditionAnimation(TachiDashAttack);

        ComboNode TachiAttack3 = ComboNode.create()
                .addConditionAnimation(TachiAuto3)
                .addConditionAnimation(TachiDashAttack);

        ComboNode TachiAttack4 = ComboNode.create()
                .addConditionAnimation(TachiAuto4)
                .addConditionAnimation(TachiDashAttack);

        ComboNode TachiAttack5 = ComboNode.create()
                .addConditionAnimation(TachiAuto5)
                .addConditionAnimation(TachiPowerAuto5)
                .addConditionAnimation(TachiDashAttack);

        ComboNode TachiPowerAttack3 = ComboNode.create()
                .addConditionAnimation(TachiPowerAuto3Success)
                .addConditionAnimation(TachiPowerAuto3Fail);

        ComboNode TachiPowerAttack4 = ComboNode.create()
                .addConditionAnimation(TachiPowerAuto4Fail)
                .addConditionAnimation(TachiPowerAuto4Success);


        Tachiroot.key1(TachiBasicAttack);//基础太刀形态 0-2层技能可用
        TachiAirSlash.key1(TachiBasicAttack);//跳跃攻击后接双形态一段普攻
        TachiDashAttack.key1(TachiBasicAttack);//疾跑攻击后接双形态一段普攻

        TachiPowerAuto4Fail.key1(TachiBasicAttack);
        TachiPowerAuto4Success.key1(TachiBasicAttack);


        TachiAuto1.key1(TachiAttack2);
        TachiAuto1.keyWeaponInnate(TachiMartialArtsAuto1);
        TachiMartialArtsAuto1.key1(TachiAttack3);

        TachiAuto2.key1(TachiAttack3);
        TachiAuto2.keyWeaponInnate(TachiMartialArtsAuto2);
        TachiMartialArtsAuto2.key1(TachiAttack5);
        TachiPowerAuto5.key1(TachiAttack3);

        TachiAuto3.key1(TachiAttack4);
        TachiPowerAttack3.key1(TachiAttack4);
        TachiAuto3.keyWeaponInnate(TachiPowerAttack3);


        TachiAuto4.key1(TachiAttack5);
        TachiAuto4.keyWeaponInnate(TachiMartialArtsAuto3);
        TachiMartialArtsAuto3.key1(TachiPowerAttack4);


        SkillManager.register(com.arc.arc.skill.StellarisTachi::new, com.arc.arc.skill.StellarisTachi.createComboBasicAttack().setCombo(Tachiroot).setShouldDrawGui(true), ArcMod.MOD_ID, "combo0");

        ComboNode UchigatanaPowerroot = ComboNode.create();
        ComboNode UchigatanaPowerJumpAttack = ComboNode.createNode(() -> Animations.UCHIGATANA_AIR_SLASH)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "effect give @s arc:hit_counter 10", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible setPlayerPhase 1", false))
                .setPriority(4)
                .addCondition(new JumpCondition());
        ComboNode UchigatanaPowerdashAttack = ComboNode.createNode(() -> Animations.UCHIGATANA_DASH)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "effect give @s arc:hit_counter 10", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible setPlayerPhase 1", false))
                .setPriority(4)
                .addCondition(new SprintingCondition());

        ComboNode UchigatanaPowerauto1 = ComboNode.createNode(() -> WOMAnimations.ENDERBLASTER_ONEHAND_AUTO_3)
                .setDamageMultiplier(ValueModifier.multiplier(0.7F))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "effect give @s arc:hit_counter 10", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible setPlayerPhase 1", false))
                .setStunTypeModifier(StunType.LONG)
                .setPriority(1);

        ComboNode UchigatanaPowerauto2 = ComboNode.createNode(() -> Animations.UCHIGATANA_AUTO1)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible setPlayerPhase 1", false));

        ComboNode UchigatanaPowerauto3 = ComboNode.createNode(() -> Animations.UCHIGATANA_AUTO2)
                .setPlaySpeed(0.9F)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible setPlayerPhase 1", false));

        ComboNode UchigatanaPowerauto4 = ComboNode.createNode(() -> Animations.UCHIGATANA_AUTO3)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible setPlayerPhase 1", false));

        ComboNode UchigatanaPowerauto5 = ComboNode.createNode(() -> Animations.BATTOJUTSU)
                .setDamageMultiplier(ValueModifier.multiplier(0.5F))
                .setStunTypeModifier(StunType.HOLD)
                .addTimeEvent(new TimeStampedEvent(0.251F, entityPatch -> {
                    entityPatch.playSound(EpicFightSounds.SWORD_IN, 0, 0);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.25F, "invincible setPlayerPhase 1", false))
                .setConvertTime(-0.2F)
                .setPlaySpeed(1.4F);

        ComboNode UchigatanaPowerSheathedAttack = ComboNode.createNode(() -> Animations.UCHIGATANA_SHEATHING_AUTO)
                .setDamageMultiplier(ValueModifier.multiplier(0.8F))
                .setPriority(4)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.6F, "invincible setPlayerPhase 1", false))
                .addCondition(new CustomCondition() {
                    @Override
                    public boolean predicate(LivingEntityPatch<?> entityPatch) {
                        if (entityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                            SkillDataManager skillDataManager = serverPlayerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager();
                            if (skillDataManager.hasData(BattojutsuPassive.SHEATH)) {
                                return skillDataManager.getDataValue(BattojutsuPassive.SHEATH);
                            }
                        }
                        return false;
                    }
                });
        ComboNode UchigatanaPowerSheathedDashAttack = ComboNode.createNode(() -> Animations.UCHIGATANA_SHEATHING_DASH)
                .setDamageMultiplier(ValueModifier.multiplier(1F))
                .setPriority(5)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.6F, "invincible setPlayerPhase 1", false))
                .addCondition(new SprintingCondition())
                .addCondition(new CustomCondition() {
                    @Override
                    public boolean predicate(LivingEntityPatch<?> entityPatch) {
                        if (entityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                            SkillDataManager skillDataManager = serverPlayerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager();
                            if (skillDataManager.hasData(BattojutsuPassive.SHEATH)) {
                                return skillDataManager.getDataValue(BattojutsuPassive.SHEATH);
                            }
                        }
                        return false;
                    }
                });
        ComboNode UchigatanaPowerSheathedAirAttack = ComboNode.createNode(() -> Animations.UCHIGATANA_SHEATH_AIR_SLASH)
                .setDamageMultiplier(ValueModifier.multiplier(1F))
                .setPriority(5)
                .addCondition(new JumpCondition())
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible setPlayerPhase 1", false))
                .addCondition(new CustomCondition() {
                    @Override
                    public boolean predicate(LivingEntityPatch<?> entityPatch) {
                        if (entityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                            SkillDataManager skillDataManager = serverPlayerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager();
                            if (skillDataManager.hasData(BattojutsuPassive.SHEATH)) {
                                return skillDataManager.getDataValue(BattojutsuPassive.SHEATH);
                            }
                        }
                        return false;
                    }
                });
        ComboNode UchigatanaPowerSheathedSkill = ComboNode.createNode(() -> Animations.BATTOJUTSU)
                .setDamageMultiplier(ValueModifier.multiplier(0.8F))
                .setPriority(4)
                .setConvertTime(-0.7F)
                .addCondition(new StackCondition(1, 4))
                .setNotCharge(true)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.8F, "invincible consumeStack 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.8F, "invincible setPlayerPhase 1", false))
                .addCondition(new CustomCondition() {
                    @Override
                    public boolean predicate(LivingEntityPatch<?> entityPatch) {
                        if (entityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                            SkillDataManager skillDataManager = serverPlayerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager();
                            if (skillDataManager.hasData(BattojutsuPassive.SHEATH)) {
                                return skillDataManager.getDataValue(BattojutsuPassive.SHEATH);
                            }
                        }
                        return false;
                    }
                });

        ComboNode UchigatanaPowerSheathedSkillDash = ComboNode.createNode(() -> Animations.BATTOJUTSU_DASH)
                .setDamageMultiplier(ValueModifier.multiplier(0.8F))
                .setPriority(5)
                .setConvertTime(-0.6F)
                .addCondition(new StackCondition(1, 4))
                .setNotCharge(true)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.7F, "invincible consumeStack 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.7F, "invincible setPlayerPhase 1", false))
                .addCondition(new SprintingCondition())
                .addCondition(new CustomCondition() {
                    @Override
                    public boolean predicate(LivingEntityPatch<?> entityPatch) {
                        if (entityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                            SkillDataManager skillDataManager = serverPlayerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager();
                            if (skillDataManager.hasData(BattojutsuPassive.SHEATH)) {
                                return skillDataManager.getDataValue(BattojutsuPassive.SHEATH);
                            }
                        }
                        return false;
                    }
                });

        ComboNode UchigatanaPowerSkill = ComboNode.createNode(() -> StarAnimations.FATAL_DRAW)
                .setDamageMultiplier(ValueModifier.multiplier(0.8F))
                .setPriority(4)
                .setConvertTime(-0.6F)
                .setPlaySpeed(1.4F)
                .addCondition(new MobEffectCondition(false, (ArcEffectsRegistry.HIT_COUNTER), 4, 9))
                .addCondition(new StackCondition(1, 2))
                .setNotCharge(true)
                .addTimeEvent(new TimeStampedEvent(0.9F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(Animations.RUSHING_TEMPO3, -0.1F);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.65F, "effect clear @s arc:hit_counter", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s arc:hit_counter 10 8", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.65F, "invincible consumeStack 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.65F, "invincible setPlayerPhase 1", false));

        ComboNode UchigatanaPowerActiveScrap = ComboNode.createNode(() -> WOMAnimations.KATANA_GUARD_HIT)
                .setCanBeInterrupt(false)
                .setCooldown(100)
                .addCondition(new CooldownCondition(false))
                .setPriority(5)
                .setPlaySpeed(1.2F)
                .addCondition(new MobEffectCondition(false, (ArcEffectsRegistry.HIT_COUNTER), 9, 1000000000))
                .addTimeEvent(new TimeStampedEvent(0.1F, (entityPatch) -> {
                    entityPatch.playSound(Sounds.YAMATO_IN, 0, 0);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "effect clear @s arc:hit_counter", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "effect give @s minecraft:strength 10 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "effect give @s irons_spellbooks:rend 5 5", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "effect give @s minecraft:slowness 1 10", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s irons_spellbooks:abyssal_shroud 1 0", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "execute as @p at @s run particle minecraft:enchant ~ ~ ~ 0 0 0 1 50 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "execute as @p at @s rotated ~ 0 run particle minecraft:enchant ^ ^ ^1 0 0 0 1 50 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "execute as @p at @s rotated ~ 36 run particle minecraft:enchant ^ ^ ^1 0 0 0 1 50 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "execute as @p at @s rotated ~ 72 run particle minecraft:enchant ^ ^ ^1 0 0 0 1 50 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "execute as @p at @s rotated ~ 108 run particle minecraft:enchant ^ ^ ^1 0 0 0 1 50 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "execute as @p at @s rotated ~ 144 run particle minecraft:enchant ^ ^ ^1 0 0 0 1 50 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible setPlayerPhase 2", false))
                .addTimeEvent(new TimeStampedEvent(0.09F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(Animations.BIPED_HOLD_UCHIGATANA_SHEATHING, 0.0F);
                }))
                .addTimeEvent(new TimeStampedEvent(0.1F, (entityPatch -> {
                    if (entityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                        SkillDataManager manager = serverPlayerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager();
                        if (manager.hasData(BattojutsuPassive.SHEATH)) {
                            manager.setDataSync(BattojutsuPassive.SHEATH, true, serverPlayerPatch.getOriginal());
                            serverPlayerPatch.modifyLivingMotionByCurrentItem();
                            serverPlayerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getSkill().setConsumptionSynchronize(serverPlayerPatch, 0.0F);
                        }
                    }
                })))
                .addTimeEvent(new TimeStampedEvent(0.15F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(Animations.BIPED_RUN_UCHIGATANA_SHEATHING, 0.0F);
                }));

        ComboNode UchigatanaPowerActiveScrapTest = ComboNode.createNode(() -> Animations.BIPED_STEP_BACKWARD)
                .setCanBeInterrupt(false)
                .setPriority(4)
                .addTimeEvent(new TimeStampedEvent(0.3F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(Animations.BIPED_WALK_UCHIGATANA, 0.0F);
                }))
                .addTimeEvent(new TimeStampedEvent(0.31F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(WOMAnimations.KATANA_GUARD_HIT, 0.0F);
                }))
                .addTimeEvent(new TimeStampedEvent(0.32F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(Animations.BIPED_HOLD_UCHIGATANA_SHEATHING, 0.0F);
                }))
                .addTimeEvent(new TimeStampedEvent(0.33F, (entityPatch -> {
                    if (entityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                        SkillDataManager manager = serverPlayerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager();
                        if (manager.hasData(BattojutsuPassive.SHEATH)) {
                            manager.setDataSync(BattojutsuPassive.SHEATH, true, serverPlayerPatch.getOriginal());
                            serverPlayerPatch.modifyLivingMotionByCurrentItem();
                            serverPlayerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getSkill().setConsumptionSynchronize(serverPlayerPatch, 0.0F);
                        }
                    }
                })))
                .addTimeEvent(new TimeStampedEvent(0.33F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(Animations.BIPED_RUN_UCHIGATANA_SHEATHING, 0.0F);
                }));

        ComboNode UchigatanaPowerActiveScrapAttack = ComboNode.createNode(() -> WOMAnimations.KATANA_AUTO_3)
                .addTimeEvent(new TimeStampedEvent(0.5F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(Animations.RUSHING_TEMPO3, -0.1F);
                }))
                .addTimeEvent(new TimeStampedEvent(0.25F, (entityPatch) -> {
                    entityPatch.playSound(EpicFightSounds.WHOOSH_SHARP, 0, 0);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.36F, "invincible groundSlam @s 1.5 false false false", true))
                .setPlaySpeed(0.85F)
                .setConvertTime(-0.2F)
                .setCanBeInterrupt(false)
                .setDamageMultiplier(ValueModifier.multiplier(1.3F))
                .setPriority(5)
                .setNotCharge(true)
                .setPlaySpeed(0.85F)
                .setConvertTime(-0.2F)
                .addCondition(new PlayerPhaseCondition(2, 2))
                .addCondition(new StackCondition(1, 2))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "effect clear @s arc:hit_counter", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "invincible consumeStack 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "invincible setPlayerPhase 1", false))
                .addCondition(new CustomCondition() {
                    @Override
                    public boolean predicate(LivingEntityPatch<?> entityPatch) {
                        if (entityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                            SkillDataManager skillDataManager = serverPlayerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager();
                            if (skillDataManager.hasData(BattojutsuPassive.SHEATH)) {
                                return skillDataManager.getDataValue(BattojutsuPassive.SHEATH);
                            }
                        }
                        return false;
                    }
                });

        ComboNode UchigatanaPowerActiveScrapSkill = ComboNode.createNode(() -> StarAnimations.FATAL_DRAW)
                .setCanBeInterrupt(false)
                .setStunTypeModifier(StunType.LONG)
                .setDamageMultiplier(ValueModifier.multiplier(1.5F))
                .setPriority(5)
                .setNotCharge(true)
                .setConvertTime(-0.5F)
                .addCondition(new StackCondition(2, 2))
                .addCondition(new PlayerPhaseCondition(2, 2))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.7F, "invincible consumeStack 2", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.7F, "effect clear @s arc:hit_counter", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.7F, "invincible setPlayerPhase 1", false))
                .addTimeEvent(new TimeStampedEvent(0.9F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(StarAnimations.BLADE_RUSH_FINISHER, 0.0F);
                }))
                .addCondition(new CustomCondition() {
                    @Override
                    public boolean predicate(LivingEntityPatch<?> entityPatch) {
                        if (entityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                            SkillDataManager skillDataManager = serverPlayerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager();
                            if (skillDataManager.hasData(BattojutsuPassive.SHEATH)) {
                                return skillDataManager.getDataValue(BattojutsuPassive.SHEATH);
                            }
                        }
                        return false;
                    }
                });
        ComboNode UchigatanaPowerActiveScrapDashAttack = ComboNode.createNode(() -> StarAnimations.FATAL_DRAW_DASH)
                .setCanBeInterrupt(false)
                .setDamageMultiplier(ValueModifier.multiplier(1.5F))
                .setPriority(6)
                .setNotCharge(true)
                .addCondition(new SprintingCondition())
                .setPlaySpeed(1.05F)
                .setConvertTime(-0.7F)
                .addCondition(new PlayerPhaseCondition(2, 2))
                .addCondition(new StackCondition(1, 2))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.8F, "invincible setPlayerPhase 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.8F, "invincible consumeStack 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.8F, "effect clear @s arc:hit_counter", false))
                .addCondition(new CustomCondition() {
                    @Override
                    public boolean predicate(LivingEntityPatch<?> entityPatch) {
                        if (entityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                            SkillDataManager skillDataManager = serverPlayerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager();
                            if (skillDataManager.hasData(BattojutsuPassive.SHEATH)) {
                                return skillDataManager.getDataValue(BattojutsuPassive.SHEATH);
                            }
                        }
                        return false;
                    }
                });
        ComboNode UchigatanaPowerActiveScrapAirAttack = ComboNode.createNode(() -> StarAnimations.FATAL_DRAW)
                .setDamageMultiplier(ValueModifier.multiplier(1.5F))
                .setCanBeInterrupt(false)
                .setNotCharge(true)
                .setPriority(6)
                .addCondition(new JumpCondition())
                .setPlaySpeed(1.05F)
                .setConvertTime(-0.5F)
                .addCondition(new PlayerPhaseCondition(2, 2))
                .addCondition(new StackCondition(1, 4))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.6F, "invincible consumeStack 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.6F, "invincible setPlayerPhase 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.6F, "effect clear @s arc:hit_counter", false))


                .addCondition(new CustomCondition() {
                    @Override
                    public boolean predicate(LivingEntityPatch<?> entityPatch) {
                        if (entityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                            SkillDataManager skillDataManager = serverPlayerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager();
                            if (skillDataManager.hasData(BattojutsuPassive.SHEATH)) {
                                return skillDataManager.getDataValue(BattojutsuPassive.SHEATH);
                            }
                        }
                        return false;
                    }
                });

        ComboNode UchigatanaPowerActiveScrapDashSkill = ComboNode.createNode(() -> WOMAnimations.KATANA_SHEATHED_DASH)
                .setCanBeInterrupt(false)
                .setDamageMultiplier(ValueModifier.multiplier(2F))
                .setPriority(6)
                .addCondition(new SprintingCondition())
                .setNotCharge(true)
                .setPlaySpeed(1.2F)
                .addCondition(new PlayerPhaseCondition(2, 2))
                .addCondition(new StackCondition(2, 2))
                .addTimeEvent(new TimeStampedEvent(0.6F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(StarAnimations.FATAL_DRAW_DASH, -0.7F);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible consumeStack 2", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible setPlayerPhase 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "effect clear @s arc:hit_counter", false))
                .addCondition(new CustomCondition() {
                    @Override
                    public boolean predicate(LivingEntityPatch<?> entityPatch) {
                        if (entityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                            SkillDataManager skillDataManager = serverPlayerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager();
                            if (skillDataManager.hasData(BattojutsuPassive.SHEATH)) {
                                return skillDataManager.getDataValue(BattojutsuPassive.SHEATH);
                            }
                        }
                        return false;
                    }
                });


        ComboNode UchigatanaPowerBasicAttack = ComboNode.create()
                .addConditionAnimation(UchigatanaPowerJumpAttack)
                .addConditionAnimation(UchigatanaPowerdashAttack)
                .addConditionAnimation(UchigatanaPowerauto1)
                .addConditionAnimation(UchigatanaPowerSheathedAttack)
                .addConditionAnimation(UchigatanaPowerSheathedDashAttack)
                .addConditionAnimation(UchigatanaPowerSheathedAirAttack)
                .addConditionAnimation(UchigatanaPowerActiveScrapAttack)
                .addConditionAnimation(UchigatanaPowerActiveScrapDashAttack);

        ComboNode UchigatanaPowerSkills = ComboNode.create()
                .addConditionAnimation(UchigatanaPowerSkill)
                .addConditionAnimation(UchigatanaPowerSheathedSkill)
                .addConditionAnimation(UchigatanaPowerSheathedSkillDash)
                .addConditionAnimation(UchigatanaPowerActiveScrap)
                .addConditionAnimation(UchigatanaPowerActiveScrapDashSkill)
                .addConditionAnimation(UchigatanaPowerActiveScrapSkill);
        ComboNode UchigatanaPowerNormalAttack2 = ComboNode.create()
                .addConditionAnimation(UchigatanaPowerauto2)
                .addConditionAnimation(UchigatanaPowerdashAttack);
        ComboNode UchigatanaPowerNormalAttack3 = ComboNode.create()
                .addConditionAnimation(UchigatanaPowerauto3)
                .addConditionAnimation(UchigatanaPowerdashAttack);
        ComboNode UchigatanaPowerNormalAttack4 = ComboNode.create()
                .addConditionAnimation(UchigatanaPowerauto4)
                .addConditionAnimation(UchigatanaPowerdashAttack);
        ComboNode UchigatanaPowerNormalAttack5 = ComboNode.create()
                .addConditionAnimation(UchigatanaPowerauto5)
                .addConditionAnimation(UchigatanaPowerdashAttack);
        ComboNode UchigatanaPowerNormalAttack4Extend = ComboNode.create()
                .addConditionAnimation(UchigatanaPowerauto5)
                .addConditionAnimation(UchigatanaPowerauto1)
                .addConditionAnimation(UchigatanaPowerdashAttack)
                .addConditionAnimation(UchigatanaPowerJumpAttack);

        ComboNode UchigatanaPowerNormalSkills = ComboNode.create()
                .addConditionAnimation(UchigatanaPowerActiveScrap)
                .addConditionAnimation(UchigatanaPowerSkill);

        ComboNode UchigatanaPowerActiveScrapAfter = ComboNode.create()
                .addConditionAnimation(UchigatanaPowerActiveScrap)
                .addConditionAnimation(UchigatanaPowerSkill);

        ComboNode UchigatanaActiveScarpAttacks = ComboNode.create()
                .addConditionAnimation(UchigatanaPowerActiveScrapAttack)
                .addConditionAnimation(UchigatanaPowerActiveScrapDashAttack)
                .addConditionAnimation(UchigatanaPowerActiveScrapAirAttack);

        ComboNode UchigatanaActiveScarpSkills = ComboNode.create()
                .addConditionAnimation(UchigatanaPowerActiveScrapSkill)
                .addConditionAnimation(UchigatanaPowerActiveScrapDashSkill);


        UchigatanaPowerroot.key1(UchigatanaPowerBasicAttack);//常态跳跃，疾跑，普攻一段，自动收刀居合泛普通攻击
        UchigatanaPowerroot.keyWeaponInnate(UchigatanaPowerSkills);//常态收刀居合攻击以及两种主动收刀
        UchigatanaPowerSkills.keyWeaponInnate(UchigatanaPowerSkill);//
        UchigatanaPowerdashAttack.key1(UchigatanaPowerauto1);//疾跑攻击后接1A
        UchigatanaPowerJumpAttack.key1(UchigatanaPowerauto1);//跳跃攻击后接1A
        UchigatanaPowerauto5.key1(UchigatanaPowerBasicAttack);//普攻五段后重置泛普攻
        UchigatanaPowerauto5.keyWeaponInnate(UchigatanaPowerNormalSkills);

        UchigatanaPowerSheathedAttack.key1(UchigatanaPowerBasicAttack);//自动收刀普通居合攻击后重置普攻
        UchigatanaPowerSheathedDashAttack.keyWeaponInnate(UchigatanaPowerNormalSkills);
        UchigatanaPowerSheathedDashAttack.key1(UchigatanaPowerBasicAttack);//自动收刀冲刺居合攻击后重置普攻
        UchigatanaPowerSheathedDashAttack.keyWeaponInnate(UchigatanaPowerNormalSkills);
        UchigatanaPowerSheathedAirAttack.key1(UchigatanaPowerBasicAttack);//自动收刀跳跃居合攻击后重置普攻
        UchigatanaPowerSheathedAirAttack.keyWeaponInnate(UchigatanaPowerNormalSkills);
        UchigatanaPowerSheathedSkill.key1(UchigatanaPowerBasicAttack);//自动收刀跳跃技能居合攻击后重置普攻
        UchigatanaPowerSheathedSkill.keyWeaponInnate(UchigatanaPowerNormalSkills);
        UchigatanaPowerSheathedSkillDash.key1(UchigatanaPowerBasicAttack);//自动收刀技能跳跃居合攻击后重置普攻
        UchigatanaPowerSheathedSkillDash.keyWeaponInnate(UchigatanaPowerNormalSkills);

        UchigatanaPowerauto1.key1(UchigatanaPowerNormalAttack2);
        UchigatanaPowerauto1.keyWeaponInnate(UchigatanaPowerNormalSkills);//普攻一段后可主动收刀
        UchigatanaPowerauto2.key1(UchigatanaPowerNormalAttack3);
        UchigatanaPowerauto2.keyWeaponInnate(UchigatanaPowerNormalSkills);
        UchigatanaPowerauto3.key1(UchigatanaPowerNormalAttack4);
        UchigatanaPowerauto3.keyWeaponInnate(UchigatanaPowerNormalSkills);
        UchigatanaPowerauto4.key1(UchigatanaPowerNormalAttack5);
        UchigatanaPowerauto4.keyWeaponInnate(UchigatanaPowerNormalSkills);


        UchigatanaPowerActiveScrap.key1(UchigatanaActiveScarpAttacks);
        UchigatanaPowerActiveScrap.keyWeaponInnate(UchigatanaActiveScarpSkills);
        UchigatanaActiveScarpSkills.key1(UchigatanaPowerBasicAttack);


        SkillManager.register(com.arc.arc.skill.UchigatanaPower::new, com.arc.arc.skill.UchigatanaPower.createComboBasicAttack().setCombo(UchigatanaPowerroot).setShouldDrawGui(true), ArcMod.MOD_ID, "combo1");


        ComboNode ArcbladeMiniroot = ComboNode.create();
        ComboNode ArcbladeMiniJump = ComboNode.createNode(() -> StarAnimations.YAMATO_AIRSLASH)
                .setConvertTime(-0.1F)
                .setPlaySpeed(1.3F)
                .addCondition(new JumpCondition())
                .setDamageMultiplier(ValueModifier.multiplier(0.5F))
                .setPriority(4)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "effect give @s arc:hit_counter 10", false));
        ComboNode ArcbladeMiniDash = ComboNode.createNode(() -> StarAnimations.YAMATO_DASH)
                .setConvertTime(-0.1F)
                .setPlaySpeed(1.2F)
                .addCondition(new SprintingCondition())
                .setDamageMultiplier(ValueModifier.multiplier(0.5F))
                .setPriority(4)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "effect give @s arc:hit_counter 10", false));
        ComboNode ArcbladeMiniAuto1 = ComboNode.createNode(() -> StarAnimations.TACHI_TWOHAND_AUTO_1)
                .setPlaySpeed(1.25F)
                .setPriority(1)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "effect give @s arc:hit_counter 10", false));
        ComboNode ArcbladeMiniAuto2 = ComboNode.createNode(() -> StarAnimations.YAMATO_AUTO3)
                .setStunTypeModifier(StunType.SHORT)
                .setPlaySpeed(1.17F);
        ComboNode ArcbladeMiniAuto3 = ComboNode.createNode(() -> StarAnimations.YAMATO_AUTO4)
                .addCondition(new PlayerPhaseCondition(0, 1))
                .addTimeEvent(new TimeStampedEvent(1.2F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(StarAnimations.YAMATO_STEP_FORWARD, 0.0F);
                }))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }))
                .setPlaySpeed(1.4F)
                .setCanBeInterrupt(false)
                .setDamageMultiplier(ValueModifier.multiplier(1.2F))
                .setConvertTime(-0.28F);
        ComboNode ArcbladeMiniPowerAuto3 = ComboNode.createNode(() -> StarAnimations.YAMATO_AUTO4)
                .setNotCharge(true)
                .addCondition(new StackCondition(1, 4))
                .addCondition(new MobEffectCondition(false, (ArcEffectsRegistry.HIT_COUNTER), 4, 999999999))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s arc:hit_counter 10 8", false))
                .addTimeEvent(new TimeStampedEvent(1.15F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(StarAnimations.BLADE_RUSH_FINISHER, 0.0F);
                }))
                .addTimeEvent(new TimeStampedEvent(0.7F, (entityPatch) -> {
                    entityPatch.playSound(EpicFightSounds.WHOOSH_SHARP, 0, 0);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.9F, "invincible groundSlam @s 2 false false false", true))
                .setPlaySpeed(1.4F)
                .setConvertTime(-0.28F)
                .setPriority(2);
        ComboNode ArcbladeMiniAuto4 = ComboNode.createNode(() -> StarAnimations.YAMATO_COUNTER1)
                .setConvertTime(-0.07F)
                .setPlaySpeed(1.03F)
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }))
                .addTimeEvent(new TimeStampedEvent(0.75F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(StarAnimations.FATAL_DRAW, -0.35F);
                }))
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

        ComboNode ArcbladeMiniPowerAuto4 = ComboNode.createNode(() -> StarAnimations.YAMATO_COUNTER1)
                .setConvertTime(-0.07F)
                .setPlaySpeed(1F)
                .setPriority(2)
                .setNotCharge(true)
                .addCondition(new StackCondition(1, 4))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s arc:hit_counter 10 8", false))
                .addCondition(new MobEffectCondition(false, (ArcEffectsRegistry.HIT_COUNTER), 9, 99999999))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "effect clear @s arc:hit_counter", false))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {
                    entityPatch.playSound(EpicFightSounds.EVISCERATE, 0, 0);
                }))
                .addTimeEvent(new TimeStampedEvent(1F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(StarAnimations.FATAL_DRAW_DASH, -0.6F);
                }))
                .setDamageMultiplier(ValueModifier.multiplier(2F))
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


        ComboNode ArcbladeMiniSkill = ComboNode.createNode(() -> StarAnimations.YAMATO_POWER1)
                .setPriority(4)
                .setConvertTime(-0.1F)
                .setPlaySpeed(1.3F)
                .setNotCharge(true)
                .setCanBeInterrupt(false)
                .addCondition(new StackCondition(1, 4))
                .addTimeEvent(new TimeStampedEvent(0.3F, entityPatch -> {
                    entityPatch.playSound(EpicFightSounds.WHOOSH_SHARP, 0, 0);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.15F, "invincible groundSlam @s 2 false false false", true))
                .addTimeEvent(new TimeStampedEvent(0.8F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(Animations.RUSHING_TEMPO3, 0.1F);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "invincible consumeStack 1", false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s arc:hit_counter 10 8", false))
                .setCanBeInterrupt(false);


        ComboNode ArcbladeMiniGP = ComboNode.createNode(() -> StarAnimations.YAMATO_STEP_FORWARD)
                .setPriority(5)
                .setPlaySpeed(1.3F)
                .addCondition(new StackCondition(1, 8))
                .setCanBeInterrupt(false)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible consumeStack 1", false))
                .addDodgeSuccessEvent(new BiEvent(((entityPatch, entity) -> entityPatch.playSound(Sounds.FORESIGHT, 0, 0))))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("invincible entityAfterImage @s", true))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("invincible consumeStack -2", false))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("invincible setPlayerPhase 2", false));


        ComboNode ArcbladeMiniGP1 = ComboNode.createNode(() -> StarAnimations.FATAL_DRAW)
                .addCondition(new StackCondition(1, 4))
                .addCondition(new PlayerPhaseCondition(2, 2))
                .addCondition(new DodgeSuccessCondition())
                .setNotCharge(true)
                .setCanBeInterrupt(false)
                .setPlaySpeed(1.05F)
                .setConvertTime(-0.5F)
                .setPriority(3)
                .setDamageMultiplier(ValueModifier.multiplier(1.2F))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "invincible consumeStack 1", false))
                .addTimeEvent(new TimeStampedEvent(0.8F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(Animations.RUSHING_TEMPO3, 0.0F);
                }))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.6F, "invincible groundSlam @s 1.5 false false false", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.6F, "invincible setPlayerPhase 1", false));

        ComboNode ArcbladeMiniGP2 = ComboNode.createNode(() -> WOMAnimations.AGONY_PLUNGE_FORWARD)
                .addCondition(new PlayerPhaseCondition(2, 2))
                .addCondition(new StackCondition(2, 4))
                .addCondition(new MobEffectCondition(false, (ArcEffectsRegistry.HIT_COUNTER), 9, 999999999))
                .addCondition(new DodgeSuccessCondition())
                .setCanBeInterrupt(false)
                .setNotCharge(true)
                .setPriority(4)
                .setPlaySpeed(1.5F)
                .setDamageMultiplier(ValueModifier.multiplier(2F))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "invincible consumeStack 2", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "effect clear @s arc:hit_counter", false))
                .addTimeEvent(new TimeStampedEvent(1.3F, (entityPatch) -> {
                    entityPatch.playAnimationSynchronized(StarAnimations.YAMATO_COUNTER1, 0.1F);
                }))
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
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.3F, "particle minecraft:explosion ~ ~1.5 ~ 0 1 0 1 1 force", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.3F, "playsound minecraft:entity.generic.explode ambient @s ~ ~ ~ 5", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(1.4F, "summon minecraft:lightning_bolt ~ ~ ~", true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "invincible setPlayerPhase 1", false));


        ComboNode ArcbladeMiniBasicAttack = ComboNode.create()
                .addConditionAnimation(ArcbladeMiniAuto1)
                .addConditionAnimation(ArcbladeMiniJump)
                .addConditionAnimation(ArcbladeMiniDash);

        ComboNode ArcbladeMiniAttack2 = ComboNode.create()
                .addConditionAnimation(ArcbladeMiniDash)
                .addConditionAnimation(ArcbladeMiniAuto2);

        ComboNode ArcbladeMiniAttack3 = ComboNode.create()
                .addConditionAnimation(ArcbladeMiniDash)
                .addConditionAnimation(ArcbladeMiniAuto3)
                .addConditionAnimation(ArcbladeMiniPowerAuto3);

        ComboNode ArcbladeMiniAttack4 = ComboNode.create()
                .addConditionAnimation(ArcbladeMiniDash)
                .addConditionAnimation(ArcbladeMiniAuto4)
                .addConditionAnimation(ArcbladeMiniPowerAuto4);

        ComboNode ArcbladeMiniGPAttack = ComboNode.create()
                .addConditionAnimation(ArcbladeMiniGP1)
                .addConditionAnimation(ArcbladeMiniGP2)
                .addConditionAnimation(ArcbladeMiniAuto3);


        ArcbladeMiniroot.key1(ArcbladeMiniBasicAttack);
        ArcbladeMiniroot.keyWeaponInnate(ArcbladeMiniSkill);
        ArcbladeMiniJump.key1(ArcbladeMiniAuto1);
        ArcbladeMiniDash.key1(ArcbladeMiniAuto1);
        ArcbladeMiniPowerAuto4.key1(ArcbladeMiniAuto1);
        ArcbladeMiniSkill.key1(ArcbladeMiniAuto3);

        ArcbladeMiniAuto1.key1(ArcbladeMiniAttack2);
        ArcbladeMiniAuto1.keyWeaponInnate(ArcbladeMiniSkill);
        ArcbladeMiniAuto2.key1(ArcbladeMiniAttack3);
        ArcbladeMiniAuto2.keyWeaponInnate(ArcbladeMiniGP);
        ArcbladeMiniGP.key1(ArcbladeMiniAuto3);
        ArcbladeMiniGP.key1(ArcbladeMiniGPAttack);
        ArcbladeMiniGP1.key1(ArcbladeMiniAttack3);
        ArcbladeMiniGP2.key1(ArcbladeMiniAttack3);
        ArcbladeMiniAuto3.key1(ArcbladeMiniAttack4);
        ArcbladeMiniAuto3.keyWeaponInnate(ArcbladeMiniSkill);
        ArcbladeMiniPowerAuto3.key1(ArcbladeMiniAttack4);
        ArcbladeMiniAuto4.key1(ArcbladeMiniAuto1);
        ArcbladeMiniAuto4.keyWeaponInnate(ArcbladeMiniSkill);


        SkillManager.register(com.arc.arc.skill.ArcbladeMini::new, com.arc.arc.skill.ArcbladeMini.createComboBasicAttack().setCombo(ArcbladeMiniroot).setShouldDrawGui(true), ArcMod.MOD_ID, "combo3");
    }

    @SubscribeEvent
    public static void BuildSkills(SkillBuildEvent event) {
        StellarisTachi = event.build(ArcMod.MOD_ID, "combo0");
        UchigatanaPower = event.build(ArcMod.MOD_ID, "combo1");
        Arcblade = event.build(ArcMod.MOD_ID, "combo2");
        ArcbladeMini = event.build(ArcMod.MOD_ID, "combo3");
        //注意和上面对应上
    }

}
