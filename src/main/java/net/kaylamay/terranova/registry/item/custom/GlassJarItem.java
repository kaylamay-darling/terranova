package net.kaylamay.terranova.registry.item.custom;

import net.kaylamay.terranova.registry.block.ModBlocks;
import net.kaylamay.terranova.registry.entity.custom.GlowwormEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class GlassJarItem extends BlockItem {

    public GlassJarItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (target instanceof GlowwormEntity glowworm && !player.level().isClientSide()) {
            glowworm.remove(net.minecraft.world.entity.Entity.RemovalReason.DISCARDED);
            stack.shrink(1);
            ItemStack filledJar = new ItemStack(ModBlocks.GLOWWORM_JAR_ITEM.get());
            if (!player.getInventory().add(filledJar)) {
                player.drop(filledJar, false);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}