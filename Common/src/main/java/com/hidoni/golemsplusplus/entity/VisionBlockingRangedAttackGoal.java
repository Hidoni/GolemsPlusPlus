package com.hidoni.golemsplusplus.entity;

import com.hidoni.golemsplusplus.tags.ItemTags;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;

public class VisionBlockingRangedAttackGoal extends RangedAttackGoal {
    private final HeadItemWearingMob headItemWearingMob;

    public VisionBlockingRangedAttackGoal(RangedAttackMob rangedAttackMob, double d, int i, float f) {
        super(rangedAttackMob, d, i, f);
        if (!(rangedAttackMob instanceof HeadItemWearingMob mob)) {
            throw new IllegalArgumentException("RangedAttackMob must implement HeadItemWearingMob!");
        }
        this.headItemWearingMob = mob;
    }

    @Override
    public boolean canUse() {
        return !this.headItemWearingMob.getHeadItem().is(ItemTags.VISION_BLOCKING_HEAD_ITEMS_TAG) && super.canUse();
    }
}
