package com.arc.arc.gameassets;

import com.arc.arc.ArcMod;
import com.p1nero.invincible.skill.api.ComboNode;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import yesman.epicfight.api.data.reloader.SkillManager;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;

public class ArcTachi {
    public static Skill ArcTachi;
    public static void registerSkills(){
        ComboNode Tachiroot = ComboNode.create();

        SkillManager.register(com.arc.arc.skill.ArcTachi::new, com.arc.arc.skill.ArcTachi.createComboBasicAttack().setCombo(Tachiroot).setShouldDrawGui(true), ArcMod.MOD_ID, "tachi_1");
    }
    @SubscribeEvent
    public static void BuildSkills(SkillBuildEvent event) {
        ArcTachi = event.build(ArcMod.MOD_ID, "tachi_1");
    }
}
