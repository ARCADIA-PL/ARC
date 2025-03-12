package com.arc.arc.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.arc.arc.entity.Arcblade_Hexagram_Entity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
public class Arcblade_Hexagram_Renderer extends EntityRenderer<Arcblade_Hexagram_Entity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("yourmod", "textures/entity/arcblade_hexagram.png");
    public Arcblade_Hexagram_Renderer(EntityRendererProvider.Context context) {
        super(context);
    }
    @Override
    public ResourceLocation getTextureLocation(Arcblade_Hexagram_Entity entity) {
        return TEXTURE; // 使用固定纹理
    }
    @Override
    public void render(Arcblade_Hexagram_Entity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (entity.isSee()) {
            float radius = entity.getRadius();
            poseStack.pushPose();
            poseStack.translate(0, 0.1, 0); // 稍微抬高法阵
            poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
            poseStack.scale(radius, radius, radius);
            VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
            this.drawVertex(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }
    }
    private void drawVertex(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay) {
        // 绘制法阵的顶点
        // 这里可以根据需要实现具体的绘制逻辑
    }
}

