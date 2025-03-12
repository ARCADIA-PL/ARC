package com.arc.arc.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;

public class ArcbladeHexagramEntity extends Entity {
    private static final int MAX_LIFE_TIME = 200; // 法阵的最大生存时间
    private int lifeTime = 0; // 当前生存时间
    private float rotation = 0.0F; // 当前旋转角度
    private float rotationSpeed = 2.0F; // 旋转速度

    public ArcbladeHexagramEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    public void tick() {
        super.tick();
        this.lifeTime++;

        // 更新旋转角度
        this.rotation += this.rotationSpeed;

        // 如果生存时间超过最大值，移除实体
        if (this.lifeTime > MAX_LIFE_TIME) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    public float getScale() {
        // 生成动画：从小变大
        if (this.lifeTime < 40) {
            return this.lifeTime / 40.0F;
        }
        // 消失动画：从大变小
        if (this.lifeTime > MAX_LIFE_TIME - 40) {
            return (MAX_LIFE_TIME - this.lifeTime) / 40.0F;
        }
        return 1.0F; // 正常大小
    }

    public float getRotation() {
        return this.rotation;
    }

    public float getRotationSpeed() {
        return this.rotationSpeed;
    }

    @Override
    protected void defineSynchedData() {
        // 如果需要同步数据，可以在这里定义
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        // 从 NBT 数据中读取额外数据
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        // 将额外数据保存到 NBT 中
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        // 返回一个用于同步实体生成的包
        return new ClientboundAddEntityPacket(this);
    }
}



