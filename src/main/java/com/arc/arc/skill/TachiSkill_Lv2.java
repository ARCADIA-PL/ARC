package com.arc.arc.skill;

import com.google.common.collect.Lists;
import com.p1nero.invincible.skill.ComboBasicAttack;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.client.input.EpicFightKeyMappings;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.List;

public class TachiSkill_Lv2 extends ComboBasicAttack{
    public TachiSkill_Lv2(Builder builder) {
        super(builder);
    }
    @Override
    public List<Component> getTooltipOnItem(ItemStack itemstack, CapabilityItem cap, PlayerPatch<?> playerCap) {
        List<Component> list = Lists.newArrayList();
        list.add(new TranslatableComponent(this.getTranslationKey()).withStyle(ChatFormatting.GOLD).append(new TextComponent(String.format("[%.0f]", this.consumption)).withStyle(ChatFormatting.AQUA)));
        list.add(new TextComponent("§c§l§o连击流太刀"));
        list.add(new TranslatableComponent("§b常态技能命中进入20s强化状态:§c§l强化普攻三段(霸体但受伤增加)§6且解锁三段普攻后的技能派生;"));
        list.add(new TranslatableComponent("§b常态技能：§7消耗一层技能按")
                         .withStyle(ChatFormatting.YELLOW)
                         .append(EpicFightKeyMappings.WEAPON_INNATE_SKILL.getTranslatedKeyMessage())
                         .append(new TextComponent("发动体术攻击；§7若命中则§6发动斩击，§b斩击命中可回复两层技能以及少量生命")));
        list.add(new TextComponent("技能派生:"));
        list.add(new TranslatableComponent("1A后按").append(new TextComponent(("§c后退S")).append(new TextComponent(("消耗一层技能上挑斩击，若命中后续可§b消耗两层技能§c§l发动招架反击:§c§l§o不死斩,§6命中标记敌人20S,释放过程霸体但受伤大幅增加,且每次攻击均会消耗血量")))));
        list.add(new TranslatableComponent("2A后按").append(new TextComponent(("§c后退S")).append(new TextComponent(("消耗两层技能主动后翻滚GP,若完美闪避则§c§l发动突进,§6若命中则标记敌人20S")))));
        list.add(new TranslatableComponent("3AS消耗一层技能释放").withStyle(ChatFormatting.YELLOW).append(new TextComponent(("§c§l强化版体术攻击，§6命中则退出强化状态；§b若命中被标记目标则§c§l§o发动处决:§l§o§5月食"))));
        return list;
    }
}

