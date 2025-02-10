package com.arc.arc.gameassets;

import com.arc.arc.skill.Demo1;
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
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.skill.*;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

@Mod.EventBusSubscriber(modid = ArcMod.MOD_ID)
public class Skills {
    public static Skill Arc;
    public static Skill Arc1;

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
        ComboNode sw1rdauto3=ComboNode.createNode(()-> Animations.UCHIGATANA_AUTO3);
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
        ComboNode sw1rdbasicAttack =ComboNode.create().addConditionAnimation(sw1rdjump).
                addConditionAnimation(sw1rddash).
                addConditionAnimation(sw1rdauto1).
                addConditionAnimation(sw1rdsheateAttack);


        sw1rdroot.key1(sw1rdbasicAttack);
        sw1rddash.key1(sw1rdauto1);
        sw1rdjump.key1(sw1rdauto1);
        sw1rdbasicAttack.key1(sw1rdauto1);
        sw1rdsheateAttack.key1(sw1rdauto2);
        sw1rdauto1.key1(sw1rdauto2);
        sw1rdauto2.key1(sw1rdauto3);



                SkillManager.register(Demo2::new, Demo2.createComboBasicAttack().setCombo(sw1rdroot).setShouldDrawGui(true), ArcMod.MOD_ID, "combo1");



    }



    @SubscribeEvent
    public static void BuildSkills(SkillBuildEvent event) {
        Arc =  event.build(ArcMod.MOD_ID, "combo0");
        Arc1 =  event.build(ArcMod.MOD_ID, "combo1");
        //注意和上面对应上
    }

}
