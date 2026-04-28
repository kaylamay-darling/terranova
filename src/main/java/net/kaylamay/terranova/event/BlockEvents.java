package net.kaylamay.terranova.event;

import net.kaylamay.terranova.TerraNova;
import net.kaylamay.terranova.registry.block.ModBlocks;
import net.kaylamay.terranova.util.ModItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.Map;

public class BlockEvents {
    private static final Map<Block, Block> LOG_TO_HEARTWOOD = Map.of(
            Blocks.STRIPPED_OAK_LOG, ModBlocks.OAK_HEARTWOOD.get(),
            Blocks.STRIPPED_BIRCH_LOG, ModBlocks.BIRCH_HEARTWOOD.get(),
            Blocks.STRIPPED_SPRUCE_LOG, ModBlocks.SPRUCE_HEARTWOOD.get(),
            Blocks.STRIPPED_ACACIA_LOG, ModBlocks.ACACIA_HEARTWOOD.get(),
            Blocks.STRIPPED_DARK_OAK_LOG, ModBlocks.DARK_OAK_HEARTWOOD.get(),
            Blocks.STRIPPED_JUNGLE_LOG, ModBlocks.JUNGLE_HEARTWOOD.get(),
            Blocks.STRIPPED_MANGROVE_LOG, ModBlocks.MANGROVE_HEARTWOOD.get(),
            Blocks.STRIPPED_CHERRY_LOG, ModBlocks.CHERRY_HEARTWOOD.get(),
            Blocks.STRIPPED_PALE_OAK_LOG, ModBlocks.PALE_OAK_HEARTWOOD.get()
    );

    @SubscribeEvent
    public static void breakLogEvent(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = player.getMainHandItem();
        Level level = (Level) event.getLevel();
        BlockState state = event.getState();
        Block block = state.getBlock();
        BlockPos pos = event.getPos();

        Block heartwood = LOG_TO_HEARTWOOD.get(block);

        if (heartwood == null) {
            return;
        }

        if (itemStack.is(ModItemTags.HATCHETS)) {
            event.setCanceled(true);

            BlockState newState = heartwood.defaultBlockState();

            if (state.hasProperty(RotatedPillarBlock.AXIS) && newState.hasProperty(RotatedPillarBlock.AXIS)) {
                newState = newState.setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));
            }

            level.setBlockAndUpdate(pos, newState);
        }
    }
}
