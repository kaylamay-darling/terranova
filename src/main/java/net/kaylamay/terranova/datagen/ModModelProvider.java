package net.kaylamay.terranova.datagen;

import net.kaylamay.terranova.TerraNova;
import net.kaylamay.terranova.registry.block.ModBlocks;
import net.minecraft.data.PackOutput;

import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.client.model.generators.BlockModelGenerators;
import net.neoforged.neoforge.client.model.generators.ItemModelGenerators;

public class ModModelProvider extends ModelProvider {

    public ModModelProvider(PackOutput output) {
        super(output, TerraNova.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        blockModels.createTrivialCube(ModBlocks.ZINC_BLOCK.get());
        blockModels.familyWithExistingFullBlock(ModBlocks.ZINC_BLOCK.get())
                .stairs(ModBlocks.ZINC_STAIRS.get())
                .slab(ModBlocks.ZINC_SLAB.get());
    }
}