package org.blockserver.net.protocol.pe.sub;

import org.blockserver.io.BinaryReader;
import org.blockserver.io.BinaryWriter;

import java.io.IOException;

/**
 * Packet for unknown packets.
 */
public class UnknownPacket extends PeDataPacket{
    public byte[] buffer;

    @Override
    protected void _encode(BinaryWriter writer) throws IOException {
        writer.write(buffer);
    }

    @Override
    protected void _decode(BinaryReader reader) throws IOException {
        buffer = reader.read(reader.getInputStream().available());
    }

    @Override
    protected int getLength() {
        return buffer.length;
    }
}
