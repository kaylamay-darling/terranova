package net.kaylamay.terranova.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record HollowLogFeatureConfiguration(BlockStateProvider log) implements FeatureConfiguration {
    public static final Codec<HollowLogFeatureConfiguration> CODEC = BlockStateProvider.CODEC.fieldOf("log")
            .xmap(HollowLogFeatureConfiguration::new, HollowLogFeatureConfiguration::log).codec();
}
