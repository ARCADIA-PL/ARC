package com.arc.arc.init;

import com.arc.arc.entity.ArcbladeHexagramEntity;
import com.arc.arc.entity.MagicCircleEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ArcModEntities {
    // 创建 DeferredRegister
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, "arc");
    // 注册法阵实体类型
    public static final RegistryObject<EntityType<MagicCircleEntity>> MAGIC_CIRCLE =
            ENTITIES.register("magic_circle", () -> EntityType.Builder.of(MagicCircleEntity::new, MobCategory.MISC)
                    .sized(1.0F, 0.1F) // 设置实体的尺寸（宽度和高度）
                    .build("magic_circle"));
    // 注册法阵实体类型
    public static final RegistryObject<EntityType<ArcbladeHexagramEntity>> ARCBLADE_HEXAGRAM = ENTITIES.register("arcblade_hexagram", () ->
            EntityType.Builder.of(ArcbladeHexagramEntity::new, MobCategory.MISC)
                    .sized(1.0F, 1.0F) // 设置实体大小
                    .build("arcblade_hexagram"));

}
