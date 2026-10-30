package com.corenya.donutsmp.commands;

import com.corenya.donutsmp.CorenyaDonutSMP;
import com.corenya.donutsmp.menus.PlayerSettingsMenu;
import com.corenya.donutsmp.utils.ColorUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SettingsCommand implements CommandExecutor {

    private final CorenyaDonutSMP plugin;

    public SettingsCommand(CorenyaDonutSMP plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) { sender.sendMessage("Player only."); return true; }
        if (!plugin.getConfigManager().isCommandEnabled("SETTINGS")) {
            player.sendMessage(ColorUtils.toComponent("&cSettings command is currently disabled."));
            return true;
        }
        if (plugin.getDialogManager() != null && plugin.getDialogManager().openSettings(player)) {
            return true;
        }
        new PlayerSettingsMenu(plugin).open(player);
        return true;
    }
}
