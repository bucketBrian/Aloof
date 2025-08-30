package com.syndicatemc.aloof.block;

import com.syndicatemc.aloof.data.AloofTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class SeaSpongeBlock extends CoralFanBlock {
    public SeaSpongeBlock(Block block, BlockBehaviour.Properties properties) {
        super(block, properties);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!scanForPreservers(state, level, pos)) {
            level.destroyBlock(pos, true);
        }
    }
    
    protected static boolean scanForPreservers(BlockState state, BlockGetter getter, BlockPos pos) {
        if (state.getValue(WATERLOGGED)) {
            return true;
        } else {
            for(Direction direction : Direction.values()) {
                if (getter.getFluidState(pos.relative(direction)).is(FluidTags.WATER) || getter.getBlockState(pos.relative(direction)).is(AloofTags.SEA_SPONGE_PLACE_ON)) {
                    return true;
                }
            }
            return false;
        }
    }

    public void animateTick(BlockState blockState, Level level, BlockPos pos, RandomSource random) {
        if (blockState.getValue(WATERLOGGED)) {
            level.addParticle(ParticleTypes.BUBBLE_COLUMN_UP, pos.getX() + Math.random(), pos.getY() + Math.random(), pos.getZ() + Math.random(), 0.0F, 0.0F, 0.0F);
        }
    }
}