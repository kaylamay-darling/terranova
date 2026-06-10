package net.kaylamay.terranova.registry.item.custom;

import net.kaylamay.terranova.registry.item.ModItems;
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
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class WaterskinItem extends Item {

    private final boolean filled;

    public WaterskinItem(Properties properties, boolean filled) {
        super(properties);
        this.filled = filled;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);

        HitResult hit = getPlayerPOVHitResult(
                level,
                player,
                filled ? ClipContext.Fluid.NONE : ClipContext.Fluid.SOURCE_ONLY
        );

        if (hit.getType() != HitResult.Type.BLOCK) return InteractionResult.PASS;

        BlockHitResult blockHit = (BlockHitResult) hit;
        BlockPos pos = blockHit.getBlockPos();

        if (!filled) {
            // Empty waterskin — fill from water source
            if (level.getFluidState(pos).getType() == Fluids.WATER
                    && level.getFluidState(pos).isSource()) {

                if (!level.isClientSide()) {
                    ItemStack filled = new ItemStack(ModItems.WATERSKIN_FILLED.get());
                    if (stack.getCount() == 1) {
                        player.setItemInHand(hand, filled);
                    } else {
                        stack.shrink(1);
                        if (!player.getInventory().add(filled)) {
                            player.drop(filled, false);
                        }
                    }

                    level.playSound(null, player.blockPosition(),
                            SoundEvents.BUCKET_FILL,
                            SoundSource.PLAYERS,
                            1.0f, 1.0f);
                }

                return InteractionResult.SUCCESS;
            }

        } else {
            // Filled waterskin — only act on campfires or valid pour targets
            BlockState state = level.getBlockState(pos);
            BlockPos place = pos.relative(blockHit.getDirection());
            boolean canPour = (state.is(BlockTags.CAMPFIRES) && state.getValue(CampfireBlock.LIT))
                    || (level.getBlockState(place).canBeReplaced() && !level.getFluidState(place).isSource());

            if (!canPour) return InteractionResult.PASS;

            if (!level.isClientSide()) {
                ItemStack empty = new ItemStack(ModItems.WATERSKIN_EMPTY.get());

                if (!player.isCreative()) {
                    if (stack.getCount() == 1) {
                        player.setItemInHand(hand, empty);
                    } else {
                        stack.shrink(1);
                        if (!player.getInventory().add(empty)) {
                            player.drop(empty, false);
                        }
                    }
                }

                player.extinguishFire();

                level.playSound(null, player.blockPosition(),
                        SoundEvents.BUCKET_EMPTY,
                        SoundSource.PLAYERS,
                        1.0f, 1.0f);

                if (state.is(BlockTags.CAMPFIRES) && state.getValue(CampfireBlock.LIT)) {
                    level.setBlock(pos, state.setValue(CampfireBlock.LIT, false), 2);
                    level.playSound(null, pos,
                            SoundEvents.GENERIC_EXTINGUISH_FIRE,
                            SoundSource.BLOCKS,
                            1.0f, 1.0f);
                } else {
                    level.setBlock(place,
                            Blocks.WATER.defaultBlockState()
                                    .setValue(LiquidBlock.LEVEL, 7),
                            2);
                }
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}