package com.arc.arc.effectblock;

import com.arc.arc.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MagicCircleBlockEntity extends BlockEntity {
    public MagicCircleBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MAGIC_CIRCLE.get(), pos, state);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        load(tag);
    }
}

