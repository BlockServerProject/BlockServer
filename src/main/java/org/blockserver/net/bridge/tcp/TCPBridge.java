package org.blockserver.net.bridge.tcp;

import org.blockserver.Server;
import org.blockserver.net.bridge.NetworkBridge;
import org.blockserver.net.bridge.NetworkBridgeManager;
import org.blockserver.net.protocol.WrappedPacket;

import java.lang.reflect.Constructor;
import java.net.SocketAddress;

/**
 * TCP/IP Network Bridge.
 */
public class TCPBridge extends NetworkBridge{
    private TCPSocket sock;
    private NetworkBridgeManager mgr;

    public TCPBridge(NetworkBridgeManager mgr, SocketAddress bindAddr, Class<? extends TCPSocket> sockImpl){
        this.mgr = mgr;
        try {
            Constructor construct = sockImpl.getConstructor(Server.class, SocketAddress.class);
            sock = (TCPSocket) construct.newInstance(mgr.getServer(), bindAddr);
        } catch(Exception e){
            throw new RuntimeException(e);
        } finally {
            start();
        }
    }

    public TCPBridge(NetworkBridgeManager mgr, TCPSocket sock){
        this.mgr = mgr;
        this.sock = sock;
        start();
    }

    private void start(){
        sock.setRunning(true);
        sock.start();
    }

    @Override
    public boolean send(byte[] buffer, SocketAddress addr) {
        return sock.send(buffer, addr);
    }

    @Override
    public WrappedPacket receive() {
        TCPPacket p = sock.receive();
        if(p != null){
            return new WrappedPacket(p.data, p.address, this);
        } else {
            return null;
        }
    }

    @Override
    public Server getServer() {
        return mgr.getServer();
    }

    @Override
    public String getDescription() {
        return "A NetworkBridge for TCP/IP.";
    }
}
