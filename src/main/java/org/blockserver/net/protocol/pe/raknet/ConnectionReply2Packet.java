package org.blockserver.net.protocol.pe.raknet;

import org.blockserver.net.protocol.pe.PeProtocolConst;

import java.nio.ByteBuffer;

/**
 * ID_OPEN_CONNECTION_REPLY_2 (0x08)
 */
public class ConnectionReply2Packet extends RakNetPacket{
    public long serverID = PeProtocolConst.SERVER_ID;
    public short clientUdpPort;
    public short mtuSize;
    public byte security = 0;

    @Override
    protected void _encode(ByteBuffer bb) {
        bb.put(PeProtocolConst.RAKNET_OPEN_CONNECTION_REPLY_2);
        bb.put(PeProtocolConst.MAGIC);
        bb.putLong(serverID);
        bb.putShort(clientUdpPort);
        bb.putShort(mtuSize);
        bb.put(security);
    }

    @Override
    public int getLength() {
        return 30;
    }
}
