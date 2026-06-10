package net.kaylamay.terranova.registry.blockentity;

import net.kaylamay.terranova.registry.ModBlockEntities;
import net.kaylamay.terranova.registry.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.FuelValues;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class KilnBlockEntity extends AbstractFurnaceBlockEntity {

    public KilnBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.KILN.get(), pos, state, ModRecipeTypes.KILN_COOKING.get());
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.terranova.kiln");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new FurnaceMenu(id, inventory, this, this.dataAccess);
    }

    @Override
    protected int getBurnDuration(FuelValues fuelValues, ItemStack fuel) {
        return super.getBurnDuration(fuelValues, fuel);
    }

    public ContainerData getDataAccess() {
        return this.dataAccess;
    }
}