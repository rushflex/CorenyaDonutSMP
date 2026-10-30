package com.corenya.donutsmp.commands;

import com.corenya.donutsmp.CorenyaDonutSMP;
import com.corenya.donutsmp.managers.LeaderboardManager;
import com.corenya.donutsmp.menus.LeaderboardMenu;
import com.corenya.donutsmp.menus.LeaderboardTypeMenu;
import com.corenya.donutsmp.utils.CommandLabelUtils;
import com.corenya.donutsmp.utils.ColorUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

public class LeaderboardCommand implements CommandExecutor {

    private final CorenyaDonutSMP plugin;

    public LeaderboardCommand(CorenyaDonutSMP plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) { sender.sendMessage("Player only."); return true; }
        if (!plugin.getConfigManager().isCommandEnabled("LEADERBOARDS")) {
            player.sendMessage(ColorUtils.toComponent("&cLeaderboards are currently disabled."));
            return true;
        }

        if (args.length == 0 && CommandLabelUtils.normalizeLabel(label, cmd).equals("baltop")) {
            new LeaderboardTypeMenu(plugin, LeaderboardManager.LeaderboardType.MONEY).open(player);
            return true;
        }

        if (args.length == 0) {
            if (plugin.getDialogManager() != null && plugin.getDialogManager().openLeaderboards(player)) {
                return true;
            }
            new LeaderboardMenu(plugin).open(player);
            return true;
        }

        var type = plugin.getLeaderboardManager().parseType(args[0]).orElse(null);
        if (type == null) {
            String available = plugin.getLeaderboardManager().getTypes().stream()
                    .map(leaderboardType -> leaderboardType.getConfigKey())
                    .collect(Collectors.joining(", "));
            player.sendMessage(ColorUtils.toComponent("&cInvalid leaderboard type. &7Available: &f" + available));
            return true;
        }

        new LeaderboardTypeMenu(plugin, type).open(player);
        return true;
    }
}
