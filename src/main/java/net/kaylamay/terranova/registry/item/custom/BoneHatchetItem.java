package net.kaylamay.terranova.registry.item.custom;

import net.kaylamay.terranova.TerraNova;
import net.kaylamay.terranova.registry.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import static net.kaylamay.terranova.util.ModBlockTags.*;

public class BoneHatchetItem extends Item {
    public BoneHatchetItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        BlockState state = level.getBlockState(pos);

        if (!state.is(BlockTags.LOGS)) {
            return InteractionResult.PASS;
        }

        BlockState strippedState = state.getToolModifiedState(context, ItemAbilities.AXE_STRIP, false);

        if (strippedState == null) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide()) {
            level.setBlock(pos, strippedState, Block.UPDATE_ALL);
            Block.popResource(level, pos, new ItemStack(ModItems.BARK.get(), 2));

            level.playSound(null, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);

            if (player != null) {
                context.getItemInHand().hurtAndBreak(1, player, context.getHand().asEquipmentSlot());
            }
        }

        return InteractionResult.SUCCESS;
    }


    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState state) {
        if (state.is(SOFT_MATERIALS)) return 1.0f;
        if (state.is(BONE_HATCHET_MINEABLE)) return 1.2f;
        return 0.2f;
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState state, BlockPos pos, LivingEntity owner) {
        if (state.is(BONE_HATCHET_MINEABLE)) {
            itemStack.hurtAndBreak(1, owner, owner.getUsedItemHand());
        }
        return true;
    }

    @Override
    public void hurtEnemy(ItemStack itemStack, LivingEntity mob, LivingEntity attacker) {
            itemStack.hurtAndBreak(1, attacker, attacker.getUsedItemHand());
    }

    @Override
    public boolean canPerformAction(ItemInstance stack, ItemAbility itemAbility) {
        return ItemAbilities.DEFAULT_AXE_ACTIONS.contains(itemAbility);
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return state.is(BONE_HATCHET_MINEABLE);
    }
}