package com.arc.arc.skill;

import com.google.common.collect.Lists;
import com.p1nero.invincible.client.keymappings.InvincibleKeyMappings;
import com.p1nero.invincible.skill.ComboBasicAttack;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.*;

public class Demo2 extends ComboBasicAttack {
    public Demo2(Builder builder) {
        super(builder);
    }

    @Override
    public List<Component> getTooltipOnItem(ItemStack itemstack, CapabilityItem cap, PlayerPatch<?> playerCap) {
        List<Component> list = Lists.newArrayList();
        list.add(new TranslatableComponent(this.getTranslationKey()).withStyle(ChatFormatting.GOLD).append(new TextComponent(String.format("[%.0f]", this.consumption)).withStyle(ChatFormatting.AQUA)));
        list.add(new TextComponent("普攻三段后按下KEY2可消耗一层技能额外居合攻击"));
        list.add(new TextComponent("无条件常态按下KEY2，消耗一层技能向后瞬步,若后瞬步为极限闪避，则GP成功，再次按下KEY2触发追击,恢复一层技能"));
        list.add(new TranslatableComponent("成功招架后按KEY2消耗一层技能触发防反"));
        list.add(new TextComponent("成功招架后按").append(InvincibleKeyMappings.getTranslatableKey2()).append(new TextComponent("消耗一层技能触发防反")));
        list.add(new TextComponent(""));
        return list;
    }

}
