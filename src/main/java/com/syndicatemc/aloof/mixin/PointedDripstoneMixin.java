package com.syndicatemc.aloof.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.syndicatemc.aloof.init.AloofBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(PointedDripstoneBlock.class)
public class PointedDripstoneMixin {
    @Inject(
            method = "maybeTransferFluid",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/PointedDripstoneBlock;findTip(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;IZ)Lnet/minecraft/core/BlockPos;",
                    shift = At.Shift.AFTER
            )
    )
    private static void aloof$maybeTransferFluid(BlockState state, ServerLevel level, BlockPos pos, float number, CallbackInfo ci, @Local(ordinal = 0) BlockPos tipPos, @Local(ordinal = 0) Optional<PointedDripstoneBlock.FluidInfo> fluidInfo, @Local(ordinal = 0) Fluid fluid) {
        if (tipPos != null) {
            if ((fluidInfo.get()).sourceState().is(AloofBlocks.SATURATED_PUMICE.get()) && fluid == Fluids.LAVA) {
                BlockState pumice = AloofBlocks.PUMICE.get().defaultBlockState();
                level.setBlockAndUpdate((fluidInfo.get()).pos(), pumice);
                Block.pushEntitiesUp((fluidInfo.get()).sourceState(), pumice, level, (fluidInfo.get()).pos());
                level.gameEvent(GameEvent.BLOCK_CHANGE, (fluidInfo.get()).pos(), GameEvent.Context.of(pumice));
                level.levelEvent(1504, tipPos, 0);
            }
        }
    }

    @Inject(
            method = "getFluidAboveStalactite",
            at = @At("RETURN"),
            cancellable = true
    )
    private static void aloof$getFluidAboveStalactite(Level level, BlockPos pos, BlockState state, CallbackInfoReturnable<Optional<PointedDripstoneBlock.FluidInfo>> cir) {
        Optional<PointedDripstoneBlock.FluidInfo> original = cir.getReturnValue();
        if (original.isPresent()) {
            BlockPos abovePos = original.get().pos();
            BlockState aboveState = level.getBlockState(abovePos);
            Fluid fluid = original.get().fluid();
            if (aboveState.is(AloofBlocks.SATURATED_PUMICE.get())) {
                cir.setReturnValue(Optional.of(FluidInfoAccessor.create(abovePos, Fluids.LAVA, aboveState)));
            }
        }
    }
}
