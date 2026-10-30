package com.corenya.donutsmp.menus;

import com.corenya.donutsmp.CorenyaDonutSMP;
import com.corenya.donutsmp.managers.SpawnManager;

public class SpawnMenu extends TeleportAreaMenu {

    public SpawnMenu(CorenyaDonutSMP plugin) {
        super(
                plugin,
                plugin.getConfigManager().getMenus().getString("SPAWN-MENU.TITLE", "&8Spawn areas"),
                plugin.getConfigManager().getMenus().getInt("SPAWN-MENU.SIZE", 54)
        );
    }

    @Override
    protected SpawnManager.AreaType getAreaType() {
        return SpawnManager.AreaType.SPAWN;
    }

    @Override
    protected String getMenuPath() {
        return "SPAWN-MENU";
    }

    @Override
    protected String getTeleportType() {
        return "SPAWN";
    }

    @Override
    protected String getEmptyTitle() {
        return "&cNo spawn areas";
    }

    @Override
    protected String getEmptyLore() {
        return "&7There are no valid cuboid-based spawn areas yet.";
    }
}
