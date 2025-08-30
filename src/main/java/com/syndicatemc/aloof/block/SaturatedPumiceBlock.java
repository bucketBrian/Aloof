package com.syndicatemc.aloof.block;

import com.syndicatemc.aloof.init.AloofBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class SaturatedPumiceBlock extends Block {
    public SaturatedPumiceBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
    
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (!entity.isSteppingCarefully() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entity)) {
            entity.hurt(level.damageSources().hotFloor(), 1.0F);
        }
        super.stepOn(level, pos, state, entity);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState aState, boolean bool) {
        if (shouldSolidify(level, pos, state, aState.getFluidState())) {
            level.setBlock(pos, AloofBlocks.SMOOTH_PUMICE.get().defaultBlockState(), 2);
            level.levelEvent(2009, pos, 0);
            level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, (1.0F + level.getRandom().nextFloat() * 0.2F) * 0.7F);
            level.updateNeighborsAt(pos, AloofBlocks.SMOOTH_PUMICE.get());
        }
    }

    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos aPos, boolean bool) {
        if (shouldSolidify(level, pos, state, level.getFluidState(aPos))) {
            level.setBlock(pos, AloofBlocks.SMOOTH_PUMICE.get().defaultBlockState(), 2);
            level.levelEvent(2009, pos, 0);
            level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, (1.0F + level.getRandom().nextFloat() * 0.2F) * 0.7F);
            level.updateNeighborsAt(pos, AloofBlocks.SMOOTH_PUMICE.get());
        }
    }

    public void animateTick(BlockState blockState, Level level, BlockPos pos, RandomSource random) {
        Direction direction = Direction.getRandom(random);
        if (direction != Direction.UP) {
            BlockPos relative = pos.relative(direction);
            BlockState relativeState = level.getBlockState(relative);
            if (!blockState.canOcclude() || !relativeState.isFaceSturdy(level, relative, direction.getOpposite())) {
                double particleX = pos.getX();
                double particleY = pos.getY();
                double particleZ = pos.getZ();
                if (direction == Direction.DOWN) {
                    particleY -= 0.05;
                    particleX += random.nextDouble();
                    particleZ += random.nextDouble();
                } else {
                    particleY += random.nextDouble() * 0.8;
                    if (direction.getAxis() == Direction.Axis.X) {
                        particleZ += random.nextDouble();
                        if (direction == Direction.EAST) {
                            ++particleX;
                        } else {
                            particleX += 0.05;
                        }
                    } else {
                        particleX += random.nextDouble();
                        if (direction == Direction.SOUTH) {
                            ++particleZ;
                        } else {
                            particleZ += 0.05;
                        }
                    }
                }
                level.addParticle(ParticleTypes.DRIPPING_LAVA, particleX, particleY, particleZ, 0.0F, 0.0F, 0.0F);
            }
        }
    }

    private static boolean shouldSolidify(BlockGetter getter, BlockPos pos, BlockState state, FluidState fluidState) {
        return state.canBeHydrated(getter, pos, fluidState, pos) || touchesLiquid(getter, pos, state);
    }

    private static boolean shouldSolidify(BlockGetter getter, BlockPos pos, BlockState state) {
        return shouldSolidify(getter, pos, state, getter.getFluidState(pos));
    }

    //from ConcretePowderBlock
    private static boolean touchesLiquid(BlockGetter getter, BlockPos pos, BlockState state) {
        boolean flag = false;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();

        for(Direction direction : Direction.values()) {
            BlockState blockstate = getter.getBlockState(blockpos$mutableblockpos);
            if (direction != Direction.DOWN || state.canBeHydrated(getter, pos, blockstate.getFluidState(), blockpos$mutableblockpos)) {
                blockpos$mutableblockpos.setWithOffset(pos, direction);
                blockstate = getter.getBlockState(blockpos$mutableblockpos);
                if (state.canBeHydrated(getter, pos, blockstate.getFluidState(), blockpos$mutableblockpos) && !blockstate.isFaceSturdy(getter, pos, direction.getOpposite())) {
                    flag = true;
                    break;
                }
            }
        }

        return flag;
    }
}
