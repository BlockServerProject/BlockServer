package org.blockserver.core.events.messages;

import org.blockserver.core.modules.message.Message;

public class MessageSendEvent<T extends Message> extends MessageEvent<T> {
    public MessageSendEvent(T message) {
        super(message);
    }
}