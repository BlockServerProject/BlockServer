package org.blockserver.net.protocol.pe.sub.v20;

import org.blockserver.io.BinaryWriter;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;

import java.io.IOException;
import java.util.Arrays;
import java.util.zip.Deflater;

/**
 * MC_FULL_CHUNK_DATA_PACKET (0xba) for protocol 20.
 */
public class FullChunkDataPacketV20 extends PeDataPacket{
    public int chunkX;
    public int chunkZ;
    public byte[] data;

    @Override
    protected void _encode(BinaryWriter writer) throws IOException {
        writer.writeByte(MC_FULL_CHUNK_DATA);
        writer.writeInt(chunkX);
        writer.writeInt(chunkZ);

        Deflater compresser = new Deflater();
        compresser.setInput(data);
        compresser.finish();
        int dataLen = compresser.deflate(data);
        compresser.end();

        data = Arrays.copyOf(data, dataLen);

        writer.write(data);
    }

    @Override
    protected int getLength() {
        return 9 + data.length;
    }
}
