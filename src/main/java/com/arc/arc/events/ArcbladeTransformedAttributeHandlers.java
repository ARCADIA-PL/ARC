package com.arc.arc.events;
import com.arc.arc.ArcItemRegistry;
import com.arc.arc.item.TransformedArcbladeItem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "arc")
public class ArcbladeTransformedAttributeHandlers {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) { // 确保在每 tick 结束时执行
            Player player = event.player;
            ItemStack mainHandItem = player.getMainHandItem();

            // 检查玩家是否手持 TransformedArcbladeItem
            if (mainHandItem.getItem() instanceof TransformedArcbladeItem) {
                // 添加跳跃提升效果（等级 1，持续 2 秒）
                player.addEffect(new MobEffectInstance(MobEffects.JUMP, 40, 1, false, false));

                // 添加缓降效果（等级 0，持续 2 秒）
                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 40, 0, false, false));

                // 监听玩家跳跃事件
                if (isPlayerJumping(player) && player.isOnGround()) { // 玩家在地面上跳跃时触发
                    Level level = player.getLevel(); // 获取玩家的世界
                    double x = player.getX();
                    double y = player.getY();
                    double z = player.getZ();

                    // 在玩家脚下生成云粒子
                    for (int i = 0; i < 10; i++) { // 生成 10 个粒子
                        double offsetX = (level.random.nextDouble() - 0.5) * 0.5; // 随机偏移
                        double offsetZ = (level.random.nextDouble() - 0.5) * 0.5; // 随机偏移
                        level.addParticle(ParticleTypes.CLOUD, x + offsetX, y, z + offsetZ, 0, 0, 0);
                    }
                }
            }
        }
    }

    // 检查玩家是否正在跳跃
    private static boolean isPlayerJumping(Player player) {
        // 通过玩家的垂直速度判断是否正在跳跃
        return player.getDeltaMovement().y > 0.0;
    }
    }
