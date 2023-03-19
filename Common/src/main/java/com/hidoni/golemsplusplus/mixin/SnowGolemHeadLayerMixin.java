package com.hidoni.golemsplusplus.mixin;

import com.hidoni.golemsplusplus.entity.ItemHoldingMob;
import net.minecraft.client.renderer.entity.layers.SnowGolemHeadLayer;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SnowGolemHeadLayer.class)
public class SnowGolemHeadLayerMixin {
    private SnowGolem instance;

    @ModifyVariable(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/SnowGolem;FFFFFF)V", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private SnowGolem storeSnowGolemInstance(SnowGolem snowGolem) {
        this.instance = snowGolem;
        return snowGolem;
    }

    @ModifyVariable(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/SnowGolem;FFFFFF)V", at = @At("STORE"), ordinal = 0)
    private ItemStack setRenderedSnowGolemHeadItem(ItemStack unused) {
        return ((ItemHoldingMob) this.instance).getHeldItem();
    }

    @ModifyVariable(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/SnowGolem;FFFFFF)V", at = @At("STORE"), ordinal = 0)
    private BlockState setRenderedSnowGolemHeadItemBlockState(BlockState fallback) {
        ItemStack headItem = ((ItemHoldingMob) this.instance).getHeldItem();
        if (headItem.getItem() instanceof BlockItem blockItem) {
            return blockItem.getBlock().defaultBlockState();
        }
        return fallback;
    }

    @ModifyVariable(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/SnowGolem;FFFFFF)V", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private int setPackedLightLevel(int packedLightIn) {
        ItemStack headItem = ((ItemHoldingMob) this.instance).getHeldItem();
        if (headItem.getItem() instanceof BlockItem blockItem) {
            int lightEmission = blockItem.getBlock().defaultBlockState().getLightEmission();
            packedLightIn |= (lightEmission << 4) | lightEmission;
        }
        return packedLightIn;
    }
}
