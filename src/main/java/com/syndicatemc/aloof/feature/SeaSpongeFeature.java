package com.syndicatemc.aloof.feature;

import com.mojang.serialization.Codec;
import com.syndicatemc.aloof.init.AloofBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;

public class SeaSpongeFeature extends Feature<CountConfiguration> {
    public SeaSpongeFeature(Codec<CountConfiguration> config) {
        super(config);
    }

    public boolean place(FeaturePlaceContext<CountConfiguration> feature) {
        int $$1 = 0;
        RandomSource random = feature.random();
        WorldGenLevel level = feature.level();
        BlockPos pos = feature.origin();
        int $$5 = (feature.config()).count().sample(random);

        for(int $$6 = 0; $$6 < $$5; ++$$6) {
            int $$7 = random.nextInt(8) - random.nextInt(8);
            int $$8 = random.nextInt(8) - random.nextInt(8);
            int $$9 = level.getHeight(Types.OCEAN_FLOOR, pos.getX() + $$7, pos.getZ() + $$8);
            BlockPos aPos = new BlockPos(pos.getX() + $$7, $$9, pos.getZ() + $$8);
            BlockState state = AloofBlocks.SEA_SPONGE.get().defaultBlockState();
            if (level.getBlockState(aPos).is(Blocks.WATER) && state.canSurvive(level, aPos)) {
                level.setBlock(aPos, state, 2);
                ++$$1;
            }
        }

        return $$1 > 0;
    }
}
