package com.hidoni.golemimprovements.mixin;

import com.hidoni.golemimprovements.entity.HeadItemWearingMob;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SnowGolem.class)
public class SnowGolemFabricMixin {
    @ModifyArg(method = "shear(Lnet/minecraft/sounds/SoundSource;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;<init>(Lnet/minecraft/world/level/ItemLike;)V"), index = 0)
    private ItemLike changeDroppedHeadItem(ItemLike unused) {
        ItemStack headItem = ((HeadItemWearingMob) this).getHeadItem();
        ((HeadItemWearingMob) this).setHeadItem(ItemStack.EMPTY);
        return headItem.getItem();
    }
}
