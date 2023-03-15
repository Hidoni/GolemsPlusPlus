package com.hidoni.golemsplusplus.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.OfferFlowerGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OfferFlowerGoal.class)
public class OfferFlowerGoalMixin {
    @Shadow @Final private IronGolem golem;

    private Player player;

    @Shadow @Final private static TargetingConditions OFFER_TARGER_CONTEXT;

    @Inject(method = "canUse()Z", at = @At(value = "RETURN", ordinal = 2), cancellable = true)
    private void changeNearestEntityTypeToPlayerIfPlayerOwned(CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() && this.golem.isPlayerCreated()) {
            this.player = this.golem.level.getNearestEntity(Player.class, OFFER_TARGER_CONTEXT, this.golem, this.golem.getX(), this.golem.getY(), this.golem.getZ(), this.golem.getBoundingBox().inflate(6.0D, 2.0D, 6.0D));
            cir.setReturnValue(this.player != null);
        }
    }

    @Inject(method = "stop()V", at=@At("TAIL"))
    private void stopTrackingPlayer(CallbackInfo ci) {
        this.player = null;
    }

    @ModifyArg(method = "tick()V", at=@At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/control/LookControl;setLookAt(Lnet/minecraft/world/entity/Entity;FF)V"))
    private Entity modifyLookAtToTrackedPlayer(Entity originalTarget) {
        if (originalTarget == null) {
            return this.player;
        }
        return originalTarget;
    }
}
