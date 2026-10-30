package com.corenya.donutsmp.listeners;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FfaNamespacedAllowProofTest {

    @Test
    void plainLeaveIsAllowed() {
        assertTrue(FfaListener.isAllowedFfaCommand("/leave"));
        assertTrue(FfaListener.isAllowedFfaCommand("/ffa"));
        assertTrue(FfaListener.isAllowedFfaCommand("/ffastats"));
    }

    @Test
    void namespacedLeaveMustBeAllowedToo() {
        assertTrue(
                FfaListener.isAllowedFfaCommand("/corenyadonutsmp:leave"),
                "FfaListener.onCommand compares the raw token to /leave and would cancel /corenyadonutsmp:leave"
        );
        assertTrue(FfaListener.isAllowedFfaCommand("/CorenyaDonutSMP:Leave"));
        assertTrue(FfaListener.isAllowedFfaCommand("/corenyadonutsmp:ffa"));
        assertTrue(FfaListener.isAllowedFfaCommand("/corenyadonutsmp:ffastats"));
        assertTrue(FfaListener.isAllowedFfaCommand("/corenyadonutsmp:ffa join"));
    }

    @Test
    void blockedCommandsRemainBlocked() {
        assertFalse(FfaListener.isAllowedFfaCommand("/spawn"));
        assertFalse(FfaListener.isAllowedFfaCommand("/corenyadonutsmp:spawn"));
        assertFalse(FfaListener.isAllowedFfaCommand("/tpa Steve"));
        assertFalse(FfaListener.isAllowedFfaCommand("/corenyadonutsmp:tpa Steve"));
        assertFalse(FfaListener.isAllowedFfaCommand("/leaveextra"));
        assertFalse(FfaListener.isAllowedFfaCommand(null));
        assertFalse(FfaListener.isAllowedFfaCommand(""));
    }
}
