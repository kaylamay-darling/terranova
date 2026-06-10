package net.kaylamay.terranova.registry.recipe;

import com.mojang.serialization.MapCodec;
import net.kaylamay.terranova.registry.ModRecipeSerializers;
import net.kaylamay.terranova.registry.ModRecipeTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.AbstractCookingRecipe.CookingBookInfo;

public class KilnCookingRecipe extends AbstractCookingRecipe {

    public static final MapCodec<KilnCookingRecipe> MAP_CODEC = cookingMapCodec(KilnCookingRecipe::new, 400);
    public static final StreamCodec<RegistryFriendlyByteBuf, KilnCookingRecipe> STREAM_CODEC = cookingStreamCodec(KilnCookingRecipe::new);

    public KilnCookingRecipe(Recipe.CommonInfo commonInfo, CookingBookInfo bookInfo, Ingredient ingredient, ItemStackTemplate result, float experience, int cookingTime) {
        super(commonInfo, bookInfo, ingredient, result, experience, cookingTime);
    }

    @Override
    public RecipeType<KilnCookingRecipe> getType() {
        return ModRecipeTypes.KILN_COOKING.get();
    }

    @Override
    public RecipeSerializer<KilnCookingRecipe> getSerializer() {
        return ModRecipeSerializers.KILN_COOKING.get();
    }

    @Override
    protected Item furnaceIcon() {
        return net.kaylamay.terranova.registry.block.ModBlocks.KILN.get().asItem();
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return switch (this.category()) {
            case BLOCKS -> RecipeBookCategories.FURNACE_BLOCKS;
            case FOOD -> RecipeBookCategories.FURNACE_FOOD;
            case MISC -> RecipeBookCategories.FURNACE_MISC;
        };
    }
}