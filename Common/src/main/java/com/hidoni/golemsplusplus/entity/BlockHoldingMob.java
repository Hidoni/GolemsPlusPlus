package com.hidoni.golemsplusplus.entity;

import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public interface BlockHoldingMob {
    @Nullable BlockState getHeldBlock();

    void setHeldBlock(@Nullable BlockState blockState);
}
