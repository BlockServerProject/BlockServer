package org.blockserver.core.modules.network.v2;

import lombok.Getter;
import org.blockserver.core.modules.network.RawPacket;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Written by Exerosis!
 */
public abstract class NetworkProvider {
    @Getter private final Set<RawPacket> packetQueue = new HashSet<>();

    protected void queuePackets(Collection<RawPacket> packets) {
        packetQueue.addAll(packets);
    }

    public Collection<RawPacket> receivePackets() {
        Set<RawPacket> messages = new HashSet<>(packetQueue);
        packetQueue.clear();
        return messages;
    }

    public abstract void sendPackets(Collection<RawPacket> messages);
}
