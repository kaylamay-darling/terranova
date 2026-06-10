package net.kaylamay.terranova.registry;

import com.mojang.serialization.MapCodec;
import net.kaylamay.terranova.TerraNova;
import net.kaylamay.terranova.registry.loot.LeavesStickModifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModLootModifiers {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS =
            DeferredRegister.create(NeoForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS, TerraNova.MODID);

    public static final Supplier<MapCodec<? extends IGlobalLootModifier>> LEAVES_EXTRA_STICKS =
            LOOT_MODIFIERS.register("leaves_extra_sticks", () -> net.kaylamay.terranova.registry.loot.LeavesStickModifier.CODEC);

    public static void register(IEventBus eventBus) {
        LOOT_MODIFIERS.register(eventBus);
    }
}