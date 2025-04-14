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
        list.add(new TranslatableComponent("§b常态技能命中§6解锁三种普攻后的技能派生；§b三种派生可§6解锁对应的强化技能"));
        list.add(new TextComponent(""));
        list.add(new TranslatableComponent("§b常态技能：§7消耗一层技能按")
                         .withStyle(ChatFormatting.YELLOW)
                         .append(EpicFightKeyMappings.WEAPON_INNATE_SKILL.getTranslatedKeyMessage())
                         .append(new TextComponent("发动体术攻击；§7若命中则§6发动斩击，§b可回复一层技能并增加第四段普攻：击中会追加突刺攻击")));
        list.add(new TextComponent(""));
        list.add(new TranslatableComponent("1AS消耗一层技能，若命中则后续可§b消耗两层技能§5发动招架反击"));
        list.add(new TranslatableComponent("2AS消耗一层技能主动后翻滚,§6若完美闪避则发动突进;§7且后续常态技能变为§b消耗两层技能的主动后翻滚,§6若完美闪避则发动§5强力突进"));
        list.add(new TranslatableComponent("3AS消耗两层技能释放§5强化版体术攻击，§6若3AS或4A命中则（没做完）；"));
        return list;
    }
}

