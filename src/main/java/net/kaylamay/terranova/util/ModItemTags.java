package net.kaylamay.terranova.util;

import net.kaylamay.terranova.TerraNova;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModItemTags {
    public static final TagKey<Item> UNSUPPRESSED_ATTACK = TagKey.create(
            Registries.ITEM, Identifier.fromNamespaceAndPath(TerraNova.MODID, "unsuppressed_attack")
    );

    public static final TagKey<Item> UNSUPPRESSED_MINE = TagKey.create(
            Registries.ITEM, Identifier.fromNamespaceAndPath(TerraNova.MODID, "unsuppressed_mine")
    );

    public static final TagKey<Item> HATCHETS = TagKey.create(
            Registries.ITEM, Identifier.fromNamespaceAndPath(TerraNova.MODID, "hatchets")
    );

    public static final TagKey<Item> BROWN_MUSHROOMS = TagKey.create(
            Registries.ITEM, Identifier.fromNamespaceAndPath(TerraNova.MODID, "brown_mushrooms")
    );

    public static final TagKey<Item> RED_MUSHROOMS = TagKey.create(
            Registries.ITEM, Identifier.fromNamespaceAndPath(TerraNova.MODID, "red_mushrooms")
    );

    public static final TagKey<Item> HEARTWOOD = TagKey.create(
            Registries.ITEM, Identifier.fromNamespaceAndPath(TerraNova.MODID, "heartwood")
    );

    public static final TagKey<Item> HOLLOW_LOGS = TagKey.create(
            Registries.ITEM, Identifier.fromNamespaceAndPath(TerraNova.MODID, "hollow_logs")
    );
}
