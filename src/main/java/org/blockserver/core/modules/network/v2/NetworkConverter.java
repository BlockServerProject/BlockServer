package org.blockserver.core.modules.network.v2;


import org.blockserver.core.modules.network.RawPacket;
import org.blockserver.core.modules.network.message.Message;

import java.util.Collection;

/**
 * Written by Exerosis!
 */
public interface NetworkConverter {
    Collection<RawPacket>  toPacket(Collection<Message> message);
    Collection<Message> toMessage(Collection<RawPacket> packet);
}