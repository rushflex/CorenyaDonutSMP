package com.corenya.donutsmp.commands;

import com.corenya.donutsmp.CorenyaDonutSMP;
import com.corenya.donutsmp.menus.PayConfirmMenu;
import com.corenya.donutsmp.models.PlayerData;
import com.corenya.donutsmp.utils.ColorUtils;
import com.corenya.donutsmp.utils.NumberUtils;
import com.corenya.donutsmp.utils.PaymentUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {

    private final CorenyaDonutSMP plugin;

    public PayCommand(CorenyaDonutSMP plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) { sender.sendMessage("Player only."); return true; }

        // With no arguments the dialog is the whole interface: pick a player, then an amount.
        if (args.length == 0 && plugin.getDialogManager() != null && plugin.getDialogManager().openPay(player)) {
            return true;
        }
        if (args.length < 2) { player.sendMessage(ColorUtils.toComponent("&cUsage: /pay <player> <amount>")); return true; }

        if (args[0].equalsIgnoreCase(player.getName())) {
            player.sendMessage(ColorUtils.toComponent(plugin.getConfigManager().getMessage("BALANCE.PAY.CANT-PAY-SELF")));
            return true;
        }

        double amount;
        try {
            amount = NumberUtils.parse(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage(ColorUtils.toComponent(plugin.getConfigManager().getMessage("BALANCE.PAY.INVALID-AMOUNT")));
            return true;
        }
        if (amount <= 0) {
            player.sendMessage(ColorUtils.toComponent(plugin.getConfigManager().getMessage("BALANCE.PAY.MUST-BE-POSITIVE")));
            return true;
        }

        PlayerData senderData = plugin.getPlayerDataManager().get(player);
        if (senderData != null && senderData.isPayConfirmMenuEnabled()) {
            var target = plugin.getEconomyManager().resolveAccount(args[0]);
            if (target != null && plugin.getDialogManager() != null
                    && plugin.getDialogManager().openPay(player, target.uuid(), target.displayName(), amount)) {
                return true;
            }
            new PayConfirmMenu(plugin, args[0], amount).open(player);
            return true;
        }

        PaymentUtils.transferMoney(plugin, player, args[0], amount);
        return true;
    }
}
