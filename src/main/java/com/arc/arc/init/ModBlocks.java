package com.arc.arc.init;

import com.arc.arc.effectblock.MagicCircleBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    // 创建 DeferredRegister
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "arc");

    // 注册魔法阵方块
    public static final RegistryObject<Block> MAGIC_CIRCLE =
            BLOCKS.register("magic_circle", () -> new MagicCircleBlock(
                    BlockBehaviour.Properties.of(Material.AIR)
                            .noCollission() // 无碰撞
                            .strength(2.0F) // 硬度
                            .requiresCorrectToolForDrops() // 需要正确的工具采集
                            .noOcclusion() // 无遮挡（允许渲染方块实体）
            ));
}
