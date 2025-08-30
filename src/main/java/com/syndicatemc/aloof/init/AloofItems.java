package com.syndicatemc.aloof.init;

import com.syndicatemc.aloof.Aloof;
import com.teamabnormals.blueprint.core.util.item.CreativeModeTabContentsPopulator;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import net.minecraft.world.item.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import static net.minecraft.world.item.CreativeModeTabs.*;
import static net.minecraft.world.item.crafting.Ingredient.of;

@Mod.EventBusSubscriber(modid = Aloof.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AloofItems {
    public static final ItemSubRegistryHelper HELPER = Aloof.REGISTRY_HELPER.getItemSubHelper();

    public static final RegistryObject<Item> LUFFA_SEEDS = HELPER.createItem("luffa_seeds", () -> new ItemNameBlockItem(AloofBlocks.LUFFA_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> PERFORATED_ARMOR_TRIM_SMITHING_TEMPLATE = HELPER.createItem("perforated_armor_trim_smithing_template", () -> SmithingTemplateItem.createArmorTrimTemplate(AloofTrimPatterns.PERFORATED));

    public static void setupTabEditors() {
        CreativeModeTabContentsPopulator.mod(Aloof.MOD_ID)
                .tab(NATURAL_BLOCKS)
                .addItemsAfter(of(Items.PITCHER_POD), LUFFA_SEEDS)
                .tab(INGREDIENTS)
                .addItemsAfter(of(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE), PERFORATED_ARMOR_TRIM_SMITHING_TEMPLATE);
    }
}
