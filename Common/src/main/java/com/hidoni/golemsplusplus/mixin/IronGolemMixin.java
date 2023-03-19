package com.hidoni.golemsplusplus.mixin;

import com.hidoni.golemsplusplus.entity.BlockHoldingMob;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.OfferFlowerGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.Optional;

@Mixin(IronGolem.class)
public abstract class IronGolemMixin extends AbstractGolem implements BlockHoldingMob {
    private static EntityDataAccessor<Optional<BlockState>> DATA_HELD_FLOWER;
    protected IronGolemMixin(EntityType<? extends AbstractGolem> $$0, Level $$1) {
        super($$0, $$1);
    }

    @Shadow
    public abstract int getOfferFlowerTick();

    @Inject(method = "mobInteract(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;", at = @At("HEAD"), cancellable = true)
    private void handlePlayerTakeFlower(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        if (this.getOfferFlowerTick() == 0) {
            return;
        }
        ItemStack itemStack = player.getItemInHand(interactionHand);
        BlockState heldBlock = getHeldBlock();
        if (itemStack.isEmpty() && heldBlock != null) {
            if (!this.level.isClientSide()) {
                WrappedGoal wrappedOfferFlowerGoal = this.goalSelector.getRunningGoals().filter((WrappedGoal goal) -> goal.getGoal() instanceof OfferFlowerGoal).findFirst().orElseThrow();
                wrappedOfferFlowerGoal.stop();
            }
            player.setItemInHand(interactionHand, new ItemStack(heldBlock.getBlock().asItem()));
            cir.setReturnValue(InteractionResult.sidedSuccess(this.level.isClientSide()));
        }
    }

    @SuppressWarnings("WrongEntityDataParameterClass")
    @Inject(method = "<clinit>", at=@At("TAIL"))
    private static void initializeHeldFlowerDataAccessor(CallbackInfo ci) {
        DATA_HELD_FLOWER = SynchedEntityData.defineId(IronGolem.class, EntityDataSerializers.BLOCK_STATE);
    }

    @Inject(method = "defineSynchedData()V", at=@At("TAIL"))
    private void defineSynchedDataForHeldFlower(CallbackInfo ci) {
        this.entityData.define(DATA_HELD_FLOWER, Optional.empty());
    }

    @Override
    public void setHeldBlock(@Nullable BlockState blockState) {
        this.entityData.set(DATA_HELD_FLOWER, Optional.ofNullable(blockState));
    }

    @Override
    public BlockState getHeldBlock() {
        return this.entityData.get(DATA_HELD_FLOWER).orElse(null);
    }
}
