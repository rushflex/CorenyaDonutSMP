package com.corenya.donutsmp.commands;

import com.corenya.donutsmp.utils.PermissionUtils;

import com.corenya.donutsmp.CorenyaDonutSMP;
import com.corenya.donutsmp.utils.ColorUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ClearLagCommand implements CommandExecutor {

    private final CorenyaDonutSMP plugin;

    public ClearLagCommand(CorenyaDonutSMP plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!PermissionUtils.has(sender, "corenyadonutsmp.admin.clearlag")) {
            sender.sendMessage(ColorUtils.toComponent("&cNo permission."));
            return true;
        }
        plugin.getClearLagManager().clearEntities();
        return true;
    }
}
