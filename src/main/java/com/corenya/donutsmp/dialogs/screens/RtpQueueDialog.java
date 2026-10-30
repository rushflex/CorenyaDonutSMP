package com.corenya.donutsmp.dialogs.screens;

import com.corenya.donutsmp.CorenyaDonutSMP;
import com.corenya.donutsmp.dialogs.DialogActions;
import com.corenya.donutsmp.dialogs.DialogConfig;
import com.corenya.donutsmp.dialogs.DialogFactory;
import com.corenya.donutsmp.dialogs.DialogScreen;
import com.corenya.donutsmp.dialogs.DialogSession;
import com.corenya.donutsmp.dialogs.DialogSupport;
import io.papermc.paper.dialog.DialogResponseView;
import org.bukkit.entity.Player;

import java.util.Map;

/** The yes/no prompt in front of the shared random-teleport queue. */
public final class RtpQueueDialog extends DialogScreen {

    private static final String PATH = "RTP_QUEUE_DIALOG";

    public RtpQueueDialog(CorenyaDonutSMP plugin, DialogConfig config, DialogSession.Store sessions) {
        super(plugin, config, sessions);
    }

    public boolean open(Player player) {
        Map<String, String> tokens = DialogConfig.tokens("player", player.getName());
        int width = config.buttonWidth(PATH, 100);

        DialogFactory.Screen screen = screen(player)
                .title(config.string(PATH + ".TITLE", "RTP Queue", tokens))
                .externalTitle(config.string(PATH + ".EXTERNAL-TITLE", "RTP Queue", tokens))
                .canCloseWithEscape(config.bool(PATH + ".CAN-CLOSE-WITH-ESCAPE", true))
                .pause(config.bool(PATH + ".PAUSE", false))
                .afterAction(config.string(PATH + ".AFTER-ACTION", "NONE"))
                .text(
                        config.string(
                                PATH + ".QUESTION",
                                "Are you sure you want to randomly teleport with another player?",
                                tokens
                        ),
                        config.integer(PATH + ".TEXT-WIDTH", 200)
                );

        DialogConfig.ButtonSpec yes = new DialogConfig.ButtonSpec(
                config.string(PATH + ".YES-LABEL", "&#00FC00Yes", tokens),
                null,
                width,
                config.string(
                        PATH + ".YES-ACTION",
                        DialogActions.NAMESPACE + ':' + DialogActions.RTP_QUEUE_ACCEPT,
                        tokens
                ),
                null
        );
        DialogConfig.ButtonSpec no = new DialogConfig.ButtonSpec(
                config.string(PATH + ".NO-LABEL", "&cNo", tokens),
                null,
                width,
                config.string(
                        PATH + ".NO-ACTION",
                        DialogActions.NAMESPACE + ':' + DialogActions.RTP_QUEUE_DENY,
                        tokens
                ),
                null
        );
        // The reference lays these out No | Yes. Confirmation type would flip that order,
        // so this stays a two-column action row instead.
        return show(player, screen.columns(config.columns(PATH, 2)).button(no).button(yes).build());
    }

    @Override
    public boolean handle(Player player, String action, DialogResponseView response) {
        if (DialogActions.RTP_QUEUE.equals(action)) {
            open(player);
            return true;
        }
        if (DialogActions.RTP_QUEUE_ACCEPT.equals(action)) {
            click(player);
            DialogSupport.close(player);
            plugin.getRtpQueueManager().join(player);
            return true;
        }
        if (DialogActions.RTP_QUEUE_DENY.equals(action)) {
            click(player);
            DialogSupport.close(player);
            return true;
        }
        return false;
    }
}
