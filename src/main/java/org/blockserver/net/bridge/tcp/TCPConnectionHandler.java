package org.blockserver.net.bridge.tcp;

import org.blockserver.io.BinaryReader;
import org.blockserver.io.BinaryWriter;
import org.blockserver.net.protocol.WrappedPacket;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

/**
 * A TCP/IP Connection handler.
 */
public abstract class TCPConnectionHandler extends Thread{
    private NonBlockTCPSocket socket;
    private Socket connection;

    private BinaryWriter writer;
    private BinaryReader reader;

    private ArrayList<WrappedPacket> packetQueue = new ArrayList<>();

    private boolean running = false;

    public TCPConnectionHandler(NonBlockTCPSocket socket, Socket connection){
        this.socket = socket;
        this.connection = connection;

        try {
            writer = new BinaryWriter(connection.getOutputStream());
            reader = new BinaryReader(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final void setRunning(boolean running){
        this.running = running;
    }

    public final void run(){
        while(running && !isInterrupted()){
            try {
                byte[] buffer = readPacket();
                WrappedPacket wp = new WrappedPacket(buffer, connection, socket.getBridge());
                packetQueue.add(wp);
            } catch(EOFException e){
                running = false;
                socket.getBridge().getServer().getLogger().info("Connection "+connection.getRemoteSocketAddress().toString()+" unexpectedly closed: java.io.EOFException");
                socket.closeHandler(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public final WrappedPacket getPacket(){
        if(!packetQueue.isEmpty()) {
            WrappedPacket wp = packetQueue.get(0);
            packetQueue.remove(wp);
            return wp;
        } else {
            return null;
        }
    }

    public SocketAddress getRemoteAddress(){
        return connection.getRemoteSocketAddress();
    }

    public BinaryWriter getWriter(){
        return writer;
    }

    public BinaryReader getReader(){
        return reader;
    }

    protected abstract byte[] readPacket() throws IOException, EOFException;
}
