package com.arc.arc.ParticleEffect;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class WeaponParticleEffect {
    private final List<TimeStampedEvent> events = new ArrayList<>();
    private final ServerLevel level; // 当前世界（强制为 ServerLevel）
    private final Vec3 pos;         // 事件触发的位置
    private final Player player;   // 玩家

    /**
     * 构造函数
     *
     * @param level  当前世界
     * @param pos    事件触发的位置
     * @param player 玩家
     */
    public WeaponParticleEffect(ServerLevel level, Vec3 pos, Player player) {
        this.level = level;
        this.pos = pos;
        this.player = player;
    }

    /**
     * 添加一个时间戳事件
     *
     * @param time    事件触发的时间（秒）
     * @param action  事件触发的动作
     */
    public void addTimeEvent(float time, Runnable action) {
        events.add(new TimeStampedEvent(time, action));
    }

    /**
     * 执行所有事件
     */
    public void execute() {
        for (TimeStampedEvent event : events) {
            // 将时间（秒）转换为毫秒
            long delayMillis = (long) (event.getTime() * 1000);

            // 使用 CompletableFuture 实现延迟任务
            CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep(delayMillis); // 延迟执行
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                level.getServer().execute(() -> event.getAction().run()); // 在主线程执行事件
            });
        }
    }

    /**
     * 时间戳事件类
     */
    private static class TimeStampedEvent {
        private final float time; // 事件触发时间（秒）
        private final Runnable action; // 事件触发的动作

        public TimeStampedEvent(float time, Runnable action) {
            this.time = time;
            this.action = action;
        }

        public float getTime() {
            return time;
        }

        public Runnable getAction() {
            return action;
        }
    }

    /**
     * 创建一个示例 WeaponParticleEffect 实例
     *
     * @param level  当前世界
     * @param pos    事件触发的位置
     * @param player 玩家
     */
    public static WeaponParticleEffect createExampleEffect(ServerLevel level, Vec3 pos, Player player) {
        WeaponParticleEffect effect = new WeaponParticleEffect(level, pos, player);

        // 添加粒子效果事件
        effect.addTimeEvent(0.05F, () -> {
            // 粒子效果：lightning_aoe_emitter
            level.sendParticles(ParticleTypes.ELECTRIC_SPARK, pos.x, pos.y + 1.5, pos.z, 160, 0, 2.5, 0, 0.1);
        });

        effect.addTimeEvent(0.01F, () -> {
            // 粒子效果：wax_off
            level.sendParticles(ParticleTypes.WAX_OFF, pos.x, pos.y + 1, pos.z, 20, 0, 4, 0, 2);
        });

        effect.addTimeEvent(0.3F, () -> {
            // 粒子效果：wax_off（多个方向）
            level.sendParticles(ParticleTypes.WAX_OFF, pos.x - 2, pos.y + 1, pos.z, 10, 0, 1.5, 0, 2);
            level.sendParticles(ParticleTypes.WAX_OFF, pos.x + 2, pos.y + 1, pos.z, 10, 0, 1.5, 0, 2);
            level.sendParticles(ParticleTypes.WAX_OFF, pos.x, pos.y + 1, pos.z - 2, 10, 0, 1.5, 0, 2);
            level.sendParticles(ParticleTypes.WAX_OFF, pos.x, pos.y + 1, pos.z + 2, 10, 0, 1.5, 0, 2);
        });

        effect.addTimeEvent(0.5F, () -> {
            // 粒子效果：wax_off（更远距离）
            level.sendParticles(ParticleTypes.WAX_OFF, pos.x - 3, pos.y + 1, pos.z, 10, 0, 0.2, 0, 2);
            level.sendParticles(ParticleTypes.WAX_OFF, pos.x + 3, pos.y + 1, pos.z, 10, 0, 0.2, 0, 2);
            level.sendParticles(ParticleTypes.WAX_OFF, pos.x, pos.y + 1, pos.z - 3, 10, 0, 0.2, 0, 2);
            level.sendParticles(ParticleTypes.WAX_OFF, pos.x, pos.y + 1, pos.z + 3, 10, 0, 0.2, 0, 2);
        });

        // 添加音效和效果事件
        effect.addTimeEvent(0.1F, () -> {
            // 播放音效：respawn_anchor.deplete
            level.playSound(null, pos.x, pos.y, pos.z, SoundEvents.RESPAWN_ANCHOR_DEPLETE, SoundSource.AMBIENT, 20, 1);
        });

        effect.addTimeEvent(0.7F, () -> {
            // 粒子效果：explosion
            level.sendParticles(ParticleTypes.EXPLOSION, pos.x, pos.y + 1.5, pos.z, 1, 0, 1, 0, 1);

            // 播放音效：entity.generic.explode
            level.playSound(null, pos.x, pos.y, pos.z, SoundEvents.GENERIC_EXPLODE, SoundSource.AMBIENT, 5, 1);
        });

        effect.addTimeEvent(0.8F, () -> {
            // 在玩家前后左右 2 格的位置生成闪电
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();

            // 前后左右 2 格的位置
            Vec3[] positions = {
                    new Vec3(x + 2, y, z), // 右
                    new Vec3(x - 2, y, z), // 左
                    new Vec3(x, y, z + 2), // 前
                    new Vec3(x, y, z - 2)  // 后
            };

            // 在每个位置生成闪电
            for (Vec3 lightningPos : positions) {
                LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                lightning.moveTo(lightningPos.x, lightningPos.y, lightningPos.z); // 设置闪电的位置
                level.addFreshEntity(lightning);
            }
        });

        return effect;
    }
}
