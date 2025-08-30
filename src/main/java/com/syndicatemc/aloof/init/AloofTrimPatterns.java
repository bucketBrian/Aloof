package com.syndicatemc.aloof.init;

import com.syndicatemc.aloof.Aloof;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("removal")
public class AloofTrimPatterns {
    public static final ResourceKey<TrimPattern> PERFORATED = createKey("perforated");

    public static void bootstrap(BootstapContext<TrimPattern> context) {
        register(context, PERFORATED, AloofItems.PERFORATED_ARMOR_TRIM_SMITHING_TEMPLATE.get());
    }

    public static ResourceKey<TrimPattern> createKey(String name) {
        return ResourceKey.create(Registries.TRIM_PATTERN, new ResourceLocation(Aloof.MOD_ID, name));
    }

    private static void register(BootstapContext<TrimPattern> context, ResourceKey<TrimPattern> key, Item item) {
        context.register(key, new TrimPattern(key.location(), ForgeRegistries.ITEMS.getHolder(item).get(), Component.translatable(Util.makeDescriptionId("trim_pattern", key.location()))));
    }
}