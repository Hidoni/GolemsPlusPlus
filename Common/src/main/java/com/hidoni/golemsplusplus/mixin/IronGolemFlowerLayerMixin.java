package com.hidoni.golemsplusplus.mixin;

import com.hidoni.golemsplusplus.entity.BlockHoldingMob;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.IronGolemFlowerLayer;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IronGolemFlowerLayer.class)
public class IronGolemFlowerLayerMixin {
    private IronGolem instance;
    @ModifyVariable(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/IronGolem;FFFFFF)V", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private IronGolem storeSnowGolemInstance(IronGolem ironGolem) {
        this.instance = ironGolem;
        return ironGolem;
    }

    // Seems like there is a 1 tick delay between the data tracker being updated to it being sent to the client, so if the data still hasn't synced, don't render.
    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/IronGolem;FFFFFF)V", at = @At("HEAD"), cancellable = true)
    private void cancelIfNoHeldFlowerYet(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, IronGolem livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (((BlockHoldingMob)this.instance).getHeldBlock() == null) {
            ci.cancel();
        }
    }

    @ModifyArg(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/IronGolem;FFFFFF)V", at=@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/BlockRenderDispatcher;renderSingleBlock(Lnet/minecraft/world/level/block/state/BlockState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V"))
    private BlockState setRenderedFlower(BlockState blockState) {
        return ((BlockHoldingMob)this.instance).getHeldBlock();
    }
}
