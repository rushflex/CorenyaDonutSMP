package com.corenya.donutsmp.tasks;

import com.corenya.donutsmp.CorenyaDonutSMP;
import com.corenya.donutsmp.managers.OptimizationManager;

/**
 * Updates tablist header/footer and entry names for all players every 40 ticks (2s).
 */
public class TablistTask implements Runnable {

    private final CorenyaDonutSMP plugin;

    public TablistTask(CorenyaDonutSMP plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (plugin.getOptimizationManager() != null
                && !plugin.getOptimizationManager().shouldRun(OptimizationManager.OptimizedTask.TABLIST)) {
            return;
        }
        plugin.getTablistManager().updateAll();
        plugin.getTablistManager().updateNamesAll();
    }

    public static void start(CorenyaDonutSMP plugin) {
        plugin.getSpigotScheduler().runGlobalTimer(new TablistTask(plugin), 40L, 40L);
    }
}
