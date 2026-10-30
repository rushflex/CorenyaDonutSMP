package com.corenya.donutsmp.listeners;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AdvancementMessageSuppressionTest {

    @Test
    void clearAdvancementMessageDoesNotThrowOnNull() {
        assertDoesNotThrow(() -> PlayerAdvancementDoneListener.clearAdvancementMessage(null));
    }
}
