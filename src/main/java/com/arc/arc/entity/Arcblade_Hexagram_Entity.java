package com.arc.arc.entity;

import com.arc.arc.init.ModEntities;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
public class Arcblade_Hexagram_Entity extends Entity {
    private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.defineId(Arcblade_Hexagram_Entity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> DATA_SEE = SynchedEntityData.defineId(Arcblade_Hexagram_Entity.class, EntityDataSerializers.BOOLEAN);
    private int duration = 100; // 法阵持续时间
    private int warmupDelayTicks = 20; // 法阵预热时间
    public Arcblade_Hexagram_Entity(EntityType<? extends Arcblade_Hexagram_Entity> type, Level level) {
        super(type, level);
        this.noPhysics = true;
        this.setRadius(3.0F); // 默认半径
    }
    public Arcblade_Hexagram_Entity(Level level, double x, double y, double z, float radius, int duration, int warmupDelay) {
        this(ModEntities.ARCBLADE_HEXAGRAM.get(), level);
        this.setPos(x, y, z);
        this.setRadius(radius);
        this.duration = duration;
        this.warmupDelayTicks = warmupDelay;
    }
    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_RADIUS, 3.0F); // 默认半径
        this.getEntityData().define(DATA_SEE, false); // 是否可见
    }
    public void setRadius(float radius) {
        if (!this.level.isClientSide) {
            this.getEntityData().set(DATA_RADIUS, Math.max(0.0F, radius));
        }
    }
    public float getRadius() {
        return this.getEntityData().get(DATA_RADIUS);
    }
    public void setSee(boolean see) {
        this.getEntityData().set(DATA_SEE, see);
    }
    public boolean isSee() {
        return this.getEntityData().get(DATA_SEE);
    }
    @Override
    public void tick() {
        super.tick();
        if (this.level.isClientSide) {
            // 客户端逻辑：生成粒子效果
            if (this.tickCount >= this.warmupDelayTicks && this.tickCount % 2 == 0) {
                float radius = this.getRadius();
                for (int i = 0; i < 10; i++) {
                    double angle = this.random.nextFloat() * Math.PI * 2;
                    double dx = this.getX() + Math.cos(angle) * radius;
                    double dz = this.getZ() + Math.sin(angle) * radius;
                    this.level.addParticle(ParticleTypes.ENCHANT, dx, this.getY(), dz, 0, 0, 0);
                }
            }
        } else {
            // 服务端逻辑：判断法阵是否结束
            if (this.tickCount >= this.warmupDelayTicks + this.duration) {
                this.discard(); // 移除实体
            } else if (this.tickCount >= this.warmupDelayTicks) {
                this.setSee(true); // 法阵可见
            }
        }
    }
    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.setRadius(tag.getFloat("Radius"));
        this.duration = tag.getInt("Duration");
        this.warmupDelayTicks = tag.getInt("WarmupDelay");
    }
    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putFloat("Radius", this.getRadius());
        tag.putInt("Duration", this.duration);
        tag.putInt("WarmupDelay", this.warmupDelayTicks);
    }
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
