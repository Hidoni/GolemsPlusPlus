package com.hidoni.golemsplusplus.mixin;

import com.hidoni.golemsplusplus.entity.ItemHoldingMob;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SnowGolem.class)
public class SnowGolemFabricMixin {
    @ModifyArg(method = "shear(Lnet/minecraft/sounds/SoundSource;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;<init>(Lnet/minecraft/world/level/ItemLike;)V"), index = 0)
    private ItemLike changeDroppedHeadItem(ItemLike defaultHeadItem) {
        ItemStack headItem = ((ItemHoldingMob) this).getHeldItem();
        if (headItem.isEmpty()) { // Could happen with a Snow Golem summoned through commands/spawn eggs.
            return defaultHeadItem;
        }
        ((ItemHoldingMob) this).setHeldItem(ItemStack.EMPTY);
        return headItem.getItem();
    }
}
