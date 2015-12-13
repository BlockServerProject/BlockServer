package org.blockserver.core.event.events;

import lombok.Getter;
import lombok.Setter;
import org.blockserver.core.event.CancellableImplementation;
import org.blockserver.core.message.Message;

/**
 * Written by Exerosis!
 */
public class MessageEvent<T extends Message> implements CancellableImplementation {
    @Getter @Setter private Message message;

    public MessageEvent(Message message) {
        this.message = message;
    }
}