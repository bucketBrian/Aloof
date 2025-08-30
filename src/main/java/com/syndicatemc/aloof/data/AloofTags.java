package com.syndicatemc.aloof.data;

import com.syndicatemc.aloof.Aloof;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class AloofTags {

    public static final TagKey<Block> SEA_SPONGE_PLACE_ON = blockTag("sea_sponge_place_on");
    public static final TagKey<Block> LAVA_VESSELS = blockTag("lava_vessels");

    @SuppressWarnings("removal")
    private static TagKey<Block> blockTag(String path) {
        return BlockTags.create(new ResourceLocation(Aloof.MOD_ID, path));
    }

    public static final TagKey<Fluid> ECTOPLASMS = fluidTag("ectoplasms");

    @SuppressWarnings("removal")
    private static TagKey<Fluid> fluidTag(String path) {
        return TagKey.create(Registries.FLUID, new ResourceLocation(Aloof.MOD_ID, path));
    }
}
