package net.kaylamay.terranova.registry.block;

import com.ibm.icu.impl.CacheValue;
import net.kaylamay.terranova.TerraNova;
import net.kaylamay.terranova.registry.block.custom.HeartwoodBlock;
import net.kaylamay.terranova.registry.block.custom.HollowBlock;
import net.kaylamay.terranova.registry.block.custom.ZincBlock;
import net.kaylamay.terranova.registry.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(TerraNova.MODID);

    public static final DeferredBlock<Block> ZINC_ORE = registerBlock(
            "zinc_ore",
            registryName -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_ORE)
                            .setId(ResourceKey.create(Registries.BLOCK, registryName)
            )
    ));

    public static final DeferredBlock<Block> RAW_ZINC_BLOCK = registerBlock(
            "raw_zinc_block",
            registryName -> new Block(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            )
    );

    public static final DeferredBlock<Block> ZINC_BLOCK = registerBlock(
           "zinc_block",
           registryName -> new ZincBlock(
                   WeatheringCopper.WeatherState.UNAFFECTED,
                   BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
                           .setId(ResourceKey.create(Registries.BLOCK, registryName))
                           .randomTicks()
           )
    );

    public static final DeferredBlock<Block> EXPOSED_ZINC = registerBlock(
            "exposed_zinc",
            registryName -> new ZincBlock(
                    WeatheringCopper.WeatherState.EXPOSED,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
                            .randomTicks()
            )
    );

    public static final DeferredBlock<Block> WEATHERED_ZINC = registerBlock(
            "weathered_zinc",
            registryName -> new ZincBlock(
                    WeatheringCopper.WeatherState.WEATHERED,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
                            .randomTicks()
            )
    );

    public static final DeferredBlock<Block> OXIDIZED_ZINC = registerBlock(
            "oxidized_zinc",
            registryName -> new ZincBlock(
                    WeatheringCopper.WeatherState.OXIDIZED,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
                            .randomTicks()
            )
    );

    public static final DeferredBlock<Block> OAK_HEARTWOOD = registerBlock(
            "oak_heartwood",
            registryName -> new HeartwoodBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
                            .noOcclusion()
            )
    );

    public static final DeferredBlock<Block> BIRCH_HEARTWOOD = registerBlock(
            "birch_heartwood",
            registryName -> new HeartwoodBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            )
    );

    public static final DeferredBlock<Block> SPRUCE_HEARTWOOD = registerBlock(
            "spruce_heartwood",
            registryName -> new HeartwoodBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            )
    );

    public static final DeferredBlock<Block> ACACIA_HEARTWOOD = registerBlock(
            "acacia_heartwood",
            registryName -> new HeartwoodBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            )
    );

    public static final DeferredBlock<Block> DARK_OAK_HEARTWOOD = registerBlock(
            "dark_oak_heartwood",
            registryName -> new HeartwoodBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            )
    );

    public static final DeferredBlock<Block> JUNGLE_HEARTWOOD = registerBlock(
            "jungle_heartwood",
            registryName -> new HeartwoodBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            )
    );

    public static final DeferredBlock<Block> MANGROVE_HEARTWOOD = registerBlock(
            "mangrove_heartwood",
            registryName -> new HeartwoodBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            )
    );

    public static final DeferredBlock<Block> CHERRY_HEARTWOOD = registerBlock(
            "cherry_heartwood",
            registryName -> new HeartwoodBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            )
    );

    public static final DeferredBlock<Block> PALE_OAK_HEARTWOOD = registerBlock(
            "pale_oak_heartwood",
            registryName -> new HeartwoodBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            )
    );

    public static final DeferredBlock<Block> OAK_HOLLOW_LOG = registerBlock(
            "oak_hollow_log",
            registryName -> new HollowBlock(
                    BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, registryName))
                            .strength(1.0f, 1.0f)
            )
    );

    public static final DeferredBlock<Block> BIRCH_HOLLOW_LOG = registerBlock(
            "birch_hollow_log",
            registryName -> new HollowBlock(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
                            .strength(1.0f, 1.0f)
            )
    );

    public static final DeferredBlock<Block> SPRUCE_HOLLOW_LOG = registerBlock(
            "spruce_hollow_log",
            registryName -> new HollowBlock(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
                            .strength(1.0f, 1.0f)
            )
    );

    public static final DeferredBlock<Block> ACACIA_HOLLOW_LOG = registerBlock(
            "acacia_hollow_log",
            registryName -> new HollowBlock(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
                            .strength(1.0f, 1.0f)
            )
    );

    public static final DeferredBlock<Block> JUNGLE_HOLLOW_LOG = registerBlock(
            "jungle_hollow_log",
            registryName -> new HollowBlock(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
                            .strength(1.0f, 1.0f)
            )
    );

    public static final DeferredBlock<Block> DARK_OAK_HOLLOW_LOG = registerBlock(
            "dark_oak_hollow_log",
            registryName -> new HollowBlock(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
                            .strength(1.0f, 1.0f)
            )
    );

    public static final DeferredBlock<Block> MANGROVE_HOLLOW_LOG = registerBlock(
            "mangrove_hollow_log",
            registryName -> new HollowBlock(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
                            .strength(1.0f, 1.0f)
            )
    );

    public static final DeferredBlock<Block> CHERRY_HOLLOW_LOG = registerBlock(
            "cherry_hollow_log",
            registryName -> new HollowBlock(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
                            .strength(1.0f, 1.0f)
            )
    );

    public static final DeferredBlock<Block> PALE_OAK_HOLLOW_LOG = registerBlock(
            "pale_oak_hollow_log",
            registryName -> new HollowBlock(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
                            .strength(1.0f, 1.0f)
            )
    );

    public static <T extends Block> void registerBlockItem(String name, Supplier<T> block){
        ModItems.ITEMS.registerSimpleBlockItem(name, block);
    }

    private static <T extends Block> DeferredBlock<Block> registerBlock(String name, Function<Identifier, ? extends T> block){
        DeferredBlock<Block> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name,toReturn);
        return toReturn;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
