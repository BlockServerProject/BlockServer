package org.blockserver.core.modules.network.v2;

import lombok.Getter;
import org.blockserver.core.Server;
import org.blockserver.core.module.Module;
import org.blockserver.core.modules.network.RawPacket;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Written by Exerosis!
 */
public abstract class NetworkProvider extends Module {
    @Getter private final Set<RawPacket> packetQueue = new HashSet<>();

    public NetworkProvider(Server server) {
        super(server);
    }

    protected void queuePackets(Collection<RawPacket> packets) {
        packetQueue.addAll(packets);
    }

    public Collection<RawPacket> receivePackets() {
        Set<RawPacket> messages = new HashSet<>(packetQueue);
        packetQueue.clear();
        return messages;
    }

    public abstract void sendPackets(Collection<RawPacket> packets);
}
