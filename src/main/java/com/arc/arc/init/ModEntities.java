package com.arc.arc.init;

import com.arc.arc.entity.MagicCircleEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    // 创建 DeferredRegister
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, "arc");

    // 注册法阵实体类型
    public static final RegistryObject<EntityType<MagicCircleEntity>> MAGIC_CIRCLE =
            ENTITIES.register("magic_circle", () -> EntityType.Builder.of(MagicCircleEntity::new, MobCategory.MISC)
                    .sized(1.0F, 0.1F) // 设置实体的尺寸（宽度和高度）
                    .build("magic_circle"));
}
