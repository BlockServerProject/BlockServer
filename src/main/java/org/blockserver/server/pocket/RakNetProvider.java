package org.blockserver.server.pocket;

import io.github.jython234.jraklibplus.protocol.raknet.EncapsulatedPacket;
import io.github.jython234.jraklibplus.protocol.raknet.Reliability;
import io.github.jython234.jraklibplus.server.HookManager;
import io.github.jython234.jraklibplus.server.RakNetServer;
import io.github.jython234.jraklibplus.server.Session;
import io.github.jython234.jraklibplus.server.ThreadedRakNetServer;
import org.blockserver.server.core.services.network.NetworkProvider;
import org.blockserver.server.core.services.network.NetworkService;
import org.blockserver.server.core.services.network.Packet;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Network Provider wrapper that wraps around JRakLibPlus.
 *
 * @author jython234
 */
public class RakNetProvider extends NetworkProvider {
    private RakNetServer server;
    private Map<String, Session> sessions = new ConcurrentHashMap<>();

    public RakNetProvider(NetworkService networkService, InetSocketAddress bindAddress) {
        super(networkService, bindAddress);

        RakNetServer.ServerOptions options = new RakNetServer.ServerOptions();
        options.broadcastName = "MCPE;A BlockServer Server;45;0.14.1;0;0";
        this.server = new ThreadedRakNetServer(bindAddress, options);
        this.server.getHookManager().addHook(HookManager.Hook.SESSION_OPENED, (session, objects) -> onSessionOpened(session));
        this.server.getHookManager().addHook(HookManager.Hook.SESSION_CLOSED, ((session, objects) -> onSessionClosed(session)));
        this.server.getHookManager().addHook(HookManager.Hook.PACKET_RECIEVED, (session, objects) -> onPacketRecieved(session, (EncapsulatedPacket) objects[0]));
    }

    @Override
    public void bind() {
        this.server.start();
    }

    @Override
    public void close() {
        this.server.stop();
        while(!this.server.isStopped());
    }

    @Override
    public void send(byte[] data, SocketAddress address) {
        if(sessions.containsKey(address.toString())) {
            EncapsulatedPacket pk = new EncapsulatedPacket();
            pk.reliability = Reliability.RELIABLE;
            pk.payload = data;
            sessions.get(address.toString()).addPacketToQueue(pk, false);
        }
        throw new IllegalArgumentException("Session "+address.toString()+" not found.");
    }

    private void onSessionOpened(Session session) {
        if(!sessions.containsValue(session)) {
            sessions.put(session.getAddress().toSocketAddress().toString(), session);
        }
    }

    private void onSessionClosed(Session session) {
        if(sessions.containsValue(session)) {
            sessions.remove(session.getAddress().toSocketAddress().toString());
        }
    }

    private void onPacketRecieved(Session session, EncapsulatedPacket pk) {
        Packet packet = new Packet();
        packet.address = session.getAddress().toSocketAddress();
        packet.buffer = pk.payload;
        this.addToQueue(packet);
    }
}
