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

public class StellarisTachi extends ComboBasicAttack {
    public StellarisTachi(Builder builder) {
        super(builder);
    }

    @Override
    public List<Component> getTooltipOnItem(ItemStack itemstack, CapabilityItem cap, PlayerPatch<?> playerCap) {
        List<Component> list = Lists.newArrayList();
        list.add(new TranslatableComponent(this.getTranslationKey()).withStyle(ChatFormatting.GOLD).append(new TextComponent(String.format("[%.0f]", this.consumption)).withStyle(ChatFormatting.AQUA)));
        list.add(new TextComponent("§6§l普攻一段,二段，四段可按").withStyle(ChatFormatting.AQUA).append(EpicFightKeyMappings.WEAPON_INNATE_SKILL.getTranslatedKeyMessage()).append(new TextComponent("§6§l消耗一层技能使用体术攻击:高冲击，但是使用时受到的伤害增加")));
        list.add(new TextComponent("§6普攻一段体术后§b衔接普攻三段，§6§l若一段体术命中，§c§l普攻三段后可按").withStyle(ChatFormatting.AQUA).append(EpicFightKeyMappings.WEAPON_INNATE_SKILL.getTranslatedKeyMessage()).append(new TextComponent("消耗技能发动追加攻击")));
        list.add(new TextComponent("§5若一段体术攻击未命中，§c普攻三段后也可§b消耗耐力与技能§c§l发动追加攻击"));
        list.add(new TextComponent("§6普攻二段体术后§b衔接普攻五段，§6§l若二段体术命中，§c§l普攻五段变为消耗一层技能的强力突刺"));
        list.add(new TextComponent("§6释放普攻四段体术后，§b普攻五段变为消耗耐力与技能的横向斩击，§c§l若体术命中则不消耗耐力"));
        return list;
    }

}
