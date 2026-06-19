package net.kaylamay.terranova.event;

import net.kaylamay.terranova.TerraNova;
import net.kaylamay.terranova.registry.block.ModBlocks;
import net.kaylamay.terranova.util.ModItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@EventBusSubscriber(modid = TerraNova.MODID)
public class BlockEvents {

    // Store the Supplier instead of the raw Block to avoid unbound registry errors
    private static final Map<Block, Supplier<Block>> LOG_TO_HEARTWOOD = new HashMap<>();

    static {
        LOG_TO_HEARTWOOD.put(Blocks.STRIPPED_OAK_LOG, ModBlocks.OAK_HEARTWOOD);
        LOG_TO_HEARTWOOD.put(Blocks.STRIPPED_BIRCH_LOG, ModBlocks.BIRCH_HEARTWOOD);
        LOG_TO_HEARTWOOD.put(Blocks.STRIPPED_SPRUCE_LOG, ModBlocks.SPRUCE_HEARTWOOD);
        LOG_TO_HEARTWOOD.put(Blocks.STRIPPED_ACACIA_LOG, ModBlocks.ACACIA_HEARTWOOD);
        LOG_TO_HEARTWOOD.put(Blocks.STRIPPED_DARK_OAK_LOG, ModBlocks.DARK_OAK_HEARTWOOD);
        LOG_TO_HEARTWOOD.put(Blocks.STRIPPED_JUNGLE_LOG, ModBlocks.JUNGLE_HEARTWOOD);
        LOG_TO_HEARTWOOD.put(Blocks.STRIPPED_MANGROVE_LOG, ModBlocks.MANGROVE_HEARTWOOD);
        LOG_TO_HEARTWOOD.put(Blocks.STRIPPED_CHERRY_LOG, ModBlocks.CHERRY_HEARTWOOD);
        LOG_TO_HEARTWOOD.put(Blocks.STRIPPED_PALE_OAK_LOG, ModBlocks.PALE_OAK_HEARTWOOD);
    }

    @SubscribeEvent
    public static void breakLogEvent(BreakBlockEvent event) {
        if (event.getLevel().isClientSide()) return;

        Block block = event.getState().getBlock();
        Supplier<Block> heartwoodSupplier = LOG_TO_HEARTWOOD.get(block);

        if (heartwoodSupplier == null) return;

        Player player = event.getPlayer();
        ItemStack itemStack = player.getMainHandItem();

        if (itemStack.is(ModItemTags.HATCHETS)) {
            event.setCanceled(true);

            // Access the block only now, when it is guaranteed to be bound
            Block heartwood = heartwoodSupplier.get();
            BlockPos pos = event.getPos();
            Level level = (Level) event.getLevel();
            BlockState state = event.getState();

            // Sync with client
            level.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);

            // Replace block
            BlockState newState = heartwood.defaultBlockState();
            if (state.hasProperty(RotatedPillarBlock.AXIS) && newState.hasProperty(RotatedPillarBlock.AXIS)) {
                newState = newState.setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));
            }

            level.setBlockAndUpdate(pos, newState);

            itemStack.hurtAndBreak(1, player, player.getUsedItemHand() == InteractionHand.MAIN_HAND
                    ? EquipmentSlot.MAINHAND
                    : EquipmentSlot.OFFHAND);
        }
    }
}