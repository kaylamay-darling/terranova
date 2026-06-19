package net.kaylamay.terranova.registry.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class LeavesStickModifier extends LootModifier {
    public static final MapCodec<LeavesStickModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
            inst.group(
                    LOOT_CONDITIONS_CODEC.fieldOf("conditions").forGetter(m -> m.conditions),
                    Codec.INT.optionalFieldOf("priority", 0).forGetter(m -> m.priority)
            ).apply(inst, LeavesStickModifier::new)
    );

    public LeavesStickModifier(LootItemCondition[] conditions, int priority) {
        super(conditions, priority);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        var blockState = context.getOptionalParameter(LootContextParams.BLOCK_STATE);
        var attacker = context.getOptionalParameter(LootContextParams.ATTACKING_ENTITY);

        if (blockState != null && blockState.getBlock() instanceof LeavesBlock
                && attacker instanceof Player
                && context.getRandom().nextFloat() < 0.25f) {
            generatedLoot.add(new ItemStack(Items.STICK, 1));
        }

        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}