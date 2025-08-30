package com.syndicatemc.aloof.init.helper;

import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;

public class AloofBlockSubRegisteryHelper extends BlockSubRegistryHelper {
    public AloofBlockSubRegisteryHelper(RegistryHelper parent) {
        super(parent, parent.getItemSubHelper().getDeferredRegister(), parent.getBlockSubHelper().getDeferredRegister());
    }
}
