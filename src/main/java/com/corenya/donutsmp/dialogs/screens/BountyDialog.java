package com.corenya.donutsmp.dialogs.screens;

import com.corenya.donutsmp.CorenyaDonutSMP;
import com.corenya.donutsmp.dialogs.DialogActions;
import com.corenya.donutsmp.dialogs.DialogConfig;
import com.corenya.donutsmp.dialogs.DialogFactory;
import com.corenya.donutsmp.dialogs.DialogScreen;
import com.corenya.donutsmp.dialogs.DialogSession;
import com.corenya.donutsmp.dialogs.DialogSupport;
import com.corenya.donutsmp.menus.BountyMenu;
import com.corenya.donutsmp.utils.SoundUtils;
import io.papermc.paper.dialog.DialogResponseView;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Dialog screen for searching bounties, matching Design/Bounty/search.png.
 */
public final class BountyDialog extends DialogScreen {

    private static final int BUTTON_WIDTH = 120;
    private final Map<UUID, BountySearchState> searchStates = new ConcurrentHashMap<>();

    public record BountySearchState(String query, boolean sortByRecent, int page) {}

    public BountyDialog(CorenyaDonutSMP plugin, DialogConfig config, DialogSession.Store sessions) {
        super(plugin, config, sessions);
    }

    public boolean openSearch(Player player, String query, boolean sortByRecent, int page) {
        searchStates.put(player.getUniqueId(), new BountySearchState(query, sortByRecent, page));
        String current = query == null ? "" : query;
        DialogFactory.Screen screen = screen(player)
                .title(text("MENUS.BOUNTIES-MENU.DIALOG.SEARCH.TITLE", "Search Bounties"))
                .item(new ItemStack(Material.OAK_SIGN))
                .columns(2)
                .input(new DialogConfig.InputSpec(
                        "query",
                        text("MENUS.BOUNTIES-MENU.DIALOG.SEARCH.INPUT", "Search"),
                        300,
                        32,
                        current,
                        List.of()
                ))
                .button(text("MENUS.BOUNTIES-MENU.DIALOG.SEARCH.CANCEL", "&cCancel!"), null, BUTTON_WIDTH, DialogActions.BOUNTY_SEARCH_CAN)
                .button(text("MENUS.BOUNTIES-MENU.DIALOG.SEARCH.SUBMIT", "&aSearch"), null, BUTTON_WIDTH, DialogActions.BOUNTY_SEARCH_GO);

        return show(player, screen.build());
    }

    @Override
    public boolean handle(Player player, String action, DialogResponseView response) {
        if (DialogActions.BOUNTY_SEARCH_CAN.equals(action)) {
            click(player);
            BountySearchState state = searchStates.remove(player.getUniqueId());
            boolean recent = state == null || state.sortByRecent();
            String q = state == null ? "" : state.query();
            int p = state == null ? 0 : state.page();
            DialogSupport.close(player);
            plugin.getSpigotScheduler().runEntity(player, () -> new BountyMenu(plugin, p, q, recent).open(player));
            return true;
        }

        if (DialogActions.BOUNTY_SEARCH_GO.equals(action)) {
            click(player);
            BountySearchState state = searchStates.remove(player.getUniqueId());
            boolean recent = state == null || state.sortByRecent();
            String query = input(response, "query");
            DialogSupport.close(player);
            plugin.getSpigotScheduler().runEntity(player, () -> new BountyMenu(
                    plugin,
                    0,
                    query == null ? "" : query.trim(),
                    recent
            ).open(player));
            return true;
        }

        return false;
    }

    private String text(String path, String fallback, String... placeholders) {
        return plugin.getLanguageManager().text(path, null, fallback, placeholders);
    }
}
