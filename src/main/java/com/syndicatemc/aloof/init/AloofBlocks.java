package com.syndicatemc.aloof.init;

import com.syndicatemc.aloof.Aloof;
import com.syndicatemc.aloof.block.*;
import com.teamabnormals.blueprint.core.util.item.CreativeModeTabContentsPopulator;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Predicate;
import java.util.function.ToIntFunction;

import static net.minecraft.world.item.CreativeModeTabs.*;
import static net.minecraft.world.item.crafting.Ingredient.of;


@Mod.EventBusSubscriber(modid = Aloof.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AloofBlocks {
    public static final BlockSubRegistryHelper HELPER = Aloof.REGISTRY_HELPER.getBlockSubHelper();

    private static ToIntFunction<BlockState> chiseledPumiceLight() {
        return (state) -> state.getValue(BlockStateProperties.LEVEL_CAULDRON) * 5;
    }
    private static ToIntFunction<BlockState> soulChiseledPumiceLight() {
        return (state) -> state.getValue(BlockStateProperties.LEVEL_CAULDRON) * 4;
    }

    public static final RegistryObject<Block> LUFFA_GOURD = HELPER.createBlock("luffa_gourd", () -> new LuffaGourdBlock(Properties.copy(Blocks.CRIMSON_PLANKS).mapColor(MapColor.COLOR_LIGHT_GREEN)));
    public static final RegistryObject<Block> LUFFA_SPONGE = HELPER.createBlock("luffa_sponge", () -> new LuffaSpongeBlock(Properties.copy(Blocks.SPONGE).mapColor(MapColor.COLOR_YELLOW)));
    public static final RegistryObject<Block> WET_LUFFA_SPONGE = HELPER.createBlock("wet_luffa_sponge", () -> new WetLuffaSpongeBlock(Properties.copy(Blocks.WET_SPONGE).sound(SoundType.MUDDY_MANGROVE_ROOTS).mapColor(MapColor.COLOR_YELLOW)));
    public static final RegistryObject<Block> SHRIVELED_LUFFA_SPONGE = HELPER.createBlockNoItem("shriveled_luffa_sponge", () -> new Block(Properties.copy(Blocks.SPONGE).mapColor(MapColor.COLOR_BROWN)));
    public static final RegistryObject<Block> PUMICE = HELPER.createBlock("pumice", () -> new PumiceBlock(Properties.copy(Blocks.BLACKSTONE).sound(SoundType.DRIPSTONE_BLOCK).mapColor(MapColor.COLOR_GRAY)));
    public static final RegistryObject<Block> SATURATED_PUMICE = HELPER.createBlock("saturated_pumice", () -> new SaturatedPumiceBlock(Properties.copy(PUMICE.get()).lightLevel((light) -> 8).mapColor(MapColor.COLOR_ORANGE)));

    public static final RegistryObject<Block> SMOOTH_PUMICE = HELPER.createBlock("smooth_pumice", () -> new Block(Properties.copy(PUMICE.get())));
    public static final RegistryObject<Block> SMOOTH_PUMICE_STAIRS = HELPER.createBlock("smooth_pumice_stairs", () -> new StairBlock(() -> SMOOTH_PUMICE.get().defaultBlockState(), Properties.copy(PUMICE.get())));
    public static final RegistryObject<Block> SMOOTH_PUMICE_SLAB = HELPER.createBlock("smooth_pumice_slab", () -> new SlabBlock(Properties.copy(PUMICE.get())));
    public static final RegistryObject<Block> POLISHED_PUMICE = HELPER.createBlock("polished_pumice", () -> new Block(Properties.copy(PUMICE.get())));
    public static final RegistryObject<Block> POLISHED_PUMICE_STAIRS = HELPER.createBlock("polished_pumice_stairs", () -> new StairBlock(() -> POLISHED_PUMICE.get().defaultBlockState(), Properties.copy(PUMICE.get())));
    public static final RegistryObject<Block> POLISHED_PUMICE_SLAB = HELPER.createBlock("polished_pumice_slab", () -> new SlabBlock(Properties.copy(PUMICE.get())));
    public static final RegistryObject<Block> PUMICE_BRICKS = HELPER.createBlock("pumice_bricks", () -> new Block(Properties.copy(PUMICE.get())));
    public static final RegistryObject<Block> PUMICE_BRICK_STAIRS = HELPER.createBlock("pumice_brick_stairs", () -> new StairBlock(() -> PUMICE_BRICKS.get().defaultBlockState(), Properties.copy(PUMICE.get())));
    public static final RegistryObject<Block> PUMICE_BRICK_SLAB = HELPER.createBlock("pumice_brick_slab", () -> new SlabBlock(Properties.copy(PUMICE.get())));
    public static final RegistryObject<Block> SATURATED_PUMICE_BRICKS = HELPER.createBlock("saturated_pumice_bricks", () -> new Block(Properties.copy(SATURATED_PUMICE.get())));
    public static final RegistryObject<Block> SATURATED_PUMICE_BRICK_STAIRS = HELPER.createBlock("saturated_pumice_brick_stairs", () -> new StairBlock(() -> SATURATED_PUMICE_BRICKS.get().defaultBlockState(), Properties.copy(SATURATED_PUMICE.get())));
    public static final RegistryObject<Block> SATURATED_PUMICE_BRICK_SLAB = HELPER.createBlock("saturated_pumice_brick_slab", () -> new SlabBlock(Properties.copy(SATURATED_PUMICE.get())));
    public static final RegistryObject<Block> PUMICE_PILLAR = HELPER.createBlock("pumice_pillar", () -> new RotatedPillarBlock(Properties.copy(PUMICE.get())));
    public static final RegistryObject<Block> SATURATED_PUMICE_PILLAR = HELPER.createBlock("saturated_pumice_pillar", () -> new RotatedPillarBlock(Properties.copy(SATURATED_PUMICE.get())));

    public static final RegistryObject<Block> CHISELED_PUMICE = HELPER.createBlock("chiseled_pumice", () -> new ChiseledPumiceBlock(Properties.copy(PUMICE.get())));
    public static final RegistryObject<Block> SATURATED_CHISELED_PUMICE = HELPER.createBlockNoItem("saturated_chiseled_pumice", () -> new SaturatedChiseledPumiceBlock(Properties.copy(SATURATED_PUMICE.get()).lightLevel(chiseledPumiceLight())));

    public static final RegistryObject<Block> LUFFA_CROP = HELPER.createBlockNoItem("luffa_crop", () -> new LuffaCropBlock((StemGrownBlock) LUFFA_GOURD.get(), AloofItems.LUFFA_SEEDS::get,  Properties.copy(Blocks.PUMPKIN_STEM).sound(SoundType.NETHER_SPROUTS).mapColor(MapColor.COLOR_LIGHT_GREEN)));
    public static final RegistryObject<Block> ATTACHED_LUFFA_CROP = HELPER.createBlockNoItem("attached_luffa_crop", () -> new AttachedStemBlock((StemGrownBlock) LUFFA_GOURD.get(), AloofItems.LUFFA_SEEDS::get, Properties.copy(Blocks.ATTACHED_PUMPKIN_STEM).sound(SoundType.NETHER_SPROUTS).mapColor(MapColor.COLOR_LIGHT_GREEN)));
    public static final RegistryObject<Block> SEA_SPONGE_WALL = HELPER.createBlockNoItem("sea_sponge_wall", () -> new SeaSpongeWallBlock(Blocks.AIR, Properties.of().noCollission().instabreak().sound(SoundType.WET_GRASS).mapColor(MapColor.COLOR_YELLOW).lightLevel((light) -> 3)));
    public static final RegistryObject<Block> SEA_SPONGE = HELPER.createStandingAndWallBlock("sea_sponge", () -> new SeaSpongeBlock(Blocks.AIR, Properties.copy(SEA_SPONGE_WALL.get())), SEA_SPONGE_WALL, Direction.DOWN);

    /*JNE compat*/
    public static final RegistryObject<Block> SOUL_SATURATED_PUMICE = HELPER.createBlock("soul_saturated_pumice", () -> new SoulSaturatedPumiceBlock(Properties.copy(PUMICE.get()).lightLevel((light) -> 7).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final RegistryObject<Block> SOUL_SATURATED_PUMICE_BRICKS = HELPER.createBlock("soul_saturated_pumice_bricks", () -> new Block(Properties.copy(SOUL_SATURATED_PUMICE.get())));
    public static final RegistryObject<Block> SOUL_SATURATED_PUMICE_BRICK_STAIRS = HELPER.createBlock("soul_saturated_pumice_brick_stairs", () -> new StairBlock(() -> SOUL_SATURATED_PUMICE_BRICKS.get().defaultBlockState(), Properties.copy(SOUL_SATURATED_PUMICE.get())));
    public static final RegistryObject<Block> SOUL_SATURATED_PUMICE_BRICK_SLAB = HELPER.createBlock("soul_saturated_pumice_brick_slab", () -> new SlabBlock(Properties.copy(SOUL_SATURATED_PUMICE.get())));
    public static final RegistryObject<Block> SOUL_SATURATED_PUMICE_PILLAR = HELPER.createBlock("soul_saturated_pumice_pillar", () -> new RotatedPillarBlock(Properties.copy(SOUL_SATURATED_PUMICE.get())));

    public static final RegistryObject<Block> SOUL_SATURATED_CHISELED_PUMICE = HELPER.createBlockNoItem("soul_saturated_chiseled_pumice", () -> new SoulSaturatedChiseledPumiceBlock(Properties.copy(SOUL_SATURATED_PUMICE.get()).lightLevel(soulChiseledPumiceLight())));

    public static void setupTabEditors() {
        CreativeModeTabContentsPopulator.mod(Aloof.MOD_ID)
                .tab(NATURAL_BLOCKS)
                .addItemsAfter(of(Items.SEA_PICKLE), SEA_SPONGE)
                .addItemsAfter(of(Items.JACK_O_LANTERN),
                        LUFFA_GOURD,
                        LUFFA_SPONGE,
                        WET_LUFFA_SPONGE
                )
                .addItemsAfter(of(Items.MAGMA_BLOCK),
                        PUMICE,
                        SATURATED_PUMICE
                )
                .addItemsBefore(modLoaded(Items.OBSIDIAN, "netherexp"),
                        SOUL_SATURATED_PUMICE
                )
                .tab(BUILDING_BLOCKS)
                .addItemsAfter(of(Items.POLISHED_ANDESITE_SLAB),
                        SMOOTH_PUMICE,
                        SMOOTH_PUMICE_STAIRS,
                        SMOOTH_PUMICE_SLAB,
                        POLISHED_PUMICE,
                        POLISHED_PUMICE_STAIRS,
                        POLISHED_PUMICE_SLAB,
                        PUMICE_BRICKS,
                        PUMICE_BRICK_STAIRS,
                        PUMICE_BRICK_SLAB,
                        PUMICE_PILLAR,
                        SATURATED_PUMICE_BRICKS,
                        SATURATED_PUMICE_BRICK_STAIRS,
                        SATURATED_PUMICE_BRICK_SLAB,
                        SATURATED_PUMICE_PILLAR
                )
                .addItemsBefore(modLoaded(Items.DEEPSLATE, "netherexp"),
                        SOUL_SATURATED_PUMICE_BRICKS,
                        SOUL_SATURATED_PUMICE_BRICK_STAIRS,
                        SOUL_SATURATED_PUMICE_BRICK_SLAB,
                        SOUL_SATURATED_PUMICE_PILLAR
                )
                .tab(FUNCTIONAL_BLOCKS)
                .addItemsAfter(of(Items.LODESTONE),
                        CHISELED_PUMICE
                );
    }
    public static Predicate<ItemStack> modLoaded(ItemLike item, String... modids) {
        return stack -> of(item).test(stack) && BlockSubRegistryHelper.areModsLoaded(modids);
    }

}
