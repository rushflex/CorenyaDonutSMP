package com.corenya.donutsmp.tasks;

import com.corenya.donutsmp.CorenyaDonutSMP;
import com.corenya.donutsmp.managers.OptimizationManager;

/**
 * Updates all player scoreboards on the configured interval (default 20 ticks).
 */
public class ScoreboardTask implements Runnable {

    private final CorenyaDonutSMP plugin;

    public ScoreboardTask(CorenyaDonutSMP plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (plugin.getOptimizationManager() != null
                && !plugin.getOptimizationManager().shouldRun(OptimizationManager.OptimizedTask.SCOREBOARD)) {
            return;
        }
        plugin.getScoreboardManager().updateAll();
    }

    public static void start(CorenyaDonutSMP plugin) {
        if (!plugin.getScoreboardManager().isRuntimeSupported()) {
            return;
        }
        long interval = plugin.getScoreboardManager().getUpdateIntervalTicks();
        plugin.getSpigotScheduler().runGlobalTimer(new ScoreboardTask(plugin), interval, interval);
    }
}
