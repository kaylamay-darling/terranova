package net.kaylamay.terranova.registry.item;

import com.jcraft.jorbis.Block;
import net.kaylamay.terranova.TerraNova;
import net.kaylamay.terranova.registry.block.ModBlocks;
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
                    .icon(() -> new ItemStack(ModItems.ZINC_INGOT.get()))
                    .title(Component.translatable("creativetab.terranova.mod_items"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.RAW_ZINC);
                        output.accept(ModItems.ZINC_INGOT);
                    })
                    .build());

  public static final Supplier<CreativeModeTab> MOD_BLOCKS = CREATIVE_MODE_TAB.register("mod_block",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.RAW_ZINC_BLOCK))
                    .withTabsBefore(Identifier.fromNamespaceAndPath(TerraNova.MODID, "mod_items"))
                    .title(Component.translatable("creativetab.terranova.mod_blocks"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModBlocks.ZINC_ORE);
                        output.accept(ModBlocks.RAW_ZINC_BLOCK);
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
