package com.syndicatemc.aloof.other;

import com.syndicatemc.aloof.init.AloofBlocks;
import com.syndicatemc.aloof.init.AloofItems;
import com.teamabnormals.blueprint.core.util.DataUtil;

public class AloofCompat {
    public static void register() {
        registerCompostables();
    }

    public static void registerCompostables() {
        DataUtil.registerCompostable(AloofBlocks.LUFFA_GOURD.get(), 0.50F);
        DataUtil.registerCompostable(AloofBlocks.LUFFA_SPONGE.get(), 0.50F);
        DataUtil.registerCompostable(AloofBlocks.WET_LUFFA_SPONGE.get(), 1.0F);

        DataUtil.registerCompostable(AloofItems.LUFFA_SEEDS.get(), 0.30F);
    }
}