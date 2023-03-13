package com.hidoni.golemimprovements.mixin;

import com.hidoni.golemimprovements.entity.HeadItemWearingMob;
import com.hidoni.golemimprovements.entity.VisionBlockingRangedAttackGoal;
import com.hidoni.golemimprovements.tags.ItemTags;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SnowGolem.class)
public abstract class SnowGolemMixin extends AbstractGolem implements Shearable, RangedAttackMob, HeadItemWearingMob {

    private static final ItemStack DEFAULT_PUMPKIN = new ItemStack(Items.CARVED_PUMPKIN);

    @Shadow
    public abstract boolean hasPumpkin();

    @Shadow
    public abstract void setPumpkin(boolean $$0);

    protected SnowGolemMixin(EntityType<? extends AbstractGolem> entityType, Level level) {
        super(entityType, level);
    }

    public void setHeadItem(ItemStack headItem) {
        this.setItemSlot(EquipmentSlot.HEAD, headItem);
    }

    public ItemStack getHeadItem() {
        ItemStack headItem = this.getItemBySlot(EquipmentSlot.HEAD);
        if (headItem.equals(ItemStack.EMPTY) && this.hasPumpkin()) {
            return DEFAULT_PUMPKIN;
        }
        return headItem;
    }

    @Inject(at = @At("HEAD"), method = "mobInteract(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;", cancellable = true)
    private void handlePumpkinRightClick(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (itemStack.is(ItemTags.SNOW_GOLEM_HEAD_ITEMS_TAG) && this.isAlive() && !this.hasPumpkin()) {
            SoundEvent soundEvent = SoundEvents.WOOD_PLACE;
            if (itemStack.getItem() instanceof BlockItem blockItem) {
                soundEvent = blockItem.getBlock().defaultBlockState().getSoundType().getPlaceSound();
            }
            this.level.playSound(null, this, soundEvent, SoundSource.PLAYERS, 1.0F, 1.0F);
            this.setPumpkin(true);
            setHeadItem(itemStack.copyWithCount(1));
            itemStack.shrink(1);
            cir.setReturnValue(InteractionResult.sidedSuccess(this.level.isClientSide));
        }
    }

    @Redirect(method = "registerGoals()V", at = @At(value = "NEW", target = "(Lnet/minecraft/world/entity/monster/RangedAttackMob;DIF)Lnet/minecraft/world/entity/ai/goal/RangedAttackGoal;"))
    private RangedAttackGoal createVisionBlockingRangedAttackGoal(RangedAttackMob mob, double d, int i, float f) {
        return new VisionBlockingRangedAttackGoal(mob, d, i, f);
    }
}