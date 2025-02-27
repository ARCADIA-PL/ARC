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

import java.util.*;

public class ArcbladeMini extends ComboBasicAttack {
    public ArcbladeMini(Builder builder) {
        super(builder);
    }

    @Override
    public List<Component> getTooltipOnItem(ItemStack itemstack, CapabilityItem cap, PlayerPatch<?> playerCap) {
        List<Component> list = Lists.newArrayList();
        list.add(new TranslatableComponent(this.getTranslationKey()).withStyle(ChatFormatting.GOLD).append(new TextComponent(String.format("[%.0f]", this.consumption)).withStyle(ChatFormatting.AQUA)));
        list.add(new TextComponent("普攻一段以及跳跃冲刺攻击会开启攻击命中计数(从一层开始)：§b攻击计数大于五层时(命中四次)，§6会强化第三段普攻(消耗技能;命中则回复一层)，§b且可按").withStyle(ChatFormatting.YELLOW).append(EpicFightKeyMappings.WEAPON_INNATE_SKILL.getTranslatedKeyMessage()).append(new TextComponent("§6§l使用技能-‘升星斩’，使用后层数清空，若命中则提升至九层；§b当攻击计数达到十层(命中九次)时会强化第四段普攻(消耗技能;命中则回复一层)")));
        list.add(new TextComponent("§d普攻二段派生GP2：消耗一层技能向前瞬身；若为完美闪避恢复两层技能，可按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("§b根据命中计数层数发动追击：§610层以下耗费一层技能发动‘十字居合’；10层以上耗费两层技能发动‘星陨坠击’")));
        return list;
    }

}
