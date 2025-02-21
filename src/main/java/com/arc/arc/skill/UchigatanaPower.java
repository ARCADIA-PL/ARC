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

public class UchigatanaPower extends ComboBasicAttack {
    public UchigatanaPower(Builder builder) {
        super(builder);
    }

    @Override
    public List<Component> getTooltipOnItem(ItemStack itemstack, CapabilityItem cap, PlayerPatch<?> playerCap) {
        List<Component> list = Lists.newArrayList();
        list.add(new TranslatableComponent(this.getTranslationKey()).withStyle(ChatFormatting.GOLD).append(new TextComponent(String.format("[%.0f]", this.consumption)).withStyle(ChatFormatting.AQUA)));
        list.add(new TextComponent("5s不攻击后自动收刀，收刀状态下可使用居合攻击;普攻连段之间可用疾跑攻击重置普攻"));
        list.add(new TextComponent("成功招架，完美闪避后可按").withStyle(ChatFormatting.YELLOW).append(EpicFightKeyMappings.WEAPON_INNATE_SKILL.getTranslatedKeyMessage()).append(new TextComponent("主动收刀进入居合架势，获得短时间的力量以及急迫效果但会削弱自身护甲：CD15S")));
        list.add(new TextComponent("主动收刀后短暂时间内无法移动，但0.5s内自动闪避攻击：主动收刀强化所有的居合攻击:普攻释放的居合消耗一层技能，技能释放的居合消耗两层技能"));
        return list;
    }

}
