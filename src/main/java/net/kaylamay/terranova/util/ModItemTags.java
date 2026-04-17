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
}
