package com.corenya.donutsmp.tasks;

import com.corenya.donutsmp.CorenyaDonutSMP;

public class FfaMatchTask implements Runnable {

    private final CorenyaDonutSMP plugin;

    private FfaMatchTask(CorenyaDonutSMP plugin) {
        this.plugin = plugin;
    }

    public static void start(CorenyaDonutSMP plugin) {
        plugin.getSpigotScheduler().runGlobalTimer(new FfaMatchTask(plugin), 1L, 1L);
    }

    @Override
    public void run() {
        if (plugin.getFfaManager() != null && plugin.getFfaManager().isEnabled()) {
            plugin.getFfaManager().tick();
        }
    }
}
