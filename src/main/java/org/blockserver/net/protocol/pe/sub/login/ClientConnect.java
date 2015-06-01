package org.blockserver.net.protocol.pe.sub.login;

import org.blockserver.io.BinaryReader;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;

import java.io.IOException;

/**
 * MC_CLIENT_CONNECT (0x09)
 */
public class ClientConnect extends PeDataPacket{
    public long clientID;
    public long sessionID;

    @Override
    protected void _decode(BinaryReader reader) throws IOException {
        reader.readByte();
        clientID = reader.readLong();
        sessionID = reader.readLong();
    }

    @Override
    protected int getLength() {
        return 18;
    }
}
