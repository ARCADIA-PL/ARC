package com.arc.arc.customdamagesource;

import net.minecraft.world.damagesource.DamageSource;

public class ModDamageSources {
    // 自定义伤害类型：百分比伤害（不可被护甲、抗性减免）
    public static final DamageSource PERCENTAGE_DAMAGE =
            new DamageSource("percentage_damage")
                    .bypassArmor()     // 无视护甲
                    .bypassMagic();    // 无视抗性药水; // 无视保护附魔
}
