package org.blockserver.net.protocol.pe.sub.v27;

import org.blockserver.io.BinaryReader;
import org.blockserver.io.BinaryWriter;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;

import java.io.IOException;

/**
 * MC_BATCH_PACKET (0xb1)
 */
public class BatchPacket extends PeDataPacket{
    public byte[] payload;

    @Override
    protected void _encode(BinaryWriter writer) throws IOException {
        writer.writeByte(MC_BATCH);
        writer.writeInt(payload.length);
        writer.write(payload);
    }

    @Override
    protected void _decode(BinaryReader reader) throws IOException {
        reader.readByte();
        int size = reader.readInt();
        payload = reader.read(size);
    }

    @Override
    protected int getLength() {
        return 5 + payload.length;
    }
}
