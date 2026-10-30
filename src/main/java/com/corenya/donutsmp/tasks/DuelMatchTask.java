package com.corenya.donutsmp.tasks;

import com.corenya.donutsmp.CorenyaDonutSMP;

public class DuelMatchTask implements Runnable {

    private final CorenyaDonutSMP plugin;

    private DuelMatchTask(CorenyaDonutSMP plugin) {
        this.plugin = plugin;
    }

    public static void start(CorenyaDonutSMP plugin) {
        plugin.getSpigotScheduler().runGlobalTimer(new DuelMatchTask(plugin), 1L, 1L);
    }

    @Override
    public void run() {
        if (plugin.getDuelManager() != null && plugin.getDuelManager().isEnabled()) {
            plugin.getDuelManager().tick();
        }
    }
}
