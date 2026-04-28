package net.kaylamay.terranova.registry.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.TriState;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HollowBlock extends RotatedPillarBlock {
    private static final VoxelShape Y_SHAPE = Shapes.or(
            Block.box(0,0,0,16,16,4),
            Block.box(0,0,12,16,16,16),
            Block.box(0,0,4,4,16,12),
            Block.box(12,0,4,16,16,12)
    );

    private static final VoxelShape X_SHAPE = Shapes.or(
            Block.box(0,0,0,16,4,16),
            Block.box(0,12,0,16,16,16),
            Block.box(0,4,0,16,12,4),
            Block.box(0,4,12,16,12,16)
    );

    private static final VoxelShape Z_SHAPE = Shapes.or(
            Block.box(0,0,0,16,4,16),
            Block.box(0,12,0,16,16,16),
            Block.box(0,4,0,4,12,16),
            Block.box(12,4,0,16,12,16)
    );

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(AXIS)) {
            case Y -> Y_SHAPE;
            case X -> X_SHAPE;
            case Z -> Z_SHAPE;
        };
    }

    @Override
    public TriState canSustainPlant(BlockState state, BlockGetter level, BlockPos soilPosition, Direction facing, BlockState plant) {
        boolean isAllowedPlant = plant.is(Blocks.BROWN_MUSHROOM) || plant.is(Blocks.RED_MUSHROOM) || plant.is(Blocks.FERN) || plant.is(Blocks.MOSS_CARPET);

        if (state.getValue(RotatedPillarBlock.AXIS) != Direction.Axis.Y && isAllowedPlant) {
            return TriState.TRUE;
        }

        return super.canSustainPlant(state, level, soilPosition, facing, plant);
    }

    public HollowBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(AXIS, Direction.Axis.Y));
    }
}
