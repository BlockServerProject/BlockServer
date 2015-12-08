package org.blockserver.core.modules.network;

import org.blockserver.core.message.Message;

import java.util.Collection;

/**
 * Written by Exerosis!
 */
public interface NetworkConverter {
    Collection<RawPacket>  toPacket(Collection<Message> message);
    Collection<Message> toMessage(Collection<RawPacket> packet);
}