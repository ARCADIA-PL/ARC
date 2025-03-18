package com.arc.arc.effect;

import com.arc.arc.ArcMod;
import com.arc.arc.Registries.ArcEffectsRegistry;
import com.arc.arc.Registries.ArcSoundRegistry;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.particle.EpicFightParticles;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class MagicCircle extends InstantenousMobEffect {
    static int ans = 0;
    static int p = 1;
    static double Y_=0; //过渡时记录高度变化量
    static double YY_=0;    //减慢过渡速度

    static int p1 = 0;  //用于记录某种星是否在生成
    static int p2=10;  //用于确定某种星的生成


    boolean pp=true;    //用于标记锚点
    int sum=0;      //用于固定位置
    static Vec3[]coordinate=new Vec3[10];      //用于存北斗七星的坐标
    static Vec3[]coordinate_1=new Vec3[10];
    static Vec3 hexagram_coordinate;

    static int loop_i=0;       //用于减少环的粒子
    static int flowi1=0,flowi2=1,flowj1=0,flowj2=0;

    // 存储每个玩家的法阵锚点位置
    private static final Map<UUID, Vec3> anchorPositions = new HashMap<>();
    // 存储每个玩家的锚点生成时间
    private static final Map<UUID, Long> anchorTimestamps = new HashMap<>();

    public MagicCircle() {
        super(MobEffectCategory.NEUTRAL, 0x00FF00);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            UUID playerUUID = player.getUUID();

            // 如果锚点未记录，则记录当前玩家位置作为锚点，并记录当前时间
            if (!anchorPositions.containsKey(playerUUID)) {
                Vec3 currentPosition = player.position().add(0, 0.1, 0);
                Vec3 adjustedPosition = adjustPosition(currentPosition, player, 0);
                anchorPositions.put(playerUUID, adjustedPosition);
                anchorTimestamps.put(playerUUID, System.currentTimeMillis()); // 记录当前时间
            }

            // 获取锚点位置
            Vec3 anchorPosition = anchorPositions.get(playerUUID);
            // 实现呼吸效果
            if (ans == 190 || ans == 0) p = (p ^ 1);
            if (p == 0) ans++;
            else ans--;
            if(pp) {
                for (MobEffectInstance effectInstance : entity.getActiveEffects()) {
                    if (effectInstance.getEffect() == ArcEffectsRegistry.MagicCircle.get()) {
                        sum = effectInstance.getDuration();// 返回剩余时间
                    }
                }
                pp = false;
            }
            //检查是否已经过了buff持续时间
            long currentTime = System.currentTimeMillis();
            long anchorTime = anchorTimestamps.getOrDefault(playerUUID, currentTime);
            if (currentTime - anchorTime >= 90000||currentTime - anchorTime>=50*(sum-1)) { // 500 毫秒 = 0.5 秒
                anchorPositions.remove(playerUUID); // 移除锚点
                anchorTimestamps.remove(playerUUID); // 移除时间记录
                pp=true;
                return;
            }
            else {
                if(currentTime - anchorTime >= 56000&&currentTime - anchorTime <= 57000){
                    if (player.level instanceof ServerLevel serverLevel) {
                        Vec3 currentPosition = player.position().add(0, 0.1, 0);
                        serverLevel.sendParticles(EpicFightParticles.FORCE_FIELD.get(), currentPosition.x, currentPosition.y,currentPosition.z, 1, 0, 0, 0, 0);
                        MobEffectInstance absorptionEffect = new MobEffectInstance(MobEffects.ABSORPTION, 240, 9);
                        player.addEffect(absorptionEffect);
                    }
                }
            }
            // 生成法阵
            if(pp==false){
                MobEffectInstance effectInstance = player.getEffect(ArcEffectsRegistry.Stargazing.get());
                if(effectInstance!=null&&effectInstance.getAmplifier()==1){
                    MobEffectInstance RecorderAhEffect = new MobEffectInstance(ArcEffectsRegistry.RecorderA.get(), 1800, 0);
                    player.addEffect(RecorderAhEffect);
                    MobEffectInstance RecorderBhEffect = new MobEffectInstance(ArcEffectsRegistry.RecorderB.get(), 1800, 0);
                    player.addEffect(RecorderBhEffect);
                    RecorderA.top=0;    //重置st
                    //给ABbuff用于启动
                }
                if(Math.abs(Y_-7)>=0.01||effectInstance==null||effectInstance.getAmplifier()<3){       //Y未到6时调用呼吸法阵
                    int v;
                    if(effectInstance!=null&&effectInstance.getAmplifier()>=3){
                        v=1;
                        if(Y_-7<=0.01){
                            if (player.level instanceof ServerLevel serverLevel) {
                                serverLevel.sendParticles(EpicFightParticles.FORCE_FIELD_END.get(), anchorPosition.x, anchorPosition.y,anchorPosition.z, 1, 0, 0, 0, 0);
                                MobEffectInstance SuperFlashEffect = new MobEffectInstance(ArcEffectsRegistry.SuperFlash.get(), 200, 0);
                                player.addEffect(SuperFlashEffect);
                            }
                        }
                    }
                    else {
                        v=0;
                        Y_=0;
                        YY_=0;
                    }
                    spawnHexagramParticles_breath(player, anchorPosition, (amplifier + 1) * 3, 0.2,v);
                }
                else spawnHexagramParticles_flow(player, anchorPosition, (amplifier + 1) * 3, 0.2);
            }
        }
    }

    /**
     * 动态调整法阵位置
     *
     * @param defaultPosition 默认位置（玩家脚下）
     * @param player          玩家实体
     * @param distance        法阵距离玩家的距离
     * @return 调整后的位置
     */
    public static Vec3 adjustPosition(Vec3 defaultPosition, Player player, double distance) {
        // 获取玩家的朝向
        float yaw = player.getYRot(); // 水平朝向（yaw）
        // 计算玩家前方的偏移量
        double offsetX = -Math.sin(Math.toRadians(yaw)) * distance;
        double offsetZ = Math.cos(Math.toRadians(yaw)) * distance;
        // 调整法阵位置
        return defaultPosition.add(offsetX, 0, offsetZ);
    }

    //北斗七星的生成
    public static void plough(Player player, Vec3 vec, double radius,int Level,int v) {
        if (player.level instanceof ServerLevel serverLevel) {
            // 给玩家力量buff，6秒，等级为Level
            MobEffectInstance strengthEffect = new MobEffectInstance(MobEffects.DAMAGE_BOOST, 120, Level-1);
            player.addEffect(strengthEffect);

            MobEffectInstance StarsTwinklingEffect = new MobEffectInstance(ArcEffectsRegistry.StarsTwinklingA.get(), 40, 0);
            player.addEffect(StarsTwinklingEffect);
            // 生成北斗七星
            for(int ii=2;ii<=7;ii++) {
                double X = (coordinate[ii].x - coordinate[ii - 1].x) / 3.0, Z = (coordinate[ii].z - coordinate[ii - 1].z) / 3.0;
                for (int jj = 0; jj <= 3; jj++) {
                    serverLevel.sendParticles(ParticleTypes.FIREWORK, coordinate[ii - 1].x + X *jj*1.0, coordinate[ii].y, coordinate[ii-1].z + Z * jj*1.0, 1, 0, 0, 0, 0); // 火焰粒子
                }
            }
            if(v==1){
                for(int ii=2;ii<=7;ii++) {
                    double X = (coordinate[ii].x - coordinate[ii - 1].x) / 3.0, Z = (coordinate[ii].z - coordinate[ii - 1].z) / 3.0;
                    for (int jj = 0; jj <= 3; jj++) {
                        serverLevel.sendParticles(ParticleTypes.FIREWORK, coordinate_1[ii - 1].x + X *jj*1.0, coordinate_1[ii].y+0.2, coordinate_1[ii-1].z + Z * jj*1.0, 1, 0, 0, 0, 0); // 火焰粒子
                    }
                }
            }
        }
    }

    //    六芒星的生成
    private static void liu1(Player player,Vec3 center,int Level,int v,Vec3 vec) {
        // 给玩家 抗性buff，6秒,等级为Level
        MobEffectInstance resistanceEffect = new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 120, Level-1);
        player.addEffect(resistanceEffect);

        MobEffectInstance StarsTwinklingEffect = new MobEffectInstance(ArcEffectsRegistry.StarsTwinklingB.get(), 40, 0);
        player.addEffect(StarsTwinklingEffect);


        double r = 3; // 小六芒星的半径
        if (player.level instanceof ServerLevel serverLevel) {
            int points = 6; // 六芒星的顶点数
            double angleIncrement = 2 * Math.PI / points;
            // 生成六芒星的连线（正三角形）
            for (int i = 0; i < points; i += 2) {
                double angle1 = i * angleIncrement;
                double angle2 = (i + 2) % points * angleIncrement;
                double x1 = center.x + r * Math.cos(angle1);
                double z1 = center.z + r * Math.sin(angle1);
                double x2 = center.x + r * Math.cos(angle2);
                double z2 = center.z + r * Math.sin(angle2);
                // 在两点之间生成粒子
                int steps = 6; // 两点之间的粒子数量
                for (int j = 0; j <= steps; j++) {
                    double t = (double) j / steps;
                    double x = x1 + (x2 - x1) * t;
                    double z = z1 + (z2 - z1) * t;
                    serverLevel.sendParticles(ParticleTypes.FIREWORK, x, center.y, z, 1, 0, 0, 0, 0); // 地狱门粒子
                }
            }
            // 生成六芒星的连线（倒三角形）
            for (int i = 1; i < points; i += 2) {
                double angle1 = i * angleIncrement;
                double angle2 = (i + 2) % points * angleIncrement;
                double x1 = center.x + r * Math.cos(angle1);
                double z1 = center.z + r * Math.sin(angle1);
                double x2 = center.x + r * Math.cos(angle2);
                double z2 = center.z + r * Math.sin(angle2);
                // 在两点之间生成粒子
                int steps = 8; // 两点之间的粒子数量
                for (int j = 0; j <= steps; j++) {
                    double t = (double) j / steps;
                    double x = x1 + (x2 - x1) * t;
                    double z = z1 + (z2 - z1) * t;
                    serverLevel.sendParticles(ParticleTypes.FIREWORK, x, center.y, z, 1, 0, 0, 0, 0); // 闪电粒子
                }
            }
            if(v==1){
                // 生成六芒星的连线（正三角形）
                for (int i = 0; i < points; i += 2) {
                    double angle1 = i * angleIncrement;
                    double angle2 = (i + 2) % points * angleIncrement;
                    double x1 = vec.x + r * Math.cos(angle1);
                    double z1 = vec.z + r * Math.sin(angle1);
                    double x2 = vec.x + r * Math.cos(angle2);
                    double z2 = vec.z + r * Math.sin(angle2);
                    // 在两点之间生成粒子
                    int steps = 6; // 两点之间的粒子数量
                    for (int j = 0; j <= steps; j++) {
                        double t = (double) j / steps;
                        double x = x1 + (x2 - x1) * t;
                        double z = z1 + (z2 - z1) * t;
                        serverLevel.sendParticles(ParticleTypes.FIREWORK, x, vec.y+0.2, z, 1, 0, 0, 0, 0); // 地狱门粒子
                    }
                }
                // 生成六芒星的连线（倒三角形）
                for (int i = 1; i < points; i += 2) {
                    double angle1 = i * angleIncrement;
                    double angle2 = (i + 2) % points * angleIncrement;
                    double x1 = vec.x + r * Math.cos(angle1);
                    double z1 = vec.z + r * Math.sin(angle1);
                    double x2 = vec.x + r * Math.cos(angle2);
                    double z2 = vec.z + r * Math.sin(angle2);
                    // 在两点之间生成粒子
                    int steps = 8; // 两点之间的粒子数量
                    for (int j = 0; j <= steps; j++) {
                        double t = (double) j / steps;
                        double x = x1 + (x2 - x1) * t;
                        double z = z1 + (z2 - z1) * t;
                        serverLevel.sendParticles(ParticleTypes.FIREWORK, x, vec.y+0.2, z, 1, 0, 0, 0, 0); // 闪电粒子
                    }
                }
            }
        }
    }




    //呼吸法阵
    public static void spawnHexagramParticles_breath(Player player, Vec3 center, double radius, double ringSpeed,int v) {
        if (player.level instanceof ServerLevel serverLevel) {
            if(v==0){
                Y_=0;
            }
            else {
                if(YY_>=3){
                    Y_+=0.2;
                    YY_=0;
                }
                else YY_++;
            }
            Y_=Math.min(Y_,7.0);
            if ((ans <= 160 && ans >= 130 && p == 1 )|| p1 >0) {
                if(p1==0) {
                    p2 =new Random().nextInt(2)+1;    //1为七星，2为六芒星
                }
                if(p1==0&&p2==1){               //控制北斗七星生成
                    p1=1;        //开始生成
                    double x = new Random().nextDouble(radius/2.0);
                    double z = new Random().nextDouble(radius/2.0);
                    x -= radius/2.0 ;
                    z -= radius /2.0;
                    coordinate_1[1]=center;
                    coordinate[1]=center.add(x,5,z);
                    coordinate[2]=coordinate[1].add(1.2,0,0);
                    coordinate[3]=coordinate[1].add(1.6,0,2.0);
                    coordinate[4]=coordinate[1].add(0.6,0,2.4);
                    coordinate[5]=coordinate[1].add(0.4,0,3.6);
                    coordinate[6]=coordinate[1].add(0.24,0,4.56);
                    coordinate[7]=coordinate[1].add(0.6,0,5.96);

                    coordinate_1[2]=coordinate_1[1].add(1.2,0,0);
                    coordinate_1[3]=coordinate_1[1].add(1.6,0,2.0);
                    coordinate_1[4]=coordinate_1[1].add(0.6,0,2.4);
                    coordinate_1[5]=coordinate_1[1].add(0.4,0,3.6);
                    coordinate_1[6]=coordinate_1[1].add(0.24,0,4.56);
                    coordinate_1[7]=coordinate_1[1].add(0.6,0,5.96);
                    int angle=new Random().nextInt(2);
                    for(int i=2;i<=7;i++){
                        if(angle==1) {
                            coordinate[i]=coordinate[1].add((coordinate[i].x-coordinate[1].x)*Math.cos(0)-(coordinate[i].z-coordinate[1].z)*Math.sin(0),0,(coordinate[i].x*-coordinate[1].x)*Math.sin(0)+(coordinate[i].z-coordinate[1].z)*Math.cos(0));
                        }
                        else {
                            coordinate_1[i]=coordinate_1[1].add((coordinate_1[i].x-coordinate_1[1].x)*Math.cos(Math.PI)-(coordinate_1[i].z-coordinate_1[1].z)*Math.sin(Math.PI),0,(coordinate_1[i].x*-coordinate_1[1].x)*Math.sin(Math.PI)+(coordinate_1[i].z-coordinate_1[1].z)*Math.cos(Math.PI));
                        }
                    }
                }
                if(p1==0&&p2==2){       //控制小六芒星生成
                    p1=1;               //开始生成
                    double x = new Random().nextDouble(radius/2.0);
                    double z = new Random().nextDouble(radius/2.0);
                    x-=radius/2.0;
                    z-=radius/2.0;
                    hexagram_coordinate=center.add(x,5,z);
                }
                if (p1>0&&p2==1) {      //生成北斗七星
                    p1++;
                    plough(player, center, radius,3,1);
                }
                if (p1>0&&p2==2) {      //生成六芒星
                    p1++;
                    liu1(player,hexagram_coordinate,3,1,center);
                }
            }
            //星图生成后重置状态
            if(p1>=20||ans<100){        //强制重置状态
                p1=0;
                p2=10;
            }


            double[] radii = {radius * 0.5, radius * 0.75, radius}; // 三层六芒星的半径
            int points = 6; // 六芒星的顶点数
            double angleIncrement = 2 * Math.PI / points;
            // 生成多层六芒星
            for (int layer = 0; layer < radii.length; layer++) {
                double layerRadius = radii[layer]; // 当前层的半径
                double layerHeight = 0.2 * layer; // 每层高度递增
                // 生成六芒星的连线（正三角形）
                for (int i = 0; i < points; i += 2) {
                    double angle1 = i * angleIncrement;
                    double angle2 = (i + 2) % points * angleIncrement;
                    double x1 = center.x + layerRadius * Math.cos(angle1);
                    double z1 = center.z + layerRadius * Math.sin(angle1);
                    double x2 = center.x + layerRadius * Math.cos(angle2);
                    double z2 = center.z + layerRadius * Math.sin(angle2);
                    // 在两点之间生成粒子
                    int steps = ((int) radii[2]) * Math.min(7, Math.max(0, ans / 10 - layer * 4 - 1)); // 两点之间的粒子数量
                    for (int j = 0; j <= steps; j++) {
                        double t = (double) j / steps;
                        double x = x1 + (x2 - x1) * t;
                        double z = z1 + (z2 - z1) * t;
                        if(v==1){
                            serverLevel.sendParticles(ParticleTypes.WAX_OFF, x, center.y + 7.0+Y_, z, 1, 0, 0, 0, 0); // 闪电粒子
                            serverLevel.sendParticles(ParticleTypes.WAX_OFF, x, center.y + 7.0-Y_, z, 1, 0, 0, 0, 0); // 闪电粒子
                        }
                        else{
                            serverLevel.sendParticles(ParticleTypes.WAX_OFF, x, center.y + 7.0, z, 1, 0, 0, 0, 0); // 闪电粒子

                        }
                    }
                }
                // 生成六芒星的连线（倒三角形）
                for (int i = 1; i < points; i += 2) {
                    double angle1 = i * angleIncrement;
                    double angle2 = (i + 2) % points * angleIncrement;
                    double x1 = center.x + layerRadius * Math.cos(angle1);
                    double z1 = center.z + layerRadius * Math.sin(angle1);
                    double x2 = center.x + layerRadius * Math.cos(angle2);
                    double z2 = center.z + layerRadius * Math.sin(angle2);
                    // 在两点之间生成粒子
                    int steps = ((int) radii[2]) * Math.min(7, Math.max(0, ans / 10 - layer * 4 - 1));
                    for (int j = 0; j <= steps; j++) {
                        double t = (double) j / steps;
                        double x = x1 + (x2 - x1) * t;
                        double z = z1 + (z2 - z1) * t;
                        if(v==1){
                            serverLevel.sendParticles(ParticleTypes.WAX_OFF, x, center.y + 7.0+Y_, z, 1, 0, 0, 0, 0); // 闪电粒子
                            serverLevel.sendParticles(ParticleTypes.WAX_OFF, x, center.y + 7.0-Y_, z, 1, 0, 0, 0, 0); // 闪电粒子
                        }
                        else {
                            serverLevel.sendParticles(ParticleTypes.WAX_OFF, x, center.y + 7.0, z, 1, 0, 0, 0, 0); // 闪电粒子
                        }
                    }
                }
            }
            // 生成粒子环
            int ringSteps = 20; // 粒子环的粒子数量
            double ringAngle = System.currentTimeMillis() * ringSpeed % (2 * Math.PI); // 根据时间计算旋转角度
            for(int i=0;i<ringSteps;i++) {
                double angle = ringAngle+i * (2 * Math.PI / ringSteps);
                double x = center.x + radius * Math.cos(angle);
                double z = center.z + radius * Math.sin(angle);
                // 生成粒子环的粒子
                if(v==1){
                    serverLevel.sendParticles(ParticleTypes.FIREWORK, x, center.y + 7.1+Y_, z, 1, 0, 0, 0, 0); // 白色粒子
                    serverLevel.sendParticles(ParticleTypes.DRIPPING_OBSIDIAN_TEAR, x, center.y + 7.1+Y_, z, 1, 0, 0, 0, 0); // 灵魂火焰粒子

                    serverLevel.sendParticles(ParticleTypes.FIREWORK, x, center.y + 7.1-Y_, z, 1, 0, 0, 0, 0); // 白色粒子
                    serverLevel.sendParticles(ParticleTypes.DRIPPING_OBSIDIAN_TEAR, x, center.y + 7.1-Y_, z, 1, 0, 0, 0, 0); // 灵魂火焰粒子

                }
                else {
                    serverLevel.sendParticles(ParticleTypes.FIREWORK, x, center.y + 7.1, z, 1, 0, 0, 0, 0); // 白色粒子
                    serverLevel.sendParticles(ParticleTypes.DRIPPING_OBSIDIAN_TEAR, x, center.y + 7.1, z, 1, 0, 0, 0, 0); // 灵魂火焰粒子
                }// 生成粒子柱
                for (double y = 0; y < 1.0; y += 0.2) { // 粒子柱高度为 0.8 格
                    if(v==1){
                        serverLevel.sendParticles(ParticleTypes.ENCHANT, x, center.y + y+7.0+Y_, z, 1, 0, 0, 0, 0); // 紫色粒子
                        serverLevel.sendParticles(ParticleTypes.ENCHANT, x, center.y + y+7.0-Y_, z, 1, 0, 0, 0, 0); // 紫色粒子
                    }
                    else serverLevel.sendParticles(ParticleTypes.ENCHANT, x, center.y + y+7.0, z, 1, 0, 0, 0, 0); // 紫色粒子
                }
                i++;
                i %= ringSteps;
            }

        }
    }


    //流动法阵
    public static void spawnHexagramParticles_flow(Player player, Vec3 center, double radius, double ringSpeed) {
        if (player.level instanceof ServerLevel serverLevel) {
            if ((ans%30==0 &&p1==0 )|| p1 >0) {
                if(p1==0) {
                    p2 =new Random().nextInt(2)+1;    //1为七星，2为六芒星
                }
                if(p1==0&&p2==1){               //控制北斗七星生成
                    p1=1;        //开始生成
                    double x = new Random().nextDouble(radius);
                    double z = new Random().nextDouble(radius);
                    double y = new Random().nextDouble(13)+1;       //限制高度在法阵上下之间
                    x -= radius/2.0 ;
                    z -= radius/2.0 ;
                    coordinate[1]=center.add(x,y,z);
                    coordinate[2]=coordinate[1].add(1.2,0,0);
                    coordinate[3]=coordinate[1].add(1.6,0,2.0);
                    coordinate[4]=coordinate[1].add(0.6,0,2.4);
                    coordinate[5]=coordinate[1].add(0.4,0,3.6);
                    coordinate[6]=coordinate[1].add(0.24,0,4.56);
                    coordinate[7]=coordinate[1].add(0.6,0,5.96);
                    int angle=new Random().nextInt(2);
                    for(int i=2;i<=7;i++){
                        if(angle==1) coordinate[i]=coordinate[1].add((coordinate[i].x-coordinate[1].x)*Math.cos(0)-(coordinate[i].z-coordinate[1].z)*Math.sin(0),0,(coordinate[i].x*-coordinate[1].x)*Math.sin(0)+(coordinate[i].z-coordinate[1].z)*Math.cos(0));
                        else coordinate[i]=coordinate[1].add((coordinate[i].x-coordinate[1].x)*Math.cos(Math.PI)-(coordinate[i].z-coordinate[1].z)*Math.sin(Math.PI),0,(coordinate[i].x*-coordinate[1].x)*Math.sin(Math.PI)+(coordinate[i].z-coordinate[1].z)*Math.cos(Math.PI));
                    }
                }
                if(p1==0&&p2==2){       //控制小六芒星生成
                    p1=1;               //开始生成
                    double x = new Random().nextDouble(radius);
                    double z = new Random().nextDouble(radius);
                    double y = new Random().nextDouble(13)+1;       //限制高度在法阵上下之间
                    x-=radius/2.0;
                    z-=radius/2.0;
                    hexagram_coordinate=center.add(x,y,z);
                }
                if (p1>0&&p2==1) {      //生成北斗七星
                    p1++;
                    plough(player, coordinate[1], radius,4,2);
                }
                if (p1>0&&p2==2) {      //生成六芒星
                    p1++;
                    liu1(player,hexagram_coordinate,3,2,center);
                }
            }
            //星图生成后重置状态
            if(p1>=10||ans%40==20){     //防止buff结束时p1未置零
                p1=0;
                p2=10;
            }

            double[] radii = {radius * 0.5, radius * 0.75, radius}; // 三层六芒星的半径
            int points = 6; // 六芒星的顶点数
            double angleIncrement = 2 * Math.PI / points;

            for(int layer = 0; layer < radii.length; layer++) {
                int flowi_1=flowi1;
                int flowj_1=flowj1;
                int flowj_2=flowj2;
                int flowi_2=flowi2;
                for (int i=1;i<=3;i++) {
                    double layerRadius = radii[layer]; // 当前层的半径

                    // 生成六芒星的连线（正三角形）
                    for (; flowi1<points; ) {
                        double angle1 = flowi1 * angleIncrement;
                        double angle2 = (flowi1 + 2) % points * angleIncrement;
                        double x1 = center.x + layerRadius * Math.cos(angle1);
                        double z1 = center.z + layerRadius * Math.sin(angle1);
                        double x2 = center.x + layerRadius * Math.cos(angle2);
                        double z2 = center.z + layerRadius * Math.sin(angle2);
                        // 在两点之间生成粒子
                        int steps = ((int) radii[2]*6); // 两点之间的粒子数量
                        for ( ;flowj1 <= steps; ) {
                            double t = (double) flowj1 / steps;
                            double x = x1 + (x2 - x1) * t;
                            double z = z1 + (z2 - z1) * t;
                            serverLevel.sendParticles(ParticleTypes.WAX_OFF, x, center.y + 0.2, z, 1, 0, 0, 0, 0); // 闪电粒子
                            serverLevel.sendParticles(ParticleTypes.FIREWORK, x, center.y + 0.2 ,z, 1, 0, 0, 0, 0); // 金色粒子
                            serverLevel.sendParticles(ParticleTypes.WAX_OFF, x, center.y + 0.2+14, z, 1, 0, 0, 0, 0); // 闪电粒子
                            serverLevel.sendParticles(ParticleTypes.FIREWORK, x, center.y + 0.2+14, z, 1, 0, 0, 0, 0); // 金色粒子
                            flowj1++;
                            if(flowj1>steps)flowi1+=2;
                            flowj1%=(steps+1);
                            if(flowi1>=points)flowi1=0;
                            break;
                        }
                        break;
                    }
                    // 生成六芒星的连线（倒三角形）
                    for (; flowi2< points; ) {
                        double angle1 = flowi2 * angleIncrement;
                        double angle2 = (flowi2 + 2) % points * angleIncrement;
                        double x1 = center.x + layerRadius * Math.cos(angle1);
                        double z1 = center.z + layerRadius * Math.sin(angle1);
                        double x2 = center.x + layerRadius * Math.cos(angle2);
                        double z2 = center.z + layerRadius * Math.sin(angle2);
                        // 在两点之间生成粒子
                        int steps = ((int) radii[2] * 6);
                        for (;flowj2 <= steps; ) {
                            double t = (double) flowj2 / steps;
                            double x = x1 + (x2 - x1) * t;
                            double z = z1 + (z2 - z1) * t;
                            serverLevel.sendParticles(ParticleTypes.WAX_OFF, x, center.y + 0.2, z, 1, 0, 0, 0, 0); // 闪电粒子
                            serverLevel.sendParticles(ParticleTypes.FIREWORK, x, center.y + 0.2, z, 1, 0, 0, 0, 0); // 金色粒子
                            serverLevel.sendParticles(ParticleTypes.WAX_OFF, x, center.y + 14.2, z, 1, 0, 0, 0, 0); // 闪电粒子
                            serverLevel.sendParticles(ParticleTypes.FIREWORK, x, center.y + 14.2, z, 1, 0, 0, 0, 0); // 金色粒子
                            flowj2++;
                            if(flowj2>steps)flowi2+=2;
                            flowj2%=steps+1;
                            if(flowi2>=points)flowi2=1;
                            break;
                        }
                        break;
                    }
                    if(layer!=2){
                        flowj1=flowj_1;
                        flowi1=flowi_1;
                        flowj2=flowj_2;
                        flowi2=flowi_2;
                    }
                }
            }
            int ringSteps = 60; // 粒子环的粒子数量
//            double ringAngle = System.currentTimeMillis() * ringSpeed % (2 * Math.PI); // 根据时间计算旋转角度
            for(int i=1;i<=3;i++) {
                for (; loop_i >=0; ) {
                    double angle = loop_i * (2 * Math.PI / ringSteps);
                    double x = center.x + radius * Math.cos(angle);
                    double z = center.z + radius * Math.sin(angle);
                    // 生成粒子环的粒子
                    serverLevel.sendParticles(ParticleTypes.FIREWORK, x, center.y + 0.1, z, 1, 0, 0, 0, 0); // 白色粒子
                    serverLevel.sendParticles(ParticleTypes.DRIPPING_OBSIDIAN_TEAR, x, center.y + 0.1, z, 1, 0, 0, 0, 0); // 灵魂火焰粒子
                    serverLevel.sendParticles(ParticleTypes.FIREWORK, x, center.y + 16+0.1, z, 1, 0, 0, 0, 0); // 白色粒子
                    serverLevel.sendParticles(ParticleTypes.DRIPPING_OBSIDIAN_TEAR, x, center.y +16+ 0.1, z, 1, 0, 0, 0, 0); // 灵魂火焰粒子
                    // 生成粒子柱
                    for (double y = 0; y < 1.0; y += 0.2) { // 粒子柱高度为 0.8 格
                        serverLevel.sendParticles(ParticleTypes.ENCHANT, x, center.y + y, z, 1, 0, 0, 0, 0); // 紫色粒子
                        serverLevel.sendParticles(ParticleTypes.ENCHANT, x, center.y + +16, z, 1, 0, 0, 0, 0); // 紫色粒子
                    }
                    loop_i--;
                    loop_i =(loop_i+ringSteps)%ringSteps;
                    break;
                }
            }
        }
    }
}
