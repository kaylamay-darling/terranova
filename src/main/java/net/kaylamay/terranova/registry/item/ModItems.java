package net.kaylamay.terranova.registry.item;

import net.kaylamay.terranova.TerraNova;
import net.kaylamay.terranova.registry.item.custom.FlintHatchetItem;
import net.kaylamay.terranova.registry.item.custom.FuelItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TerraNova.MODID);

    public static final DeferredItem<Item> RAW_ZINC = ITEMS.register(
            "raw_zinc",
            registryName -> new Item(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, registryName))
            )
    );

    public static final DeferredItem<Item> ZINC_INGOT = ITEMS.register(
            "zinc_ingot",
            registryName -> new Item(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, registryName)))
    );

    public static final DeferredItem<Item> BARK = ITEMS.register(
            "bark",
            registryName -> new FuelItem(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, registryName)),
                    200)
    );

    public static final DeferredItem<Item> FLINT_HATCHET = ITEMS.register(
            "flint_hatchet",
            registryName -> new FlintHatchetItem(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, registryName))
                    .durability(59)
                    .attributes(
                            ItemAttributeModifiers.builder()
                                    .add(
                                            Attributes.ATTACK_DAMAGE,
                                            new AttributeModifier(
                                                    Identifier.fromNamespaceAndPath(TerraNova.MODID, "flint_hatchet_damage"),
                                                    0.5,
                                                    AttributeModifier.Operation.ADD_VALUE),
                                            EquipmentSlotGroup.MAINHAND)
                                    .add(
                                            Attributes.ATTACK_SPEED,
                                            new AttributeModifier(
                                                    Identifier.fromNamespaceAndPath(TerraNova.MODID, "flint_hatchet_speed"),
                                                    -3.0,
                                                    AttributeModifier.Operation.ADD_VALUE),
                                            EquipmentSlotGroup.MAINHAND)
                                    .build()
                    )
            ));

    public static final DeferredItem<Item> GRASS_FIBER = ITEMS.register(
            "grass_fiber",
            registryName -> new FuelItem(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, registryName)),
                    75)
    );

    public static final DeferredItem<Item> WOODEN_HAFT = ITEMS.register(
            "wooden_haft",
            registryName -> new FuelItem(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, registryName)),
                    150)
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}