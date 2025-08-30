package com.syndicatemc.aloof.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PointedDripstoneBlock.FluidInfo.class)
public interface FluidInfoAccessor {
    @Invoker("<init>")
    static PointedDripstoneBlock.FluidInfo create(BlockPos pos, Fluid fluid, BlockState sourceState) {
        throw new AssertionError();
    }
}