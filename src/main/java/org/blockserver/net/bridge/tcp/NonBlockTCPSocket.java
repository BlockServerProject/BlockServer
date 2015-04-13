package org.blockserver.net.bridge.tcp;

import org.blockserver.net.bridge.NetworkBridge;
import org.blockserver.net.bridge.NonBlockSocket;
import org.blockserver.net.protocol.WrappedPacket;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

/**
 * A Non-Blocking TCP Socket.
 */
public class NonBlockTCPSocket extends NonBlockSocket{
    private TCPBridge tcp;
    private SocketAddress bindAddress;
    private ServerSocket socket;

    private Class<? extends TCPConnectionHandler> handlerImplementation;
    private ArrayList<TCPConnectionHandler> handlers = new ArrayList<>();

    private boolean running;

    public NonBlockTCPSocket(TCPBridge tcp, SocketAddress bindAddress, Class<? extends TCPConnectionHandler> handlerImpl){
        this.tcp = tcp;
        this.bindAddress = bindAddress;
        handlerImplementation = handlerImpl;
    }

    public void run(){
        setName("TCPSocket-"+bindAddress.toString());
        try {
            socket = new ServerSocket();
            socket.bind(bindAddress);
            while(running && !isInterrupted()){
                socket.setSoTimeout(500);
                Socket connection = socket.accept();
                tcp.getServer().getLogger().debug("Accepting new TCP connection from: "+connection.getRemoteSocketAddress().toString());

                try {
                    Constructor constructor = handlerImplementation.getConstructor(NonBlockTCPSocket.class, Socket.class);
                    TCPConnectionHandler handler = (TCPConnectionHandler) constructor.newInstance(this, connection);
                    handler.setRunning(true);
                    handler.start();
                    handlers.add(handler);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        running = true;
        super.start();
    }

    @Override
    public void send(WrappedPacket pk) {
        try {
            for(TCPConnectionHandler handler: handlers){
                if(handler.getRemoteAddress().toString().equals(pk.getAddress().toString())){
                    handler.getWriter().write(pk.bb().array());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public WrappedPacket receive_() {
        for(TCPConnectionHandler handler: handlers){
            WrappedPacket wp = handler.getPacket();
            if(wp != null){
                return wp;
            }
        }
        return null;
    }

    @Override
    public void finalizeStuffs() throws IOException {
        running = false;
        socket.close();
    }

    protected void closeHandler(TCPConnectionHandler handler){
        handlers.remove(handler);
    }

    @Override
    public NetworkBridge getBridge() {
        return tcp;
    }
}
