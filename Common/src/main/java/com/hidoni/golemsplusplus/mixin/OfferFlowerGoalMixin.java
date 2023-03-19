package com.hidoni.golemsplusplus.mixin;

import com.hidoni.golemsplusplus.entity.BlockHoldingMob;
import com.hidoni.golemsplusplus.world.level.FakeWorldGenLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.OfferFlowerGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorPreset;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorPresets;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

@Mixin(OfferFlowerGoal.class)
public class OfferFlowerGoalMixin {
    @Shadow @Final private IronGolem golem;

    private Player player;

    @Shadow @Final private static TargetingConditions OFFER_TARGER_CONTEXT;

    @Inject(method = "canUse()Z", at = @At(value = "RETURN", ordinal = 2), cancellable = true)
    private void changeNearestEntityTypeToPlayerIfPlayerOwned(CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() && this.golem.isPlayerCreated()) {
            this.player = this.golem.level.getNearestEntity(Player.class, OFFER_TARGER_CONTEXT, this.golem, this.golem.getX(), this.golem.getY(), this.golem.getZ(), this.golem.getBoundingBox().inflate(6.0D, 2.0D, 6.0D));
            cir.setReturnValue(this.player != null);
        }
    }

    @NotNull
    private BlockState getRandomFlowerForBiome() {
        RegistryAccess registryAccess = this.golem.level.registryAccess();
        Optional<Registry<DimensionType>> dimensionTypeRegistry = registryAccess.registry(Registries.DIMENSION_TYPE);
        if (dimensionTypeRegistry.isEmpty()) {
            return Blocks.POPPY.defaultBlockState();
        }
        DimensionType dimensionType = dimensionTypeRegistry.get().get(BuiltinDimensionTypes.OVERWORLD);
        if (dimensionType == null) {
            return Blocks.POPPY.defaultBlockState();
        }
        FakeWorldGenLevel fakeWorldGenLevel = new FakeWorldGenLevel(dimensionType);
        Optional<Registry<FlatLevelGeneratorPreset>> generatorSettingsRegistry = registryAccess.registry(Registries.FLAT_LEVEL_GENERATOR_PRESET);
        if (generatorSettingsRegistry.isEmpty()) {
            return Blocks.POPPY.defaultBlockState();
        }
        FlatLevelGeneratorPreset flatLevelGeneratorPreset = generatorSettingsRegistry.get().get(FlatLevelGeneratorPresets.CLASSIC_FLAT);
        if (flatLevelGeneratorPreset == null) {
            return Blocks.POPPY.defaultBlockState();
        }
        ChunkGenerator chunkGenerator = new FlatLevelSource(flatLevelGeneratorPreset.settings());
        RandomSource random = this.golem.level.getRandom();
        List<ConfiguredFeature<?, ?>> flowerFeatures = this.golem.level.getBiome(this.golem.blockPosition()).value().getGenerationSettings().getFlowerFeatures();
        if (flowerFeatures.isEmpty()) {
            return Blocks.POPPY.defaultBlockState();
        }
        ConfiguredFeature<?, ?> flowerFeature;
        if (flowerFeatures.size() != 1) {
            flowerFeature = flowerFeatures.get(this.golem.getRandom().nextInt(flowerFeatures.size()));
        } else {
            flowerFeature = flowerFeatures.get(0);
        }
        flowerFeature.place(fakeWorldGenLevel, chunkGenerator, random, new BlockPos(0, 0, 0));
        Optional<BlockState> flower = fakeWorldGenLevel.getPlacedBlocks().stream().filter((blockState -> blockState.is(BlockTags.SMALL_FLOWERS))).findFirst();
        return flower.orElseGet(Blocks.POPPY::defaultBlockState);
    }

    @Inject(method = "start()V", at=@At("HEAD"))
    private void generateFlowerForGolem(CallbackInfo ci) {
        BlockState flower = getRandomFlowerForBiome();
        ((BlockHoldingMob)this.golem).setHeldBlock(flower);
    }

    @Inject(method = "stop()V", at=@At("TAIL"))
    private void stopTrackingPlayer(CallbackInfo ci) {
        this.player = null;
        ((BlockHoldingMob)this.golem).setHeldBlock(null);
    }

    @ModifyArg(method = "tick()V", at=@At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/control/LookControl;setLookAt(Lnet/minecraft/world/entity/Entity;FF)V"))
    private Entity modifyLookAtToTrackedPlayer(Entity originalTarget) {
        if (originalTarget == null) {
            return this.player;
        }
        return originalTarget;
    }
}
