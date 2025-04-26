package com.arc.arc.entity;

import com.arc.arc.Registries.ArcEntities;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.spells.SchoolType;
import io.redspace.ironsspellbooks.spells.SpellType;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DriveAlpha extends Projectile {
    //     基础参数配置
    private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.defineId(DriveAlpha.class, EntityDataSerializers.FLOAT);
    public final int animationSeed;     //0-9998的随机数
    private final float maxRadius;      //最大半径
    private EntityDimensions dimensions;     //高度
    public AABB oldBB;          //实体边界
    private List<Entity> victims;   //受击生物列表
    private double SPEED = 1.5d;
    private float damage;

    private int age;
    private static final int EXPIRE_TIME = 8 * 20;      //存在4秒

    public int animationTime;
    public float Angle;


    // 定义同步数据
    private static final EntityDataAccessor<Float> DATA_ANGLE = SynchedEntityData.defineId(DriveAlpha.class, EntityDataSerializers.FLOAT);





    // 必须的构造函数
    public DriveAlpha(EntityType<? extends DriveAlpha> entityType, Level level) {
        super(entityType, level);
        animationSeed = level.random.nextInt(9999);

        float initialRadius = 4;        //初始半径
        maxRadius = 4;                  //最大半径
        dimensions = EntityDimensions.scalable(initialRadius, 0.5f);    //高度
        oldBB = getBoundingBox();       //获取实体的边界框并保存到oldBB中
        victims = new ArrayList<>();    //被攻击到的生物列表
        this.setNoGravity(true);        //不受重力影响
    }


    public DriveAlpha(EntityType<? extends DriveAlpha> entityType, Level levelIn, LivingEntity shooter) {
        this(entityType, levelIn);      //初始化基础属性
        setOwner(shooter);              //记录实体的发射者:
        setYRot(shooter.getYRot());     //设置旋转角度
        setXRot(shooter.getXRot());     //设置旋转角度
        shoot(shooter.getLookAngle());


    }


    public DriveAlpha(Level levelIn, LivingEntity shooter) {
        this(ArcEntities.Drive.get(), levelIn, shooter);
    }

    public void setspeed(double speed) {                //速度构造函数
        this.SPEED = speed;
        Vec3 currentMovement = this.getDeltaMovement();
        if (currentMovement.length() > 0) {
            this.setDeltaMovement(currentMovement.normalize().scale(speed));
        }
    }

    public void shoot(Vec3 direction) {  //设置实体的移动速度和方向      //
        setDeltaMovement(direction.normalize().scale(SPEED));  //将direction的每个分量乘以SPEED从而得到一个新的向量。这个新向量表示了投射物应该以多快的速度沿着direction向量的方向移动。
    }

    public void setDamage(float damage) {
        this.damage = damage;                   //设置伤害
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_RADIUS, 0.5F);
        this.getEntityData().define(DATA_ANGLE, 0.0F); // 初始化角度为0
    }


    public void setAngle(float angle) {
        this.getEntityData().set(DATA_ANGLE, angle);
    }   //角度构造函数

    public float getAngle() {
        return this.getEntityData().get(DATA_ANGLE);
    }


    public void setRadius(float newRadius) {            //将新半径值限制在合理范围内
        if (newRadius <= maxRadius && !this.level.isClientSide) {
            this.getEntityData().set(DATA_RADIUS, Mth.clamp(newRadius, 0.0F, maxRadius));
        }
    }

    public float getRadius() {          //获取实体半径
        return this.getEntityData().get(DATA_RADIUS);
    }


    public void refreshDimensions() {       //更新实体大小
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        super.refreshDimensions();
        this.setPos(d0, d1, d2);
    }

    public void setangle(float angle_){
        this.Angle =angle_;
    }

    @Override
    public void tick() {
        super.tick();

        if (++age > EXPIRE_TIME) {      //检测是否超时
            discard();
            return;
        }
        oldBB = getBoundingBox();       //保存当前的边界框，以便后续使用
        setRadius(getRadius() + 0.12f); //扩大半径

        if (!level.isClientSide) {
            HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);        //方法检测实体是否击中了某个方块。
            if (hitresult.getType() == HitResult.Type.BLOCK) {
                onHitBlock((BlockHitResult) hitresult);
            }

            for (Entity entity : level.getEntities(this, this.getBoundingBox()).stream().filter(target -> canHitEntity(target) && !victims.contains(target)).collect(Collectors.toSet())) {
                damageEntity(entity);
                MagicManager.spawnParticles(level, ParticleHelper.BLOOD, entity.getX(), entity.getY(), entity.getZ(), 50, 0, 0, 0, .5, true);   //生成粒子
            }

        }

        setPos(position().add(getDeltaMovement()));         //更新位置
    }
    public EntityDimensions getDimensions(Pose p_19721_) {
        this.getBoundingBox();
        return EntityDimensions.scalable(this.getRadius() + 2.0F,4.0F);
    }
    public void onSyncedDataUpdated(EntityDataAccessor<?> p_19729_) {   //响应实体同步数据的更新事件
        if (DATA_RADIUS.equals(p_19729_)) {
            this.refreshDimensions();
        }

        super.onSyncedDataUpdated(p_19729_);
    }
    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {      //在击中方块时移除
        super.onHitBlock(blockHitResult);
        discard();
    }
    private void damageEntity(Entity entity) {              ///伤害
        if (!victims.contains(entity)) {
            var hit = DamageSources.applyDamage(entity, damage, SpellType.BLOOD_SLASH_SPELL.getDamageSource(this, getOwner()), SchoolType.BLOOD);   //调用 DamageSources.applyDamage 方法对实体应用伤害
            victims.add(entity);
        }
    }
}
