package com.corenya.donutsmp.listeners;

import com.corenya.donutsmp.CorenyaDonutSMP;
import com.corenya.donutsmp.models.PlayerData;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class PhantomListener implements Listener {

    private final CorenyaDonutSMP plugin;

    public PhantomListener(CorenyaDonutSMP plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPhantomTarget(EntityTargetEvent event) {
        if (!plugin.getFeatureManager().isEnabled(com.corenya.donutsmp.managers.FeatureManager.Feature.PHANTOM)) return;
        if (!(event.getEntity() instanceof Phantom)) return;
        if (!(event.getTarget() instanceof Player player)) return;

        PlayerData data = plugin.getPlayerDataManager().get(player);
        if (data != null && !data.isPhantomEnabled()) {
            event.setCancelled(true);
        }
    }
}
