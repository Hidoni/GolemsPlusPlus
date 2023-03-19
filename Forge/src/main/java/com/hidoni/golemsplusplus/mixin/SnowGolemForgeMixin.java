package com.hidoni.golemsplusplus.mixin;

import com.hidoni.golemsplusplus.entity.ItemHoldingMob;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SnowGolem.class)
public class SnowGolemForgeMixin {
    // Forge completely ignores the original SnowGolem class's shear function, so we need 2 separate mixins for this.
    @ModifyArg(method = "onSheared", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;<init>(Lnet/minecraft/world/level/ItemLike;)V"), index = 0)
    private ItemLike changeDroppedHeadItem(ItemLike defaultHeadItem) {
        ItemStack headItem = ((ItemHoldingMob)this).getHeldItem();
        if (headItem.equals(ItemStack.EMPTY)) { // Could happen with a Snow Golem summoned through commands/spawn eggs.
            return defaultHeadItem;
        }
        ((ItemHoldingMob)this).setHeldItem(ItemStack.EMPTY);
        return headItem.getItem();
    }
}
