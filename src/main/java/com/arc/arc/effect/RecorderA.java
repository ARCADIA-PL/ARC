package com.arc.arc.effect;

import com.arc.arc.Registries.ArcEffectsRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import java.util.Stack;
import java.awt.*;

public class RecorderA extends MobEffect {
    static int []st=new int[4];
    static int top=0;

    public RecorderA() {
        super(MobEffectCategory.BENEFICIAL, 0xFF0000); // 设置 BUFF 类型和颜色
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            MobEffectInstance Magic = player.getEffect(ArcEffectsRegistry.MagicCircle.get());
            MobEffectInstance Starg = player.getEffect(ArcEffectsRegistry.Stargazing.get());
            MobEffectInstance ReA = player.getEffect(ArcEffectsRegistry.RecorderA.get());
            MobEffectInstance ReB = player.getEffect(ArcEffectsRegistry.RecorderB.get());
            if(Starg!=null&&Magic!=null) {
                if (Starg.getAmplifier() == 2&&top==0) {        //如果观星buff为3，并且st里面没东西
                    if (ReA != null && ReA.getAmplifier() == 1) {
                        st[++top]=0;
                    } else if (Starg != null && ReB != null && ReB.getAmplifier() == 1) {
                        st[++top]=1;
                    }
                }
                if(Starg.getAmplifier()==3&&top==1){  //观星buff为4，并且st里面有一个东西
                    if(ReA!=null&&ReA.getAmplifier()==2)st[++top]=0;
                    else if(ReB!=null&&ReB.getAmplifier()==2)st[++top]=1;
                    else if(ReA!=null&&ReB!=null&&ReA.getAmplifier()+ReB.getAmplifier()==2){
                        if(st[top]==0)st[++top]=1;
                        else st[++top]=0;
                    }
                }
                if(Starg.getAmplifier()==3&&top==2){  //观星buff为4.并且st里面有两个东西
                    int sum=0;
                    sum+=st[top];
                    top--;
                    sum+=st[top]*2;
                    top--;
                    st[1]=st[2]=0;
                    MobEffectInstance StarCrakerEffect = new MobEffectInstance(ArcEffectsRegistry.StarCraker.get(), 1800, sum);
                    player.addEffect(StarCrakerEffect);
                }
            }

        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true; // 每 tick 都执行逻辑
    }
}
