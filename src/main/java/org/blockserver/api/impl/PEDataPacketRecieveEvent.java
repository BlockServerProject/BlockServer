package org.blockserver.api.impl;

import org.blockserver.api.Event;
import org.blockserver.net.protocol.pe.PeProtocolSession;
import org.blockserver.net.protocol.pe.login.RaknetReceivedCustomPacket;

/**
 * Fired when a new encapsulated packet is received.
 * NOTE: If you cancel this event, the server WILL NOT run the handling code for this packet.
 */
public class PEDataPacketRecieveEvent extends Event{
    /**
     * Get the RakNet Custom packet received.
     * @return The Custom packet.
     */
    public RaknetReceivedCustomPacket getPacket(){
        return (RaknetReceivedCustomPacket) getArguments().get(1).value;
    }

    /**
     * Get the ProtocolSession this packet was sent to.
     * @return The ProtocolSession.
     */
    public PeProtocolSession getSession(){
        return (PeProtocolSession) getArguments().get(0).value;
    }
}
