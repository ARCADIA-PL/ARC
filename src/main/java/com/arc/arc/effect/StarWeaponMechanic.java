package com.arc.arc.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StarWeaponMechanic extends MobEffect {
    private final Map<String, Integer> combinationToLevel = new HashMap<>(); // 组合到等级的映射
    private final StarCraker starCrakerEffect = new StarCraker(); // StarCraker Buff 实例
    private final Map<Class<?>, String> BUFF_TO_SKILL = new HashMap<>(); // BUFF 与技能的映射
    private final List<String> skillSequence = new ArrayList<>(); // 存储技能使用顺序

    public StarWeaponMechanic() {
        super(MobEffectCategory.BENEFICIAL, 0x00FF00); // 设置 BUFF 类型和颜色
        // 初始化组合映射
        combinationToLevel.put("AA", 2);
        combinationToLevel.put("AB", 3);
        combinationToLevel.put("BA", 4);
        combinationToLevel.put("BB", 5);
        // 初始化 BUFF 与技能映射
        BUFF_TO_SKILL.put(RecorderA.class, "A");
        BUFF_TO_SKILL.put(RecorderB.class, "B");
        System.out.println("StarWeaponMechanic BUFF 初始化完成。");
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // 检查实体是否为玩家
        if (entity instanceof Player) {
            Player player = (Player) entity;

            // 检查技能序列并生成 StarCraker Buff
            String combination = getCombination();
            if (combination != null && combinationToLevel.containsKey(combination)) {
                int level = combinationToLevel.get(combination); // 获取 Buff 等级
                System.out.println("检测到组合: " + combination + "，生成 " + level + " 级 StarCraker Buff。");
                // 创建对应等级的 StarCraker Buff
                MobEffectInstance effect = new MobEffectInstance(starCrakerEffect, 600, level - 1); // 持续 30 秒，等级为 level - 1
                player.addEffect(effect); // 应用 Buff
                System.out.println("已将 " + level + " 级 StarCraker Buff 应用到玩家 " + player.getName().getString() + "。");
                skillSequence.clear(); // 清空技能记录
                System.out.println("技能序列已清空。");
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        // 每 tick 都执行逻辑
        return true;
    }

    /**
     * 记录技能使用
     *
     * @param skill 技能类型（A 或 B）
     */
    public void recordSkill(String skill) {
        skillSequence.add(skill); // 添加技能到列表
        if (skillSequence.size() > 4) {
            skillSequence.remove(0); // 保持列表长度为 4
        }
        System.out.println("记录技能: " + skill + "，当前技能序列: " + skillSequence);
    }

    /**
     * 获取技能组合
     *
     * @return 组合字符串（AA、AB、BA 或 BB）
     */
    public String getCombination() {
        if (skillSequence.size() < 4) {
            System.out.println("技能序列不足 4 次，无法生成组合。当前技能序列: " + skillSequence);
            return null; // 不足 4 次技能使用，无法生成组合
        }
        // 提取最后两个技能
        String thirdSkill = skillSequence.get(2);
        String fourthSkill = skillSequence.get(3);
        String combination = thirdSkill + fourthSkill; // 返回组合
        System.out.println("提取技能组合: " + combination + "，当前技能序列: " + skillSequence);
        return combination;
    }
}
