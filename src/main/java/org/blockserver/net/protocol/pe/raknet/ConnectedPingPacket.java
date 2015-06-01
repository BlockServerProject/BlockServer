package org.blockserver.net.protocol.pe.raknet;

import java.nio.ByteBuffer;

/**
 * ID_CONNECTED_PING_OPEN_CONNECTIONS (0x01)
 */
public class ConnectedPingPacket extends RakNetPacket{
    public long pingID;

    @Override
    protected void _decode(ByteBuffer bb) {
        bb.get(); //PID
        pingID = bb.getLong();
    }

    @Override
    public int getLength() {
        return 25;
    }
}
