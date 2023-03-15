package com.hidoni.golemsplusplus.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.OfferFlowerGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IronGolem.class)
public abstract class IronGolemMixin extends AbstractGolem {
    protected IronGolemMixin(EntityType<? extends AbstractGolem> $$0, Level $$1) {
        super($$0, $$1);
    }

    @Shadow
    public abstract int getOfferFlowerTick();

    private static final ItemStack HELD_ITEM = new ItemStack(Items.POPPY);

    @Inject(method = "mobInteract(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;", at = @At("HEAD"), cancellable = true)
    private void handlePlayerTakeFlower(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        if (this.getOfferFlowerTick() == 0) {
            return;
        }
        if (!this.level.isClientSide()) {
            WrappedGoal wrappedOfferFlowerGoal = this.goalSelector.getRunningGoals().filter((WrappedGoal goal) -> goal.getGoal() instanceof OfferFlowerGoal).findFirst().orElseThrow();
            wrappedOfferFlowerGoal.stop();
        }
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (itemStack.equals(ItemStack.EMPTY)) {
            player.setItemInHand(interactionHand, HELD_ITEM);
            cir.setReturnValue(InteractionResult.sidedSuccess(this.level.isClientSide()));
        }
    }
}
