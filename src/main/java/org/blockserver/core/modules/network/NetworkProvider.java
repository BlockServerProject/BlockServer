package org.blockserver.core.modules.network;

import lombok.Getter;

import java.net.SocketAddress;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * Abstract class, represents a provider that handles low-level
 * networking (TCP, UDP, RakNet, etc.)
 *
 * @author BlockServer Team
 */
public abstract class NetworkProvider {
    @Getter private NetworkAdapter adapter;
    private Queue<RawPacket> packetQueue = new ConcurrentLinkedQueue<>();

    public NetworkProvider(NetworkAdapter adapter) {
        this.adapter = adapter;
    }

    protected abstract void init(SocketAddress address);

    protected final RawPacket getNextPacket() {
        if(!packetQueue.isEmpty()) {
            return packetQueue.remove();
        }
        return null;
    }

    /**
     * Used by implementing class.
     * @param packet Packet to be added.
     */
    protected final void addPacketToQueue(RawPacket packet) {
        packetQueue.add(packet);
    }
}
