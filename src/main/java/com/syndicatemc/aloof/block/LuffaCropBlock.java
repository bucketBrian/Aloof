package com.syndicatemc.aloof.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class LuffaCropBlock extends StemBlock {
    protected static final float AABB_OFFSET = 1.0F;

    public LuffaCropBlock(StemGrownBlock block, Supplier<Item> item, BlockBehaviour.Properties properties) {
        super(block, item, properties);
        this.registerDefaultState((this.stateDefinition.any()).setValue(AGE, 0));
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState blockState) {
        int i = Math.min(7, blockState.getValue(AGE) + Mth.nextInt(level.random, 1, 2));
        BlockState blockstate = blockState.setValue(AGE, i);
        level.setBlock(pos, blockstate, 2);
        if (i == 7) {
            blockstate.randomTick(level, pos, level.random);
        }
    }
}
