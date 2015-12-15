package org.blockserver.core.events;

import lombok.Getter;
import lombok.Setter;
import org.blockserver.core.event.CancellableImplementation;
import org.blockserver.core.message.Message;

/**
 * Written by Exerosis!
 */
public class MessageHandleEvent<T extends Message> implements CancellableImplementation {
    @Getter @Setter private Message message;

    public MessageHandleEvent(Message message) {
        this.message = message;
    }
}