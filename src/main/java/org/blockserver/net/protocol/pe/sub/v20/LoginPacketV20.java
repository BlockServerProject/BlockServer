package org.blockserver.net.protocol.pe.sub.v20;

import org.blockserver.io.BinaryReader;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;

import java.io.IOException;

/**
 * MC_LOGIN_PACKET (0x82) for protocol 20.
 */
public class LoginPacketV20 extends PeDataPacket{
    public String username;
    public int protocol;
    public int protocol2;
    public int clientID;

    @Override
    protected void _decode(BinaryReader reader) throws IOException {
        reader.readByte();
        username = reader.readString();
        protocol = reader.readInt();
        protocol2 = reader.readInt();
        clientID = reader.readInt();
    }

    @Override
    protected int getLength() {
        return 13 + username.getBytes().length;
    }
}
