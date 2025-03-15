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

public class ArcbladeSkill extends ComboBasicAttack {
    public ArcbladeSkill(Builder builder) {
        super(builder);
    }

    @Override
    public List<Component> getTooltipOnItem(ItemStack itemstack, CapabilityItem cap, PlayerPatch<?> playerCap) {
        List<Component> list = Lists.newArrayList();
        list.add(new TranslatableComponent(this.getTranslationKey()).withStyle(ChatFormatting.GOLD).append(new TextComponent(String.format("[%.0f]", this.consumption)).withStyle(ChatFormatting.AQUA)));
        list.add(new TextComponent("§5§l§o旧世幻影之刃-ArcBlade"));
        list.add(new TextComponent("§4§l§o旧界终焉力量铸造的究极兵刃,其强大的力量使得常人难以驾驭..."));
        list.add(new TextComponent("§4§l§o逆咒回响：每段普攻都会使你碎甲，若命中目标则将碎甲效果转移；普攻三段命中目标后获得霸体及抗性提升五，同时强化接下来的三次普攻"));
        list.add(new TextComponent("共有六段普攻，每段普攻后可使用").append(InvincibleKeyMappings.getTranslatableKey2()).append(new TextComponent("§b派生技能")));
        list.add(new TextComponent("每次普攻之间可以穿插跳跃攻击和冲刺攻击，§b跳跃攻击后衔接普攻二段，冲刺攻击后衔接普攻一段"));
        list.add(new TextComponent("§d普攻一段派生1：拔剑上挑后一文字斩击，给予自身短暂的攻速提升以及霸体；,可按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey2()).append(new TextComponent("§d发动二段一文字斩击:命中获得抗性提升4以及霸体，恢复两层技能,可按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("§b衔接普攻二段"))));
        list.add(new TextComponent("§d普攻二段派生GP2：消耗一层技能向前瞬身；若为完美闪避恢复两层技能，可按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("或者").append(InvincibleKeyMappings.getTranslatableKey2()).append(new TextComponent("§p§c派生攻击"))));
        list.add(new TextComponent("普攻二段派生GP2若非完美闪避，短暂时间内无法攻击，可消耗技能按").append(InvincibleKeyMappings.getTranslatableKey3()).append(new TextComponent("§c进行一次跨步紧急逃生")));
        list.add(new TextComponent("§6§oGP2:追击A2：共7段，消耗两层技能连点").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("释放：结束后按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("§b衔接普攻四段"))));
        list.add(new TextComponent("§6§oGP2:追击S2：共6段空中攻击2段地面攻击，消耗两层技能连点").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey2()).append(new TextComponent("释放：结束后按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("§b衔接普攻五段"))));
        list.add(new TextComponent("§d普攻三段派生：共2段，连点").append(InvincibleKeyMappings.getTranslatableKey2()).append(new TextComponent("释放")));
        list.add(new TextComponent("§d普攻三段派生一可停滞目标，结束后按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("§b衔接普攻第四段")));
        list.add(new TextComponent("§d普攻三段派生二§6§o可直接使用普攻三段的前瞬步GP3，§d若非完美闪避，可按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("§b衔接普攻第二段")));
        list.add(new TextComponent("§6§oGP3：普攻三段后自动向前瞬身，若闪避成功，可按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey2()).append(new TextComponent("或").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey3()).append(new TextComponent("发动追击"))));
        list.add(new TextComponent("§6§o普攻三段命中目标后的第一第二次强化普攻后可按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey2()).append(new TextComponent("发动GP3")));;
        list.add(new TextComponent("§6§o普攻三段完美闪避追击1：消耗三层技能连按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey2()).append(new TextComponent("发动7段空中攻击,结束后按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("§b衔接普攻第四段:可按下").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey2()).append(new TextComponent("发动GP2")))));
        list.add(new TextComponent("§6§o普攻三段完美闪避追击2：消耗四层技能连按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey3()).append(new TextComponent("发动总计10段攻击,结束后按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("§b衔接普攻第五段:可按下").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey2()).append(new TextComponent("发动GP2")))));
        list.add(new TextComponent("§d普攻四段派生：瞬身居合，可给予自身伤害吸收以及短时间霸体和抗性提升五，可按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey2()).append(new TextComponent("§d追击二段：直接打出普攻第三段：命中获得短时间抗性提升五，恢复一层充能；结束后按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("§b衔接普攻四段"))));
        list.add(new TextComponent("§d普攻五段派生：共四段攻击，可连按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey2()).append(new TextComponent("发动，每次消耗一层技能，最后一段命中则恢复4层技能,期间可按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("§b衔接普攻六段"))));
        list.add(new TextComponent("§b§l§o普攻六段派生:  I AM THE STORM THAT IS APPAROCHING! ! ! !"));
        list.add(new TextComponent(""));
        list.add(new TextComponent(""));
        list.add(new TextComponent("§6常态技能"));
        list.add(new TranslatableComponent("§6常态按下").append(InvincibleKeyMappings.getTranslatableKey2()).append(new TextComponent("§b释放GP1，消耗一层技能向后跨步，CD7S，释放6AS的CD内无法使用：若为完美闪避则恢复两层技能，可使用").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("或者").append(InvincibleKeyMappings.getTranslatableKey2()).append(new TextComponent("§c派生攻击")))));
        list.add(new TextComponent("常态GP1若非完美闪避，短暂时间内无法攻击，可消耗技能按").append(InvincibleKeyMappings.getTranslatableKey3()).append(new TextComponent("§c进行一次跨步紧急逃生")));
        list.add(new TextComponent("§6§oGP1:追击A1：共2段，消耗技能连点").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("释放：结束后按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("§b衔接普攻三段"))));
        list.add(new TextComponent("§6§oGP1:追击S1：共3段，消耗技能连点").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey2()).append(new TextComponent("释放,结束后按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("§b衔接普攻四段"))));
        list.add(new TextComponent("§6§oGP1:两种追击后按").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey2()).append(new TextComponent("§6§o可释放GP2")));
        list.add(new TranslatableComponent("§b常态疾跑按下").append(InvincibleKeyMappings.getTranslatableKey3()).append(new TextComponent("消耗一层技能释放疾跑技能，疾跑技能后§b可衔接普攻二段")));
        list.add(new TranslatableComponent("§b常态跳跃按下").append(InvincibleKeyMappings.getTranslatableKey3()).append(new TextComponent("消耗三层层技能释放跳跃技能,命中使目标硬直以及碎甲，恢复一层技能;§b可衔接普攻二段")));
        list.add(new TextComponent(""));
        list.add(new TextComponent("§b弹反技能"));
        list.add(new TranslatableComponent("§b常态成功招架后按下").append(InvincibleKeyMappings.getTranslatableKey3()).append(new TextComponent("可消耗技能§6发起招架反击§6：共两段,连按").append(InvincibleKeyMappings.getTranslatableKey3()).append(new TextComponent("使用"))));
        list.add(new TranslatableComponent("§b招架反击1段后按下").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("§b接普攻二段")));
        list.add(new TranslatableComponent("§b招架反击1段后按下").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey2()).append(new TextComponent("§s使用GP1")));
        list.add(new TranslatableComponent("§b招架反击2段后按下").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey1()).append(new TextComponent("§b接普攻三段")));
        list.add(new TranslatableComponent("§b招架反击2段后按下").withStyle(ChatFormatting.YELLOW).append(InvincibleKeyMappings.getTranslatableKey2()).append(new TextComponent("§s使用GP2")));
        list.add(new TranslatableComponent("§b招架反击2段后可继续触发§s招架反击"));


        return list;
    }

}
