package org.blockserver.core.message;

import java.net.InetSocketAddress;

/**
 * Written by Exerosis!
 */
public class MessageInPlayerLogin extends Message {
    public MessageInPlayerLogin(InetSocketAddress address) {
        super(address);
    }
}