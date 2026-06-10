package net.kaylamay.terranova.registry.block.custom;

import com.mojang.serialization.MapCodec;
import net.kaylamay.terranova.registry.blockentity.FieldCraftingTableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

public class FieldCraftingTableBlock extends BaseEntityBlock implements EntityBlock {
    public static final MapCodec<FieldCraftingTableBlock> CODEC = simpleCodec(FieldCraftingTableBlock::new);

    public FieldCraftingTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FieldCraftingTableBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        player.openMenu(new SimpleMenuProvider(
                (id, inventory, p) -> new FieldCraftingMenu(id, inventory, level, pos),
                Component.literal("Field Crafting Table")
        ));
        return InteractionResult.CONSUME;
    }

    private static class FieldCraftingMenu extends CraftingMenu {
        private final Level level;
        private final BlockPos pos;

        public FieldCraftingMenu(int containerId, Inventory playerInventory, Level level, BlockPos pos) {
            super(containerId, playerInventory, ContainerLevelAccess.create(level, pos));
            this.level = level;
            this.pos = pos;

            Slot original = this.slots.get(0);
            CraftingContainer craftingGrid = (CraftingContainer) this.slots.get(1).container;
            Container resultContainer = original.container;
            int ox = original.x;
            int oy = original.y;

            this.slots.set(0, new ResultSlot(playerInventory.player, craftingGrid, resultContainer, 0, ox, oy) {
                @Override
                public void onTake(Player player, ItemStack stack) {
                    super.onTake(player, stack);
                    BlockEntity be = level.getBlockEntity(pos);
                    if (be instanceof FieldCraftingTableBlockEntity ftbe) {
                        ftbe.incrementUseCount(level, pos);
                        // Close menu for all players if block was destroyed
                        if (level.getBlockEntity(pos) == null) {
                            level.players().stream()
                                    .filter(p -> p.containerMenu instanceof CraftingMenu)
                                    .filter(p -> p.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) < 64)
                                    .forEach(Player::closeContainer);
                        }
                    }
                }
            });
        }

        @Override
        public boolean stillValid(Player player) { return true; }
    }
}