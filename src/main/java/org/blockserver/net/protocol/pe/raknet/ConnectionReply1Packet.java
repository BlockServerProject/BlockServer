package org.blockserver.net.protocol.pe.raknet;


import org.blockserver.net.protocol.pe.PeProtocolConst;

import java.nio.ByteBuffer;

/**
 * ID_OPEN_CONNECTION_REPLY_1 (0x06)
 */
public class ConnectionReply1Packet extends RakNetPacket{
    public long serverID = PeProtocolConst.SERVER_ID;
    public byte security = 0;
    public short mtuSize;

    @Override
    protected void _encode(ByteBuffer bb) {
        bb.put(PeProtocolConst.RAKNET_OPEN_CONNECTION_REPLY_1);
        bb.put(PeProtocolConst.MAGIC);
        bb.putLong(serverID);
        bb.put(security);
        bb.putShort(mtuSize);
    }

    @Override
    public int getLength() {
        return 28;
    }
}
