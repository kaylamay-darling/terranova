package net.kaylamay.terranova.registry.entity.custom;

import net.kaylamay.terranova.entity.ai.GlowwormWanderGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class GlowwormEntity extends AmbientCreature {
    public GlowwormEntity(EntityType<? extends GlowwormEntity> type, Level level) {
        super(type, level);
        this.noPhysics = false;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return AmbientCreature.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 0.1)
                .add(Attributes.FLYING_SPEED, 0.05)
                .add(Attributes.MOVEMENT_SPEED, 0.05);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new GlowwormWanderGoal(this));
    }

    @Override
    protected int getBaseExperienceReward(ServerLevel level) {
        return 0;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isInWater()) {
            this.moveRelative(0.02f, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.8));
        } else {
            this.moveRelative(0.02f, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.91));
        }
    }

    private int lifespanTicks = 1200 + this.random.nextInt(1200);

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            if (--lifespanTicks <= 0) {
                this.remove(RemovalReason.DISCARDED);
                return;
            }
        }
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return false;
    }

    @Override
    public boolean isInWall() {
        return false;
    }
}
