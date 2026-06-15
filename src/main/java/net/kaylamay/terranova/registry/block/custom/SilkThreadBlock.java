package net.kaylamay.terranova.registry.block.custom;

import net.kaylamay.terranova.registry.entity.ModEntities;
import net.kaylamay.terranova.registry.entity.custom.GlowwormEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class SilkThreadBlock extends Block {

    public static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 16, 14);

    public SilkThreadBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos above = pos.above();
        return level.getBlockState(above).isFaceSturdy(level, above, Direction.DOWN);
    }

    @Override
    public BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess,
                                  BlockPos pos, Direction direction, BlockPos neighborPos,
                                  BlockState neighborState, RandomSource random) {
        if (direction == Direction.UP && !state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, level, tickAccess, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos())
                ? this.defaultBlockState()
                : null;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        List<GlowwormEntity> nearby = level.getEntitiesOfClass(GlowwormEntity.class,
                new AABB(pos).inflate(8));
        if (nearby.size() >= 3) return;
        BlockPos spawnPos = pos.below();
        if (level.getBlockState(spawnPos).isAir()) {
            GlowwormEntity glowworm = ModEntities.GLOWWORM.get().create(level, EntitySpawnReason.NATURAL);
            if (glowworm != null) {
                glowworm.snapTo(spawnPos.getX() + 0.5, spawnPos.getY() + 0.5, spawnPos.getZ() + 0.5);
                level.addFreshEntity(glowworm);
            }
        }
    }
}
