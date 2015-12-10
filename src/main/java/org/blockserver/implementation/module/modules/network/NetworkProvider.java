package org.blockserver.implementation.module.modules.network;

import lombok.Getter;
import org.blockserver.core.Server;
import org.blockserver.core.module.Module;

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

    protected void queuePacket(RawPacket packet) {
        packetQueue.add(packet);
    }

    public Collection<RawPacket> receivePackets() {
        Set<RawPacket> messages = new HashSet<>(packetQueue);
        packetQueue.clear();
        return messages;
    }

    public abstract void sendPacket(RawPacket packet);
}
