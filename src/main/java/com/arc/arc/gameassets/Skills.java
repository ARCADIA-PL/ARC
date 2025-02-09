package com.arc.arc.gameassets;

import com.arc.arc.skill.Demo1;
import com.p1nero.invincible.api.events.BiEvent;
import com.p1nero.invincible.api.events.TimeStampedEvent;
import com.p1nero.invincible.conditions.*;
import com.p1nero.invincible.skill.api.ComboNode;
import com.arc.arc.ArcMod;
import com.arc.arc.skill.DemoComboAttack;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.data.reloader.SkillManager;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.skill.BasicAttack;
import yesman.epicfight.skill.Skill;

@Mod.EventBusSubscriber(modid = ArcMod.MOD_ID)
public class Skills {
    public static Skill Arc;
    public static Skill Arc1;

    public static void registerSkills() {
        ComboNode sw0rdroot = ComboNode.create();
        ComboNode sw0rdjump=ComboNode.createNode(()-> Animations.AXE_AIRSLASH).setPriority(3).addCondition(new JumpCondition());
        ComboNode sw0rddash=ComboNode.createNode(()-> Animations.BATTOJUTSU_DASH).setPriority(3).addCondition(new SprintingCondition());
        ComboNode sw0rdbasicAttack=ComboNode.createNode(()-> WOMAnimations.KATANA_AUTO_1).setPriority(1);
        ComboNode sw0rdextend=ComboNode.createNode(()->WOMAnimations.AGONY_AIR_SLASH).setPriority(1);
        ComboNode sw0rdgp =ComboNode.createNode(()->WOMAnimations.KATANA_FATAL_DRAW).setPriority(4).addCondition(new ParrySuccessCondition()).setCanBeInterrupt(false).setConvertTime(-0.2F);
        ComboNode sw0rddodge=ComboNode.createNode(()->WOMAnimations.KATANA_SHEATHED_DASH).setPriority(1).addCondition(new DodgeSuccessCondition()).setCanBeInterrupt(false).addCondition(new StackCondition(1,4)).addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.0F, "invincible consumeStack value 1", false));
        ComboNode dodge=ComboNode.createNode(()->Animations.BIPED_STEP_BACKWARD).addDodgeSuccessEvent(new BiEvent(((entityPatch, entity) -> entityPatch.playSound(EpicFightSounds.ENTITY_MOVE,0,0))));
        ComboNode a=ComboNode.create().addConditionAnimation(sw0rdjump).
                  addConditionAnimation(sw0rddash).
                  addConditionAnimation(sw0rdbasicAttack);

        sw0rdroot.key1(a);
        sw0rdbasicAttack.key4(dodge);
        dodge.key2(sw0rddodge);
        sw0rdroot.key3(sw0rdgp);
        ComboNode aa=ComboNode.createNode(()-> WOMAnimations.KATANA_AUTO_2);
        sw0rdbasicAttack.key1(aa);
        sw0rdbasicAttack.key2_4(dodge);
        ComboNode aaa=ComboNode.createNode(()-> WOMAnimations.KATANA_AUTO_3);
        aa.key1(aaa);
        aaa.key2(sw0rdextend);
        aaa.key1(a);
        sw0rdjump.key1(a);
        sw0rddash.key1(a);
        sw0rdextend.key1(a);

        SkillManager.register(Demo1::new, Demo1.createComboBasicAttack().setCombo(sw0rdroot).setShouldDrawGui(true), ArcMod.MOD_ID, "combo0");

        ComboNode sw1rdroot = ComboNode.create();
        SkillManager.register(DemoComboAttack::new, DemoComboAttack.createComboBasicAttack().setCombo(sw1rdroot).setShouldDrawGui(true), ArcMod.MOD_ID, "combo1");



    }



    @SubscribeEvent
    public static void BuildSkills(SkillBuildEvent event) {
        Arc =  event.build(ArcMod.MOD_ID, "combo0");
        Arc1 =  event.build(ArcMod.MOD_ID, "combo1");
        //注意和上面对应上
    }

}
