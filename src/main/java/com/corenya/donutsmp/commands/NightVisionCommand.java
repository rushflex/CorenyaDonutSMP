package com.corenya.donutsmp.commands;

import com.corenya.donutsmp.CorenyaDonutSMP;
import com.corenya.donutsmp.utils.ColorUtils;
import com.corenya.donutsmp.utils.NightVisionUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NightVisionCommand implements CommandExecutor {

    private final CorenyaDonutSMP plugin;

    public NightVisionCommand(CorenyaDonutSMP plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) { sender.sendMessage("Player only."); return true; }
        boolean enabled = NightVisionUtils.toggle(plugin, player);
        if (!enabled) {
            player.sendMessage(ColorUtils.toComponent("&7Night vision &cdisabled&7."));
        } else {
            player.sendMessage(ColorUtils.toComponent("&7Night vision &aenabled&7."));
        }
        return true;
    }
}
