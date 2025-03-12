package com.arc.arc.Renderer;

import com.arc.arc.entity.MagicCircleEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class MagicCircleRenderer extends EntityRenderer<MagicCircleEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("arc", "textures/entity/magic_circle.png");

    public MagicCircleRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(MagicCircleEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        // 调整法阵的位置
        poseStack.translate(0, 0.1, 0);

        // 应用生成和消失动画
        float scale = entity.getScale();
        poseStack.scale(scale, 0.1F, scale);

        // 法阵绕 Y 轴旋转
        float magicCircleRotation = entity.getMagicCircleRotation() + partialTicks * (entity.getMagicCircleRotationSpeed() / 20.0F);
        poseStack.mulPose(new Quaternion(Vector3f.YP, magicCircleRotation, false)); // 使用角度

        // 渲染法阵
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        Matrix4f matrix = poseStack.last().pose();
        this.renderMagicCircle(matrix, vertexConsumer, packedLight);

        // 粒子圈绕 Y 轴旋转
        float particleRingRotation = entity.getParticleRingRotation() + partialTicks * (entity.getParticleRingRotationSpeed() / 20.0F);
        poseStack.mulPose(new Quaternion(Vector3f.YP, particleRingRotation, false)); // 使用角度

        // 渲染粒子圈
        this.renderParticleRing(entity, partialTicks, poseStack, buffer, packedLight);

        poseStack.popPose();
    }

    private void renderParticleRing(MagicCircleEntity entity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        Level level = entity.level;
        Random random = new Random();

        // 粒子圈的位置
        double x = entity.getX();
        double y = entity.getY() + 0.1; // 稍微高于法阵
        double z = entity.getZ();

        // 粒子圈的半径
        float radius = 3.5F;

        // 生成粒子
        for (int i = 0; i < 5; i++) { // 每帧生成 10 个粒子
            float angle = (float) (random.nextFloat() * 2 * Math.PI); // 随机角度
            double offsetX = radius * Math.cos(angle);
            double offsetZ = radius * Math.sin(angle);

            // 第一种粒子（默认粒子）
            level.addParticle(ParticleTypes.PORTAL, x + offsetX, y, z + offsetZ, 0, 0, 0);

            // 第二种粒子（自定义粒子）
            level.addParticle(ParticleTypes.LAVA, x - offsetX, y, z - offsetZ, 0, 0, 0);
        }
    }

    private void renderMagicCircle(Matrix4f matrix, VertexConsumer vertexConsumer, int packedLight) {
        float size = 3.0F; // 法阵的大小
        float u0 = 0.0F; // 纹理起始 U 坐标
        float u1 = 1.0F; // 纹理结束 U 坐标
        float v0 = 0.0F; // 纹理起始 V 坐标
        float v1 = 1.0F; // 纹理结束 V 坐标

        // 渲染一个四边形
        vertexConsumer.vertex(matrix, -size, 0, -size).color(255, 255, 255, 255).uv(u0, v0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(0, 1, 0).endVertex();
        vertexConsumer.vertex(matrix, -size, 0, size).color(255, 255, 255, 255).uv(u0, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(0, 1, 0).endVertex();
        vertexConsumer.vertex(matrix, size, 0, size).color(255, 255, 255, 255).uv(u1, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(0, 1, 0).endVertex();
        vertexConsumer.vertex(matrix, size, 0, -size).color(255, 255, 255, 255).uv(u1, v0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(0, 1, 0).endVertex();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(MagicCircleEntity entity) {
        return TEXTURE;
    }
}
