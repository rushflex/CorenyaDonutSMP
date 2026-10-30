package com.corenya.donutsmp.tasks;

import com.corenya.donutsmp.CorenyaDonutSMP;

/**
 * Auto-saves dirty player data every 5 minutes.
 */
public class AutoSaveTask implements Runnable {

    private final CorenyaDonutSMP plugin;

    public AutoSaveTask(CorenyaDonutSMP plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (plugin.getServerWipeManager() != null && plugin.getServerWipeManager().isMaintenanceMode()) {
            return;
        }
        plugin.getPlayerDataManager().autoSaveDirty();
        if (plugin.getConfigManager().getDatabase().getBoolean("DATABASE.MONGODB.SYNC-ON-AUTOSAVE", true)) {
            plugin.getDatabaseManager().flush();
        }
    }

    public static void start(CorenyaDonutSMP plugin) {
        plugin.getSpigotScheduler().runAsyncTimer(new AutoSaveTask(plugin), 5 * 60 * 20L, 5 * 60 * 20L);
    }
}
