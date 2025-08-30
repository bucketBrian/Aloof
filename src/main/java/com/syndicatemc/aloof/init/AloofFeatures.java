package com.syndicatemc.aloof.init;

import com.syndicatemc.aloof.Aloof;
import com.syndicatemc.aloof.feature.SeaSpongeFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AloofFeatures<FC extends FeatureConfiguration> {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Aloof.MOD_ID);

    public static final RegistryObject<Feature<CountConfiguration>> SEA_SPONGE = FEATURES.register("sea_sponge", () -> new SeaSpongeFeature(CountConfiguration.CODEC));

    public static void init(IEventBus bus) {
        FEATURES.register(bus);
    }
}
