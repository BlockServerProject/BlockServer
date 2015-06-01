package org.blockserver.net.protocol.pe.raknet;

import org.blockserver.net.protocol.pe.PeProtocolConst;

import java.nio.ByteBuffer;

/**
 * ID_UNCONNECTED_PING_OPEN_CONNECTIONS (0x1C)
 */
public class UnconnectedPingPacket extends RakNetPacket{
    public long pingID;
    public long serverID = PeProtocolConst.SERVER_ID;
    public String mcpeIdentifier = "MCCPP;Demo;";
    public String identifier;

    @Override
    protected void _encode(ByteBuffer bb) {
        bb.put(PeProtocolConst.RAKNET_BROADCAST_PONG_1);
        bb.putLong(pingID);
        bb.putLong(serverID);
        bb.put(PeProtocolConst.MAGIC);

        String total = mcpeIdentifier + identifier;
        bb.putShort((short) total.getBytes().length);
        bb.put(total.getBytes());
    }

    @Override
    public int getLength() {
        return 35 + mcpeIdentifier.getBytes().length + identifier.getBytes().length;
    }
}
