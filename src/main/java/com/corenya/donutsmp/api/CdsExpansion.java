package com.corenya.donutsmp.api;

import com.corenya.donutsmp.CorenyaDonutSMP;
import org.jetbrains.annotations.NotNull;

public class CdsExpansion extends EconomyExpansion {

    public CdsExpansion(CorenyaDonutSMP plugin) {
        super(plugin);
    }

    @Override
    public @NotNull String getIdentifier() {
        return "cds";
    }
}
