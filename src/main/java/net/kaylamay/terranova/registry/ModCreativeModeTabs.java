package net.kaylamay.terranova.registry;

import net.kaylamay.terranova.TerraNova;
import net.kaylamay.terranova.registry.block.ModBlocks;
import net.kaylamay.terranova.registry.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TerraNova.MODID);


    public static final Supplier<CreativeModeTab> MOD_ITEMS = CREATIVE_MODE_TAB.register("mod_items",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.GRASS_FIBER.get()))
                    .title(Component.translatable("creativetab.terranova.mod_items"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.GRASS_FIBER);
                        output.accept(ModItems.BARK);
                        output.accept(ModItems.WOODEN_HAFT);
                        output.accept(ModItems.BOVID_SHARD);
                        output.accept(ModItems.REFINED_BOVID_SHARD);
                        output.accept(ModItems.FIRESTARTER);
                        output.accept(ModItems.ASH);
                        output.accept(ModItems.WATERSKIN_EMPTY);
                        output.accept(ModItems.WATERSKIN_FILLED);
                        output.accept(ModItems.FLINT_HATCHET);
                        output.accept(ModItems.BONE_HATCHET);
                        output.accept(ModItems.RAW_ZINC);
                        output.accept(ModItems.ZINC_NUGGET);
                        output.accept(ModItems.ZINC_INGOT);
                        output.accept(ModItems.STEEL_ALLOY_INGOT);
                        output.accept(ModItems.STEEL_WIRE);
                    })
                    .build());

  public static final Supplier<CreativeModeTab> MOD_BLOCKS = CREATIVE_MODE_TAB.register("mod_block",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.FIELD_CRAFTING_TABLE))
                    .withTabsBefore(Identifier.fromNamespaceAndPath(TerraNova.MODID, "mod_items"))
                    .title(Component.translatable("creativetab.terranova.mod_blocks"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModBlocks.OAK_HOLLOW_LOG);
                        output.accept(ModBlocks.SPRUCE_HOLLOW_LOG);
                        output.accept(ModBlocks.BIRCH_HOLLOW_LOG);
                        output.accept(ModBlocks.JUNGLE_HOLLOW_LOG);
                        output.accept(ModBlocks.ACACIA_HOLLOW_LOG);
                        output.accept(ModBlocks.DARK_OAK_HOLLOW_LOG);
                        output.accept(ModBlocks.MANGROVE_HOLLOW_LOG);
                        output.accept(ModBlocks.CHERRY_HOLLOW_LOG);
                        output.accept(ModBlocks.PALE_OAK_HOLLOW_LOG);
                        output.accept(ModBlocks.OAK_HEARTWOOD);
                        output.accept(ModBlocks.SPRUCE_HEARTWOOD);
                        output.accept(ModBlocks.BIRCH_HEARTWOOD);
                        output.accept(ModBlocks.JUNGLE_HEARTWOOD);
                        output.accept(ModBlocks.ACACIA_HEARTWOOD);
                        output.accept(ModBlocks.DARK_OAK_HEARTWOOD);
                        output.accept(ModBlocks.MANGROVE_HEARTWOOD);
                        output.accept(ModBlocks.CHERRY_HEARTWOOD);
                        output.accept(ModBlocks.PALE_OAK_HEARTWOOD);
                        output.accept(ModBlocks.FIELD_CRAFTING_TABLE);
                        output.accept(ModBlocks.KILN);
                        output.accept(ModBlocks.ASH_LAYER);
                        output.accept(ModBlocks.BROWN_CREEPING_MUSHROOM);
                        output.accept(ModBlocks.RED_CREEPING_MUSHROOM);
                        output.accept(ModBlocks.ZINC_ORE);
                        output.accept(ModBlocks.DEEPSLATE_ZINC_ORE);
                        output.accept(ModBlocks.RAW_ZINC_BLOCK);
                        output.accept(ModBlocks.ZINC_BLOCK);
                        output.accept(ModBlocks.EXPOSED_ZINC);
                        output.accept(ModBlocks.WEATHERED_ZINC);
                        output.accept(ModBlocks.OXIDIZED_ZINC);
                        output.accept(ModBlocks.WAXED_ZINC_BLOCK);
                        output.accept(ModBlocks.WAXED_EXPOSED_ZINC);
                        output.accept(ModBlocks.WAXED_WEATHERED_ZINC);
                        output.accept(ModBlocks.WAXED_OXIDIZED_ZINC);
                        output.accept(ModBlocks.RESIN_TORCH);
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
