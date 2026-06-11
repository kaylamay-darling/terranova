package net.kaylamay.terranova.registry.block.custom;

import com.mojang.serialization.MapCodec;
import net.kaylamay.terranova.registry.ModBlockEntities;
import net.kaylamay.terranova.registry.blockentity.KilnBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class KilnBlock extends AbstractFurnaceBlock {

    public static final MapCodec<KilnBlock> CODEC = simpleCodec(KilnBlock::new);

    public KilnBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends AbstractFurnaceBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new KilnBlockEntity(pos, state);
    }

    @Override
    protected void openContainer(Level level, BlockPos pos, Player player) {
        if (level.getBlockEntity(pos) instanceof KilnBlockEntity kiln) {
            player.openMenu(new SimpleMenuProvider(
                    (id, inventory, p) -> new FurnaceMenu(id, inventory, kiln, kiln.getDataAccess()),
                    Component.translatable("container.terranova.kiln")
            ));
        }
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createFurnaceTicker(level, type, ModBlockEntities.KILN.get());
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (!state.getValue(LIT)) return;

        double x = pos.getX() + 0.5;
        double y = pos.getY();
        double z = pos.getZ() + 0.5;

        if (random.nextDouble() < 0.1) {
            level.playLocalSound(x, y, z, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
        }

        var facing = state.getValue(FACING);
        double fx = 0.52;
        double rx = random.nextDouble() * 0.6 - 0.3;

        double ox = facing.getStepX() == 0 ? rx : fx * facing.getStepX();
        double oz = facing.getStepZ() == 0 ? rx : fx * facing.getStepZ();

        level.addParticle(ParticleTypes.SMOKE, x + ox, y + random.nextDouble() * 6.0 / 16.0, z + oz, 0, 0, 0);
        level.addParticle(ParticleTypes.FLAME, x + ox, y + random.nextDouble() * 6.0 / 16.0, z + oz, 0, 0, 0);
    }
}