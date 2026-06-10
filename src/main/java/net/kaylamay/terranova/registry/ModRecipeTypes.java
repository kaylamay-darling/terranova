package net.kaylamay.terranova.registry;

import net.kaylamay.terranova.TerraNova;
import net.kaylamay.terranova.registry.recipe.KilnCookingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipeTypes {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, TerraNova.MODID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<KilnCookingRecipe>> KILN_COOKING =
            RECIPE_TYPES.register("kiln_cooking", () -> RecipeType.simple(
                    Identifier.fromNamespaceAndPath(TerraNova.MODID, "kiln_cooking")
            ));

    public static void register(IEventBus eventBus) {
        RECIPE_TYPES.register(eventBus);
    }
}