package org.blockserver.api.impl;

import org.blockserver.api.Event;
import org.blockserver.net.protocol.pe.PeProtocolSession;
import org.blockserver.net.protocol.pe.login.RaknetSentCustomPacket;

/**
 * Fired whenever a RakNet Custom packet is to be sent.
 */
public class PEDataPacketSendEvent extends Event{
    /**
     * Get the RakNet Custom packet to be sent.
     * @return The Custom packet.
     */
    public RaknetSentCustomPacket getPacket(){
        return (RaknetSentCustomPacket) getArguments().get(1).value;
    }

    /**
     * Get the ProtocolSession this packet will send from.
     * @return The ProtocolSession.
     */
    public PeProtocolSession getSession(){
        return (PeProtocolSession) getArguments().get(0).value;
    }
}
