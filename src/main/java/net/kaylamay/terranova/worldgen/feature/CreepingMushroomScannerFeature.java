package net.kaylamay.terranova.worldgen.feature;

import com.mojang.serialization.Codec;
import net.kaylamay.terranova.property.CreepingMushroomSize;
import net.kaylamay.terranova.registry.block.ModBlocks;
import net.kaylamay.terranova.registry.block.custom.CreepingMushroomBlock;
import net.kaylamay.terranova.util.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class CreepingMushroomScannerFeature extends Feature<NoneFeatureConfiguration> {

    private static final CreepingMushroomSize[] SIZES = CreepingMushroomSize.values();

    public CreepingMushroomScannerFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    private boolean tryPlacementDecoration(WorldGenLevel level, BlockPos pos, BlockState state) {
        if (level.getBlockState(pos).canBeReplaced()) {
            level.setBlock(pos, state, 2);
            return true;
        }
        return false;
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        int horizontalRadius = 4;
        int verticalHeight = 20;

        for (int x = -horizontalRadius; x <= horizontalRadius; x++) {
            for (int z = -horizontalRadius; z <= horizontalRadius; z++) {
                for (int y = -2; y <= verticalHeight; y++) {

                    BlockPos currentCheckPos = origin.offset(x, y, z);
                    BlockState currentBlockState = level.getBlockState(currentCheckPos);

                    if (!currentBlockState.is(BlockTags.LOGS) && !currentBlockState.is(ModBlockTags.HOLLOW_LOGS)) {
                        continue;
                    }

                    if (random.nextFloat() >= 0.15f) {
                        continue;
                    }

                    Direction side = Direction.Plane.HORIZONTAL.getRandomDirection(random);
                    BlockPos mushroomTargetPos = currentCheckPos.relative(side);

                    BlockState mushroomState = (random.nextBoolean()
                            ? ModBlocks.RED_CREEPING_MUSHROOM.get()
                            : ModBlocks.BROWN_CREEPING_MUSHROOM.get())
                            .defaultBlockState()
                            .setValue(CreepingMushroomBlock.FACING, side)
                            .setValue(CreepingMushroomBlock.SIZE, SIZES[random.nextInt(SIZES.length)]);

                    if (mushroomState.canSurvive(level, mushroomTargetPos)) {
                        tryPlacementDecoration(level, mushroomTargetPos, mushroomState);
                    }
                }
            }
        }
        return true;
    }
}