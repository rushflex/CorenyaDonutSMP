package com.corenya.donutsmp.listeners;

import com.corenya.donutsmp.CorenyaDonutSMP;
import com.corenya.donutsmp.menus.BaseMenu;
import com.corenya.donutsmp.menus.CrateEditorMenu;
import com.corenya.donutsmp.menus.OrdersInventoryItemMenu;
import com.corenya.donutsmp.menus.OrdersDepositMenu;
import com.corenya.donutsmp.menus.OrdersNewMenu;
import com.corenya.donutsmp.menus.PvpKitEditMenu;
import com.corenya.donutsmp.menus.RTPMenu;
import com.corenya.donutsmp.menus.QuickBuyInsertItemMenu;
import com.corenya.donutsmp.menus.SellMenu;
import com.corenya.donutsmp.menus.MenuNavigationTracker;
import com.corenya.donutsmp.utils.SoundUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class InventoryClickListener implements Listener {

    private final CorenyaDonutSMP plugin;

    public InventoryClickListener(CorenyaDonutSMP plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        Inventory inv = event.getInventory();
        if (!(inv.getHolder() instanceof BaseMenu menu)) return;
        Inventory topInventory = event.getView().getTopInventory();

        MenuNavigationTracker tracker = plugin != null ? plugin.getMenuNavigationTracker() : null;
        if (tracker != null) {
            tracker.setHandlingClick(player.getUniqueId(), true);
        }
        try {

        if (menu instanceof SellMenu sellMenu) {
            sellMenu.handleInventoryClick(event);
            return;
        }

        if (menu instanceof QuickBuyInsertItemMenu insertMenu) {
            insertMenu.handleInventoryClick(event);
            return;
        }

        if (menu instanceof RTPMenu) {
            handleRtpMenuClick(event, player, menu);
            return;
        }

        if (menu instanceof CrateEditorMenu crateEditorMenu) {
            crateEditorMenu.handleInventoryClick(event);
            return;
        }

        if (menu instanceof OrdersInventoryItemMenu ordersInventoryItemMenu) {
            ordersInventoryItemMenu.handleInventoryClick(event);
            return;
        }

        if (menu instanceof OrdersDepositMenu ordersDepositMenu) {
            ordersDepositMenu.handleInventoryClick(event);
            return;
        }

        if (menu instanceof com.corenya.donutsmp.menus.SpawnerStorageMenu spawnerStorageMenu) {
            spawnerStorageMenu.handleInventoryClick(event);
            return;
        }

        if (menu instanceof PvpKitEditMenu pvpKitEditMenu) {
            pvpKitEditMenu.handleInventoryClick(event);
            return;
        }

        if (menu instanceof OrdersNewMenu && event.getRawSlot() == 23) {
            handleProtectedMenuClick(event, player, menu);
            return;
        }

        event.setCancelled(true);
        if (event.getClickedInventory() == null || !event.getClickedInventory().equals(topInventory)) return;
        if (event.getCurrentItem() == null) return;
        menu.handleClick(event.getSlot(), player, event.getClick());
        } finally {
            if (tracker != null) {
                tracker.setHandlingClick(player.getUniqueId(), false);
            }
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        Inventory inv = event.getInventory();
        if (!(inv.getHolder() instanceof BaseMenu menu)) return;

        if (menu instanceof com.corenya.donutsmp.menus.SpawnerStorageMenu spawnerStorageMenu) {
            spawnerStorageMenu.handleInventoryDrag(event);
            return;
        }

        if (menu instanceof SellMenu sellMenu) {
            sellMenu.handleInventoryDrag(event);
            return;
        }

        if (menu instanceof QuickBuyInsertItemMenu insertMenu) {
            insertMenu.handleInventoryDrag(event);
            return;
        }

        if (menu instanceof RTPMenu && event.getWhoClicked() instanceof Player player) {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
            syncInventory(player);
            return;
        }

        if (menu instanceof CrateEditorMenu crateEditorMenu) {
            crateEditorMenu.handleInventoryDrag(event);
            return;
        }

        if (menu instanceof OrdersInventoryItemMenu ordersInventoryItemMenu) {
            ordersInventoryItemMenu.handleInventoryDrag(event);
            return;
        }

        if (menu instanceof OrdersDepositMenu ordersDepositMenu) {
            ordersDepositMenu.handleInventoryDrag(event);
            return;
        }

        if (menu instanceof PvpKitEditMenu pvpKitEditMenu) {
            pvpKitEditMenu.handleInventoryDrag(event);
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        if (!(event.getInventory().getHolder() instanceof BaseMenu menu)) return;

        menu.onClose(player);

        MenuNavigationTracker tracker = plugin != null ? plugin.getMenuNavigationTracker() : null;
        if (tracker == null || !tracker.isEnabled()) {
            return;
        }

        UUID uuid = player.getUniqueId();
        if (tracker.isTransitioning(uuid)) {
            return;
        }

        if (!tracker.isPlayerInitiatedClose(event, uuid)) {
            tracker.clear(uuid);
            return;
        }

        BaseMenu previousMenu = tracker.popPrevious(player);
        if (previousMenu != null) {
            plugin.getSpigotScheduler().runEntity(player, () -> {
                if (!player.isOnline()) {
                    tracker.clear(uuid);
                    return;
                }
                try {
                    previousMenu.open(player);
                    SoundUtils.play(player, plugin.getConfigManager().getSound("MENUS.BUTTON-CLICK"));
                } catch (Throwable error) {
                    plugin.getLogger().warning("Failed to reopen previous menu for " + player.getName() + ": " + error.getMessage());
                    tracker.clear(uuid);
                }
            });
        }
    }

    private void handleRtpMenuClick(InventoryClickEvent event, Player player, BaseMenu menu) {
        Inventory topInventory = event.getView().getTopInventory();
        ItemStack originalCursor = event.getCursor() == null ? null : event.getCursor().clone();
        int rawSlot = event.getRawSlot();
        ClickType clickType = event.getClick();
        boolean validTopClick = event.getClickedInventory() != null
                && event.getClickedInventory().equals(topInventory)
                && event.getCurrentItem() != null
                && !event.getCurrentItem().getType().isAir();

        event.setCancelled(true);
        event.setResult(Event.Result.DENY);

        player.setItemOnCursor(originalCursor);
        if (validTopClick) {
            menu.handleClick(rawSlot, player, clickType);
        } else {
            menu.close(player);
        }
        plugin.getSpigotScheduler().runEntity(player, player::updateInventory);
    }

    private void handleProtectedMenuClick(InventoryClickEvent event, Player player, BaseMenu menu) {
        Inventory topInventory = event.getView().getTopInventory();
        ItemStack originalCursor = event.getCursor() == null ? null : event.getCursor().clone();
        int rawSlot = event.getRawSlot();
        ClickType clickType = event.getClick();
        boolean validTopClick = event.getClickedInventory() != null
                && event.getClickedInventory().equals(topInventory)
                && event.getCurrentItem() != null
                && !event.getCurrentItem().getType().isAir();

        event.setCancelled(true);
        event.setResult(Event.Result.DENY);

        plugin.getSpigotScheduler().runEntity(player, () -> {
            if (!player.isOnline()) {
                return;
            }

            player.setItemOnCursor(originalCursor);
            player.updateInventory();

            if (validTopClick) {
                menu.handleClick(rawSlot, player, clickType);
            }
        });
    }

    private void syncInventory(Player player) {
        plugin.getSpigotScheduler().runEntity(player, player::updateInventory);
    }
}
