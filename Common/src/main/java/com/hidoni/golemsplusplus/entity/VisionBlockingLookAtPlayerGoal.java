package com.hidoni.golemsplusplus.entity;

import com.hidoni.golemsplusplus.tags.ModItemTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;

public class VisionBlockingLookAtPlayerGoal extends LookAtPlayerGoal {
    private final ItemHoldingMob itemHoldingMob;

    public VisionBlockingLookAtPlayerGoal(Mob mob, Class<? extends LivingEntity> lookAtType, float f) {
        super(mob, lookAtType, f);
        if (!(this.mob instanceof ItemHoldingMob itemHoldingMob)) {
            throw new IllegalArgumentException("RangedAttackMob must implement HeadItemWearingMob!");
        }
        this.itemHoldingMob = itemHoldingMob;
    }

    @Override
    public boolean canUse() {
        return !this.itemHoldingMob.getHeldItem().is(ModItemTags.VISION_BLOCKING_HEAD_ITEMS_TAG) && super.canUse();
    }
}
