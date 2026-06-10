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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
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
import java.util.Comparator;
import java.util.List;

@Mixin(CampfireBlockEntity.class)
public class CampfireRandomTickMixin {

    @Unique private static final int MAX_RADIUS = 5;
    @Unique private static final int SPREAD_INTERVAL = 1200;
    @Unique private static final int HEAL_INTERVAL = 40;
    @Unique private int terranova$tickCounter = 0;

    @Inject(method = "cookTick", at = @At("TAIL"))
    private static void terranova$onTick(
            ServerLevel level, BlockPos pos, BlockState state,
            CampfireBlockEntity blockEntity,
            RecipeManager.CachedCheck<SingleRecipeInput, CampfireCookingRecipe> recipeCache,
            CallbackInfo ci) {

        if (!state.getValue(BlockStateProperties.LIT)) return;

        if (level.getGameTime() % HEAL_INTERVAL == 0) {
            AABB area = new AABB(pos).inflate(7.0);
            level.getEntitiesOfClass(Player.class, area).forEach(player -> {
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 80, 0));
            });

            level.sendParticles(
                    ParticleTypes.HEART,
                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    3,
                    2, 2, 2,
                    0.04
            );
        }

        BlockPos floorCenter = pos.below();
        if (!terranova$isAshable(level.getBlockState(floorCenter))) return;

        CampfireRandomTickMixin self = (CampfireRandomTickMixin) (Object) blockEntity;
        self.terranova$tickCounter++;
        if (self.terranova$tickCounter < SPREAD_INTERVAL) return;
        self.terranova$tickCounter = 0;

        List<BlockPos> candidates = new ArrayList<>();
        for (int dx = -MAX_RADIUS; dx <= MAX_RADIUS; dx++) {
            for (int dz = -MAX_RADIUS; dz <= MAX_RADIUS; dz++) {
                if (dx * dx + dz * dz <= MAX_RADIUS * MAX_RADIUS) {
                    candidates.add(floorCenter.offset(dx, 0, dz));
                }
            }
        }
        candidates.sort(Comparator.comparingDouble(p -> p.distSqr(floorCenter)));

        for (BlockPos candidate : candidates) {
            if (terranova$isAshable(level.getBlockState(candidate)) && terranova$isReachable(level, floorCenter, candidate)) {
                level.setBlock(candidate, ModBlocks.ASH_BLOCK.get().defaultBlockState(), 3);
                return;
            }
        }
    }

    @Unique
    private static boolean terranova$isReachable(Level level, BlockPos center, BlockPos target) {
        if (target.equals(center)) return true;
        int tx = target.getX() - center.getX();
        int tz = target.getZ() - center.getZ();
        int steps = Math.max(Math.abs(tx), Math.abs(tz));
        for (int i = 1; i < steps; i++) {
            int ix = Math.round((float) tx * i / steps);
            int iz = Math.round((float) tz * i / steps);
            BlockPos intermediate = center.offset(ix, 0, iz);
            if (!level.getBlockState(intermediate).is(ModBlocks.ASH_BLOCK.get()) && !intermediate.equals(center)) {
                return false;
            }
        }
        return true;
    }

    @Unique
    private static boolean terranova$isAshable(BlockState state) {
        return state.is(Blocks.GRASS_BLOCK) ||
                state.is(Blocks.DIRT) ||
                state.is(Blocks.COARSE_DIRT) ||
                state.is(Blocks.ROOTED_DIRT) ||
                state.is(Blocks.PODZOL) ||
                state.is(Blocks.MYCELIUM) ||
                state.is(Blocks.GRAVEL) ||
                state.is(Blocks.SAND) ||
                state.is(Blocks.RED_SAND);
    }
}