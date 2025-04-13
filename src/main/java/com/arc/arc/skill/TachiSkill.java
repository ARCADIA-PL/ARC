package com.arc.arc.skill;

import com.google.common.collect.Lists;
import com.p1nero.invincible.client.keymappings.InvincibleKeyMappings;
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

public class TachiSkill extends ComboBasicAttack{
    public TachiSkill(Builder builder) {
        super(builder);
    }
    @Override
    public List<Component> getTooltipOnItem(ItemStack itemstack, CapabilityItem cap, PlayerPatch<?> playerCap) {
        List<Component> list = Lists.newArrayList();
        list.add(new TranslatableComponent(this.getTranslationKey()).withStyle(ChatFormatting.GOLD).append(new TextComponent(String.format("[%.0f]", this.consumption)).withStyle(ChatFormatting.AQUA)));
        list.add(new TextComponent("§5§l§o连击流太刀"));
        list.add(new TranslatableComponent("共有3段普通攻击；§7其中普攻二段与三段为两次攻击;§b常态技能命中§6解锁三种普攻后的派生技能；§b三种派生技能命中§6解锁对应的强化技能"));
        list.add(new TranslatableComponent("§b常态技能：§7消耗一层技能按")
                         .withStyle(ChatFormatting.YELLOW)
                         .append(EpicFightKeyMappings.WEAPON_INNATE_SKILL.getTranslatedKeyMessage())
                         .append(new TextComponent("发动体术攻击；§7若命中则§6发动斩击；§7同时后续普攻后可消耗一层技能按")
                         .withStyle(ChatFormatting.YELLOW)
                         .append(EpicFightKeyMappings.WEAPON_INNATE_SKILL.getTranslatedKeyMessage())
                         .append(new TextComponent("§b派生技能"))));
        list.add(new TranslatableComponent("普攻一段派生：§b上挑斩击；§6命中派生强化技能(消耗两层技能)：§b招架反击"));
        list.add(new TranslatableComponent("普攻二段派生：§b后翻滚,§6若完美闪避则发动突进;§6闪避成功派生强化技能(消耗两层技能)：§b闪避反击"));
        list.add(new TranslatableComponent("普攻三段派生：§b第一段攻击后消耗一层技能§6发动强化版体术攻击；§b第二段攻击后消耗一层技能§6发动突刺;§7普攻三段派生强化技能(消耗两层技能)：§b还没做完嘻嘻"));
        return list;
    }
}

