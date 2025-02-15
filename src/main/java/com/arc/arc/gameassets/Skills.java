package com.arc.arc.gameassets;

import com.arc.arc.skill.ArcbladeSkill;
import com.arc.arc.skill.Demo1;
import com.dfdyz.epicacg.registry.MyAnimations;
import com.guhao.star.efmex.StarAnimations;
import com.guhao.star.regirster.Sounds;
import com.p1nero.invincible.api.events.BiEvent;
import com.p1nero.invincible.api.events.TimeStampedEvent;
import com.p1nero.invincible.conditions.*;
import com.p1nero.invincible.skill.api.ComboNode;
import com.arc.arc.ArcMod;
import com.arc.arc.skill.Demo2;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.data.reloader.SkillManager;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.skill.*;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.StunType;

@Mod.EventBusSubscriber(modid = ArcMod.MOD_ID)
public class Skills {
    public static Skill Arc;
    public static Skill Arc1;
    public static Skill Arcblade;

    public static void registerSkills() {
        ComboNode sw0rdroot = ComboNode.create();
        ComboNode sw0rdjump=ComboNode.createNode(()-> Animations.AXE_AIRSLASH).setPriority(3).addCondition(new JumpCondition());
        ComboNode sw0rddash=ComboNode.createNode(()-> Animations.BATTOJUTSU_DASH).setPriority(3).addCondition(new SprintingCondition()).setConvertTime(-0.3F);
        ComboNode sw0rdbasicAttack=ComboNode.createNode(()-> WOMAnimations.KATANA_AUTO_1).setPriority(1);
        ComboNode sw0rdextend=ComboNode.createNode(()->WOMAnimations.RUINE_AUTO_2).setNotCharge(true).setPriority(2).addCondition(new StackCondition(1,4)).addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F,"invincible consumeStack 1", false));
        ComboNode sw0rdgp =ComboNode.createNode(()->Animations.RUSHING_TEMPO3).setNotCharge(true).setPriority(3).addCondition(new ParrySuccessCondition()).setCanBeInterrupt(false).addCondition(new StackCondition(1,4)).addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F,"invincible consumeStack 1", false));
        ComboNode sw0rddodgegp=ComboNode.createNode(()->WOMAnimations.ENDERBLASTER_ONEHAND_SHOOT_3).setNotCharge(true).setPriority(3).addCondition(new DodgeSuccessCondition()).setCanBeInterrupt(false).addCondition(new StackCondition(0,4)).addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack -1", false));
        ComboNode sw0rddodgegp1=ComboNode.createNode(()->WOMAnimations.RUINE_AUTO_4).setPriority(4).setNotCharge(true).addCondition(new DodgeSuccessCondition()).setCanBeInterrupt(false).addCondition(new StackCondition(1,4)).addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack 1", false));
        ComboNode GP=ComboNode.createNode(()->WOMAnimations.ENDERSTEP_BACKWARD).setPriority(3).setConvertTime(-0.2F).addCondition(new StackCondition(1,4)).addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "invincible consumeStack 1", false)).addDodgeSuccessEvent(new BiEvent(((entityPatch, entity) -> entityPatch.playSound(EpicFightSounds.ENTITY_MOVE,0,0)))).addDodgeSuccessEvent(BiEvent.createBiCommandEvent("invincible entityAfterImage @s",true));;
        ComboNode aa=ComboNode.createNode(()-> WOMAnimations.KATANA_AUTO_2);
        ComboNode aaa=ComboNode.createNode(()-> WOMAnimations.KATANA_AUTO_3);
        ComboNode a=ComboNode.create().addConditionAnimation(sw0rdjump)
                  .addConditionAnimation(sw0rddash)
                  .addConditionAnimation(sw0rdbasicAttack)
                  .addConditionAnimation(sw0rddodgegp);
        ComboNode b=ComboNode.create().addConditionAnimation(sw0rdgp)
                                      .addConditionAnimation(GP)
                                      .addConditionAnimation(sw0rddodgegp1);

        sw0rdroot.key1(a);//无条件使用普攻一段,跳劈，跑攻
        sw0rdroot.key2(b);//常态按技能后瞬步
        GP.key1(sw0rddodgegp);//若后瞬步为极限闪避，则GP成功，再次按下KEY1触发追击1,恢复一层技能
        GP.key2(sw0rddodgegp1);//若后瞬步为极限闪避，则GP成功，再次按下KEY2触发追击2
        GP.key2(b);//瞬步后可衔接瞬步
        sw0rdjump.key1(a);//跳劈后接普攻一段
        sw0rddash.key1(a);//跑攻后接普攻一段
        sw0rdgp.key1(a);//触发防反后接普攻一段
        sw0rdextend.key1(a);//额外攻击后接普攻一段
        sw0rdextend.key2(b);//额外攻击后可衔接瞬步
        sw0rddodgegp.key1(aa);//触发瞬步GP追击1后接普攻二段
        sw0rddodgegp1.key1(aaa);//触发瞬步GP2追击后接普攻三段
        sw0rddodgegp.key2(b);//触发瞬步GP追击1后可继续瞬步
        sw0rddodgegp1.key2(b);//触发瞬步GP追击2后可继续瞬步
        sw0rdgp.key2(b);//触发招架反击后可继续瞬步
        a.key1(aa);//普攻一段接普攻二段
        a.key2(b);//普攻一段可衔接瞬步
        aa.key1(aaa);//普攻二段接普攻三段
        aa.key2(b);//普攻二段可衔接瞬步
        aaa.key1(a);//普攻三段后接普攻一段
        aaa.key2(sw0rdextend);//普攻三段后按下KEY2可消耗一层技能额外攻击
        sw0rdroot.key2(b);//成功招架后按KEY2消耗一层技能触发防反






        SkillManager.register(Demo1::new, Demo1.createComboBasicAttack().setCombo(sw0rdroot).setShouldDrawGui(true), ArcMod.MOD_ID, "combo0");

        ComboNode sw1rdroot = ComboNode.create();
        ComboNode sw1rdjump=ComboNode.createNode(()-> Animations.UCHIGATANA_AIR_SLASH).setPriority(3).addCondition(new JumpCondition());
        ComboNode sw1rddash=ComboNode.createNode(()-> Animations.UCHIGATANA_DASH).setPriority(3).addCondition(new JumpCondition());
        ComboNode sw1rdauto1=ComboNode.createNode(()-> Animations.UCHIGATANA_AUTO1).setPriority(1);
        ComboNode sw1rdauto2=ComboNode.createNode(()-> Animations.UCHIGATANA_AUTO2);
        ComboNode sw1rdauto3=ComboNode.createNode(()-> Animations.UCHIGATANA_AUTO3).addCondition(new DownCondition());
        ComboNode sw1rdsheateAttack =ComboNode.createNode(()-> Animations.UCHIGATANA_SHEATHING_AUTO).setPriority(4).addCondition(new CustomCondition(){
            @Override
            public boolean predicate(LivingEntityPatch<?> entityPatch) {
                if(entityPatch instanceof ServerPlayerPatch serverPlayerPatch){
                    SkillDataManager skillDataManager = serverPlayerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager();
                    if(skillDataManager.hasData(BattojutsuPassive.SHEATH)){
                        return skillDataManager.getDataValue(BattojutsuPassive.SHEATH);
                    }
                }
                return false;
            }
        });
        ComboNode sw1rdfocussheathattack =ComboNode.createNode(()-> WOMAnimations.KATANA_AUTO_3).setPriority(3);
        ComboNode sw1rdbasicAttack =ComboNode.create().addConditionAnimation(sw1rdjump).
                addConditionAnimation(sw1rddash).
                addConditionAnimation(sw1rdauto1).
                addConditionAnimation(sw1rdsheateAttack);



        sw1rdroot.key1(sw1rdbasicAttack);
        sw1rdroot.keyWeaponInnate(sw1rdbasicAttack);
        sw1rddash.key1(sw1rdauto1);
        sw1rdjump.key1(sw1rdauto1);
        sw1rdbasicAttack.key1(sw1rdauto1);
        sw1rdsheateAttack.key1(sw1rdauto2);
        sw1rdauto1.key1(sw1rdauto2);
        sw1rdauto2.key1(sw1rdauto3);



        SkillManager.register(Demo2::new, Demo2.createComboBasicAttack().setCombo(sw1rdroot).setShouldDrawGui(true), ArcMod.MOD_ID, "combo1");


        ComboNode Arcbladeroot = ComboNode.create();
        ComboNode ArcJump = ComboNode.createNode(()-> StarAnimations.LONGSWORD_OLD_AIRSLASH)
                .addCondition(new JumpCondition())
                .setDamageMultiplier(ValueModifier.multiplier(0.7F))
                .setPriority(5)
                ;;
        ComboNode ArcDash = ComboNode.createNode(()-> Animations.UCHIGATANA_DASH)
                .addCondition(new SprintingCondition())
                .setDamageMultiplier(ValueModifier.multiplier(0.7F))
                .setPriority(5)
                ;;
        ComboNode ArcAuto1 = ComboNode.createNode(()-> StarAnimations.TACHI_TWOHAND_AUTO_1)
                .setDamageMultiplier(ValueModifier.multiplier(0.6F))
                .setPlaySpeed(1.25F)
                .setPriority(1)
                ;;
        ComboNode ArcAuto2 = ComboNode.createNode(()-> StarAnimations.YAMATO_AUTO3)
                .setStunTypeModifier(StunType.SHORT)
                .setDamageMultiplier(ValueModifier.multiplier(0.6F))
                .setPlaySpeed(1.17F)
                ;;
        ComboNode ArcAuto3 = ComboNode.createNode(()-> StarAnimations.YAMATO_AUTO4)
                .addTimeEvent(new TimeStampedEvent(1.2F, (entityPatch)-> {entityPatch.playAnimationSynchronized(StarAnimations.YAMATO_STEP_FORWARD,0.0F);}))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {entityPatch.playSound(EpicFightSounds.EVISCERATE,0,0);}))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("particle epicacg:dmc_jc_blade_trail ~0 ~0.0 ~0 0.0 1.0 0.0 0.02 1 force @s",false))
                .setPlaySpeed(1.4F)
                .setCanBeInterrupt(false)
                .setDamageMultiplier(ValueModifier.multiplier(1.2F))
                .setConvertTime(-0.28F)
                ;;;;;
        ComboNode ArcAuto4 = ComboNode.createNode(()-> WOMAnimations.KATANA_AUTO_2)
                .setDamageMultiplier(ValueModifier.multiplier(0.5F))
                .setConvertTime(-0.1F)
                .setPlaySpeed(1.3F)
                ;;
        ComboNode ArcAuto5 = ComboNode.createNode(()-> WOMAnimations.ENDERBLASTER_ONEHAND_AUTO_3)
                .setDamageMultiplier(ValueModifier.multiplier(0.3F))
                .setPlaySpeed(1.1F)
               ;;
        ComboNode ArcAuto6 = ComboNode.createNode(()-> StarAnimations.YAMATO_POWER3_FINISH)
                .addTimeEvent(new TimeStampedEvent(0.9F, (entityPatch)-> {entityPatch.playAnimationSynchronized(StarAnimations.YAMATO_STEP_BACKWARD,0.1F);}))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F,"particle epicacg:dmc_jc_blade_trail ~0 ~0.0 ~0 0.0 1.5 0.0 0.00 1 force @s",false))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {entityPatch.playSound(EpicFightSounds.EVISCERATE,0,0);}))
                .setCanBeInterrupt(false)
                .setDamageMultiplier(ValueModifier.multiplier(0.8F))
                .setConvertTime(0.1F)
                .setPlaySpeed(1.1F)
                ;;
        ComboNode ArcdashSkill = ComboNode.createNode(()-> StarAnimations.YAMATO_POWER_DASH)
                .addCondition(new SprintingCondition())
                .addCondition(new StackCondition(1,8))
                .setCanBeInterrupt(false)
                .setConvertTime(-0.2F)
                .setPlaySpeed(1.2F)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "invincible consumeStack 1", false))
                .setDamageMultiplier(ValueModifier.multiplier(0.6F))
                .setPriority(6);

        ComboNode ArcjumpSkill = ComboNode.createNode(()-> WOMAnimations.SOLAR_HORNO)
                .addCondition(new JumpCondition())
                .addCondition(new StackCondition(1,8))
                .setCanBeInterrupt(false)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "invincible consumeStack 1", false))
                .setDamageMultiplier(ValueModifier.multiplier(0.6F))
                .setPriority(6);


        ComboNode Free =ComboNode.createNode(()-> Animations.BIPED_STEP_BACKWARD)
                .addCondition(new StackCondition(1,8))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack 1", false));

        ComboNode ArcbasicAttack =ComboNode.create().addConditionAnimation(ArcJump)
                                                    .addConditionAnimation(ArcDash)
                                                    .addConditionAnimation(ArcAuto1)
                                                    .setPriority(4)
                                                    ;


        Arcbladeroot.key1(ArcbasicAttack);//初始无条件使用1A 疾跑攻击 跳跃攻击
        ArcDash.key1(ArcAuto1);//疾跑攻击后接1A
        ArcdashSkill.key1(ArcAuto2);//疾跑技能后接2A
        ArcJump.key1(ArcAuto1);//跳跃攻击后接1A
        ArcjumpSkill.key1(ArcAuto2);//跳跃技能后接2A
        ArcAuto1.key1(ArcAuto2);//1A后接2A
        ArcAuto2.key1(ArcAuto3);//2A后接3A
        ArcAuto3.key1(ArcAuto4);//3A后接4A
        ArcAuto4.key1(ArcAuto5);//4A后接5A
        ArcAuto5.key1(ArcAuto6);//5A后接6A
        ArcAuto6.key1(ArcbasicAttack);//6A后可使用1A 疾跑攻击 跳跃攻击

        ComboNode ArcGP1=ComboNode.createNode(()->Animations.BIPED_STEP_BACKWARD)
                .setPriority(3)
                .setPlaySpeed(0.9F)
                .setConvertTime(0F)
                .addCondition(new StackCondition(1,8))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack 1", false))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("invincible consumeStack -2",false))
                .addDodgeSuccessEvent(new BiEvent(((entityPatch, entity) -> entityPatch.playSound(Sounds.FORESIGHT,0,0))))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("invincible entityAfterImage @s",true))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.3 0.6",true))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("particle epicacg:dmc_jc_blade_trail ~0 ~0.0 ~0 0.0 1.5 0.0 0.02 1 force @s",false))
                ;;


        ComboNode ArcGP1extendA=ComboNode.createNode(()->WOMAnimations.KATANA_SHEATHED_AUTO_2)
                .setPlaySpeed(1F)
                .setPriority(4)
                .addCondition(new DodgeSuccessCondition())
                .setCanBeInterrupt(false)
                .addCondition(new StackCondition(0,8))
                .setDamageMultiplier(ValueModifier.multiplier(1.5F))
                .addTimeEvent(new TimeStampedEvent(0.32F, (entityPatch)-> {entityPatch.playAnimationSynchronized(WOMAnimations.DODGEMASTER_BACKWARD,0.0F);}))
                .setDamageMultiplier(ValueModifier.multiplier(0.7F));;

        ComboNode ArcGP1extendA1=ComboNode.createNode(()->StarAnimations.YAMATO_COUNTER2)
                .setStunTypeModifier(StunType.SHORT)
                .setConvertTime(-0.2F)
                .setPlaySpeed(1F)
                .setPriority(4)
                .setCanBeInterrupt(false)
                .setDamageMultiplier(ValueModifier.multiplier(2F))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack -1", false))
                .setDamageMultiplier(ValueModifier.multiplier(0.7F));;;




        ComboNode ArcGP1extendS1=ComboNode.createNode(()->StarAnimations.YAMATO_STRIKE2)
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.2 0.5",true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.25F, "invincible consumeStack 1", false))

                .addCondition(new StackCondition(1,8))
                .setNotCharge(true)
                .setPriority(5)
                .setConvertTime(-0.2F)
                .setPlaySpeed(1.2F)
                .setCanBeInterrupt(false)
                .addCondition(new DodgeSuccessCondition())
                .setDamageMultiplier(ValueModifier.multiplier(0.8F));
                ;

        ComboNode ArcGP1extendS2=ComboNode.createNode(()->StarAnimations.YAMATO_POWER3)
                .setConvertTime(-0.1F)
                .setPlaySpeed(1F)
                .setNotCharge(true)
                .setPriority(5)
                .setCanBeInterrupt(false)
                .setDamageMultiplier(ValueModifier.multiplier(0.8F));
                ;

        ComboNode ArcGP1extendS3=ComboNode.createNode(()->StarAnimations.YAMATO_POWER3_REPEAT)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible consumeStack 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible consumeStamina -5", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "effect give @s minecraft:haste 3 2", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "effect @s minecraft:strength 5 3", false))
                .addCondition(new StackCondition(1,8))
                .setNotCharge(true)
                .setPlaySpeed(0.9F)
                .setPriority(5)
                .setCanBeInterrupt(false)
                ;

        ComboNode ArcGP1extendS4=ComboNode.createNode(()->StarAnimations.YAMATO_POWER3_FINISH)
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStack -2", false))
                .setCanBeInterrupt(false)
                ;


        Arcbladeroot.key2(ArcGP1);//常态按技能消耗一层技能后瞬步尝试GP，若为极限闪避则GP成功，则给予敌人1S晕眩
        ArcGP1.key3(Free);//无论GP成功与否，可按key3消耗一层充能使用后跨步重置普攻
        Free.key1(ArcbasicAttack);//key3跨步重置普攻


        ArcGP1.key1(ArcGP1extendA);//后瞬步GP成功后可按KEY1接A追击1
        ArcGP1extendA.key1(ArcGP1extendA1);//后瞬步GP成功后可按KEY1接A追击2
        ArcGP1extendA1.key1(ArcAuto3);//A1追击后接普攻3A
        ArcGP1extendA.key2(ArcGP1);//
        ArcGP1extendA1.key2(ArcGP1);//

        ArcGP1.key2(ArcGP1extendS1);//后瞬步GP成功后可消耗技能按KEY2接S1追击第一段
        ArcGP1extendS1.key2(ArcGP1extendS2);//GP成功发动S1追击后衔接S2第二段追击
        ArcGP1extendS2.key2(ArcGP1extendS3);//S2追击后衔接S3第三段追击
        ArcGP1extendS2.key1(ArcGP1extendS4);//S2追击后提前结算发动S4四段
        ArcGP1extendS3.key2(ArcGP1extendS4);//S3追击后衔接S4第四段追击
        ArcGP1extendS1.key1(ArcAuto3);//S1追击后接普攻3A
        ArcGP1extendS4.key1(ArcAuto4);//S4追击后接普攻4A;
        ArcGP1extendS3.key1(ArcAuto4);//S3追击后接普攻4A;

        ArcGP1extendS4.key2(ArcGP1);//



        ComboNode Arc1AS=ComboNode.createNode(()->StarAnimations.YAMATO_POWER1)
                .setConvertTime(-0.2F)
                .setPlaySpeed(1.5F)
                .setCanBeInterrupt(false)
                .addTimeEvent(new TimeStampedEvent(0.3F,entityPatch ->{entityPatch.playSound(EpicFightSounds.WHOOSH_SHARP,0,0);}))
                .addTimeEvent(new TimeStampedEvent(0.8F, (entityPatch)-> {entityPatch.playAnimationSynchronized(WOMAnimations.DODGEMASTER_BACKWARD,0.0F);}))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "effect give @s minecraft:haste 5 2", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "effect give @s epicfight:stun_immunity 2", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "effect give @s star:really_stun_immunity 2", false))
                .addCondition(new StackCondition(1,8))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "invincible consumeStack 1", false))
                .setDamageMultiplier(ValueModifier.multiplier(1.1F));;;

        ComboNode Arc1AS1=ComboNode.createNode(()->Animations.RUSHING_TEMPO3)
                .setPlaySpeed(1.2F)
                .setCanBeInterrupt(false)
                .addTimeEvent(new TimeStampedEvent(0.0F,entityPatch ->{entityPatch.playSound(EpicFightSounds.WHOOSH_SHARP,0,0);}))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "invincible groundSlam @s 1.5 false false false", true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s epicfight:stun_immunity 3",false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s star:really_stun_immunity 3",false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:resistance 3 3",false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:haste 5 3",false))
                .addCondition(new StackCondition(1,8))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.3F, "invincible consumeStack 1", false))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStack -2",false))
                .setDamageMultiplier(ValueModifier.multiplier(1.2F));;;;

        ComboNode Arc2ASGP2 =ComboNode.createNode(()->StarAnimations.YAMATO_STEP_FORWARD)
                .setPriority(4)
                .setPlaySpeed(1.3F)
                .setConvertTime(0F)
                .addCondition(new StackCondition(1,8))
                .setCanBeInterrupt(false)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.1F, "invincible consumeStack 1", false))
                .addDodgeSuccessEvent(new BiEvent(((entityPatch, entity) -> entityPatch.playSound(Sounds.FORESIGHT,0,0))))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("invincible entityAfterImage @s",true))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("invincible consumeStack -2",true))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 0.8 0.5",true))
                ;


        ComboNode Arc2ASGP2fail =ComboNode.createNode(()->WOMAnimations.ENDERSTEP_FORWARD)
                .setPriority(4)
                .setConvertTime(-0.1F)
                .addCondition(new StackCondition(1,8))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack 1", false));

        ComboNode GP2=ComboNode.create().addConditionAnimation(Arc2ASGP2).addConditionAnimation(Arc2ASGP2fail);


        ComboNode Arc3AS1=ComboNode.createNode(()->StarAnimations.YAMATO_COUNTER1)
                .setPriority(3)
                .setConvertTime(-0.07F)
                .addHitEvent(new BiEvent((entityPatch, entity) -> {entityPatch.playSound(EpicFightSounds.EVISCERATE,0,0);}))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "effect give @2 minecraft:slowness 2 255", true))
                .addTimeEvent(new TimeStampedEvent(0.8F, (entityPatch)-> {entityPatch.playAnimationSynchronized(WOMAnimations.DODGEMASTER_BACKWARD,0.0F);}))
                .addCondition(new StackCondition(1,8))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "invincible consumeStack 1", false))
                .setDamageMultiplier(ValueModifier.multiplier(0.7F));
        ComboNode Arc3AS2=ComboNode.createNode(()->StarAnimations.YAMATO_STEP_FORWARD)
                .addCondition(new StackCondition(1,8))
                .setCanBeInterrupt(false)
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("invincible consumeStack -2",true))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 0.8 0.5",true))
                .addDodgeSuccessEvent(BiEvent.createBiCommandEvent("particle epicacg:dmc_jc_blade_trail ~0 ~0.0 ~0 0.0 1.5 0.0 0.02 1 force @s",false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack -1", false));;;;

        ComboNode Arc4AS=ComboNode.createNode(()->WOMAnimations.KATANA_SHEATHED_DASH)
                .setNotCharge(true)
                .addCondition(new StackCondition(1,8))
                .setConvertTime(-0.1F)
                .setPlaySpeed(1.2F)
                .setDamageMultiplier(ValueModifier.multiplier(2))
                .setDamageMultiplier(ValueModifier.multiplier(0.5F))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 0.3 0.5",true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s minecraft:absorption 10 3", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s star:really_stun_immunity 5 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s epicfight:stun_immunity 5 1", false))
                .addTimeEvent(new TimeStampedEvent(0.6F, (entityPatch)-> {entityPatch.playAnimationSynchronized(StarAnimations.YAMATO_COUNTER2,0.0F);}))
                ;
        ComboNode Arc4AS1=ComboNode.createNode(()->StarAnimations.YAMATO_AUTO4)
                .setConvertTime(-0.4F)
                .setPlaySpeed(1.4F)
                .setCanBeInterrupt(false)
                .addTimeEvent(new TimeStampedEvent(1.3F, (entityPatch)-> {entityPatch.playAnimationSynchronized(StarAnimations.YAMATO_STEP_FORWARD,0.0F);}))
                .addCondition(new StackCondition(1,8))
                .setDamageMultiplier(ValueModifier.multiplier(2F))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack 1", false))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStack -1",false))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:resistance 3 4",false))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {entityPatch.playSound(EpicFightSounds.EVISCERATE,0,0);}));;
                ;

        ComboNode Arc5As=ComboNode.createNode(()->StarAnimations.YAMATO_STRIKE1)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "execute as @s at @s run particle irons_spellbooks:electricity ~ ~0.1 ~ 0.4 0.2 0.4 0.1 32", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "execute as @p at @s run playsound minecraft:item.trident.thunder block @s ~ ~ ~ 0.5 2.0", false))
                .setNotCharge(true)
                .addCondition(new StackCondition(1,8))
                .setDamageMultiplier(ValueModifier.multiplier(2F))
                .setPlaySpeed(1.3F)
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.2 0.5",true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack 1", false));
        ComboNode Arc5As2=ComboNode.createNode(()->WOMAnimations.KATANA_AUTO_1)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "execute as @s at @s run particle irons_spellbooks:electricity ~ ~1.0 ~ 0.1 0.3 0.1 1.0 32", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "particle isleofberk:lightning_aoe_emitter ~ ~1.5 ~ 0 2.5 0 0.1 160 force", false))
                .setNotCharge(true)
                .addCondition(new StackCondition(1,8))
                .setDamageMultiplier(ValueModifier.multiplier(1.5F))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {entityPatch.playSound(EpicFightSounds.EVISCERATE,0,0);}))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack 1", false));
        ComboNode Arc5As3=ComboNode.createNode(()->WOMAnimations.KATANA_AUTO_2)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "execute as @s at @s run particle irons_spellbooks:electricity ~ ~1.0 ~ 0.1 0.3 0.1 1.0 32", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.2F, "execute as @p at @s run playsound minecraft:item.trident.thunder block @s ~ ~ ~ 0.5 1.75", false))
                .setNotCharge(true)
                .addCondition(new StackCondition(1,8))
                .setDamageMultiplier(ValueModifier.multiplier(2))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.2 0.5",true))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {entityPatch.playSound(EpicFightSounds.EVISCERATE,0,0);}))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack 1", false));
        ComboNode Arc5As4=ComboNode.createNode(()->WOMAnimations.KATANA_AUTO_3)
                .setConvertTime(0.1F)
                .setPlaySpeed(1F)
                .addCondition(new StackCondition(1,8))
                .setDamageMultiplier(ValueModifier.multiplier(3))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {entityPatch.playSound(EpicFightSounds.EVISCERATE,0,0);}))

                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack 1", false))
                .addHitEvent(BiEvent.createBiCommandEvent("particle epicacg:dmc_jc_blade_trail ~0 ~0.0 ~0 0.0 1.5 0.0 0.02 1 force @s",false))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStack -4",false))
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
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.7F, "playsound minecraft:entity.generic.explode ambient @s ~ ~ ~ 5", false))




                ;
        ;

        ComboNode Arc6As=ComboNode.createNode(()-> MyAnimations.DMC5_V_JC)
                .addCondition(new StackCondition(8,8))
                .setCooldown(3600)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F,"effect give @s cataclysm:stun 3",true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack 8", false));;

        ArcAuto1.key2(Arc1AS);//普攻一段派生一段
        Arc1AS.key2(Arc1AS1);//派生一段接二段
        Arc1AS.key1(ArcAuto2);//一段派生接2A
        Arc1AS1.key1(ArcAuto2);//二段派生接2A
        ArcAuto2.key2(GP2);//普攻二段派生，特殊GP
        GP2.key3(Free);//普攻二段派生失败紧急逃生
        ArcAuto3.key2(Arc3AS1);//普攻三段派生一段
        Arc3AS1.key2(Arc3AS2);//普攻三段派生二段
        Arc3AS1.key1(ArcAuto4);//普攻三段派生一段接4A
        Arc3AS2.key1(ArcAuto2);//普攻三段派生二段接1A
        ArcAuto4.key2(Arc4AS);//普攻四段派生一段
        Arc4AS.key2(Arc4AS1);//普攻四段派生二段
        Arc4AS.key1(ArcAuto5);//普攻四段派生一段接5A
        Arc4AS1.key1(ArcAuto5);//普攻四段派生二段接5A
        ArcAuto5.key2(Arc5As);//普攻五段派生1 阎魔刀2A
        Arc5As.key2(Arc5As2);//普攻五段派生2 人斩1A
        Arc5As2.key2(Arc5As3);//普攻五段派生3 人斩2A
        Arc5As3.key2(Arc5As4);//普攻五段派生4 人斩3A
        Arc5As.key1(ArcAuto6);//普攻五段派生1接1A
        Arc5As2.key1(ArcAuto6);//普攻五段派生2接2A
        Arc5As3.key1(ArcAuto6);//普攻五段派生3接3A
        Arc5As4.key1(ArcAuto6);//普攻五段派生4接6A
        ArcAuto6.key2(Arc6As);//普攻六段派生，次元斩绝
        Arc6As.key1(ArcbasicAttack);//普攻六段派生重置平A
        ArcGP1extendS4.key3(Arc2ASGP2);//GP1S追击后KEY3发动GP2
        ArcGP1extendA1.key3(Arc2ASGP2);//GP1A追击后KEY3发动GP2


        ComboNode ArcGP2extendAttack1 =ComboNode.createNode(()->WOMAnimations.AGONY_CLAWSTRIKE)
                .setPriority(4)
                .setNotCharge(true)
                .setCanBeInterrupt(false)
                .addCondition(new DodgeSuccessCondition())
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:levitation 1 7",true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:slow_falling 2",true))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 0.8 0.5",true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s cataclysm:stun 6",true))
                .addCondition(new StackCondition(1,8))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s star:really_stun_immunity 5 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s epicfight:stun_immunity 5 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s minecraft:resistance 6 4", false))
                .setDamageMultiplier(ValueModifier.multiplier(0.5F));;


        ComboNode ArcGP2extendAttack2 =ComboNode.createNode(()->WOMAnimations.AGONY_PLUNGE_FORWARD)
                .setNotCharge(true)
                .addTimeEvent(new TimeStampedEvent(0.43F, (entityPatch)-> {entityPatch.playAnimationSynchronized(Animations.BIPED_WALK_LONGSWORD,0.1F);}))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 0.3 0.5",true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0F, "effect give @s minecraft:slow_falling 2", false))
                .setDamageMultiplier(ValueModifier.multiplier(0.5F))
                ;;;

        ComboNode ArcGP2extendAttack3 =ComboNode.createNode(()->WOMAnimations.TORMENT_BERSERK_AUTO_1)
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 0.3 0.5",true))
                .setNotCharge(true)
                .setConvertTime(0.1F)
                .setPlaySpeed(1.8F)
                .setDamageMultiplier(ValueModifier.multiplier(1.1F))
                ;

        ComboNode ArcGP2extendAttack4 =ComboNode.createNode(()->WOMAnimations.TORMENT_BERSERK_AUTO_2)
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 0.3 0.5",true))
                .setNotCharge(true)
                .setDamageMultiplier(ValueModifier.multiplier(1.1F))
                .setPlaySpeed(1.8F);

        ComboNode ArcGP2extendAttack5 =ComboNode.createNode(()->WOMAnimations.MOONLESS_AUTO_2)
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 0.3 0.5",true))
                .addHitEvent(BiEvent.createBiCommandEvent("particle epicacg:dmc_jc_blade_trail ~0 ~0.0 ~0 0.0 1.0 0.0 0.02 1 force @s",false))
                .setNotCharge(true)
                .addTimeEvent(new TimeStampedEvent(0.7F, (entityPatch)-> {entityPatch.playAnimationSynchronized(Animations.BIPED_WALK_LONGSWORD,0.1F);}))
                .setPlaySpeed(1.8F)
                .setDamageMultiplier(ValueModifier.multiplier(1.4F))
                ;

        ComboNode ArcGP2extendAttack6 =ComboNode.createNode(()->WOMAnimations.SOLAR_HORNO)
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 0.3 0.5",true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0F, "effect give @s minecraft:slow_falling 2", false))
                .setNotCharge(true)
                .setConvertTime(-0.1F)
                .setDamageMultiplier(ValueModifier.multiplier(0.8F))
                .setPlaySpeed(1.35F);

        ComboNode ArcGP2extendAttack7 =ComboNode.createNode(()->StarAnimations.YAMATO_COUNTER1)
                .addHitEvent(new BiEvent((entityPatch, entity) -> {entityPatch.playSound(EpicFightSounds.EVISCERATE,0,0);}))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 0.3 0.5",true))
                .setNotCharge(true)
                .setDamageMultiplier(ValueModifier.multiplier(1.1F))
                .setPlaySpeed(1F);

        ComboNode ArcGP2extendAttack8 =ComboNode.createNode(()->StarAnimations.BLADE_RUSH_FINISHER)
                .addHitEvent(new BiEvent((entityPatch, entity) -> {entityPatch.playSound(EpicFightSounds.EVISCERATE,0,0);}))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 0.3 0.5",true))
                .setNotCharge(true)
                .setDamageMultiplier(ValueModifier.multiplier(0.8F))
                .setPlaySpeed(1.1F);

        ;;


        Arc2ASGP2.key2(ArcGP2extendAttack1);//特殊GP成功后消耗一层技能按KEY1挑飞敌人，消耗一层技能,给予漂浮

        Arc2ASGP2.key3(Free);//特殊GP无论成功与否可按key3消耗充能，后撤重置普攻
        ArcGP2extendAttack1.key2(ArcGP2extendAttack2);//
        ArcGP2extendAttack2.key2(ArcGP2extendAttack3);//
        ArcGP2extendAttack3.key2(ArcGP2extendAttack4);//
        ArcGP2extendAttack4.key2(ArcGP2extendAttack5);//
        ArcGP2extendAttack5.key2(ArcGP2extendAttack6);//
        ArcGP2extendAttack6.key2(ArcGP2extendAttack7);//
        ArcGP2extendAttack7.key2(ArcGP2extendAttack8);//
        ArcGP2extendAttack8.key1(ArcAuto5);//空中下砸落地后KEY1接普攻5A
        ArcGP2extendAttack8.key2(ArcGP1);//



        ComboNode ArcGP2extendSkill1 =ComboNode.createNode(()->WOMAnimations.KATANA_SHEATHED_DASH)
                .setPlaySpeed(1.3F)
                .setPriority(4)
                .setNotCharge(true)
                .setCanBeInterrupt(false)
                .addCondition(new DodgeSuccessCondition())
                .addCondition(new StackCondition(1,8))
                .setDamageMultiplier(ValueModifier.multiplier(1.6F))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack 1", false))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.3 0.5",true))
                .addTimeEvent(new TimeStampedEvent(0.5F, (entityPatch)-> {entityPatch.playAnimationSynchronized(Animations.BIPED_WALK_LONGSWORD,0.1F);}));;;;
                ;

        ComboNode ArcGP2extendSkill2 =ComboNode.createNode(()->StarAnimations.TACHI_TWOHAND_AUTO_1)
                .setDamageMultiplier(ValueModifier.multiplier(0.7F))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.3 0.5",true))
                .setNotCharge(true)
                .setPlaySpeed(1.3F)
                ;

        ComboNode ArcGP2extendSkill3 =ComboNode.createNode(()->WOMAnimations.TORMENT_BERSERK_AUTO_1)
                .setDamageMultiplier(ValueModifier.multiplier(1.2F))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.3 0.5",true))
                .setNotCharge(true)
                .setPlaySpeed(1.1F)
                ;

        ComboNode ArcGP2extendSkill4 =ComboNode.createNode(()->StarAnimations.TACHI_TWOHAND_AUTO_2)
                .setDamageMultiplier(ValueModifier.multiplier(0.7F))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.3 0.5",true))
                .setNotCharge(true)
                .setPlaySpeed(1.3F)
                ;
        ComboNode ArcGP2extendSkill5 =ComboNode.createNode(()->WOMAnimations.TORMENT_BERSERK_AUTO_2)
                .setDamageMultiplier(ValueModifier.multiplier(1.2F))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.3 0.5",true))
                .setNotCharge(true)
                .setPlaySpeed(1.1F)
                ;
        ComboNode ArcGP2extendSkill6 =ComboNode.createNode(()->StarAnimations.YAMATO_AUTO4)
                .setDamageMultiplier(ValueModifier.multiplier(1.2F))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {entityPatch.playSound(EpicFightSounds.EVISCERATE,0,0);}))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.3 0.5",true))
                .addHitEvent(BiEvent.createBiCommandEvent("particle epicacg:dmc_jc_blade_trail ~0 ~0.0 ~0 0.0 1.0 0.0 0.02 1 force @s",false))
                .addTimeEvent(new TimeStampedEvent(1F, (entityPatch)-> {entityPatch.playAnimationSynchronized(Animations.BIPED_WALK_LONGSWORD,0.0F);}))
                .setNotCharge(true)
                .setPlaySpeed(1.4F)
                .setConvertTime(-0.2F)
                ;

        ComboNode ArcGP2extendSkill7 =ComboNode.createNode(()->StarAnimations.BLADE_RUSH_FINISHER)
                .setDamageMultiplier(ValueModifier.multiplier(1.2F))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 0.3 0.5",true))
                .setNotCharge(true)
                .setPlaySpeed(1F)
                ;

        Arc2ASGP2.key1(ArcGP2extendSkill1);//

        ArcGP2extendSkill1.key1(ArcGP2extendSkill2);//
        ArcGP2extendSkill2.key1(ArcGP2extendSkill3);//
        ArcGP2extendSkill3.key1(ArcGP2extendSkill4);//
        ArcGP2extendSkill4.key1(ArcGP2extendSkill5);//
        ArcGP2extendSkill5.key1(ArcGP2extendSkill6);//
        ArcGP2extendSkill6.key1(ArcGP2extendSkill7);//
        ArcGP2extendSkill7.key1(ArcAuto4);//
        ArcGP2extendSkill7.key2(ArcGP1);//

        ComboNode ArcParryStrike1 =ComboNode.createNode(()->StarAnimations.YAMATO_COUNTER1)
                .addCondition(new ParrySuccessCondition())
                .addCondition(new StackCondition(1,8))
                .setCanBeInterrupt(false)
                .setNotCharge(true)
                .setStunTypeModifier(StunType.SHORT)
                .setDamageMultiplier(ValueModifier.multiplier(0.5F))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s minecraft:absorption 5 2", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s epicfight:stun_immunity 5 2", false))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {entityPatch.playSound(EpicFightSounds.EVISCERATE,0,0);}));


        ComboNode ArcParryStrike2 =ComboNode.createNode(()->StarAnimations.YAMATO_COUNTER2)
                .setDamageMultiplier(ValueModifier.multiplier(0.5F))
                .setCanBeInterrupt(false)
                .setStunTypeModifier(StunType.SHORT)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s minecraft:haste 5 2", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s minecraft:resistance 3 3", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s star:really_stun_immunity 3 2", false));;

        ComboNode ArcAdvancedAttack =ComboNode
                .create().addConditionAnimation(ArcdashSkill)
                .addConditionAnimation(ArcjumpSkill)
                .addConditionAnimation(ArcParryStrike1)
                .setPriority(4);

        Arcbladeroot.key3(ArcAdvancedAttack);//常态按下key3招架反击
        ArcdashSkill.key3(ArcAdvancedAttack);//疾跑技能后可接进阶攻击
        ArcjumpSkill.key3(ArcAdvancedAttack);//跳跃技能后可接进阶攻击
        ArcParryStrike1.key3(ArcParryStrike2);//招架反击1段后按下key3使用二段
        ArcParryStrike1.key1(ArcAuto2);//招架反击1段后按下key1，接普攻二段
        ArcParryStrike1.key2(ArcGP1);//招架反击1段后按下key2，使用GP1
        ArcParryStrike2.key1(ArcAuto3);//招架反击2段后按下key1，接普攻三段
        ArcParryStrike2.key2(Arc2ASGP2);//招架反击2段后按下key2，使用GP2
        ArcParryStrike2.key3(ArcParryStrike1);//招架反击2段后可继续触发招架反击

        ComboNode ArcGP3Skill1 =ComboNode.createNode(()->WOMAnimations.AGONY_CLAWSTRIKE)
                .setPriority(5)
                .setNotCharge(true)
                .setCanBeInterrupt(false)
                .addCondition(new DodgeSuccessCondition())
                .setDamageMultiplier(ValueModifier.multiplier(0.5F))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:levitation 1 7",true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:slow_falling 3",true))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 0.8 0.5",true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s cataclysm:stun 3",true))
                .addCondition(new StackCondition(1,8))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s star:really_stun_immunity 3 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s epicfight:stun_immunity 3 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s minecraft:resistance 3 4", false));;


        ComboNode ArcGP3Skill2 =ComboNode.createNode(()->WOMAnimations.AGONY_PLUNGE_FORWARD)
                .setNotCharge(true)
                .setDamageMultiplier(ValueModifier.multiplier(0.5F))
                .addTimeEvent(new TimeStampedEvent(0.4F, (entityPatch)-> {entityPatch.playAnimationSynchronized(Animations.BIPED_WALK_LONGSWORD,0.1F);}))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.3 0.5",true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0F, "effect give @s minecraft:slow_falling 4", false));

        ComboNode ArcGP3Skill3 =ComboNode.createNode(()->StarAnimations.YAMATO_COUNTER1)
                .setDamageMultiplier(ValueModifier.multiplier(2F))
                .setConvertTime(0.15F)
                .setPlaySpeed(1.2F)
                .setNotCharge(true)
                .addHitEvent(new BiEvent((entityPatch, entity) -> {entityPatch.playSound(EpicFightSounds.EVISCERATE,0,0);}))
                .addHitEvent(BiEvent.createBiCommandEvent("summon minecraft:lightning_bolt ~ ~ ~",true))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.3 0.5",true))
                .addHitEvent(BiEvent.createBiCommandEvent("particle epicacg:dmc_jc_blade_trail ~0 ~0.0 ~0 0.0 1 0.0 0.02 1 force @s",false))
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

        ComboNode ArcGP3Skill4 =ComboNode.createNode(()->WOMAnimations.AGONY_PLUNGE_FORWARD)
                .setConvertTime(-0.3F)
                .addTimeEvent(new TimeStampedEvent(1.3F, (entityPatch)-> {entityPatch.playAnimationSynchronized(Animations.BIPED_WALK_LONGSWORD,0.0F);}))
                .setNotCharge(true)
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 2 0.5",true))
                ;

        ComboNode ArcGP3Skill5 =ComboNode.createNode(()->WOMAnimations.KATANA_SHEATHED_AUTO_2)
                .setDamageMultiplier(ValueModifier.multiplier(2F))
                .setConvertTime(-0.1F)
                .addTimeEvent(new TimeStampedEvent(0.5F, (entityPatch)-> {entityPatch.playAnimationSynchronized(Animations.BIPED_WALK_LONGSWORD,0.0F);}))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {entityPatch.playSound(EpicFightSounds.EVISCERATE,0,0);}))
                .addHitEvent(BiEvent.createBiCommandEvent("particle epicacg:dmc_jc_blade_trail ~0 ~0.0 ~0 0.0 2.0 0.0 0.02 1 force @s",false));

        ComboNode ArcGP3Skill6 =ComboNode.createNode(()->WOMAnimations.KATANA_SHEATHED_AUTO_2)
                .setDamageMultiplier(ValueModifier.multiplier(2F))
                .addTimeEvent(new TimeStampedEvent(0.5F, (entityPatch)-> {entityPatch.playAnimationSynchronized(Animations.BIPED_WALK_LONGSWORD,0.0F);}))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {entityPatch.playSound(EpicFightSounds.EVISCERATE,0,0);}))
                .addHitEvent(BiEvent.createBiCommandEvent("particle epicacg:dmc_jc_blade_trail ~0 ~0.0 ~0 0.0 2.0 0.0 0.02 1 force @s",false))
                ;;

        ComboNode ArcGP3Skill7 =ComboNode.createNode(()->StarAnimations.YAMATO_COUNTER2)
                .setDamageMultiplier(ValueModifier.multiplier(2F))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 0.5 0.5",true))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {entityPatch.playSound(EpicFightSounds.EVISCERATE,0,0);}))

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
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.7F, "playsound minecraft:entity.generic.explode ambient @s ~ ~ ~ 5", false));;


        ComboNode ArcAuto3extend2 =ComboNode.create()
                .addConditionAnimation(Arc3AS1)
                .addConditionAnimation(ArcGP3Skill1);

        ArcAuto3.key2(ArcAuto3extend2);//普攻三段完美闪避派生S
        Arc3AS2.key2(ArcGP3Skill1);
        ArcAuto6.key2(ArcAuto3extend2);
        ArcGP3Skill1.key2(ArcGP3Skill2);

        ArcGP3Skill2.key2(ArcGP3Skill3);
        ArcGP3Skill3.key2(ArcGP3Skill4);
        ArcGP3Skill4.key2(ArcGP3Skill5);
        ArcGP3Skill5.key2(ArcGP3Skill6);
        ArcGP3Skill6.key2(ArcGP3Skill7);
        ArcGP3Skill7.key1(ArcAuto5);
        ArcGP3Skill7.key2(Arc2ASGP2);



        ComboNode ArcGP3Attack1 =ComboNode.createNode(()->WOMAnimations.AGONY_CLAWSTRIKE)
                .setPriority(5)
                .setNotCharge(true)
                .setCanBeInterrupt(false)
                .addCondition(new DodgeSuccessCondition())
                .setDamageMultiplier(ValueModifier.multiplier(0.5F))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:levitation 1 7",true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s minecraft:slow_falling 2",true))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 0.8 0.5",true))
                .addHitEvent(BiEvent.createBiCommandEvent("effect give @s cataclysm:stun 3",true))
                .addCondition(new StackCondition(1,8))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack 2", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s star:really_stun_immunity 3 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s epicfight:stun_immunity 3 1", false))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "effect give @s minecraft:resistance 3 4", false));;


        ComboNode ArcGP3Attack2 =ComboNode.createNode(()->WOMAnimations.AGONY_PLUNGE_FORWARD)
                .setNotCharge(true)
                .setDamageMultiplier(ValueModifier.multiplier(0.5F))
                .addTimeEvent(new TimeStampedEvent(0.4F, (entityPatch)-> {entityPatch.playAnimationSynchronized(Animations.BIPED_WALK_LONGSWORD,0.1F);}))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.3 0.5",true))
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0F, "effect give @s minecraft:slow_falling 2", false));

        ComboNode ArcGP3Attack3 =ComboNode.createNode(()->WOMAnimations.MOONLESS_AUTO_2)
                .setDamageMultiplier(ValueModifier.multiplier(1.1F))
                .setNotCharge(true)
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.3 0.5",true))
                .addHitEvent(BiEvent.createBiCommandEvent("particle epicacg:dmc_jc_blade_trail ~0 ~0.0 ~0 0.0 1.0 0.0 0.02 1 force @s",false))
                .addTimeEvent(new TimeStampedEvent(0.8F, (entityPatch)-> {entityPatch.playAnimationSynchronized(Animations.BIPED_WALK_LONGSWORD,0.0F);}))
                .setPlaySpeed(1.6F);


        ComboNode ArcGP3Attack4 =ComboNode.createNode(()->WOMAnimations.AGONY_PLUNGE_FORWARD)
                .setDamageMultiplier(ValueModifier.multiplier(1.2F))
                .setConvertTime(-0.95F)
                .addTimeEvent(new TimeStampedEvent(1.3F, (entityPatch)-> {entityPatch.playAnimationSynchronized(Animations.BIPED_WALK_LONGSWORD,0.0F);}))
                .setNotCharge(true)
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 2 0.5",true));

        ComboNode ArcGP3Attack5 =ComboNode.createNode(()->StarAnimations.YAMATO_COUNTER1)
                .setDamageMultiplier(ValueModifier.multiplier(0.8F))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {entityPatch.playSound(EpicFightSounds.EVISCERATE,0,0);}))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.3 0.5",true))
                .addTimeEvent(new TimeStampedEvent(0.8F, (entityPatch)-> {entityPatch.playAnimationSynchronized(Animations.BIPED_WALK_LONGSWORD,0.0F);}))
                .setNotCharge(true)
                .setConvertTime(-0.1F)
                .setPlaySpeed(1F);

        ComboNode ArcGP3Attack6 =ComboNode.createNode(()->StarAnimations.YAMATO_POWER_DASH)
                .setDamageMultiplier(ValueModifier.multiplier(0.8F))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_long\" 3 0.5",true))
                .setNotCharge(true)
                .setConvertTime(-0.2F)
                .setPlaySpeed(1.7F);

        ComboNode ArcGP3Attack7 =ComboNode.createNode(()->StarAnimations.YAMATO_STRIKE1)
                .setDamageMultiplier(ValueModifier.multiplier(2F))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.3 0.5",true))
                .setNotCharge(true)
                .setPlaySpeed(1F);

        ComboNode ArcGP3Attack8 =ComboNode.createNode(()->StarAnimations.YAMATO_STRIKE2)
                .setDamageMultiplier(ValueModifier.multiplier(1.1F))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.3 0.5",true))
                .setNotCharge(true)
                .setPlaySpeed(1.2F);

        ComboNode ArcGP3Attack9 =ComboNode.createNode(()->StarAnimations.YAMATO_POWER3)
                .setNotCharge(true)
                .setDamageMultiplier(ValueModifier.multiplier(1.1F))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.3 0.5",true))
                .setConvertTime(-0.1F)
                .setPlaySpeed(1.1F);

        ComboNode ArcGP3Attack10 =ComboNode.createNode(()->StarAnimations.YAMATO_POWER3_REPEAT)
                .setNotCharge(true)
                .setDamageMultiplier(ValueModifier.multiplier(1.3F))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.3 0.5",true))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStamina -3",false))
                .setPlaySpeed(0.7F);

        ComboNode ArcGP3Attack11 =ComboNode.createNode(()->StarAnimations.YAMATO_POWER3_REPEAT)
                .setNotCharge(true)
                .setDamageMultiplier(ValueModifier.multiplier(1.5F))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.3 0.5",true))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStamina -3",false))
                .setPlaySpeed(0.7F);

        ComboNode ArcGP3Attack12 =ComboNode.createNode(()->StarAnimations.YAMATO_POWER3_REPEAT)
                .setNotCharge(true)
                .setDamageMultiplier(ValueModifier.multiplier(1.7F))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.3 0.5",true))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStamina -3",false))
                .setPlaySpeed(0.7F);

        ComboNode ArcGP3Attack13 =ComboNode.createNode(()->StarAnimations.YAMATO_POWER3_REPEAT)
                .setNotCharge(true)
                .setDamageMultiplier(ValueModifier.multiplier(1.9F))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.3 0.5",true))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStamina -3",false))
                .setPlaySpeed(0.7F);

        ComboNode ArcGP3Attack14 =ComboNode.createNode(()->StarAnimations.YAMATO_POWER3_FINISH)
                .setDamageMultiplier(ValueModifier.multiplier(2.5F))
                .addHitEvent(BiEvent.createBiCommandEvent("particle epicacg:dmc_jc_blade_trail ~0 ~0.0 ~0 0.0 1.5 0.0 0.02 1 force @s",false))
                .addHitEvent(new BiEvent((entityPatch, entity) -> {entityPatch.playSound(EpicFightSounds.EVISCERATE,0,0);}))
                .addHitEvent(BiEvent.createBiCommandEvent("indestructible @s play \"epicfight:biped/combat/hit_short\" 0.3 0.5",true))
                .addHitEvent(BiEvent.createBiCommandEvent("invincible consumeStamina -3",false))
                .setPlaySpeed(1.2F)

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



        ComboNode ArcAuto3extend1 =ComboNode.create()
                .addConditionAnimation(ArcGP3Attack1)
                ;

        ArcAuto3.key3(ArcAuto3extend1);//普攻三段完美闪避派生A
        ArcAuto6.key3(ArcAuto3extend1);
        Arc3AS2.key3(ArcAuto3extend1);
        ArcGP3Attack1.key3(ArcGP3Attack2);
        ArcGP3Attack2.key3(ArcGP3Attack3);
        ArcGP3Attack3.key3(ArcGP3Attack4);
        ArcGP3Attack4.key3(ArcGP3Attack5);
        ArcGP3Attack5.key3(ArcGP3Attack6);
        ArcGP3Attack6.key3(ArcGP3Attack7);
        ArcGP3Attack7.key3(ArcGP3Attack8);
        ArcGP3Attack8.key3(ArcGP3Attack9);
        ArcGP3Attack9.key3(ArcGP3Attack10);
        ArcGP3Attack10.key3(ArcGP3Attack11);
        ArcGP3Attack11.key3(ArcGP3Attack12);
        ArcGP3Attack12.key3(ArcGP3Attack13);
        ArcGP3Attack13.key3(ArcGP3Attack14);
        ArcGP3Attack14.key1(ArcAuto4);
        ArcGP3Attack14.key2(Arc2ASGP2);
        ArcGP3Attack7.key1(ArcAuto4);
        ArcGP3Attack8.key1(ArcAuto4);
        ArcGP3Attack9.key1(ArcAuto4);
        ArcGP3Attack10.key1(ArcAuto4);
        ArcGP3Attack11.key1(ArcAuto4);
        ArcGP3Attack12.key1(ArcAuto4);
        ArcGP3Attack13.key1(ArcAuto4);





        SkillManager.register(ArcbladeSkill::new, ArcbladeSkill.createComboBasicAttack().setCombo(Arcbladeroot).setShouldDrawGui(true), ArcMod.MOD_ID, "combo2");



    }



    @SubscribeEvent
    public static void BuildSkills(SkillBuildEvent event) {
        Arc =  event.build(ArcMod.MOD_ID, "combo0");
        Arc1 =  event.build(ArcMod.MOD_ID, "combo1");
        Arcblade =  event.build(ArcMod.MOD_ID, "combo2");
        //注意和上面对应上
    }

}
