package net.kaylamay.terranova.registry;

import net.kaylamay.terranova.TerraNova;
import net.kaylamay.terranova.registry.block.ModBlocks;
import net.kaylamay.terranova.registry.blockentity.FieldCraftingTableBlockEntity;
import net.kaylamay.terranova.registry.blockentity.KilnBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
        public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
                DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, TerraNova.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FieldCraftingTableBlockEntity>> FIELD_CRAFTING_TABLE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("field_crafting_table_block_entity", () ->
                    new BlockEntityType<>(
                            FieldCraftingTableBlockEntity::new,
                            ModBlocks.FIELD_CRAFTING_TABLE.get()
                    )
            );

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<KilnBlockEntity>> KILN =
            BLOCK_ENTITIES.register("kiln", () ->
                    new BlockEntityType<>(
                            KilnBlockEntity::new,
                            ModBlocks.KILN.get()
                    )
            );


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
    }
