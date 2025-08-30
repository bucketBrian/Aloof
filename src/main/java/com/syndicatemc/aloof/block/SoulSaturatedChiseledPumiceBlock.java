package com.syndicatemc.aloof.block;

import com.syndicatemc.aloof.compat.JNECompat;
import com.syndicatemc.aloof.init.AloofBlocks;
import com.syndicatemc.aloof.init.AloofItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fml.ModList;

import static com.syndicatemc.aloof.block.ChiseledPumiceBlock.LEVEL;

public class SoulSaturatedChiseledPumiceBlock extends Block {
    public SoulSaturatedChiseledPumiceBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, 1));
    }

    public boolean isFull(BlockState state) {
        return state.getValue(LEVEL) == 3;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return new ItemStack(AloofBlocks.CHISELED_PUMICE.get());
    }

    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState aState, boolean bool) {
        level.scheduleTick(pos, this, 20 + level.getRandom().nextInt(20));
    }

    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        this.drainBelowVessel(state, level, pos);
        level.scheduleTick(pos, this, 20 + random.nextInt(20));
    }

    protected void drainBelowVessel(BlockState state, Level level, BlockPos pos) {
        BlockPos belowPos = pos.relative(Direction.DOWN);
        BlockState belowState = level.getBlockState(belowPos);
        if (belowState.is(AloofBlocks.SOUL_SATURATED_CHISELED_PUMICE.get()) && state.getValue(LEVEL) < 3) {
            if (belowState.getValue(LEVEL) > 1) {
                level.setBlockAndUpdate(belowPos, AloofBlocks.SOUL_SATURATED_CHISELED_PUMICE.get().defaultBlockState().setValue(LEVEL, belowState.getValue(LEVEL) - 1));
                level.setBlockAndUpdate(pos, AloofBlocks.SOUL_SATURATED_CHISELED_PUMICE.get().defaultBlockState().setValue(LEVEL, state.getValue(LEVEL) + 1));
            } else {
                level.setBlockAndUpdate(belowPos, AloofBlocks.CHISELED_PUMICE.get().defaultBlockState());
                level.setBlockAndUpdate(pos, AloofBlocks.SOUL_SATURATED_CHISELED_PUMICE.get().defaultBlockState().setValue(LEVEL, state.getValue(LEVEL) + 1));
            }
        }
    }

    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (ModList.get().isLoaded("netherexp")) {
            if (itemstack.is(JNECompat.ectoplasmBucket) && !isFull(blockState)) {
                if (!level.isClientSide) {
                    level.setBlockAndUpdate(pos, AloofBlocks.SOUL_SATURATED_CHISELED_PUMICE.get().defaultBlockState().setValue(LEVEL, blockState.getValue(LEVEL) + 1));
                    level.playSound(null, pos, JNECompat.ectoplasmBucketEmpty, SoundSource.BLOCKS, 1.0F, (0.9F + level.getRandom().nextFloat() * 0.2F));
                    if (!player.isCreative()) {
                        itemstack.shrink(1);
                        player.addItem(Items.BUCKET.getDefaultInstance());
                    }
                    level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                }
                return InteractionResult.SUCCESS;
            } else if (itemstack.is(Items.BUCKET)) {
                if (!level.isClientSide) {
                    if (blockState.getValue(LEVEL) == 1) {
                        level.setBlockAndUpdate(pos, AloofBlocks.CHISELED_PUMICE.get().defaultBlockState());
                        level.playSound(null, pos, JNECompat.ectoplasmBucketFill, SoundSource.BLOCKS, 1.0F, (0.9F + level.getRandom().nextFloat() * 0.2F));
                    } else {
                        level.setBlockAndUpdate(pos, AloofBlocks.SOUL_SATURATED_CHISELED_PUMICE.get().defaultBlockState().setValue(LEVEL, blockState.getValue(LEVEL) - 1));
                        level.playSound(null, pos, JNECompat.ectoplasmBucketFill, SoundSource.BLOCKS, 1.0F, (0.9F + level.getRandom().nextFloat() * 0.2F));
                    }
                    if (!player.isCreative()) {
                        itemstack.shrink(1);
                        player.addItem(JNECompat.ectoplasmBucket.getDefaultInstance());
                    }
                    level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                }
                return InteractionResult.SUCCESS;
            } else {
                return super.use(blockState, level, pos, player, hand, result);
            }
        } else {
            return super.use(blockState, level, pos, player, hand, result);
        }
    }
}