package net.kaylamay.terranova.registry;

import net.kaylamay.terranova.TerraNova;
import net.kaylamay.terranova.registry.recipe.KilnCookingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, TerraNova.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<KilnCookingRecipe>> KILN_COOKING =
            RECIPE_SERIALIZERS.register("kiln_cooking", () ->
                    new RecipeSerializer<>(
                            KilnCookingRecipe.MAP_CODEC,
                            KilnCookingRecipe.STREAM_CODEC
                    )
            );

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
    }
}