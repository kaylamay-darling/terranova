package net.kaylamay.terranova.registry.block.custom;

import net.kaylamay.terranova.TerraNova;
import net.kaylamay.terranova.property.CreepingMushroomSize;
import net.kaylamay.terranova.util.ModBlockTags;
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
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;
import org.lwjgl.system.ffm.mapping.Mapping;

import java.util.Properties;

import static net.kaylamay.terranova.property.CreepingMushroomSize.LARGE;

public class CreepingMushroomBlock extends Block {
    private static final VoxelShape NORTH_SHAPE = Block.box(0,0,0,16,16,1);
    private static final VoxelShape SOUTH_SHAPE = Block.box(0,0,15,16,16,16);
    private static final VoxelShape EAST_SHAPE = Block.box(15,0,0,16,16,16);
    private static final VoxelShape WEST_SHAPE = Block.box(0,0,0,1,16,16);

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
        Direction face = context.getClickedFace();

        if (!face.getAxis().isHorizontal()) {
            return null;
        }

        BlockState state = this.defaultBlockState().setValue(FACING, face.getOpposite());
        return state.canSurvive(context.getLevel(), context.getClickedPos()) ? state : null;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction facing = state.getValue(FACING);
        BlockPos supportPos = pos.relative(facing);
        BlockState supportState = level.getBlockState(supportPos);

        if (!supportState.is(ModBlockTags.HOLLOW_LOGS)) {
            return false;
        }

        return facing.getAxis().isHorizontal();
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
            default -> NORTH_SHAPE;
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
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!itemStack.is(ModItemTags.CREEPING_MUSHROOM_SUBSTRATE)) {
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
