package org.blockserver.implementation.module.modules.network;


import org.blockserver.implementation.module.modules.network.message.Message;

import java.util.Collection;

/**
 * Written by Exerosis!
 */
public interface NetworkConverter {
    Collection<RawPacket> toPackets(Collection<Message> messages);

    RawPacket toPacket(Message message);

    Collection<Message> toMessages(Collection<RawPacket> packets);

    Message toMessage(RawPacket packet);
}