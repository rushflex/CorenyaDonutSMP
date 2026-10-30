package com.corenya.donutsmp.tasks;

import com.corenya.donutsmp.CorenyaDonutSMP;

/**
 * Checks every 30 seconds whether the farming meta rotation timer has expired and, if so,
 * moves the meta on to the next item in the rotation.
 */
public class FarmingMetaTask implements Runnable {

    private final CorenyaDonutSMP plugin;

    private FarmingMetaTask(CorenyaDonutSMP plugin) {
        this.plugin = plugin;
    }

    public static void start(CorenyaDonutSMP plugin) {
        // 600 ticks = 30 seconds; first check after 30 s as well
        plugin.getSpigotScheduler().runGlobalTimer(new FarmingMetaTask(plugin), 600L, 600L);
    }

    @Override
    public void run() {
        if (!plugin.getFeatureManager().isEnabled(com.corenya.donutsmp.managers.FeatureManager.Feature.WORTH)) {
            return;
        }
        if (plugin.getFarmingMetaManager().isTimeToRotate()) {
            plugin.getFarmingMetaManager().rotate();
        }
    }
}
