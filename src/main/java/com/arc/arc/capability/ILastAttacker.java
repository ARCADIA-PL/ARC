package com.arc.arc.capability;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface ILastAttacker {
    // 明确声明 public 方法并添加注解
    @Nullable
    UUID getLastAttackerUUID();
    void setLastAttackerUUID(@Nullable UUID uuid);
}
