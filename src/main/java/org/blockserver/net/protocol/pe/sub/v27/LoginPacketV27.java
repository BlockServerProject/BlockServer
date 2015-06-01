package org.blockserver.net.protocol.pe.sub.v27;

import org.blockserver.io.BinaryReader;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;

import java.io.IOException;

/**
 * MC_LOGIN_PACKET (0x82) for protocol 27.
 */
public class LoginPacketV27 extends PeDataPacket{
    public String username;
    public int protocol;
    public int protocol2;
    public int clientID;

    public boolean slim;
    public String skin;

    @Override
    protected void _decode(BinaryReader reader) throws IOException {
        reader.readByte();
        username = reader.readString();
        protocol = reader.readInt();
        protocol2 = reader.readInt();
        clientID = reader.readInt();

        slim = reader.readByte() > 0;
        skin = reader.readString();
    }

    @Override
    protected int getLength() {
        return 14 + username.getBytes().length + skin.getBytes().length;
    }
}
