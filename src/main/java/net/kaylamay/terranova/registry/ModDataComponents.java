package net.kaylamay.terranova.registry;

import com.mojang.serialization.Codec;
import net.kaylamay.terranova.TerraNova;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponents {
    public static final DeferredRegister.DataComponents COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, TerraNova.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> FILLED =
            COMPONENTS.register("filled", () -> DataComponentType.<Boolean>builder()
                    .persistent(Codec.BOOL).build());

    public static void register(IEventBus eventBus) {
        COMPONENTS.register(eventBus);
    }
}
