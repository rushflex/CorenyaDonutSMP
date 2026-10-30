package com.corenya.donutsmp.commands;

import com.corenya.donutsmp.CorenyaDonutSMP;
import com.corenya.donutsmp.menus.ConfirmKillMenu;
import com.corenya.donutsmp.utils.ColorUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KillCommand implements CommandExecutor {

    private final CorenyaDonutSMP plugin;

    public KillCommand(CorenyaDonutSMP plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Player only.");
            return true;
        }

        if (plugin.getCombatManager().isInCombat(player.getUniqueId())) {
            player.sendMessage(ColorUtils.toComponent(plugin.getConfigManager().getConfig()
                    .getString("COMBAT-MANAGER.BLOCK-MESSAGE", "&cYou can't use this command in combat.")));
            return true;
        }

        new ConfirmKillMenu(plugin).open(player);
        return true;
    }
}
