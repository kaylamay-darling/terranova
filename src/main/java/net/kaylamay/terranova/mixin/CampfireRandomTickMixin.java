package net.kaylamay.terranova.mixin;

import net.kaylamay.terranova.registry.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mixin(CampfireBlockEntity.class)
public class CampfireRandomTickMixin {

    @Unique private static final int MAX_RADIUS = 5;
    @Unique private static final int HEAL_INTERVAL = 40;
    @Unique private static final int SPREAD_CHANCE = 2000;

    @Inject(method = "cookTick", at = @At("TAIL"))
    private static void terranova$onTick(
            ServerLevel level, BlockPos pos, BlockState state,
            CampfireBlockEntity blockEntity,
            RecipeManager.CachedCheck<SingleRecipeInput, CampfireCookingRecipe> recipeCache,
            CallbackInfo ci) {

        if (!state.getValue(BlockStateProperties.LIT)) return;

        // Healing
        if (level.getGameTime() % HEAL_INTERVAL == 0) {
            AABB area = new AABB(pos).inflate(7.0);
            level.getEntitiesOfClass(Player.class, area).forEach(player ->
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 80, 0))
            );
            level.sendParticles(ParticleTypes.HEART,
                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    3, 0.5, 0.5, 0.5, 0.05);
        }

        // Ash spreading
        if (level.getRandom().nextInt(SPREAD_CHANCE) != 0) return;

        List<BlockPos> candidates = new ArrayList<>();
        for (int dx = -MAX_RADIUS; dx <= MAX_RADIUS; dx++) {
            for (int dz = -MAX_RADIUS; dz <= MAX_RADIUS; dz++) {
                if (dx == 0 && dz == 0) continue;
                if (dx * dx + dz * dz <= MAX_RADIUS * MAX_RADIUS) {
                    candidates.add(pos.offset(dx, 0, dz)); // same Y as campfire
                }
            }
        }
        Collections.shuffle(candidates, new java.util.Random(level.getRandom().nextLong()));

        for (BlockPos candidate : candidates) {
            if (!terranova$isReachable(level, pos, candidate)) continue;

            BlockState atCandidate = level.getBlockState(candidate);
            BlockState below = level.getBlockState(candidate.below());

            if (atCandidate.is(ModBlocks.ASH_LAYER.get())) {
                int currentLayers = atCandidate.getValue(SnowLayerBlock.LAYERS);
                if (currentLayers < 8) {
                    level.setBlock(candidate, atCandidate.setValue(SnowLayerBlock.LAYERS, currentLayers + 1), 3);
                    return;
                }
            } else if (atCandidate.isAir() && terranova$isAshable(below)) {
                // Candidate is air, surface below is ashable — place ash on top
                level.setBlock(candidate, ModBlocks.ASH_LAYER.get().defaultBlockState(), 3);
                return;
            }
        }
    }

    @Unique
    private static boolean terranova$isReachable(ServerLevel level, BlockPos center, BlockPos target) {
        int tx = target.getX() - center.getX();
        int tz = target.getZ() - center.getZ();
        int steps = Math.max(Math.abs(tx), Math.abs(tz));
        for (int i = 1; i < steps; i++) {
            int ix = Math.round((float) tx * i / steps);
            int iz = Math.round((float) tz * i / steps);
            BlockPos intermediate = center.offset(ix, 0, iz);
            BlockState s = level.getBlockState(intermediate);
            if (!s.isAir() && !s.is(ModBlocks.ASH_LAYER.get())) return false;
        }
        return true;
    }

    @Unique
    private static boolean terranova$isAshable(BlockState state) {
        return state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT) || state.is(Blocks.COARSE_DIRT) ||
                state.is(Blocks.ROOTED_DIRT) || state.is(Blocks.PODZOL) || state.is(Blocks.MYCELIUM) ||
                state.is(Blocks.GRAVEL) || state.is(Blocks.SAND) || state.is(Blocks.RED_SAND);
    }
}