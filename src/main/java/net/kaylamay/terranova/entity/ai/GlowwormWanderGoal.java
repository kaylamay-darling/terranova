package net.kaylamay.terranova.entity.ai;

import net.kaylamay.terranova.registry.block.ModBlocks;
import net.kaylamay.terranova.registry.entity.custom.GlowwormEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class GlowwormWanderGoal extends Goal {

    private final GlowwormEntity entity;
    private Vec3 targetPos;
    private int recalcTimer = 0;

    public GlowwormWanderGoal(GlowwormEntity entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return true; // always active
    }

    @Override
    public boolean canContinueToUse() {
        return true;
    }

    @Override
    public void tick() {
        recalcTimer--;

        if (recalcTimer <= 0) {
            recalcTimer = 5 + entity.getRandom().nextInt(10);
            targetPos = findNearbyTarget();
        }

        if (targetPos != null) {
            Vec3 diff = targetPos.subtract(entity.position());
            if (diff.lengthSqr() > 0.1) {
                Vec3 motion = diff.normalize().scale(0.06);
                entity.setDeltaMovement(
                        entity.getDeltaMovement().scale(0.85).add(motion)
                );
            }
        }
    }

    private Vec3 findNearbyTarget() {
        BlockPos origin = entity.blockPosition();
        int range = 6;
        for (int attempt = 0; attempt < 8; attempt++) {
            BlockPos candidate = origin.offset(
                    entity.getRandom().nextIntBetweenInclusive(-range, range),
                    entity.getRandom().nextIntBetweenInclusive(-1, 1),
                    entity.getRandom().nextIntBetweenInclusive(-range, range)
            );

            if (entity.level().getBlockState(candidate).is(ModBlocks.SILK_THREAD_BLOCK.get())) {
                return Vec3.atCenterOf(candidate).add(0, -0.5, 0);
            }
        }

        return Vec3.atCenterOf(origin).add(
                entity.getRandom().nextIntBetweenInclusive(-3, 3),
                entity.getRandom().nextIntBetweenInclusive(-1, 1),
                entity.getRandom().nextIntBetweenInclusive(-3, 3)
        );
    }
}