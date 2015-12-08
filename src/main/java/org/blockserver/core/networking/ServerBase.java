package org.blockserver.core.networking;

import org.blockserver.core.networking.channel.ClientConnection;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public abstract class ServerBase {
    private Map<InetSocketAddress, ClientConnection> connections = new HashMap<>();

    public ServerBase() {

    }

    public void sendPacket(Object packet, ClientConnection connection){

    }

    public void receive(Object packet, InetSocketAddress address){
        connections.get(address).receive(packet);
    }

    public Map<InetSocketAddress, ClientConnection> getConnections() {
        return connections;
    }
}
