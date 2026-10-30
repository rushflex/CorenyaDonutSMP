package com.corenya.donutsmp.menus;

import com.corenya.donutsmp.CorenyaDonutSMP;
import com.corenya.donutsmp.managers.AuctionHouseManager;
import org.bukkit.entity.Player;

public final class AuctionHouseMyListingsMenu extends BaseMenu {

    private final int page;

    public AuctionHouseMyListingsMenu(
            CorenyaDonutSMP plugin,
            int page,
            AuctionHouseManager.AuctionSort ignoredSort
    ) {
        super(plugin, plugin.getAuctionHouseManager().getMyListingsTitle(), plugin.getAuctionHouseManager().getMyListingsSize());
        this.page = Math.max(1, page);
    }

    @Override
    public void build(Player player) {
    }

    @Override
    public void open(Player player) {
        new PlayerAuctionGui(plugin, page).open(player);
    }
}
