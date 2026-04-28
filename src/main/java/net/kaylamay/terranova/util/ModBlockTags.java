package net.kaylamay.terranova.util;

import net.kaylamay.terranova.TerraNova;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {
    public static final TagKey<Block> FLINT_HATCHET_MINEABLE = TagKey.create(
            Registries.BLOCK, Identifier.fromNamespaceAndPath(TerraNova.MODID, "flint_hatchet_mineable")
    );

    public static final TagKey<Block> HEARTWOOD = TagKey.create(
            Registries.BLOCK, Identifier.fromNamespaceAndPath(TerraNova.MODID, "heartwood")
    );

    public static final TagKey<Block> HOLLOW_LOGS = TagKey.create(
            Registries.BLOCK, Identifier.fromNamespaceAndPath(TerraNova.MODID, "hollow_logs")
    );
}
