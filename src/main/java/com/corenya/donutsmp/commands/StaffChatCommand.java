package com.corenya.donutsmp.commands;

import com.corenya.donutsmp.utils.PermissionUtils;

import com.corenya.donutsmp.CorenyaDonutSMP;
import com.corenya.donutsmp.utils.ColorUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatCommand implements CommandExecutor {

    private static final String PERMISSION = "corenyadonutsmp.staff.chat.use";

    private final CorenyaDonutSMP plugin;

    public StaffChatCommand(CorenyaDonutSMP plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player && !PermissionUtils.has(player, PERMISSION)) {
            player.sendMessage(ColorUtils.toComponent(
                    plugin.getConfigManager().getMessageOrDefault("STAFFCHAT.NO_PERMISSION", "&cYou do not have permission to use staff chat.")
            ));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ColorUtils.toComponent(
                    plugin.getConfigManager().getMessageOrDefault("STAFFCHAT.USAGE", "&cUsage: /staffchat <message>")
            ));
            return true;
        }

        plugin.getNetworkStaffChatManager().sendStaffChat(sender, String.join(" ", args));
        return true;
    }
}
