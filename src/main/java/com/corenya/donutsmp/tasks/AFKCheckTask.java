package com.corenya.donutsmp.tasks;

import com.corenya.donutsmp.CorenyaDonutSMP;
import com.corenya.donutsmp.managers.ShardManager;
import org.bukkit.entity.Player;

public class AFKCheckTask implements Runnable {

    private final CorenyaDonutSMP plugin;

    public AFKCheckTask(CorenyaDonutSMP plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (!plugin.getAFKManager().isEnabled()) {
            return;
        }

        plugin.getSpigotScheduler().forEachOnlinePlayer(this::tickPlayer);
    }

    private void tickPlayer(Player player) {
        if (plugin.getAFKManager().isAfk(player.getUniqueId())) {
            return;
        }

        ShardManager.ShardCuboidConfig active = plugin.getShardManager().findMatchingShardCuboid(player);
        if (active != null) {
            tickShardCuboidPlayer(player, active);
            return;
        }

        if (plugin.getAFKManager().isInSpawnCuboid(player)
                && plugin.getAFKManager().shouldGoAfk(player.getUniqueId())) {
            plugin.getAFKManager().sendToAfk(player);
        }
    }

    private void tickShardCuboidPlayer(Player player, ShardManager.ShardCuboidConfig config) {
        boolean idleLongEnough = plugin.getAFKManager()
                .shouldGoAfk(player.getUniqueId(), config.afkTimeoutSeconds());
        boolean insideAfkZone = config.isInAfkZone(player, plugin.getCuboidManager(), plugin.getSpawnManager());

        if (!ShardManager.shouldTeleportToShardAfkArea(
                config.teleportOnAfk(),
                config.isWorldExcluded(player.getWorld().getName()),
                insideAfkZone,
                idleLongEnough)) {
            return;
        }

        plugin.getAFKManager().sendToAfk(
                player,
                plugin.getShardManager().resolveAfkLocation(config),
                config.afkMessage()
        );
    }

    public static void start(CorenyaDonutSMP plugin) {
        plugin.getSpigotScheduler().runGlobalTimer(new AFKCheckTask(plugin), 200L, 200L);
    }
}
