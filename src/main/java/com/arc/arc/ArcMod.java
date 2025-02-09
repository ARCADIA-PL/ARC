package com.arc.arc;

import com.arc.arc.gameassets.Skills;
import net.minecraftforge.fml.common.Mod;

@Mod(ArcMod.MOD_ID)
public class ArcMod {
    public static final String MOD_ID = "arc";
    public ArcMod() {
        Skills.registerSkills();
    }

}
