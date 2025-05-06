package com.arc.arc;

//import com.arc.arc.events.StarWeaponMechanic;
import com.arc.arc.gameassets.ArcTachi;
import net.minecraftforge.fml.common.Mod;

@Mod(ArcMod.MOD_ID)
public class ArcMod {
    public static final String MOD_ID = "arc";
    public ArcMod() {
        //无坚不摧技能注册
        ArcTachi.registerSkills();
    }
}



