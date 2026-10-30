package com.corenya.donutsmp.tasks;

import com.corenya.donutsmp.CorenyaDonutSMP;

public final class AuctionOrderBotTask implements Runnable {

    private final CorenyaDonutSMP plugin;

    private AuctionOrderBotTask(CorenyaDonutSMP plugin) {
        this.plugin = plugin;
    }

    public static void start(CorenyaDonutSMP plugin) {
        // Tick every 20 seconds
        long periodTicks = 400L; 
        plugin.getSpigotScheduler().runAsyncTimer(new AuctionOrderBotTask(plugin), periodTicks, periodTicks);
    }

    @Override
    public void run() {
        if (plugin.getAuctionOrderBotManager() != null) {
            plugin.getAuctionOrderBotManager().tick();
        }
    }
}
