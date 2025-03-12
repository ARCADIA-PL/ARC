package com.arc.arc.Renderer;

import com.arc.arc.entity.ArcbladeHexagramEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ArcbladeHexagramRenderer extends EntityRenderer<ArcbladeHexagramEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("arc", "textures/entity/arcblade_hexagram.png");

    public ArcbladeHexagramRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(ArcbladeHexagramEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        // 调整法阵的位置
        poseStack.translate(0, 0.1, 0);

        // 应用生成和消失动画
        float scale = entity.getScale();
        poseStack.scale(scale, 0.1F, scale);

        // 法阵绕 Y 轴旋转
        float rotation = entity.getRotation() + partialTicks * (entity.getRotationSpeed() / 20.0F);
        poseStack.mulPose(new Quaternion(Vector3f.YP, rotation, false));

        // 渲染法阵
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        Matrix4f matrix = poseStack.last().pose();
        this.renderHexagram(matrix, vertexConsumer, packedLight);

        poseStack.popPose();
    }

    private void renderHexagram(Matrix4f matrix, VertexConsumer vertexConsumer, int packedLight) {
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
    public @NotNull ResourceLocation getTextureLocation(@NotNull ArcbladeHexagramEntity entity) {
        return TEXTURE;
    }
}

