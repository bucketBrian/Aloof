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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ToolActions;

public class LuffaGourdBlock extends StemGrownBlock {
    public LuffaGourdBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.canPerformAction(ToolActions.AXE_STRIP)) {
            if (!level.isClientSide) {
                level.playSound(null, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.setBlock(pos, AloofBlocks.LUFFA_SPONGE.get().defaultBlockState(), 11);
                itemstack.hurtAndBreak(1, player, (breakEventPlayer) -> breakEventPlayer.broadcastBreakEvent(hand));
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return super.use(blockState, level, pos, player, hand, result);
        }
    }

    public StemBlock getStem() {
        return (StemBlock) AloofBlocks.LUFFA_CROP.get();
    }
    public AttachedStemBlock getAttachedStem() {
        return (AttachedStemBlock) AloofBlocks.ATTACHED_LUFFA_CROP.get();
    }
}
