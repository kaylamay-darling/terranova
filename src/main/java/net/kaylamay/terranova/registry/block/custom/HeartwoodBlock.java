package net.kaylamay.terranova.registry.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HeartwoodBlock extends RotatedPillarBlock {
    private static final VoxelShape Y_SHAPE = Block.box(4, 0, 4, 12, 16, 12);
    private static final VoxelShape X_SHAPE = Block.box(0,4,4,16,12,12);
    private static final VoxelShape Z_SHAPE = Block.box(4,4,0,12,12,16);

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(AXIS)) {
            case Y -> Y_SHAPE;
            case X -> X_SHAPE;
            case Z -> Z_SHAPE;
        };
    }

    public HeartwoodBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(AXIS, Direction.Axis.Y));
    }
}