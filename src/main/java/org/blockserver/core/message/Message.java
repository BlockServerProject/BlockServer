package org.blockserver.core.message;

import lombok.Getter;
import lombok.Setter;

import java.net.SocketAddress;

/**
 * Written by Exerosis!
 */
public class Message {
    @Getter @Setter private SocketAddress address;

    public Message(SocketAddress address) {
        this.address = address;
    }
}