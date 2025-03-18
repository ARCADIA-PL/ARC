package com.arc.arc.events;

import com.arc.arc.effect.RecorderA;
import com.arc.arc.effect.RecorderB;
import com.arc.arc.effect.StarCraker;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StarWeaponMechanic {
    private final List<String> skillSequence = new ArrayList<>(); // 存储技能使用顺序
    private final Map<String, Integer> combinationToLevel = new HashMap<>(); // 组合到等级的映射
    private final StarCraker starCrakerEffect = new StarCraker(); // StarCraker Buff 实例

    // BUFF 与技能的映射
    private static final Map<Class<?>, String> BUFF_TO_SKILL = new HashMap<>();

    static {
        // 初始化 BUFF 与技能映射
        BUFF_TO_SKILL.put(RecorderA.class, "A"); // RecorderA 对应技能 A
        BUFF_TO_SKILL.put(RecorderB.class, "B"); // RecorderB 对应技能 B
    }

    public StarWeaponMechanic() {
        // 初始化组合映射
        combinationToLevel.put("AA", 2);
        combinationToLevel.put("AB", 3);
        combinationToLevel.put("BA", 4);
        combinationToLevel.put("BB", 5);
        System.out.println("StarWeaponMechanic 初始化完成，组合映射已加载。");
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

    /**
     * 生成 starcraker Buff
     *
     * @param player 玩家对象
     */
    public void generateStarcrakerBuff(Player player) {
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
        } else {
            System.out.println("无法生成 Buff：无效的组合或技能序列不足。");
        }
    }

    /**
     * 监听玩家获得 BUFF 的事件
     *
     * @param buffClass 获得的 BUFF 类
     */
    public void onBuffGained(Class<?> buffClass) {
        // 检查 BUFF 是否在映射中
        if (BUFF_TO_SKILL.containsKey(buffClass)) {
            String skillName = BUFF_TO_SKILL.get(buffClass);
            recordSkill(skillName); // 记录技能
            System.out.println("玩家获得 BUFF: " + buffClass.getSimpleName() + "，记录技能: " + skillName);
        } else {
            System.out.println("未知 BUFF: " + buffClass.getSimpleName());
        }
    }
}
