package com.syndicatemc.aloof.block;

import com.syndicatemc.aloof.compat.JNECompat;
import com.syndicatemc.aloof.init.AloofBlocks;
import com.teamabnormals.blueprint.core.util.NetworkUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fml.ModList;

public class SoulSaturatedPumiceBlock extends Block {
    public SoulSaturatedPumiceBlock(Properties properties) {
        super(properties);
    }
    
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (!entity.isSteppingCarefully() && entity instanceof LivingEntity) {
            entity.setTicksFrozen(entity.getTicksFrozen() + 4);
        }
        super.stepOn(level, pos, state, entity);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState aState, boolean bool) {
        if (shouldSolidify(level, pos, state, aState.getFluidState())) {
            level.setBlock(pos, AloofBlocks.PUMICE.get().defaultBlockState(), 2);
            level.playSound(null, pos, SoundEvents.SOUL_SAND_BREAK, SoundSource.BLOCKS, 1.0F, (1.0F + level.getRandom().nextFloat() * 0.2F) * 0.7F);
            exorcize(level, pos);
            level.updateNeighborsAt(pos, AloofBlocks.PUMICE.get());
        }
    }

    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos aPos, boolean bool) {
        if (shouldSolidify(level, pos, state, level.getFluidState(aPos))) {
            level.setBlock(pos, AloofBlocks.PUMICE.get().defaultBlockState(), 2);
            level.playSound(null, pos, SoundEvents.SOUL_SAND_BREAK, SoundSource.BLOCKS, 1.0F, (1.0F + level.getRandom().nextFloat() * 0.2F) * 0.7F);
            exorcize(level, pos);
            level.updateNeighborsAt(pos, AloofBlocks.PUMICE.get());
        }
    }

    private void exorcize(Level level, BlockPos pos) {
        if (!level.isClientSide) {
            for (int i = 0; i < 10; i++) {
                double speedShuffle = Math.max(0.1, Math.random()) / 5;
                NetworkUtil.spawnParticle(
                        ParticleTypes.SOUL.writeToString(),
                        pos.getX() + Math.random(),
                        pos.getY() + 1,
                        pos.getZ() + Math.random(),
                        0.0, speedShuffle, 0.0
                );
            }
        }
    }

    public void animateTick(BlockState blockState, Level level, BlockPos pos, RandomSource random) {
        Direction direction = Direction.getRandom(random);
        if (direction != Direction.DOWN) {
            BlockPos relative = pos.relative(direction);
            BlockState relativeState = level.getBlockState(relative);
            if (!blockState.canOcclude() || !relativeState.isFaceSturdy(level, relative, direction.getOpposite())) {
                double particleX = pos.getX();
                double particleY = pos.getY();
                double particleZ = pos.getZ();
                if (direction == Direction.UP) {
                    particleY += 0.1;
                    particleX += random.nextDouble();
                    particleZ += random.nextDouble();
                } else {
                    particleY += random.nextDouble() * 1.0;
                    if (direction.getAxis() == Direction.Axis.X) {
                        particleZ += random.nextDouble();
                        if (direction == Direction.EAST) {
                            ++particleX;
                        } else {
                            particleX += 0.1;
                        }
                    } else {
                        particleX += random.nextDouble();
                        if (direction == Direction.SOUTH) {
                            ++particleZ;
                        } else {
                            particleZ += 0.1;
                        }
                    }
                }
                if (ModList.get().isLoaded("netherexp")) {
                    level.addParticle((ParticleOptions) JNECompat.ectoplasmaParticle, particleX, particleY, particleZ, 0.0, 0.0, 0.0);
                    if (Math.random() < 0.3) {
                        level.addParticle(ParticleTypes.SOUL, particleX, particleY, particleZ, 0.0, 0.1, 0.0);
                    }
                } else {
                    level.addParticle(ParticleTypes.SOUL, particleX, particleY, particleZ, 0.0, 0.1, 0.0);
                }
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
