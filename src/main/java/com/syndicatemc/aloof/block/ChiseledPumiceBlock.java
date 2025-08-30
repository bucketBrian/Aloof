package com.syndicatemc.aloof.block;

import com.syndicatemc.aloof.compat.JNECompat;
import com.syndicatemc.aloof.init.AloofBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fml.ModList;

public class ChiseledPumiceBlock extends Block {
    public ChiseledPumiceBlock(Properties properties) {
        super(properties);
    }

    public static final IntegerProperty LEVEL;

    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState aState, boolean bool) {
        level.scheduleTick(pos, this, 20 + level.getRandom().nextInt(20));
    }

    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        this.drainAboveVessel(state, level, pos);
        this.drainBelowVessel(state, level, pos);
        level.scheduleTick(pos, this, 20 + random.nextInt(20));
    }

    protected void drainAboveVessel(BlockState state, Level level, BlockPos pos) {
        BlockPos abovePos = pos.relative(Direction.UP);
        BlockState aboveState = level.getBlockState(abovePos);
        if (aboveState.is(AloofBlocks.SATURATED_CHISELED_PUMICE.get())) {
            if (aboveState.getValue(LEVEL) > 1) {
                level.setBlockAndUpdate(abovePos, AloofBlocks.SATURATED_CHISELED_PUMICE.get().defaultBlockState().setValue(LEVEL, aboveState.getValue(LEVEL) - 1));
                level.setBlockAndUpdate(pos, AloofBlocks.SATURATED_CHISELED_PUMICE.get().defaultBlockState().setValue(LEVEL, 1));
            } else {
                level.setBlockAndUpdate(abovePos, AloofBlocks.CHISELED_PUMICE.get().defaultBlockState());
                level.setBlockAndUpdate(pos, AloofBlocks.SATURATED_CHISELED_PUMICE.get().defaultBlockState().setValue(LEVEL, 1));
            }
        }
    }

    protected void drainBelowVessel(BlockState state, Level level, BlockPos pos) {
        BlockPos belowPos = pos.relative(Direction.DOWN);
        BlockState belowState = level.getBlockState(belowPos);
        if (belowState.is(AloofBlocks.SOUL_SATURATED_CHISELED_PUMICE.get())) {
            if (belowState.getValue(LEVEL) > 1) {
                level.setBlockAndUpdate(belowPos, AloofBlocks.SOUL_SATURATED_CHISELED_PUMICE.get().defaultBlockState().setValue(LEVEL, belowState.getValue(LEVEL) - 1));
                level.setBlockAndUpdate(pos, AloofBlocks.SOUL_SATURATED_CHISELED_PUMICE.get().defaultBlockState().setValue(LEVEL, 1));
            } else {
                level.setBlockAndUpdate(belowPos, AloofBlocks.CHISELED_PUMICE.get().defaultBlockState());
                level.setBlockAndUpdate(pos, AloofBlocks.SOUL_SATURATED_CHISELED_PUMICE.get().defaultBlockState().setValue(LEVEL, 1));
            }
        }
    }

    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.is(Items.LAVA_BUCKET)) {
            if (!level.isClientSide) {
                level.setBlockAndUpdate(pos, AloofBlocks.SATURATED_CHISELED_PUMICE.get().defaultBlockState());
                level.playSound(null, pos, SoundEvents.BUCKET_EMPTY_LAVA, SoundSource.BLOCKS, 1.0F, (1.0F + level.getRandom().nextFloat() * 0.2F));
                if (!player.isCreative()) {
                    itemstack.shrink(1);
                    player.addItem(Items.BUCKET.getDefaultInstance());
                }
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            }
            return InteractionResult.SUCCESS;
        } else if (ModList.get().isLoaded("netherexp")) {
            if (itemstack.is(JNECompat.ectoplasmBucket)) {
                if (!level.isClientSide) {
                    level.setBlockAndUpdate(pos, AloofBlocks.SOUL_SATURATED_CHISELED_PUMICE.get().defaultBlockState());
                    level.playSound(null, pos, JNECompat.ectoplasmBucketEmpty, SoundSource.BLOCKS, 1.0F, (0.9F + level.getRandom().nextFloat() * 0.2F));
                    if (!player.isCreative()) {
                        itemstack.shrink(1);
                        player.addItem(Items.BUCKET.getDefaultInstance());
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

    static {
        LEVEL = BlockStateProperties.LEVEL_CAULDRON;
    }
}