package org.blockserver.net.protocol.pe.sub.v27;

import org.blockserver.io.BinaryReader;
import org.blockserver.io.BinaryWriter;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;

import java.io.IOException;

/**
 * MC_DISCONNECT_PACKET (0x84)
 */
public class DisconnectPacket extends PeDataPacket{
    public String reason;

    @Override
    protected void _encode(BinaryWriter writer) throws IOException {
        writer.writeByte((byte) 0x84);
        writer.writeString(reason);
    }

    @Override
    protected void _decode(BinaryReader reader) throws IOException {
        reader.readByte();
        reason = reader.readString();
    }

    @Override
    protected int getLength() {
        return 1 + reason.getBytes().length;
    }
}
