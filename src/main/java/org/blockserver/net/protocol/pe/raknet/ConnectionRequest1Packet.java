package org.blockserver.net.protocol.pe.raknet;

import java.nio.ByteBuffer;

/**
 * ID_OPEN_CONNECTION_REQUEST_1 (0x05)
 */
public class ConnectionRequest1Packet extends RakNetPacket{
    public byte raknetProtocol;
    public int nullPayloadLength;

    @Override
    protected void _decode(ByteBuffer bb) {
        bb.get();
        bb.position(bb.position() + 16); //MAGIC
        raknetProtocol = bb.get();
        nullPayloadLength = bb.remaining();
    }

    @Override
    public int getLength() {
        return 18 + nullPayloadLength;
    }
}
