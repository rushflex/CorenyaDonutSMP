package com.corenya.donutsmp.commands;

import com.corenya.donutsmp.CorenyaDonutSMP;
import com.corenya.donutsmp.managers.FarmingMetaManager;
import com.corenya.donutsmp.utils.ColorUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MetaCommand implements CommandExecutor {

    private final CorenyaDonutSMP plugin;

    public MetaCommand(CorenyaDonutSMP plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FarmingMetaManager farmingMetaManager = plugin.getFarmingMetaManager();

        if (!farmingMetaManager.isActive()) {
            sender.sendMessage(ColorUtils.toComponent(
                    plugin.getConfigManager().getMessage("WORTH.META-INACTIVE")));
            return true;
        }

        sender.sendMessage(ColorUtils.toComponent(
                farmingMetaManager.formatMetaMessage("WORTH.META-CURRENT")));
        return true;
    }
}
