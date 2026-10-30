package com.corenya.donutsmp.managers;

import com.corenya.donutsmp.models.PlayerData;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerVisibilityManagerTest {

    @Test
    void joinDoesNotHideAnyoneWhenTheSettingIsOff() {
        PlayerData data = new PlayerData(UUID.randomUUID(), "Viewer");
        assertFalse(PlayerVisibilityManager.shouldHideAllPlayers(null));
        assertFalse(PlayerVisibilityManager.shouldHideAllPlayers(data));
    }

    @Test
    void joinHidesPlayersOnlyWhenTheViewerAskedToHideEveryone() {
        PlayerData data = new PlayerData(UUID.randomUUID(), "Viewer");
        data.setHideAllPlayersEnabled(true);
        assertTrue(PlayerVisibilityManager.shouldHideAllPlayers(data));
    }
}
