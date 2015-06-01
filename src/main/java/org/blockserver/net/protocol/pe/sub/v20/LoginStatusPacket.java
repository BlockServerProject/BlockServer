package org.blockserver.net.protocol.pe.sub.v20;

import org.blockserver.io.BinaryWriter;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;

import java.io.IOException;

/**
 * MC_LOGIN_STATUS_PACKET (0x82)
 */
public class LoginStatusPacket extends PeDataPacket{
    public int status;

    @Override
    protected void _encode(BinaryWriter writer) throws IOException {
        writer.writeByte(MC_LOGIN_STATUS_PACKET);
        writer.writeInt(status);
    }

    @Override
    protected int getLength() {
        return 5;
    }
}
