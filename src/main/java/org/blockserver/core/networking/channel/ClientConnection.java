package org.blockserver.core.networking.channel;


import org.blockserver.core.networking.ServerBase;
import org.blockserver.core.networking.protocol.ProtocolType;

import java.net.InetSocketAddress;

public class ClientConnection {
    private InetSocketAddress address;
    private ProtocolType type;
    private ServerBase server;

    public ClientConnection(InetSocketAddress address, ProtocolType type, ServerBase server) {
        this.address = address;
        this.type = type;
        this.server = server;
    }

    public void sendPacket(Object packet) {
        server.sendPacket(packet, this);
    }

    public void receive(Object packet){

    }

    public ServerBase getServer() {
        return server;
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public ProtocolType getType() {
        return type;
    }
}
