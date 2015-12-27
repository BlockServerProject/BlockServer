package org.blockserver.core.message.block;

import org.blockserver.core.message.Message;

import java.net.SocketAddress;

/**
 * Written by Exerosis!
 */
public class MessageOutBlockChange extends Message {
    public MessageOutBlockChange(SocketAddress address) {
        super(address);
    }
}
