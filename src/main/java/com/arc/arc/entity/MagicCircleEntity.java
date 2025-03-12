package com.arc.arc.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class MagicCircleEntity extends Entity {
    private int lifeTime = 0; // 法阵的生存时间
    private static final int MAX_LIFE_TIME = 160; // 5秒（20 ticks = 1秒）

    // 法阵和粒子圈的旋转速度（度/秒）
    private float magicCircleRotationSpeed = 1.0F; // 法阵旋转速度
    private float particleRingRotationSpeed = -277.0F; // 粒子圈旋转速度

    // 当前旋转角度
    private float magicCircleRotation = 0.0F;
    private float particleRingRotation = 0.0F;

    public MagicCircleEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {
        // 定义需要同步的数据（如果需要）
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        // 从 NBT 中读取数据
        this.lifeTime = compound.getInt("LifeTime");
        this.magicCircleRotationSpeed = compound.getFloat("MagicCircleRotationSpeed");
        this.particleRingRotationSpeed = compound.getFloat("ParticleRingRotationSpeed");
        this.magicCircleRotation = compound.getFloat("MagicCircleRotation");
        this.particleRingRotation = compound.getFloat("ParticleRingRotation");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        // 将数据保存到 NBT 中
        compound.putInt("LifeTime", this.lifeTime);
        compound.putFloat("MagicCircleRotationSpeed", this.magicCircleRotationSpeed);
        compound.putFloat("ParticleRingRotationSpeed", this.particleRingRotationSpeed);
        compound.putFloat("MagicCircleRotation", this.magicCircleRotation);
        compound.putFloat("ParticleRingRotation", this.particleRingRotation);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return (Packet<ClientGamePacketListener>) NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        super.tick();

        // 更新生命周期
        this.lifeTime++;
        if (this.lifeTime > MAX_LIFE_TIME) {
            this.discard(); // 超过最大生存时间后移除实体
        }

        // 更新旋转角度
        this.magicCircleRotation += this.magicCircleRotationSpeed / 20.0F; // 每 tick 更新
        this.particleRingRotation += this.particleRingRotationSpeed / 20.0F;

        // 确保角度在 0-360 度之间
        this.magicCircleRotation %= 360.0F;
        this.particleRingRotation %= 360.0F;
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

    public float getMagicCircleRotation() {
        return this.magicCircleRotation;
    }

    public float getParticleRingRotation() {
        return this.particleRingRotation;
    }

    public float getMagicCircleRotationSpeed() {
        return this.magicCircleRotationSpeed;
    }

    public float getParticleRingRotationSpeed() {
        return this.particleRingRotationSpeed;
    }

    public void setMagicCircleRotationSpeed(float speed) {
        this.magicCircleRotationSpeed = speed;
    }

    public void setParticleRingRotationSpeed(float speed) {
        this.particleRingRotationSpeed = speed;
    }
}
