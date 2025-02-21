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
        list.add(new TextComponent("普攻一段以及跳跃冲刺攻击会开启攻击计数(从一层开始)：§b攻击计数大于五层时，解锁普攻五段-居合斩，使用后层数清空，若命中则保留五层；§6§o当攻击计数大于十层时，可使用居合架势"));
        list.add(new TextComponent("§6§o居合架势：可按").withStyle(ChatFormatting.YELLOW).append(EpicFightKeyMappings.WEAPON_INNATE_SKILL.getTranslatedKeyMessage()).append(new TextComponent("主动收刀进入居合架势，获得短时间的力量以及急迫效果但会削弱自身护甲：CD5S")));
        list.add(new TextComponent("主动收刀后短暂时间内无法移动，但0.8s内自动闪避攻击：§6§o主动收刀强化所有的居合攻击且在发动时清除计数:普攻释放的居合消耗一层技能，技能释放的居合消耗两层技能"));
        return list;
    }

}
