package org.blockserver.net.protocol.pe.sub.v27;

import org.blockserver.io.BinaryWriter;
import org.blockserver.net.protocol.pe.sub.v20.FullChunkDataPacketV20;

import java.io.IOException;

/**
 * MC_FULL_CHUNK_DATA_PACKET (0xba) for protocol 27.
 */
public class FullChunkDataPacketV27 extends FullChunkDataPacketV20{

    @Override
    protected void _encode(BinaryWriter writer) throws IOException {
        writer.writeByte(MC_FULL_CHUNK_DATA);
        writer.writeInt(chunkX);
        writer.writeInt(chunkZ);
        writer.writeInt(data.length);
        writer.write(data);
    }

    @Override
    public int getLength(){
        return 13 + data.length;
    }
}
