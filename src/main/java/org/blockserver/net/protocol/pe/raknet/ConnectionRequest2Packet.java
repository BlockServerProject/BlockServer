package org.blockserver.net.protocol.pe.raknet;

import java.nio.ByteBuffer;

/**
 * ID_OPEN_CONNECTION_REQUEST_2 (0x07)
 */
public class ConnectionRequest2Packet extends RakNetPacket{
    public byte[] securityCookie = new byte[4];
    public short serverUdpPort;
    public short mtuSize;
    public long clientID;

    @Override
    protected void _decode(ByteBuffer bb) {
        bb.get();
        bb.position(bb.position() + 16); //MAGIC
        bb.get(securityCookie);
        serverUdpPort = bb.getShort();
        mtuSize = bb.getShort();
        clientID = bb.getLong();
    }

    @Override
    public int getLength() {
        return 34;
    }
}
