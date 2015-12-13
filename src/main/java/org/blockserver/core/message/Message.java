package org.blockserver.core.message;

import lombok.Getter;
import lombok.Setter;
import org.blockserver.core.event.CancellableImplementation;

import java.net.SocketAddress;

/**
 * Written by Exerosis!
 */
public class Message implements CancellableImplementation {
    @Getter @Setter private SocketAddress address;

    public Message(SocketAddress address) {
        this.address = address;
    }
}