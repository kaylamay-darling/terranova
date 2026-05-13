package net.kaylamay.terranova.registry.block.custom;

import net.kaylamay.terranova.property.CreepingMushroomSize;
import net.kaylamay.terranova.util.ModItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class CreepingMushroomBlock extends Block {

    private static final VoxelShape SHAPE_NORTH = Block.box(0, 0, 15, 16, 16, 16); // back against south wall (log side)
    private static final VoxelShape SHAPE_SOUTH = Block.box(0, 0, 0,  16, 16, 1);
    private static final VoxelShape SHAPE_EAST  = Block.box(0, 0, 0,  1,  16, 16);
    private static final VoxelShape SHAPE_WEST  = Block.box(15, 0, 0, 16, 16, 16);

    public static final EnumProperty<Direction> FACING = EnumProperty.create("facing", Direction.class, Direction.Plane.HORIZONTAL);
    public static final EnumProperty<CreepingMushroomSize> SIZE = EnumProperty.create("size", CreepingMushroomSize.class);

    public CreepingMushroomBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(SIZE, CreepingMushroomSize.SMALL));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, SIZE);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction clickedFace = context.getClickedFace();

        if (!clickedFace.getAxis().isHorizontal()) {
            clickedFace = context.getHorizontalDirection().getOpposite();
        }

        BlockState state = this.defaultBlockState().setValue(FACING, clickedFace);
        return state.canSurvive(context.getLevel(), context.getClickedPos()) ? state : null;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos supportPos = pos.relative(state.getValue(FACING).getOpposite());
        return level.getBlockState(supportPos).is(BlockTags.LOGS);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos,
                                     Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState,
                                     RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case EAST  -> SHAPE_EAST;
            case WEST  -> SHAPE_WEST;
            default    -> SHAPE_NORTH;
        };
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        CreepingMushroomSize size = state.getValue(SIZE);
        if (size.canGrow() && random.nextFloat() < 0.1f) {
            level.setBlock(pos, state.setValue(SIZE, size.grow()), 2);
        }
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos,
                                          Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!itemStack.is(this.asItem())) {
            return InteractionResult.PASS;
        }

        CreepingMushroomSize size = state.getValue(SIZE);
        if (!size.canGrow()) {
            return InteractionResult.FAIL;
        }

        if (!level.isClientSide()) {
            level.setBlock(pos, state.setValue(SIZE, size.grow()), 2);
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
        }
        return InteractionResult.SUCCESS;
    }
}