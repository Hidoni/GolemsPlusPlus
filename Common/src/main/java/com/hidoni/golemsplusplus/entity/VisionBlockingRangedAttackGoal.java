package com.hidoni.golemsplusplus.entity;

import com.hidoni.golemsplusplus.tags.ModItemTags;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;

public class VisionBlockingRangedAttackGoal extends RangedAttackGoal {
    private final ItemHoldingMob itemHoldingMob;

    public VisionBlockingRangedAttackGoal(RangedAttackMob rangedAttackMob, double d, int i, float f) {
        super(rangedAttackMob, d, i, f);
        if (!(rangedAttackMob instanceof ItemHoldingMob itemHoldingMob)) {
            throw new IllegalArgumentException("RangedAttackMob must implement HeadItemWearingMob!");
        }
        this.itemHoldingMob = itemHoldingMob;
    }

    @Override
    public boolean canUse() {
        return !this.itemHoldingMob.getHeldItem().is(ModItemTags.VISION_BLOCKING_HEAD_ITEMS_TAG) && super.canUse();
    }
}
