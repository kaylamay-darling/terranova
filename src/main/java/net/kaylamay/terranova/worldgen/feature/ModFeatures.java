package net.kaylamay.terranova.worldgen.feature;

import net.kaylamay.terranova.TerraNova;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(
            Registries.FEATURE, TerraNova.MODID);

    public static final DeferredHolder<Feature<?>, HollowLogFeature> HOLLOW_LOG = FEATURES.register(
            "hollow_log",
            registryName -> new HollowLogFeature(HollowLogFeatureConfiguration.CODEC)
    );

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
