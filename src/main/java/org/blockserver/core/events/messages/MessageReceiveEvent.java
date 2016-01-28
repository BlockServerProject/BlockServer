package org.blockserver.core.events.messages;

import org.blockserver.core.modules.message.Message;

public class MessageReceiveEvent<T extends Message> extends MessageEvent<T> {
    public MessageReceiveEvent(T message) {
        super(message);
    }
}