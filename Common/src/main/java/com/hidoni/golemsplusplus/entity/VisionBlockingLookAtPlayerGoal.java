package com.hidoni.golemsplusplus.entity;

import com.hidoni.golemsplusplus.tags.ModItemTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;

public class VisionBlockingLookAtPlayerGoal extends LookAtPlayerGoal {
    private final HeadItemWearingMob headItemWearingMob;

    public VisionBlockingLookAtPlayerGoal(Mob mob, Class<? extends LivingEntity> lookAtType, float f) {
        super(mob, lookAtType, f);
        if (!(this.mob instanceof HeadItemWearingMob headItemWearingMob)) {
            throw new IllegalArgumentException("RangedAttackMob must implement HeadItemWearingMob!");
        }
        this.headItemWearingMob = headItemWearingMob;
    }

    @Override
    public boolean canUse() {
        return !this.headItemWearingMob.getHeadItem().is(ModItemTags.VISION_BLOCKING_HEAD_ITEMS_TAG) && super.canUse();
    }
}
