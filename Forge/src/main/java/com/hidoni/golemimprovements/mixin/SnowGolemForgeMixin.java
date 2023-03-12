package com.hidoni.golemimprovements.mixin;

import com.hidoni.golemimprovements.entity.HeadItemWearingMob;
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
    private ItemLike changeDroppedHeadItem(ItemLike $$0) {
        ItemStack headItem = ((HeadItemWearingMob)this).getHeadItem();
        ((HeadItemWearingMob)this).setHeadItem(ItemStack.EMPTY);
        return headItem.getItem();
    }
}
