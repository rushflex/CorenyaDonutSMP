package com.corenya.donutsmp.api;

import com.corenya.donutsmp.CorenyaDonutSMP;
import org.jetbrains.annotations.NotNull;

public class CorenyaDonutSMPExpansion extends EconomyExpansion {

    public CorenyaDonutSMPExpansion(CorenyaDonutSMP plugin) {
        super(plugin);
    }

    @Override
    public @NotNull String getIdentifier() {
        return "corenyadonutsmp";
    }
}
