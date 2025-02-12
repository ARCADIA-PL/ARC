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
        list.add(new TextComponent("旧世幻影之刃-ArcBlade"));
        list.add(new TextComponent("共有六段普攻，每段普攻后可使用KEY2派生技能"));
        list.add(new TextComponent("普攻一段派生：拔剑上挑，给予自身短暂的攻速提升；命中目标则恢复技能层数,结束后按KEY2衔接普攻二段"));
        list.add(new TextComponent("普攻二段派生GP2：向前瞬身；若为完美闪避，可按KEY1或者KEY2派生攻击"));
        list.add(new TextComponent("普攻二段派生GP2若非完美闪避，短暂时间内无法攻击，可消耗技能按KEY3进行一次跨步紧急逃生"));
        list.add(new TextComponent("GP2:KEY1追击：共5段，消耗技能连点KEY1释放：结束后按KEY1衔接普攻第四段"));
        list.add(new TextComponent("GP2:KEY2追击：共6段空中攻击，消耗技能连点KEY2释放：结束后按KEY1衔接普攻第五段"));
        list.add(new TextComponent("普攻三段派生：共2段，连点KEY2释放"));
        list.add(new TextComponent("普攻三段派生一可牵引目标，结束后按KEY1衔接普攻第四段"));
        list.add(new TextComponent("普攻三段派生二可恢复一层技能，结束后按KEY1衔接普攻第一段"));
        list.add(new TextComponent("普攻四段派生：居合后瞬身，可给予自身伤害吸收以及短时间霸体，结束后按KEY1衔接普攻第五段"));
        list.add(new TextComponent("普攻五段派生：共4段，连点KEY2释放，每次消耗一层技能，若最后一次攻击命中，则恢复4层技能;期间按KEY1衔接普攻六段"));
        list.add(new TextComponent("普攻六段派生：消耗所有技能释放 次 元 斩 绝"));
        list.add(new TextComponent(""));
        list.add(new TextComponent(""));
        list.add(new TextComponent("常态技能"));
        list.add(new TranslatableComponent("常态按下KEY2释放GP2，消耗技能向后瞬身：若为完美闪避，可使用KEY1或者KEY2派生攻击"));
        list.add(new TextComponent("常态GP1若非完美闪避，短暂时间内无法攻击，可消耗技能按KEY3进行一次跨步紧急逃生"));
        list.add(new TextComponent("GP1:KEY1追击：共2段，消耗技能连点KEY1释放：结束后按KEY1衔接普攻第三段"));
        list.add(new TextComponent("GP1:KEY2追击：共2段，消耗技能连点KEY2释放：结束后按KEY1衔接普攻第四段"));
        list.add(new TranslatableComponent("常态疾跑按下KEY3释放疾跑技能，疾跑技能后可衔接普攻二段"));
        list.add(new TranslatableComponent("常态跳跃按下KEY3释放跳跃技能，跳跃技能后可衔接普攻二段"));
        list.add(new TextComponent(""));
        list.add(new TextComponent("弹反技能"));
        list.add(new TranslatableComponent("常态成功招架后按下KEY3可消耗技能发起招架反击：共两段,连按KEY3使用"));
        list.add(new TranslatableComponent("招架反击1段后按下KEY1，接普攻二段"));
        list.add(new TranslatableComponent("招架反击1段后按下KEY2，使用GP1"));
        list.add(new TranslatableComponent("招架反击2段后按下KEY1，接普攻三段"));
        list.add(new TranslatableComponent("招架反击2段后按下KEY2，使用GP2"));
        list.add(new TranslatableComponent("招架反击2段后可继续触发招架反击"));


        return list;
    }

}
