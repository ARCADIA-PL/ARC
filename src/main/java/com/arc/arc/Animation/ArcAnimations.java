package com.arc.arc.Animation;

import com.arc.arc.ArcMod;
import com.arc.arc.Registries.ArcSoundRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import reascer.wom.particle.WOMParticles;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.BasicAttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.MultiOBBCollider;
import yesman.epicfight.api.collider.OBBCollider;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.damagesource.SourceTags;
import yesman.epicfight.world.damagesource.StunType;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = ArcMod.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ArcAnimations {
    //碰撞箱
    public static final Collider GESSHOKU = new OBBCollider(6.0, 6.0, 14.0, 0.0, 0.0, -11.0);
    private static final Collider FUSHIGIRI_HAIRUI_SLASH = new MultiOBBCollider(3, 0.4, 0.4, 4.0, 0.0, 0.0, 0.0);
    private static final Collider SAKURA_DANCE_COLL = new MultiOBBCollider(4, 0.8, 0.8, 6.0, 0.0, 0.0, 0.0);
    //雪华尘
    public static StaticAnimation SnowSlash;
    //月食
    public static StaticAnimation Gesshoku;
    //樱舞
    public static StaticAnimation SAKURA_DANCE;
    //不死斩
    public static StaticAnimation FUSHIGIRI_HAIRUI_SLASH_1;
    public static StaticAnimation FUSHIGIRI_HAIRUI_SLASH_2;
    @SubscribeEvent
    public static void registerAnimations(AnimationRegistryEvent event) {
        event.getRegistryMap().put(ArcMod.MOD_ID, ArcAnimations::build);
    }
    private static void build() {
        HumanoidArmature biped = Armatures.BIPED;
        SnowSlash = new BasicAttackAnimation(0.15F, 0.2916F, 0.5000F, 0.5833F, null, biped.toolR, "biped/combat/snowslash", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1) -> 1.0F));
        Gesshoku = (new BasicAttackAnimation(0.05F, 2.05F, 2.1F, 5.65F,
                                             GESSHOKU, biped.rootJoint, "biped/combat/gesshoku", biped))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(100.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(100.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(10.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(Float.MAX_VALUE))
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.NO_SOUND)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, ArcSoundRegistry.Gesshoku.get())
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_DOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(SourceTags.WEAPON_INNATE, SourceTags.GUARD_PUNCTURE))
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.0F)
                .addEvents(
                // 时间区间事件：0.05s-1.5s的粒子效果
                AnimationEvent.TimePeriodEvent.create(0.05F, 1.5F, (entitypatch, self, params) -> {
                    if (!entitypatch.getOriginal().getLevel().isClientSide()) {
                        AnimationPlayer player = entitypatch.getAnimator().getPlayerFor(self);
                        int n = (int)(player.getElapsedTime() * 5.0F);
                        double r = 1.3;
                        double t = 0.01;

                        for(int i = 0; i < n; ++i) {
                            double theta = 6.283185307179586 * (new Random()).nextDouble();
                            double phi = ((new Random()).nextDouble() - 0.5) * Math.PI * t / r;
                            double x = r * Math.cos(phi) * Math.cos(theta);
                            double y = r * Math.cos(phi) * Math.sin(theta);
                            double z = r * Math.sin(phi);
                            Vec3f direction = new Vec3f((float)x, (float)y, (float)z + 2.0F);
                            OpenMatrix4f rotation = new OpenMatrix4f().rotate(-((float)Math.toRadians(entitypatch.getOriginal().getYRot())), new Vec3f(0.0F, 1.0F, 0.0F));
                            OpenMatrix4f.transform3v(rotation, direction, direction);
                            ((ServerLevel)entitypatch.getOriginal().getLevel()).sendParticles(
                                    ParticleTypes.REVERSE_PORTAL,
                                    entitypatch.getOriginal().getX() + direction.x,
                                    entitypatch.getOriginal().getY() + direction.y + entitypatch.getOriginal().getBbHeight(),
                                    entitypatch.getOriginal().getZ() + direction.z,
                                    1, 0.0, 0.0, 0.0, 0.0
                            );
                        }
                    }
                }, AnimationEvent.Side.SERVER)
        )
                .addEvents(
                        // 时间戳事件：0.05s施加减速效果
                        AnimationEvent.TimeStampedEvent.create(0.05F, (entitypatch, self, params) -> {
                            entitypatch.getOriginal().addEffect(new MobEffectInstance(
                                    MobEffects.MOVEMENT_SLOWDOWN,
                                    41, 10, true, false, false
                            ));
                        }, AnimationEvent.Side.SERVER),
                        // 时间戳事件：2.05s施加伤害提升和护盾
                        AnimationEvent.TimeStampedEvent.create(2.05F, (entitypatch, self, params) -> {
                            entitypatch.getOriginal().addEffect(new MobEffectInstance(
                                    MobEffects.DAMAGE_BOOST,
                                    65, 10, true, false, false
                            ));
                            entitypatch.getOriginal().addEffect(new MobEffectInstance(
                                    MobEffects.ABSORPTION,
                                    21, 0, true, false, false
                            ));
                            // 爆发粒子效果
                            OpenMatrix4f baseRotation = new OpenMatrix4f();
                            for(int i = 0; i < 170; ++i) {
                                Vec3f direction = new Vec3f(0.0F, 0.0F, 0.0F);
                                OpenMatrix4f rotation = new OpenMatrix4f(baseRotation)
                                        .rotate(-((float)Math.toRadians(entitypatch.getOriginal().getYRot() + 90.0F)), new Vec3f(0.0F, 1.0F, 0.0F))
                                        .translate(new Vec3f(
                                                ((new Random()).nextFloat() - 0.3F) * 24.0F,
                                                ((new Random()).nextFloat() - 0.2F) * 13.0F,
                                                ((new Random()).nextFloat() - 0.5F) * 20.0F
                                        ));
                                OpenMatrix4f.transform3v(rotation, direction, direction);
                                ((ServerLevel)entitypatch.getOriginal().getLevel()).sendParticles(
                                        ParticleTypes.END_ROD,
                                        entitypatch.getOriginal().getX() + direction.x,
                                        entitypatch.getOriginal().getY() + 1.5 + direction.y,
                                        entitypatch.getOriginal().getZ() + direction.z,
                                        1, 0.0, 0.0, 0.0, 0.0
                                );
                            }
                            // 地面冲击波粒子
                            for(int i = 0; i < 50; ++i) {
                                Vec3f direction = new Vec3f(0.0F, 0.0F, 0.0F);
                                OpenMatrix4f rotation = new OpenMatrix4f(baseRotation)
                                        .rotate(-((float)Math.toRadians(entitypatch.getOriginal().getYRot() + 90.0F)), new Vec3f(0.0F, 1.0F, 0.0F))
                                        .translate(new Vec3f(
                                                ((new Random()).nextFloat() - 0.1F) * 24.0F,
                                                ((new Random()).nextFloat() - 0.2F) * 3.0F,
                                                ((new Random()).nextFloat() - 0.5F) * 7.0F
                                        ));
                                OpenMatrix4f.transform3v(rotation, direction, direction);
                                for(int y = 2; y < 12; ++y) {
                                    ((ServerLevel)entitypatch.getOriginal().getLevel()).sendParticles(
                                            ParticleTypes.SOUL,
                                            entitypatch.getOriginal().getX() + direction.x,
                                            entitypatch.getOriginal().getY() + direction.y + (float)(y * y * y) * 0.003F,
                                            entitypatch.getOriginal().getZ() + direction.z,
                                            1, 0.0, 0.0, 0.0, 0.0
                                    );
                                }
                            }
                        }, AnimationEvent.Side.SERVER),
                        // 在4.65秒事件中添加以下内容：
                        AnimationEvent.TimeStampedEvent.create(4.65F, (entitypatch, self, params) -> {
                            if (!entitypatch.getOriginal().getLevel().isClientSide() && entitypatch.getOriginal() instanceof Player attacker) {
                                List<LivingEntity> hitTargets = entitypatch.getCurrenltyHurtEntities()
                                        .stream()
                                        .filter(e -> e instanceof LivingEntity)
                                        .map(e -> (LivingEntity)e)
                                        .toList();
                                for (LivingEntity target : hitTargets) {
                                    ServerLevel level = (ServerLevel)target.getLevel();
                                    // 粒子效果
                                    level.sendParticles(
                                            WOMParticles.ANTITHEUS_BLACKHOLE_END.get(),
                                            target.getX(),
                                            target.getY() + target.getBbHeight() * 0.7,
                                            target.getZ(),
                                            1, 0, 0, 0, 0
                                    );
                                    // 伤害来源构造
                                    DamageSource damageSource;
                                    if (attacker instanceof Player) {
                                        damageSource = DamageSource.playerAttack((Player) attacker); // 玩家攻击
                                    } else {
                                        damageSource = DamageSource.mobAttack(attacker); // 非玩家生物攻击
                                    }
                                    //斩击粒子
                                    level.sendParticles(
                                            WOMParticles.KATANA_SHEATHED_HIT.get(),
                                            target.getX(),
                                            target.getY() + target.getBbHeight() * 1,
                                            target.getZ(),
                                            5, 0.3, 0.2, 0.3, 0.1
                                    );
                                    // 造成伤害（30点）
                                    target.hurt(damageSource, 100.0F);
                                }
                            }
                        }, AnimationEvent.Side.SERVER)
                );

        FUSHIGIRI_HAIRUI_SLASH_1 = (new BasicMultipleAttackAnimation(0.15F, 2.0F, 2.3F, 2.4F,
                                                                     FUSHIGIRI_HAIRUI_SLASH, biped.toolR, "biped/skill/fushigiri_hairui_slash_1", biped))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(30.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(SourceTags.WEAPON_INNATE, SourceTags.GUARD_PUNCTURE))
                .addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS, 2)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true);
        FUSHIGIRI_HAIRUI_SLASH_2 = (new BasicMultipleAttackAnimation(0.15F, 1.5F, 1.7F, 1.8F,
                                                                     FUSHIGIRI_HAIRUI_SLASH, biped.toolR, "biped/skill/fushigiri_hairui_slash_2", biped))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(50.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(SourceTags.WEAPON_INNATE, SourceTags.GUARD_PUNCTURE))
                .addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS, 2)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true);
        SAKURA_DANCE = (new BasicMultipleAttackAnimation(0.2F, "biped/skill/sakura_dance", biped,
                                                         new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.1F, 0.167F, 0.333F, 0.333F, InteractionHand.MAIN_HAND, biped.toolR, SAKURA_DANCE_COLL),
                                                                 new AttackAnimation.Phase(0.333F, 0.333F, 0.5F, 0.5F, 0.5F, InteractionHand.MAIN_HAND, biped.toolR, SAKURA_DANCE_COLL),
                                                                 new AttackAnimation.Phase(0.5F, 0.9F, 1.067F, 1.067F, 1.067F, InteractionHand.MAIN_HAND, biped.toolR, SAKURA_DANCE_COLL)}))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(30.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(new float[]{0.0F, 1.067F}))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicAnimation, livingEntityPatch, v, v1) -> {
                    return 0.99F;
                });
    }
}
