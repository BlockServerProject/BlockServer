package org.blockserver.net.bridge.tcp;

import org.blockserver.Server;
import org.blockserver.net.protocol.WrappedPacket;

import java.net.SocketAddress;

/**
 * TCP/IP Socket. Must be extended from all implementations.
 */
public abstract class TCPSocket extends Thread{
    private Server server;
    private SocketAddress bindAddr;
    private boolean running = false;

    public TCPSocket(Server server, SocketAddress bindAddr){
        this.server = server;
        this.bindAddr = bindAddr;
    }

    public abstract TCPPacket receive();
    public abstract boolean send(byte[] buffer, SocketAddress sendTo);

    public boolean send(WrappedPacket wp){
        return send(wp.bb().array(), wp.getAddress());
    }

    public void setRunning(boolean running){
        this.running = running;
    }

    public boolean isRunning() { return running; }

    public Server getServer(){
        return server;
    }

    public SocketAddress getAddress(){
        return bindAddr;
    }
}
