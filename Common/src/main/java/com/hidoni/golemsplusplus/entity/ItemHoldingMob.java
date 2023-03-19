package com.hidoni.golemsplusplus.entity;

import net.minecraft.world.item.ItemStack;

public interface ItemHoldingMob {
    void setHeldItem(ItemStack headItem);

    ItemStack getHeldItem();

}
