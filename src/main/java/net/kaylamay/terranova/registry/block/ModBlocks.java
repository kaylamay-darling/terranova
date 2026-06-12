package net.kaylamay.terranova.registry.block;

import com.ibm.icu.impl.CacheValue;
import com.mojang.serialization.MapCodec;
import net.kaylamay.terranova.TerraNova;
import net.kaylamay.terranova.registry.ModParticles;
import net.kaylamay.terranova.registry.block.custom.*;
import net.kaylamay.terranova.registry.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
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

    public static final DeferredBlock<Block> DEEPSLATE_ZINC_ORE = registerBlock(
            "deepslate_zinc_ore",
            registryName -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_COPPER_ORE)
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

    public static final DeferredBlock<Block> WAXED_ZINC_BLOCK = registerBlock(
            "waxed_zinc_block",
            registryName -> new Block(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
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

    public static final DeferredBlock<Block> WAXED_EXPOSED_ZINC = registerBlock(
            "waxed_exposed_zinc",
            registryName -> new Block(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
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

    public static final DeferredBlock<Block> WAXED_WEATHERED_ZINC = registerBlock(
            "waxed_weathered_zinc",
            registryName -> new Block(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
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

    public static final DeferredBlock<Block> WAXED_OXIDIZED_ZINC = registerBlock(
            "waxed_oxidized_zinc",
            registryName -> new Block(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
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

    public static final DeferredBlock<Block> BROWN_CREEPING_MUSHROOM = registerBlockWithCustomItem(
            "brown_creeping_mushroom",
            registryName -> new CreepingMushroomBlock(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
                            .noOcclusion().noCollision().randomTicks()
            ),
            props -> props.food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationModifier(0.4f)
                    .build()
            )
    );

    public static final DeferredBlock<Block> RED_CREEPING_MUSHROOM = registerBlockWithCustomItem(
            "red_creeping_mushroom",
            registryName -> new CreepingMushroomBlock(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
                            .noOcclusion().noCollision().randomTicks()
            ),
            props -> props.food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationModifier(0.4f)
                    .build()
            )
    );

    public static final DeferredBlock<Block> FIELD_CRAFTING_TABLE = registerBlock(
            "field_crafting_table",
            registryName -> new FieldCraftingTableBlock(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
                            .strength(1.0f, 1.0f)
                            .noOcclusion()
            )
    );

    public static final DeferredBlock<Block> ASH_LAYER = registerBlock(
            "ash_layer",
            registryName -> new SnowLayerBlock(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
                            .strength(0.1f)
                            .sound(SoundType.SAND)
                            .replaceable()
            ) {
                @Override
                public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
                    int layers = state.getValue(LAYERS);
                    if (layers == 8) return false;
                    if (context.getItemInHand().is(asItem())) return true;
                    return layers == 1;
                }
            }
    );



    public static final DeferredBlock<Block> KILN = registerBlock(
            "kiln",
            registryName -> new KilnBlock(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
                            .strength(3.5f)
                            .sound(SoundType.STONE)
                            .requiresCorrectToolForDrops()
            )
    );

    public static final DeferredBlock<Block> RESIN_WALL_TORCH = registerBlock(
            "resin_wall_torch",
            registryName -> new ResinWallTorchBlock(
                    ModParticles.RESIN_FLAME.get(),
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
                            .noCollision()
                            .instabreak()
                            .lightLevel(state -> 14)
                            .sound(SoundType.WOOD)
            )
    );

    public static final DeferredBlock<Block> RESIN_TORCH = registerStandingAndWallBlock(
            "resin_torch",
            registryName -> new ResinTorchBlock(
                    ModParticles.RESIN_FLAME.get(),
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
                            .noCollision()
                            .instabreak()
                            .lightLevel(state -> 14)
                            .sound(SoundType.WOOD)
            ),
            RESIN_WALL_TORCH,
            Direction.DOWN
    );

    private static <T extends Block> void registerBlockItem(String name, Supplier<T> block, Item.Properties properties) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), properties));
    }

    private static <T extends Block> void registerBlockItem(String name, Supplier<T> block) {
        ModItems.ITEMS.registerSimpleBlockItem(name, block);
    }

    private static <T extends Block> DeferredBlock<Block> registerBlock(String name, Function<Identifier, ? extends T> block) {
        DeferredBlock<Block> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> DeferredBlock<Block> registerBlockWithCustomItem(String name, Function<Identifier, ? extends T> block, Function<Item.Properties, Item.Properties> itemProperties) {
        DeferredBlock<Block> toReturn = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> {
            Identifier id = toReturn.getId();
            ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(id.getNamespace(), id.getPath()));
            return new BlockItem(
                    toReturn.get(),
                    itemProperties.apply(new Item.Properties().setId(key))
            );
        });
        return toReturn;
    }


    private static <T extends Block> DeferredBlock<Block> registerStandingAndWallBlock(
            String name,
            Function<Identifier, ? extends T> block,
            Supplier<? extends Block> wallBlock,
            Direction attachmentDirection) {

        DeferredBlock<Block> toReturn = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> {
            Identifier id = toReturn.getId();
            ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(id.getNamespace(), id.getPath()));
            return new StandingAndWallBlockItem(
                    toReturn.get(),
                    wallBlock.get(),
                    attachmentDirection,
                    new Item.Properties().setId(key)
            );
        });
        return toReturn;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}