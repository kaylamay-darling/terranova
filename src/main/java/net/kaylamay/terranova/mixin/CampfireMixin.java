package net.kaylamay.terranova.mixin;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class CampfireMixin {
    @Inject(method = "getPlacementState", at = @At("RETURN"), cancellable = true)
    private void forceUnlitCampfire(BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
        BlockState state = cir.getReturnValue();

        if (state.getBlock() instanceof CampfireBlock) {
            if (state.hasProperty(CampfireBlock.LIT)) {
                cir.setReturnValue(state.setValue(CampfireBlock.LIT, false));
            }
        }
    }
}