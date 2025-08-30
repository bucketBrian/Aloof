package com.syndicatemc.aloof.compat;

import net.jadenxgamer.netherexp.registry.fluid.JNEFluids;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;

public class JNECompat {
    //particles
    public static ParticleType<SimpleParticleType> ectoplasmaParticle = JNEParticleTypes.ECTOPLASMA.get();

    //sounds
    public static SoundEvent ectoplasmBucketFill = JNESoundEvents.BUCKET_FILL_ECTOPLASM.get();
    public static SoundEvent ectoplasmBucketEmpty = JNESoundEvents.BUCKET_EMPTY_ECTOPLASM.get();

    //items
    public static Item ectoplasmBucket = JNEFluids.ECTOPLASM_BUCKET.get();
}
