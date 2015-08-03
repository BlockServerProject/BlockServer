package org.blockserver.net.protocol.pe;

import org.blockserver.Server;
import org.blockserver.net.bridge.raknet.RakNetBridge;
import org.blockserver.net.protocol.Protocol;
import org.blockserver.net.protocol.ProtocolSession;
import org.blockserver.net.protocol.WrappedPacket;

/**
 * Created by jython234 on 8/3/2015.
 */
public class PeProtocol extends Protocol{
    private Server server;

    public PeProtocol(Server server){
        this.server = server;
    }

    @Override
    public ProtocolSession openSession(WrappedPacket pk) {
        byte pid = pk.bb().get();
        switch (pid){
            case RakNetBridge.PACKET_OPEN_SESSION:

                return null;
                //break;

            default:
                return null;
        }
    }

    @Override
    public String getDescription() {
        return "Supports MCPE and MCW10 clients.";
    }
}
