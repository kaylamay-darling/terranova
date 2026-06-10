package net.kaylamay.terranova.registry.blockentity;

import net.kaylamay.terranova.registry.ModBlockEntities;
import net.kaylamay.terranova.registry.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FieldCraftingTableBlockEntity extends BlockEntity {
    private int usesRemaining = 10;

    public FieldCraftingTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FIELD_CRAFTING_TABLE_BLOCK_ENTITY.get(), pos, state);
    }

    public void incrementUseCount(Level level, BlockPos pos) {
        this.usesRemaining--;
        this.setChanged();

        if (this.usesRemaining <= 0) {
            level.destroyBlock(pos, true);
        }
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter getter) {
        super.applyImplicitComponents(getter);
        Integer value = getter.get(ModDataComponents.USES_REMAINING.get());
        this.usesRemaining = (value != null) ? value : 10;
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder builder) {
        super.collectImplicitComponents(builder);
        builder.set(ModDataComponents.USES_REMAINING, this.usesRemaining);
    }
}