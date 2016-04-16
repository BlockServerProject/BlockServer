package org.blockserver.server.core.services.network;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import lombok.Getter;

/**
 * Class that provides packets to the server. Handles
 * I/O
 *
 * @author BlockServer Team
 */
public abstract class NetworkProvider {
    @Getter private NetworkService networkService;
    @Getter private InetSocketAddress bindAddress;

    private final Queue<Packet> packetQueue = new ConcurrentLinkedQueue<>();

    public NetworkProvider(NetworkService networkService, InetSocketAddress bindAddress) {
        this.networkService = networkService;
        this.bindAddress = bindAddress;
    }

    protected void addToQueue(Packet packet) {
        packetQueue.add(packet);
    }

    public Packet getNextPacket() {
        if(packetQueue.isEmpty()) return null;
        return packetQueue.remove();
    }

    public abstract void bind();
    public abstract void close();

    public abstract void send(byte[] data, SocketAddress address);
}
