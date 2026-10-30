package com.corenya.donutsmp.utils;

import com.corenya.donutsmp.CorenyaDonutSMP;
import com.corenya.donutsmp.models.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class NightVisionUtils {

    private NightVisionUtils() {
    }

    public static boolean isEnabled(CorenyaDonutSMP plugin, Player player) {
        PlayerData data = plugin.getPlayerDataManager().get(player);
        return data != null
                ? data.isNightVisionEnabled()
                : player.hasPotionEffect(PotionEffectType.NIGHT_VISION);
    }

    public static boolean toggle(CorenyaDonutSMP plugin, Player player) {
        boolean enabled = !isEnabled(plugin, player);
        setEnabled(plugin, player, enabled);
        return enabled;
    }

    public static void setEnabled(CorenyaDonutSMP plugin, Player player, boolean enabled) {
        PlayerData data = plugin.getPlayerDataManager().get(player);
        if (data != null) {
            data.setNightVisionEnabled(enabled);
        }

        if (enabled) {
            apply(player);
        } else {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        }
    }

    public static void restoreIfEnabled(CorenyaDonutSMP plugin, Player player) {
        if (player.isOnline() && isEnabled(plugin, player)) {
            apply(player);
        }
    }

    private static void apply(Player player) {
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.NIGHT_VISION,
                Integer.MAX_VALUE,
                0,
                false,
                false
        ));
    }
}
