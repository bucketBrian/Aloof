package com.syndicatemc.aloof;

import com.mojang.logging.LogUtils;
import com.syndicatemc.aloof.init.*;
import com.syndicatemc.aloof.init.helper.AloofBlockSubRegisteryHelper;
import com.syndicatemc.aloof.other.AloofCompat;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

@Mod(Aloof.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@SuppressWarnings("removal")
public class Aloof {
    public static final String MOD_ID = "aloof";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final RegistryHelper REGISTRY_HELPER = RegistryHelper.create(MOD_ID, helper -> helper.putSubHelper(ForgeRegistries.BLOCKS, new AloofBlockSubRegisteryHelper(helper)));

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(AloofCompat::register);
    }

    public Aloof() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        REGISTRY_HELPER.register(bus);
        AloofFeatures.init(bus);
        bus.addListener(this::commonSetup);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            AloofBlocks.setupTabEditors();
            AloofItems.setupTabEditors();
        });
    }
}
