package com.arc.arc.gameassets;

import com.arc.arc.ArcMod;
import com.arc.arc.skill.ArcbladeTransformedSkill;
import com.guhao.star.efmex.StarAnimations;
import com.p1nero.invincible.api.events.TimeStampedEvent;
import com.p1nero.invincible.conditions.*;
import com.p1nero.invincible.skill.api.ComboNode;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.data.reloader.SkillManager;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mod.EventBusSubscriber(modid = ArcMod.MOD_ID)
public class ArcbladeTransformed {
    public static Skill ArcbladeTransformed;

    public static void registerSkills() {
        ComboNode ArcbladeTransformedroot = ComboNode.create();
        ComboNode ArcbladeTransformedAirSlash1 = ComboNode.createNode(() -> WOMAnimations.SOLAR_AUTO_4_POLVORA)
                .setPriority(4).setConvertTime(-0.05F).setPlaySpeed(1.1F)
                .addCondition(new CustomCondition() {
                    @Override
                    public boolean predicate(LivingEntityPatch<?> entityPatch) {
                        // 获取玩家实体
                        LivingEntity livingEntity = entityPatch.getOriginal();
                        // 检测玩家是否滞空
                        return !livingEntity.isOnGround();
                    }
                });
        ComboNode ArcbladeTransformedAirSlash2 = ComboNode.createNode(() -> WOMAnimations.TORMENT_AUTO_4)
                .setPriority(4).setConvertTime(-0.25F).addCondition(new UpCondition())
                .addCondition(new CustomCondition() {
                    @Override
                    public boolean predicate(LivingEntityPatch<?> entityPatch) {
                        // 获取玩家实体
                        LivingEntity livingEntity = entityPatch.getOriginal();
                        // 检测玩家是否滞空
                        return !livingEntity.isOnGround();
                    }
                });
        ComboNode ArcbladeTransformedDashSlash = ComboNode.createNode(() -> WOMAnimations.RUINE_DASH)
                .setPriority(4).addCondition(new SprintingCondition());
        ComboNode ArcbladeTransformedAuto1 = ComboNode.createNode(() -> StarAnimations.GREATSWORD_OLD_AUTO3)
                .setPriority(1).setPlaySpeed(0.9F)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.35F, "invincible groundSlam @s 2 false false false", true));
        ComboNode ArcbladeTransformedAuto2 = ComboNode.createNode(() -> StarAnimations.GREATSWORD_OLD_AUTO1)
                .setConvertTime(0.15F).setPlaySpeed(0.8F)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.25F, "invincible groundSlam @s 1.5 false false false", true));;
        ComboNode ArcbladeTransformedAuto3 = ComboNode.createNode(() -> WOMAnimations.TORMENT_AUTO_4)
                .setConvertTime(-0.2F).setPlaySpeed(0.8F);
        ComboNode ArcbladeTransformedAuto4 = ComboNode.createNode(() -> WOMAnimations.SOLAR_AUTO_3)
                        .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.25F, "invincible groundSlam @s 2 false false false", true));
        ComboNode ArcbladeTransformedAuto5 = ComboNode.createNode(() -> WOMAnimations.RUINE_AUTO_3)
                .addTimeEvent(TimeStampedEvent.createTimeCommandEvent(0.55F, "invincible groundSlam @s 2 false false false", true));

        ComboNode ArcbladeTransformedBasicAttack =ComboNode.create()
                .addConditionAnimation(ArcbladeTransformedAirSlash1)
                .addConditionAnimation(ArcbladeTransformedDashSlash)
                .addConditionAnimation(ArcbladeTransformedAuto1);
        ComboNode ArcbladeTransformedBasicAttack2 =ComboNode.create()
                .addConditionAnimation(ArcbladeTransformedDashSlash)
                .addConditionAnimation(ArcbladeTransformedAirSlash1)
                .addConditionAnimation(ArcbladeTransformedAuto1);
        ComboNode ArcbladeTransformedBasicAttack3 =ComboNode.create()
                .addConditionAnimation(ArcbladeTransformedDashSlash)
                .addConditionAnimation(ArcbladeTransformedAuto1);
        ComboNode ArcbladeTransformedAutoAttack2=ComboNode.create()
                .addConditionAnimation(ArcbladeTransformedAuto2)
                .addConditionAnimation(ArcbladeTransformedAirSlash1)
                .addConditionAnimation(ArcbladeTransformedDashSlash);
        ComboNode ArcbladeTransformedAutoAttack3=ComboNode.create()
                .addConditionAnimation(ArcbladeTransformedAuto3)
                .addConditionAnimation(ArcbladeTransformedAirSlash1)
                .addConditionAnimation(ArcbladeTransformedDashSlash);
        ComboNode ArcbladeTransformedAutoAttack4=ComboNode.create()
                .addConditionAnimation(ArcbladeTransformedAirSlash1)
                .addConditionAnimation(ArcbladeTransformedAuto4)
                .addConditionAnimation(ArcbladeTransformedDashSlash);
        ComboNode ArcbladeTransformedAutoAttack5=ComboNode.create()
                .addConditionAnimation(ArcbladeTransformedAirSlash1)
                .addConditionAnimation(ArcbladeTransformedAuto5)
                .addConditionAnimation(ArcbladeTransformedDashSlash);

        ComboNode ArcbladeTransformedAirAuto=ComboNode.create()
                .addConditionAnimation(ArcbladeTransformedAirSlash2)
                .addConditionAnimation(ArcbladeTransformedAuto1);

        ArcbladeTransformedroot.key1(ArcbladeTransformedBasicAttack);
        ArcbladeTransformedDashSlash.key1(ArcbladeTransformedBasicAttack2);
        ArcbladeTransformedAirSlash1.key1(ArcbladeTransformedAirAuto);
        ArcbladeTransformedAirSlash2.key1(ArcbladeTransformedBasicAttack3);
        ArcbladeTransformedAuto1.key1(ArcbladeTransformedAutoAttack2);
        ArcbladeTransformedAuto2.key1(ArcbladeTransformedAutoAttack3);
        ArcbladeTransformedAuto3.key1(ArcbladeTransformedAutoAttack4);
        ArcbladeTransformedAuto4.key1(ArcbladeTransformedAutoAttack5);
        ArcbladeTransformedAuto5.key1(ArcbladeTransformedBasicAttack);



        SkillManager.register(ArcbladeTransformedSkill::new, ArcbladeTransformedSkill.createComboBasicAttack().setCombo(ArcbladeTransformedroot).setShouldDrawGui(true), ArcMod.MOD_ID, "combo4");
    }
    @SubscribeEvent
    public static void BuildSkills(SkillBuildEvent event) {
        ArcbladeTransformed = event.build(ArcMod.MOD_ID, "combo4");
    }
}
