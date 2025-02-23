package com.arc.arc.command;

import com.arc.arc.init.ArcEffectsRegistry;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import io.redspace.ironsspellbooks.util.Component;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.Collection;

public class HurtCounterCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("hurtcounter")
                .requires(source -> source.hasPermission(2)) // 需要OP权限
                .then(Commands.literal("give")
                        .then(Commands.argument("target", EntityArgument.entities())
                                .then(Commands.argument("level", IntegerArgumentType.integer(1))
                                        .then(Commands.argument("duration", IntegerArgumentType.integer(1))
                                                .executes(context -> executeGive(
                                                        context.getSource(),
                                                        EntityArgument.getEntities(context, "target"),
                                                        IntegerArgumentType.getInteger(context, "level"),
                                                        IntegerArgumentType.getInteger(context, "duration")
                                                )))
                                )
                                .then(Commands.literal("remove")
                                        .then(Commands.argument("target", EntityArgument.entities())
                                                .executes(context -> executeRemove(
                                                        context.getSource(),
                                                        EntityArgument.getEntities(context, "target")
                                                ))
                                        )
                                        .then(Commands.literal("query")
                                                .then(Commands.argument("target", EntityArgument.entity())
                                                        .executes(context -> executeQuery(
                                                                context.getSource(),
                                                                EntityArgument.getEntity(context, "target")
                                                        ))
                                                ))))));
    }

    // 给予BUFF逻辑
    private static int executeGive(CommandSourceStack source,
                                   Collection<? extends Entity> targets,
                                   int level,
                                   int durationSeconds) {
        for (Entity entity : targets) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.removeEffect(ArcEffectsRegistry.HURT_COUNTER.get());
                livingEntity.addEffect(new MobEffectInstance(
                        ArcEffectsRegistry.HURT_COUNTER.get(),
                        durationSeconds * 20, // 转换为tick
                        -1, // 等级从0开始
                        false,
                        true
                ));
            }
        }
        source.sendSuccess(Component.literal("已为" + targets.size() + "个实体添加被命中计数效果"), true);
        return 1;
    }

    // 移除BUFF逻辑
    private static int executeRemove(CommandSourceStack source,
                                     Collection<? extends Entity> targets) {
        for (Entity entity : targets) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.removeEffect(ArcEffectsRegistry.HURT_COUNTER.get());
            }
        }
        source.sendSuccess(Component.literal("已移除" + targets.size() + "个实体的效果"), true);
        return 1;
    }

    // 查询BUFF逻辑
    private static int executeQuery(CommandSourceStack source,
                                    Entity target) {
        if (target instanceof LivingEntity livingEntity) {
            MobEffectInstance effect = livingEntity.getEffect(ArcEffectsRegistry.HURT_COUNTER.get());
            if (effect != null) {
                int level = effect.getAmplifier() + 1;
                source.sendSuccess(Component.literal("当前被命中次数: " + level), false);
            } else {
                source.sendFailure(Component.literal("目标没有被命中计数效果"));
            }
        }
        return 1;
    }
}
