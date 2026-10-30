package com.corenya.donutsmp.dialogs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class ChatClickMessagesTest {

    @Test
    void aMissingViewerCannotReceiveAServerClick() {
        assertFalse(ChatClickMessages.sendInstantAction(null, null, "hi", null, player -> {}));
    }
}
