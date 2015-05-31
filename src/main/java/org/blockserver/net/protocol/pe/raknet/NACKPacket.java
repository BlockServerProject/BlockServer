package org.blockserver.net.protocol.pe.raknet;

import org.blockserver.net.protocol.pe.PeProtocolConst;

/**
 * RAKNET_NACK (0xA0)
 */
public class NACKPacket extends ACKPacket{
    @Override
    public byte getPID() {
        return PeProtocolConst.RAKNET_NACK;
    }
}
