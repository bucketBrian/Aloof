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
import net.minecraft.world.level.block.WetSpongeBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class WetLuffaSpongeBlock extends WetSpongeBlock {
    public WetLuffaSpongeBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.is(Items.FLINT_AND_STEEL)) {
            if (!level.isClientSide) {
                Direction direction = result.getDirection();
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
    public void onPlace(BlockState blockState, Level level, BlockPos pos, BlockState blockStateB, boolean bool) {
        if (level.dimensionType().ultraWarm()) {
            level.setBlock(pos, AloofBlocks.SHRIVELED_LUFFA_SPONGE.get().defaultBlockState(), 3);
            level.levelEvent(2009, pos, 0);
            level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, (1.0F + level.getRandom().nextFloat() * 0.2F) * 0.7F);
        }
    }
}
