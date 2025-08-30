package com.syndicatemc.aloof.block;

import com.syndicatemc.aloof.compat.JNECompat;
import com.syndicatemc.aloof.data.AloofTags;
import com.syndicatemc.aloof.init.AloofBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fml.ModList;

public class PumiceBlock extends Block {
    public PumiceBlock(Properties properties) {
        super(properties);
    }
    private static final Direction[] ALL_DIRECTIONS = Direction.values();
    public static final int MAX_DEPTH = 3;
    public static final int MAX_COUNT = 16;

    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState aState, boolean bool) {
        if (!aState.is(state.getBlock())) {
            this.tryAbsorbLava(level, pos);
            this.tryAbsorbEctoplasm(level, pos);
        }
    }

    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos aPos, boolean bool) {
        this.tryAbsorbLava(level, pos);
        this.tryAbsorbEctoplasm(level, pos);
        super.neighborChanged(state, level, pos, block, aPos, bool);
    }

    protected void tryAbsorbLava(Level level, BlockPos pos) {
        if (this.removeLavaBreadthFirstSearch(level, pos)) {
            level.setBlock(pos, AloofBlocks.SATURATED_PUMICE.get().defaultBlockState(), 2);
            level.levelEvent(2001, pos, Block.getId(Blocks.LAVA.defaultBlockState()));
            level.playSound(null, pos, SoundEvents.BUCKET_FILL_LAVA, SoundSource.BLOCKS, 1.0F, (1.0F + level.getRandom().nextFloat() * 0.2F));
        }
    }

    public boolean removeLavaBreadthFirstSearch(Level level, BlockPos pos) {
        return BlockPos.breadthFirstTraversal(pos, MAX_DEPTH, MAX_COUNT + 1, (aPos, consumerPos) -> {
            for (Direction direction : ALL_DIRECTIONS) {
                consumerPos.accept(aPos.relative(direction));
            }
        }, (bPos) -> {
            if (bPos.equals(pos)) {
                return true;
            } else {
                BlockState blockstate = level.getBlockState(bPos);
                FluidState fluidstate = level.getFluidState(bPos);
                if (!fluidstate.is(FluidTags.LAVA)) {
                    return false;
                } else {
                    Block block = blockstate.getBlock();
                    if (block instanceof BucketPickup) {
                        BucketPickup bucketpickup = (BucketPickup)block;
                        if (!bucketpickup.pickupBlock(level, bPos, blockstate).isEmpty()) {
                            return true;
                        }
                    }

                    if (blockstate.getBlock() instanceof LiquidBlock) {
                        level.setBlock(bPos, Blocks.AIR.defaultBlockState(), 3);
                    } else {
                        BlockEntity blockentity = blockstate.hasBlockEntity() ? level.getBlockEntity(bPos) : null;
                        dropResources(blockstate, level, bPos, blockentity);
                        level.setBlock(bPos, Blocks.AIR.defaultBlockState(), 3);
                    }

                    return true;
                }
            }
        }) > 1;
    }

    protected void tryAbsorbEctoplasm(Level level, BlockPos pos) {
        if (this.removeEctoplasmBreadthFirstSearch(level, pos)) {
            level.setBlock(pos, AloofBlocks.SOUL_SATURATED_PUMICE.get().defaultBlockState(), 2);
            level.levelEvent(2001, pos, Block.getId(Blocks.LAVA.defaultBlockState()));
            if (ModList.get().isLoaded("netherexp")) {
                level.playSound(null, pos, JNECompat.ectoplasmBucketFill, SoundSource.BLOCKS, 1.0F, (0.7F + level.getRandom().nextFloat() * 0.2F));
            } else {
                level.playSound(null, pos, SoundEvents.BUCKET_FILL_LAVA, SoundSource.BLOCKS, 1.0F, (1.0F + level.getRandom().nextFloat() * 0.2F));
            }
        }
    }

    public boolean removeEctoplasmBreadthFirstSearch(Level level, BlockPos pos) {
        return BlockPos.breadthFirstTraversal(pos, MAX_DEPTH, MAX_COUNT, (aPos, consumerPos) -> {
            for (Direction direction : ALL_DIRECTIONS) {
                consumerPos.accept(aPos.relative(direction));
            }
        }, (bPos) -> {
            if (bPos.equals(pos)) {
                return true;
            } else {
                BlockState blockstate = level.getBlockState(bPos);
                FluidState fluidstate = level.getFluidState(bPos);
                if (!fluidstate.is(AloofTags.ECTOPLASMS)) {
                    return false;
                } else {
                    Block block = blockstate.getBlock();
                    if (block instanceof BucketPickup) {
                        BucketPickup bucketpickup = (BucketPickup)block;
                        if (!bucketpickup.pickupBlock(level, bPos, blockstate).isEmpty()) {
                            return true;
                        }
                    }

                    if (blockstate.getBlock() instanceof LiquidBlock) {
                        level.setBlock(bPos, Blocks.AIR.defaultBlockState(), 3);
                    } else {
                        BlockEntity blockentity = blockstate.hasBlockEntity() ? level.getBlockEntity(bPos) : null;
                        dropResources(blockstate, level, bPos, blockentity);
                        level.setBlock(bPos, Blocks.AIR.defaultBlockState(), 3);
                    }
                    return true;
                }
            }
        }) > 1;
    }
}