package net.kaylamay.terranova.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class SnagFeature extends Feature<HollowLogFeatureConfiguration> {
    public SnagFeature(Codec<HollowLogFeatureConfiguration> codec) {
        super(codec);
    }

    private boolean tryPlacementLog(WorldGenLevel level, BlockPos origin, BlockPos pos, BlockState state) {
        if ((level.getBlockState(pos).canBeReplaced() || !level.getBlockState(pos).isCollisionShapeFullBlock(level, pos))) {
            boolean isSupported = (pos.equals(origin) && level.getBlockState(pos.below()).isCollisionShapeFullBlock(level, pos.below())) || level.getBlockState(pos.below()).is(state.getBlock());

            if (isSupported) {

                level.setBlock(pos, state, 2);
                return true;
            }
            return false;
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

       int length = 2 + random.nextInt(2);

        BlockState hollowState = featurePlaceContext.config().log().getState(level, random, origin)
                .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y);

        for (int i = 0; i < length; i++) {
            BlockPos pos = origin.above(i);
            boolean isHollowLog = tryPlacementLog(level, origin, pos, hollowState);

            if (isHollowLog) {
                int vineTries = 1 + random.nextInt(4);
                for (int j = 0; j < vineTries; j++) {
                    float vineRoll = random.nextFloat();
                    if (vineRoll < 0.6) {
                        Direction side = Direction.Plane.HORIZONTAL.getRandomDirection(random);
                        BlockPos vinePos = pos.relative(side);

                        tryPlacementDecoration(level, vinePos, Blocks.VINE.defaultBlockState().setValue(VineBlock.getPropertyForFace(side.getOpposite()), true));
                    }
                }
            }
        }
        return true;
    }
}
