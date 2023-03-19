package com.hidoni.golemsplusplus.world.level;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.ticks.LevelTickAccess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


/*
    The code for generating flowers in a biome is insanely complex and dynamic, being modifiable by both mods and
    data packs with even the vanilla implementation resulting in seven nested class calls just to generate a flower patch
    The solution to this is to implement a fake WorldGenLevel which only has the functions strictly necessary to generate
    a flower patch, while also keeping track of the flowers that were generated, so that they can be referenced by the
    calling code. This allows us to generate a random flower for Iron Golems to hold instead of limiting them exclusively
    to poppies.
 */
@SuppressWarnings("NullableProblems")
public class FakeWorldGenLevel implements WorldGenLevel {
    private final DimensionType dimensionType;
    private final List<BlockState> placedBlocks;

    public FakeWorldGenLevel(DimensionType dimensionType) {
        this.dimensionType = dimensionType;
        this.placedBlocks = new ArrayList<>();
    }

    public List<BlockState> getPlacedBlocks() {
        return placedBlocks;
    }

    @NotNull
    private static FakeWorldGenLevelException getUnexpectedFunctionUseException() {
        return new FakeWorldGenLevelException("Unexpected use of function in FakeWorldGenLevel! This is probably an incompatibility with a mod, please report this on the Golems++ GitHub repo!");
    }

    @Override
    public long getSeed() {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public ServerLevel getLevel() {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public long nextSubTickCount() {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public LevelTickAccess<Block> getBlockTicks() {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public LevelTickAccess<Fluid> getFluidTicks() {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public LevelData getLevelData() {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public DifficultyInstance getCurrentDifficultyAt(BlockPos pos) {
        throw getUnexpectedFunctionUseException();
    }

    @Nullable
    @Override
    public MinecraftServer getServer() {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public ChunkSource getChunkSource() {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public RandomSource getRandom() {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public void playSound(@Nullable Player var1, BlockPos var2, SoundEvent var3, SoundSource var4, float var5, float var6) {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public void addParticle(ParticleOptions var1, double var2, double var4, double var6, double var8, double var10, double var12) {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public void levelEvent(@Nullable Player var1, int var2, BlockPos var3, int var4) {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public void gameEvent(GameEvent var1, Vec3 var2, GameEvent.Context var3) {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public float getShade(Direction var1, boolean var2) {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public LevelLightEngine getLightEngine() {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public WorldBorder getWorldBorder() {
        throw getUnexpectedFunctionUseException();
    }

    @Nullable
    @Override
    public BlockEntity getBlockEntity(BlockPos var1) {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public BlockState getBlockState(BlockPos pos) {
        if (pos.getY() == 0) {
            return Blocks.AIR.defaultBlockState();
        }
        return Blocks.DIRT.defaultBlockState();
    }

    @Override
    public FluidState getFluidState(BlockPos var1) {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public List<Entity> getEntities(@Nullable Entity var1, AABB var2, Predicate<? super Entity> var3) {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public <T extends Entity> List<T> getEntities(EntityTypeTest<Entity, T> var1, AABB var2, Predicate<? super T> var3) {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public List<? extends Player> players() {
        throw getUnexpectedFunctionUseException();
    }

    @Nullable
    @Override
    public ChunkAccess getChunk(int var1, int var2, ChunkStatus var3, boolean var4) {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public int getHeight(Heightmap.Types var1, int var2, int var3) {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public int getSkyDarken() {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public BiomeManager getBiomeManager() {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public Holder<Biome> getUncachedNoiseBiome(int var1, int var2, int var3) {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public boolean isClientSide() {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public int getSeaLevel() {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public @NotNull DimensionType dimensionType() {
        return this.dimensionType;
    }

    @Override
    public RegistryAccess registryAccess() {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public FeatureFlagSet enabledFeatures() {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public boolean isStateAtPosition(BlockPos var1, Predicate<BlockState> var2) {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public boolean isFluidAtPosition(BlockPos var1, Predicate<FluidState> var2) {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public boolean setBlock(BlockPos pos, BlockState state, int flags, int recursionLeft) {
        this.placedBlocks.add(state);
        return true;
    }

    @Override
    public boolean removeBlock(BlockPos var1, boolean var2) {
        throw getUnexpectedFunctionUseException();
    }

    @Override
    public boolean destroyBlock(BlockPos var1, boolean var2, @Nullable Entity var3, int var4) {
        throw getUnexpectedFunctionUseException();
    }

    static class FakeWorldGenLevelException extends RuntimeException {
        public FakeWorldGenLevelException(String message) {
            super(message);
        }
    }
}
