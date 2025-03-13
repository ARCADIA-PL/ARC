package com.arc.arc.Renderer;

import com.arc.arc.entity.ArcbladeHexagramEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ArcbladeHexagramRenderer extends EntityRenderer<ArcbladeHexagramEntity> {
    // 外圈、中圈、内圈的纹理材质
    private static final ResourceLocation OUTER_TEXTURE = new ResourceLocation("arc", "textures/entity/hexagram_outer.png");
    private static final ResourceLocation MIDDLE_TEXTURE = new ResourceLocation("arc", "textures/entity/hexagram_middle.png");
    private static final ResourceLocation INNER_TEXTURE = new ResourceLocation("arc", "textures/entity/hexagram_inner.png");

    public ArcbladeHexagramRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(ArcbladeHexagramEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        // 应用生成和消失动画
        float scale = entity.getScale();
        poseStack.scale(scale, 0.1F, scale);
        // 获取外圈、中圈、内圈的旋转角度
        float outerRotation = entity.getOuterRotation();
        float middleRotation = entity.getMiddleRotation();
        float innerRotation = entity.getInnerRotation();
        // 渲染外圈
        renderRing(poseStack, buffer, packedLight, OUTER_TEXTURE, outerRotation, 2F);
        // 渲染中圈
        renderRing(poseStack, buffer, packedLight, MIDDLE_TEXTURE, middleRotation, 2F);
        // 渲染内圈
        renderRing(poseStack, buffer, packedLight, INNER_TEXTURE, innerRotation, 2F);
    }

    private void renderRing(PoseStack poseStack, MultiBufferSource buffer, int packedLight, ResourceLocation texture, float rotation, float scale) {
        poseStack.pushPose();
        // 调整法阵的位置
        poseStack.translate(0, 0.15, 0);
        // 使用 Quaternion 实现绕 Y 轴旋转
        Quaternion quaternion = new Quaternion(0, Mth.sin(rotation * 0.5F), 0, Mth.cos(rotation * 0.5F));
        poseStack.mulPose(quaternion); // 应用旋转
        poseStack.scale(scale, scale, scale); // 缩放

        // 获取当前变换矩阵
        Matrix4f matrix = poseStack.last().pose();

        // 渲染平面
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(texture));
        renderMagicCircle(matrix, vertexConsumer, packedLight);

        poseStack.popPose();
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
    public ResourceLocation getTextureLocation(ArcbladeHexagramEntity entity) {
        return OUTER_TEXTURE; // 默认返回外圈纹理
    }
}





