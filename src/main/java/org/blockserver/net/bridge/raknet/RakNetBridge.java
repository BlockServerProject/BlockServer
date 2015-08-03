package org.blockserver.net.bridge.raknet;

import net.beaconpe.jraklib.JRakLib;
import net.beaconpe.jraklib.Logger;
import net.beaconpe.jraklib.protocol.EncapsulatedPacket;
import net.beaconpe.jraklib.server.JRakLibServer;
import net.beaconpe.jraklib.server.ServerHandler;
import net.beaconpe.jraklib.server.ServerInstance;
import org.blockserver.Server;
import org.blockserver.net.bridge.NetworkBridge;
import org.blockserver.net.bridge.NetworkBridgeManager;
import org.blockserver.net.protocol.WrappedPacket;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * A Network Bridge for the MCPE RakNet protocol.
 */
public class RakNetBridge extends NetworkBridge implements ServerInstance {
    public final static byte PACKET_OPEN_SESSION = 0x01;
    public final static byte PACKET_CLOSE_SESSION = 0x02;
    public final static byte PACKET_DATA_PACKET = 0x03;

    private NetworkBridgeManager mgr;
    private InetSocketAddress address;

    private JRakLibServer server;
    private ServerHandler handler;

    private Deque<WrappedPacket> recieveQueue = new ConcurrentLinkedDeque<>();

    public RakNetBridge(NetworkBridgeManager mgr){
        this.mgr = mgr;
        address = new InetSocketAddress(getServer().getAddress(), getServer().getPort());

        server = new JRakLibServer(new JRakLibLogger(getServer().getLogger()), address.getPort(), address.getHostString());
        handler = new ServerHandler(server, this);

        handler.sendOption("name", "MCPE;Testing;27;0.11.1;0;5");

        new NonBlockRakLibHandler(this).start();
    }

    /**
     * Sends a RAW packet to the address. If you want to send encapsulated, use <code>RakNetBridge.sendEncapsulated()</code>
     * @param buffer
     * @param addr
     * @return
     */
    @Override
    public boolean send(byte[] buffer, SocketAddress addr) {
        if(addr instanceof InetSocketAddress){
            String address = ((InetSocketAddress) addr).getHostString();
            int port = ((InetSocketAddress) addr).getPort();
            handler.sendRaw(address, (short) port, buffer);
            return true;
        } else {
            //TODO
        }
        return false;
    }

    /**
     * Wraps a PeDataPacket into an encapsulated packet and sends it to the specified client.
     * @param packet A PeDataPacket ready to be sent.
     * @param identifier The client's JRakLib identifier (in the format: [IP]:[Port])
     * @return If the packet was successfully sent.
     */
    public boolean sendEncapsulated(PeDataPacket packet, String identifier, boolean immediate){
        try{
            byte[] buffer = packet.encode();
            EncapsulatedPacket pk = new EncapsulatedPacket();
            pk.messageIndex = 0;
            pk.buffer = buffer;
            if(packet.getChannel() != NetworkChannel.CHANNEL_NONE){
                pk.reliability = 3;
                pk.orderIndex = 0;
                pk.orderChannel = packet.getChannel().getAsByte();
            } else {
                pk.reliability = 2;
            }
            handler.sendEncapsulated(identifier, pk, (byte) ((0 | (immediate ? JRakLib.PRIORITY_IMMEDIATE : JRakLib.PRIORITY_NORMAL))));
            return true;
        } catch(Exception e){
            getServer().getLogger().error("Exception while sending encapsulated packet: "+e.getMessage());
            getServer().getLogger().trace(e.getCause());
            return false;
        }
    }

    @Override
    public WrappedPacket receive() {
        if(server.getState() == Thread.State.TERMINATED){
            getServer().getLogger().fatal("[RakNetBridge]: JRakLib crashed!");
        }
        if(!recieveQueue.isEmpty()){
            return recieveQueue.pop();
        }
        return null;
    }

    @Override
    public Server getServer() {
        return mgr.getServer();
    }

    @Override
    public String getDescription() {
        return "A bridge for the networking library JRakLib";
    }

    @Override
    public void openSession(String identifier, String address, int port, long clientID) {
        getServer().getLogger().debug("("+identifier+"): Session opened with clientID: "+clientID);
        byte[] buffer = ByteBuffer.allocate(9).put(PACKET_OPEN_SESSION).putLong(clientID).array();
        recieveQueue.add(new WrappedPacket(buffer, identifierToSocketAddress(identifier), this));
    }

    @Override
    public void closeSession(String identifier, String reason) {
        getServer().getLogger().debug("("+identifier+"): Session closed.");
        recieveQueue.addLast(new WrappedPacket(new byte[] {PACKET_CLOSE_SESSION}, identifierToSocketAddress(identifier), this));
    }

    @Override
    public void handleEncapsulated(String identifier, EncapsulatedPacket encapsulatedPacket, int flags) {
        getServer().getLogger().buffer("("+identifier+") Packet IN: ", encapsulatedPacket.buffer, "");
        if(encapsulatedPacket.buffer != null || encapsulatedPacket.length > 0){
            ByteBuffer bb = ByteBuffer.allocate(encapsulatedPacket.buffer.length + 1);
            bb.put(PACKET_DATA_PACKET);
            bb.put(encapsulatedPacket.buffer);
            WrappedPacket wp = new WrappedPacket(bb.array(), identifierToSocketAddress(identifier), this);
            recieveQueue.addLast(wp);
            bb = null;
        }
    }

    @Override
    public void handleRaw(String address, int port, byte[] payload) {
        getServer().getLogger().buffer("("+address+":"+port+"): RAW IN: ", payload, "");
        //TODO: Add raw packets to receive queue?
    }

    @Override
    public void notifyACK(String identifier, int identifierACK) {
        //No need for this
    }

    @Override
    public void handleOption(String option, String value) {
        //TODO: Bandwith data update
        System.out.println(option+", "+value);
    }

    public static InetSocketAddress identifierToSocketAddress(String identifier){
        String address = identifier.split(":")[0];
        int port = Integer.parseInt(identifier.split(":")[1]);
        return new InetSocketAddress(address, port);
    }

    public void process() {
        if(handler.handlePacket()){
            while(handler.handlePacket()) {
            }
        }
    }

    public static class JRakLibLogger implements Logger {
        private org.blockserver.ui.Logger logger;

        public JRakLibLogger(org.blockserver.ui.Logger logger){
            this.logger = logger;
        }

        @Override
        public void notice(String s) {
            logger.info(s);
        }

        @Override
        public void critical(String s) {
            logger.error(s);
        }

        @Override
        public void emergency(String s) {
            logger.fatal(s);
        }
    }
}
