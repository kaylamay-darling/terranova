package net.kaylamay.terranova.registry.item;

import net.kaylamay.terranova.TerraNova;
import net.kaylamay.terranova.registry.item.custom.BoneHatchetItem;
import net.kaylamay.terranova.registry.item.custom.FlintHatchetItem;
import net.kaylamay.terranova.registry.item.custom.WaterskinItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
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

    public static final DeferredItem<Item> STEEL_ALLOY_INGOT = ITEMS.register(
            "steel_alloy_ingot",
            registryName -> new Item(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, registryName)))
    );

    public static final DeferredItem<Item> STEEL_WIRE = ITEMS.register(
            "steel_wire",
            registryName -> new Item(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, registryName)))
    );

    public static final DeferredItem<Item> BARK = ITEMS.register(
            "bark",
            registryName -> new Item(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, registryName)))
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
                                                    2.0,
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

    public static final DeferredItem<Item> BONE_HATCHET = ITEMS.register(
            "bone_hatchet",
            registryName -> new BoneHatchetItem(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, registryName))
                    .durability(95)
                    .attributes(
                            ItemAttributeModifiers.builder()
                                    .add(
                                            Attributes.ATTACK_DAMAGE,
                                            new AttributeModifier(
                                                    Identifier.fromNamespaceAndPath(TerraNova.MODID, "bone_hatchet_damage"),
                                                    3.0,
                                                    AttributeModifier.Operation.ADD_VALUE),
                                            EquipmentSlotGroup.MAINHAND)
                                    .add(
                                            Attributes.ATTACK_SPEED,
                                            new AttributeModifier(
                                                    Identifier.fromNamespaceAndPath(TerraNova.MODID, "bone_hatchet_speed"),
                                                    -2.0,
                                                    AttributeModifier.Operation.ADD_VALUE),
                                            EquipmentSlotGroup.MAINHAND)
                                    .build()
                    )
            ));

    public static final DeferredItem<Item> GRASS_FIBER = ITEMS.register(
            "grass_fiber",
            registryName -> new Item(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, registryName)))
    );

    public static final DeferredItem<Item> WOODEN_HAFT = ITEMS.register(
            "wooden_haft",
            registryName -> new Item(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, registryName)))
    );

    public static final DeferredItem<Item> WATERSKIN_EMPTY = ITEMS.register(
            "waterskin_empty",
            registryName -> new WaterskinItem(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, registryName))
                    .stacksTo(1), false
            ));

    public static final DeferredItem<Item> WATERSKIN_FILLED = ITEMS.register(
            "waterskin_filled",
            registryName -> new WaterskinItem(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, registryName))
                    .craftRemainder(ModItems.WATERSKIN_EMPTY.get())
                    .stacksTo(1), true
            ));

    public static final DeferredItem<Item> FIRESTARTER = ITEMS.register(
            "firestarter",
            registryName -> new FlintAndSteelItem(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, registryName))
                    .durability(2))
    );

    public static final DeferredItem<Item> BOVID_SHARD = ITEMS.register(
            "bovid_shard",
            registryName -> new Item(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, registryName)))
    );

    public static final DeferredItem<Item> REFINED_BOVID_SHARD = ITEMS.register(
            "refined_bovid_shard",
            registryName -> new Item(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, registryName)))
    );

    public static final DeferredItem<Item> ASH = ITEMS.register(
            "ash",
            registryName -> new BoneMealItem(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, registryName)))
    );

    public static final DeferredItem<Item> RAW_HIDE = ITEMS.register(
            "raw_hide",
            registryName -> new BoneMealItem(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, registryName)))
    );

    public static final DeferredItem<Item> ZINC_NUGGET = ITEMS.register(
            "zinc_nugget",
            registryName -> new Item(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, registryName)))
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}