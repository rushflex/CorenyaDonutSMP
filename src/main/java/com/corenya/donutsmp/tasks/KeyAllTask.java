package com.corenya.donutsmp.tasks;

import com.corenya.donutsmp.CorenyaDonutSMP;

/**
 * Checks every second if it's time for key-all.
 */
public class KeyAllTask implements Runnable {

    private final CorenyaDonutSMP plugin;

    public KeyAllTask(CorenyaDonutSMP plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (!plugin.getKeyAllManager().isEnabled()) return;
        plugin.getKeyAllManager().tickOnlinePlayers();
    }

    public static void start(CorenyaDonutSMP plugin) {
        plugin.getSpigotScheduler().runGlobalTimer(new KeyAllTask(plugin), 20L, 20L);
    }
}
