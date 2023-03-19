package com.hidoni.golemsplusplus.mixin;

import com.hidoni.golemsplusplus.entity.ItemHoldingMob;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CarvedPumpkinBlock.class)
public class CarvedPumpkinBlockMixin {
    @Inject(method = "spawnGolemInWorld(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/pattern/BlockPattern$BlockPatternMatch;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/BlockPos;)V", at = @At("HEAD"))
    private static void setSnowGolemHeadItem(Level level, BlockPattern.BlockPatternMatch blockPatternMatch, Entity entity, BlockPos blockPos, CallbackInfo ci) {
        if (entity instanceof SnowGolem snowGolem) {
            ItemStack headItem = new ItemStack(level.getBlockState(blockPatternMatch.getFrontTopLeft()).getBlock().asItem());
            ((ItemHoldingMob) snowGolem).setHeldItem(headItem);
        }
    }
}
