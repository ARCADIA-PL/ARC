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

import java.util.List;

public class ArcbladeSkill extends ComboBasicAttack {
    public ArcbladeSkill(Builder builder) {
        super(builder);
    }

    @Override
    public List<Component> getTooltipOnItem(ItemStack itemstack, CapabilityItem cap, PlayerPatch<?> playerCap) {
        List<Component> list = Lists.newArrayList();
        list.add(new TranslatableComponent(this.getTranslationKey()).withStyle(ChatFormatting.GOLD).append(new TextComponent(String.format("[%.0f]", this.consumption)).withStyle(ChatFormatting.AQUA)));
        list.add(new TextComponent("§6§l§o旧世幻影之刃-ArcBlade"));
        list.add(new TextComponent("共有六段普攻，每段普攻后可使用").append(InvincibleKeyMappings.getTranslatableKey2()).append(new TextComponent("§b派生技能")));
        list.add(new TextComponent("§d普攻一段派生：拔剑上挑，给予自身短暂的攻速提升；命中目标则恢复技能层数,结束后按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("§b衔接普攻二段")));
        list.add(new TextComponent("§d普攻二段派生GP2：向前瞬身；若为完美闪避，可按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("或者").append(InvincibleKeyMappings.getTranslatableKey2()).append(new TextComponent("§p§c派生攻击"))));
        list.add(new TextComponent("普攻二段派生GP2若非完美闪避，短暂时间内无法攻击，可消耗技能按").append(InvincibleKeyMappings.getTranslatableKey3()).append(new TextComponent("§c进行一次跨步紧急逃生")));
        list.add(new TextComponent("§6§oGP2:KEY1追击：共5段，消耗技能连点").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("释放：结束后按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("§b衔接普攻第四段"))));
        list.add(new TextComponent("§6§oGP2:KEY2追击：共6段空中攻击，消耗技能连点").append(InvincibleKeyMappings.getTranslatableKey2()).append(new TextComponent("释放：结束后按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("§b衔接普攻第五段"))));
        list.add(new TextComponent("§d普攻三段派生：共2段，连点").append(InvincibleKeyMappings.getTranslatableKey2()).append(new TextComponent("释放")));
        list.add(new TextComponent("§d普攻三段派生一可牵引目标，结束后按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("§b衔接普攻第四段")));
        list.add(new TextComponent("§d普攻三段派生二可恢复一层技能，结束后按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("§b衔接普攻第二段")));
        list.add(new TextComponent("§d普攻四段派生：居合后瞬身，可给予自身伤害吸收以及短时间霸体，结束后按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("§b衔接普攻第五段")));
        list.add(new TextComponent("§d普攻五段派生：共4段，连点").append(InvincibleKeyMappings.getTranslatableKey2()).append(new TextComponent("释放，每次消耗一层技能，若最后一次攻击命中，则恢复4层技能;期间按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("§b衔接普攻六段"))));
        list.add(new TextComponent("§b§l§o普攻六段派生:  I AM THE STORM THAT IS APPAROCHING! ! ! !"));
        list.add(new TextComponent(""));
        list.add(new TextComponent(""));
        list.add(new TextComponent("§6常态技能"));
        list.add(new TranslatableComponent("§6常态按下").append(InvincibleKeyMappings.getTranslatableKey2()).append(new TextComponent("§b释放GP2，消耗技能向后瞬身：若为完美闪避，可使用").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("或者").append(InvincibleKeyMappings.getTranslatableKey2()).append(new TextComponent("§c派生攻击")))));
        list.add(new TextComponent("常态GP1若非完美闪避，短暂时间内无法攻击，可消耗技能按").append(InvincibleKeyMappings.getTranslatableKey3()).append(new TextComponent("§c进行一次跨步紧急逃生")));
        list.add(new TextComponent("§6§oGP1:追击A：共2段，消耗技能连点").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("释放：结束后按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("§b衔接普攻第三段"))));
        list.add(new TextComponent("§6§oGP1:追击S：共2段，消耗技能连点").append(InvincibleKeyMappings.getTranslatableKey2()).append(new TextComponent("释放：结束后按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("§b衔接普攻第四段"))));
        list.add(new TranslatableComponent("§b常态疾跑按下").append(InvincibleKeyMappings.getTranslatableKey3()).append(new TextComponent("释放疾跑技能，疾跑技能后可§b衔接普攻二段")));
        list.add(new TranslatableComponent("§b常态跳跃按下").append(InvincibleKeyMappings.getTranslatableKey3()).append(new TextComponent("释放跳跃技能，跳跃技能后可§b衔接普攻二段")));
        list.add(new TextComponent(""));
        list.add(new TextComponent("§b弹反技能"));
        list.add(new TranslatableComponent("§b常态成功招架后按下").append(InvincibleKeyMappings.getTranslatableKey3()).append(new TextComponent("可消耗技能§6发起招架反击§6：共两段,连按").append(InvincibleKeyMappings.getTranslatableKey3()).append(new TextComponent("使用"))));
        list.add(new TranslatableComponent("§b招架反击1段后按下").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("§b接普攻二段")));
        list.add(new TranslatableComponent("§b招架反击1段后按下").append(InvincibleKeyMappings.getTranslatableKey2()).append(new TextComponent("§s使用GP1")));
        list.add(new TranslatableComponent("§b招架反击2段后按下").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("§b接普攻三段")));
        list.add(new TranslatableComponent("§b招架反击2段后按下").append(InvincibleKeyMappings.getTranslatableKey2()).append(new TextComponent("§s使用GP2")));
        list.add(new TranslatableComponent("§b招架反击2段后可继续触发§s招架反击"));


        return list;
    }

}
