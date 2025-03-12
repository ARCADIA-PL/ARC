package com.arc.arc.init;

import com.arc.arc.effectblock.MagicCircleBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    // 创建 DeferredRegister
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, "arc");

    // 注册魔法阵方块的方块实体类型
    public static final RegistryObject<BlockEntityType<MagicCircleBlockEntity>> MAGIC_CIRCLE =
            BLOCK_ENTITIES.register("magic_circle", () -> BlockEntityType.Builder.of(
                    MagicCircleBlockEntity::new, ModBlocks.MAGIC_CIRCLE.get()).build(null));
}
