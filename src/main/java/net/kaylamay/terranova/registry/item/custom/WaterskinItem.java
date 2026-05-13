package net.kaylamay.terranova.registry.item.custom;

import net.kaylamay.terranova.registry.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.fluids.FluidType;

public class WaterskinItem extends Item {
    public WaterskinItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        boolean filled = itemStack.has(ModDataComponents.FILLED.get());
        HitResult hitResult = getPlayerPOVHitResult(level, player, filled ? ClipContext.Fluid.NONE : ClipContext.Fluid.SOURCE_ONLY);

        if (hitResult.getType() != HitResult.Type.BLOCK) {
            return InteractionResult.PASS;
        }

        BlockHitResult blockHit = (BlockHitResult) hitResult;
        BlockPos targetPos = blockHit.getBlockPos();

        if (!filled) {
            if (level.getFluidState(targetPos).getType() == Fluids.WATER && level.getFluidState(targetPos).isSource()) {
                if (!level.isClientSide()) {
                    itemStack.set(ModDataComponents.FILLED.get(), true);
                    level.playSound(null, player.blockPosition(), SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 1.0f, 1.0f);
                }
                return InteractionResult.SUCCESS;
            }
        } else {
            if (!level.isClientSide()) {
                player.extinguishFire();
                itemStack.remove(ModDataComponents.FILLED.get());
                level.playSound(null, player.blockPosition(), SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 1.0f, 1.0f);

                BlockState targetState = level.getBlockState(targetPos);

                if (targetState.is(BlockTags.CAMPFIRES) && targetState.getValue(CampfireBlock.LIT)) {
                    level.setBlock(targetPos, targetState.setValue(CampfireBlock.LIT, false), 2);
                    level.playSound(null, targetPos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0f, 1.0f);
                } else {
                    BlockPos placePos = targetPos.relative(blockHit.getDirection());

                    if (level.getBlockState(placePos).canBeReplaced() && !level.getFluidState(placePos).isSource()) {
                        level.setBlock(placePos, Blocks.WATER.defaultBlockState().setValue(LiquidBlock.LEVEL, 7), 2);
                    }
                }
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
