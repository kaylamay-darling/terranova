package net.kaylamay.terranova.registry.item.custom;

import net.kaylamay.terranova.registry.block.ModBlocks;
import net.kaylamay.terranova.registry.entity.ModEntities;
import net.kaylamay.terranova.registry.entity.custom.GlowwormEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class GlowwormJarItem extends BlockItem {

    public GlowwormJarItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            Vec3 pos = player.position().add(player.getLookAngle().scale(1.5));
            GlowwormEntity glowworm = ModEntities.GLOWWORM.get().create(level, EntitySpawnReason.NATURAL);

            if (glowworm != null) {
                glowworm.snapTo(pos.x, pos.y, pos.z);
                level.addFreshEntity(glowworm);
                heldStack.shrink(1);
                ItemStack emptyJar = new ItemStack(ModBlocks.GLASS_JAR_ITEM.get());
                if (!player.getInventory().add(emptyJar)) {
                    player.drop(emptyJar, false);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}