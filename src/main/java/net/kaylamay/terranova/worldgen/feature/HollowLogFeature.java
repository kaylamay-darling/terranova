package net.kaylamay.terranova.worldgen.feature;

import com.mojang.serialization.Codec;
import net.kaylamay.terranova.registry.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class HollowLogFeature extends Feature<HollowLogFeatureConfiguration> {
    public HollowLogFeature(Codec<HollowLogFeatureConfiguration> codec) {
        super(codec);
    }

    private boolean tryPlacementLog(WorldGenLevel level, BlockPos pos, BlockState state) {
        if (level.getBlockState(pos).canBeReplaced() && !level.getBlockState(pos.below()).canBeReplaced()) {
            level.setBlock(pos, state, 2);
            return true;
        }
        return false;
    }

    private boolean tryPlacementDecoration(WorldGenLevel level, BlockPos pos, BlockState state) {
        if (level.getBlockState(pos).canBeReplaced()) {
            level.setBlock(pos, state, 2);
            return true;
        }
        return false;
    }

    @Override
    public boolean place(FeaturePlaceContext<HollowLogFeatureConfiguration> featurePlaceContext) {
        WorldGenLevel level = featurePlaceContext.level();
        BlockPos origin = featurePlaceContext.origin();
        RandomSource random = featurePlaceContext.random();

        Direction dir = random.nextBoolean() ? Direction.NORTH : Direction.EAST;
        int length = 2 + random.nextInt(5);

        BlockState hollowState = featurePlaceContext.config().log().getState(level, random, origin)
                .setValue(RotatedPillarBlock.AXIS, dir.getAxis());

        for (int i = 0; i < length; i++) {
            BlockPos pos = origin.relative(dir, i);
            boolean isHollowLog = tryPlacementLog(level, pos, hollowState);

            if (isHollowLog) {
                float vegetationRoll = random.nextFloat();
                float vineRoll = random.nextFloat();

                if (vegetationRoll < 0.1) {
                    tryPlacementDecoration(level, pos.relative(Direction.UP, 1), Blocks.BROWN_MUSHROOM.defaultBlockState());
                } else if (vegetationRoll < 0.2) {
                    tryPlacementDecoration(level, pos.relative(Direction.UP, 1), Blocks.RED_MUSHROOM.defaultBlockState());
                } else if (vegetationRoll < 0.3) {
                    tryPlacementDecoration(level, pos.relative(Direction.UP, 1), Blocks.FERN.defaultBlockState());
                } else if (vegetationRoll < 0.4) {
                    tryPlacementDecoration(level, pos.relative(Direction.UP, 1), Blocks.MOSS_CARPET.defaultBlockState());
                }

                if (vineRoll < 0.6) {
                    Direction side = Direction.Plane.HORIZONTAL.getRandomDirection(random);
                    BlockPos vinePos = pos.relative(side);

                    tryPlacementDecoration(level, vinePos, Blocks.VINE.defaultBlockState().setValue(VineBlock.getPropertyForFace(side.getOpposite()), true));
                }

            }
        }
        return true;
    }
}
