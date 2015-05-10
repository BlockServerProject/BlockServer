package org.blockserver.net.protocol.pe.raknet;

import org.blockserver.net.protocol.pe.PeProtocolConst;

/**
 * ID_OPEN_CONNECTION_REPLY_2 (0x08)
 */
public class ConnectionReply2Packet extends RakNetPacket{
    public long serverID = PeProtocolConst.SERVER_ID;
    public short clientUdpPort;
    public short mtuSize;
    public byte security = 0;
    
    @Override
    public int getLength() {
        return 30;
    }
}
