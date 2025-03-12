package com.arc.arc.ParticleEffect;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ArcbladeHexagram extends TextureSheetParticle {

    protected ArcbladeHexagram(ClientLevel world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z, motionX, motionY, motionZ);
        this.lifetime = 100; // 粒子的生命周期
        this.quadSize = 1.0F; // 粒子的大小
    }

    @Override
    public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        // 这里可以自定义渲染逻辑
        super.render(buffer, renderInfo, partialTicks);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE; // 使用不透明的粒子渲染类型
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(@NotNull SimpleParticleType typeIn, @NotNull ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            ArcbladeHexagram particle = new ArcbladeHexagram(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.pickSprite(this.spriteSet);
            return particle;
        }
    }
}

