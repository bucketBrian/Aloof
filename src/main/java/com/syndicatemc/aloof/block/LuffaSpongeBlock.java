package com.syndicatemc.aloof.block;

import com.syndicatemc.aloof.init.AloofBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;

public class LuffaSpongeBlock extends SpongeBlock {
    public LuffaSpongeBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
    private static final Direction[] ALL_DIRECTIONS = Direction.values();
    public static final int MAX_DEPTH = 5;
    public static final int MAX_COUNT = 32;

    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.is(Items.FLINT_AND_STEEL)) {
            if (!level.isClientSide) {
                level.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
                level.setBlock(pos, AloofBlocks.SHRIVELED_LUFFA_SPONGE.get().defaultBlockState(), 11);
                level.levelEvent(2009, pos, 0);
                level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, (1.0F + level.getRandom().nextFloat() * 0.2F) * 0.7F);
                itemstack.hurtAndBreak(1, player, (breakEventPlayer) -> breakEventPlayer.broadcastBreakEvent(hand));
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return super.use(blockState, level, pos, player, hand, result);
        }
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState aState, boolean bool) {
        if (!aState.is(state.getBlock())) {
            this.tryAbsorbWater(level, pos);
        }
        if (level.dimensionType().ultraWarm()) {
            level.setBlock(pos, AloofBlocks.SHRIVELED_LUFFA_SPONGE.get().defaultBlockState(), 3);
            level.levelEvent(2009, pos, 0);
            level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, (1.0F + level.getRandom().nextFloat() * 0.2F) * 0.7F);
        }
    }
    
    @Override
    protected void tryAbsorbWater(Level level, BlockPos pos) {
        if (this.removeWaterBreadthFirstSearch(level, pos)) {
            level.setBlock(pos, AloofBlocks.WET_LUFFA_SPONGE.get().defaultBlockState(), 2);
            level.levelEvent(2001, pos, Block.getId(Blocks.WATER.defaultBlockState()));
            level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0F, (1.0F + level.getRandom().nextFloat() * 0.2F));
        }
    }

    public boolean removeWaterBreadthFirstSearch(Level level, BlockPos pos) {
        BlockState spongeState = level.getBlockState(pos);
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
                if (!spongeState.canBeHydrated(level, pos, fluidstate, bPos)) {
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
                        if (!blockstate.is(Blocks.KELP) && !blockstate.is(Blocks.KELP_PLANT) && !blockstate.is(Blocks.SEAGRASS) && !blockstate.is(Blocks.TALL_SEAGRASS)) {
                            return false;
                        }

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
