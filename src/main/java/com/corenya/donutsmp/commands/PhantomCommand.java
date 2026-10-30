package com.corenya.donutsmp.commands;

import com.corenya.donutsmp.CorenyaDonutSMP;
import com.corenya.donutsmp.models.PlayerData;
import com.corenya.donutsmp.utils.ColorUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PhantomCommand implements CommandExecutor {

    private final CorenyaDonutSMP plugin;

    public PhantomCommand(CorenyaDonutSMP plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) { sender.sendMessage("Player only."); return true; }
        PlayerData data = plugin.getPlayerDataManager().get(player);
        if (data == null) return true;
        data.setPhantomEnabled(!data.isPhantomEnabled());
        String msg = data.isPhantomEnabled()
                ? plugin.getConfigManager().getMessage("PHANTOM.DISABLED")
                : plugin.getConfigManager().getMessage("PHANTOM.ENABLED");
        player.sendMessage(ColorUtils.toComponent(msg));
        return true;
    }
}
