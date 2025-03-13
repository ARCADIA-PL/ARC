package com.arc.arc.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class ArcbladeHexagramEntity extends Entity {
    // 法阵的生存时间
    private static final int MAX_LIFE_TIME = 200; // 法阵的最大生存时间
    private int lifeTime = 0; // 当前生存时间

    // 外圈、中圈、内圈的旋转角度和速度
    private float outerRotation = 0.0F; // 外圈旋转角度
    private float middleRotation = 0.0F; // 中圈旋转角度
    private float innerRotation = 0.0F; // 内圈旋转角度

    private final float outerRotationSpeed = 1.0F; // 外圈旋转速度
    private final float middleRotationSpeed = -2.0F; // 中圈旋转速度（反向）
    private final float innerRotationSpeed = 3.0F; // 内圈旋转速度

    // 纹理材质路径
    private final String outerTexture = "textures/entity/hexagram_outer.png";
    private final String middleTexture = "textures/entity/hexagram_middle.png";
    private final String innerTexture = "textures/entity/hexagram_inner.png";

    public ArcbladeHexagramEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    public void tick() {
        super.tick();
        this.lifeTime++;

        // 更新外圈、中圈、内圈的旋转角度
        this.outerRotation += this.outerRotationSpeed;
        this.middleRotation += this.middleRotationSpeed;
        this.innerRotation += this.innerRotationSpeed;

        // 如果生存时间超过最大值，移除实体
        if (this.lifeTime > MAX_LIFE_TIME) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    // 获取外圈、中圈、内圈的旋转角度
    public float getOuterRotation() {
        return this.outerRotation;
    }

    public float getMiddleRotation() {
        return this.middleRotation;
    }

    public float getInnerRotation() {
        return this.innerRotation;
    }

    // 获取外圈、中圈、内圈的纹理材质路径
    public String getOuterTexture() {
        return this.outerTexture;
    }

    public String getMiddleTexture() {
        return this.middleTexture;
    }

    public String getInnerTexture() {
        return this.innerTexture;
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




