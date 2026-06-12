package net.kaylamay.terranova.registry.block.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jspecify.annotations.Nullable;

public class ResinWallTorchBlock extends WallTorchBlock implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;

    // Required field for the codec to access during serialization/deserialization
    private final SimpleParticleType particleType;

    public static final MapCodec<WallTorchBlock> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    BuiltInRegistries.PARTICLE_TYPE.byNameCodec().fieldOf("particle").forGetter(block -> ((ResinWallTorchBlock) block).particleType),
                    propertiesCodec()
            ).apply(instance, (particle, properties) -> new ResinWallTorchBlock((SimpleParticleType) particle, properties))
    );

    public ResinWallTorchBlock(SimpleParticleType particle, Properties properties) {
        super(particle, properties);
        this.particleType = particle;
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false));
    }

    @Override
    public MapCodec<WallTorchBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        if (state == null) return null;
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        boolean waterlogged = fluidState.getType() == Fluids.WATER;
        return state.setValue(WATERLOGGED, waterlogged);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        if (state.getValue(WATERLOGGED)) {
            ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return directionToNeighbour.getOpposite() == state.getValue(FACING) && !state.canSurvive(level, pos)
                ? Blocks.AIR.defaultBlockState()
                : state;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (!state.getValue(WATERLOGGED)) {
            super.animateTick(state, level, pos, random);
        } else {
            Direction direction = state.getValue(FACING);
            Direction opposite = direction.getOpposite();
            double x = pos.getX() + 0.5 + 0.27 * opposite.getStepX();
            double y = pos.getY() + 0.7 + 0.22;
            double z = pos.getZ() + 0.5 + 0.27 * opposite.getStepZ();
            level.addParticle(ParticleTypes.BUBBLE, x, y, z, 0, 0.05, 0);
        }
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }
}