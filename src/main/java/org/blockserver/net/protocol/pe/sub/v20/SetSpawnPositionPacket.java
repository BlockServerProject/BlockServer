package org.blockserver.net.protocol.pe.sub.v20;

import org.blockserver.io.BinaryWriter;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;

import java.io.IOException;

/**
 *
 */
public class SetSpawnPositionPacket extends PeDataPacket{
    public int x;
    public int z;
    public byte y;

    @Override
    protected void _encode(BinaryWriter writer) throws IOException {
        writer.writeByte(MC_SPAWN_POSITION_PACKET);
        writer.writeInt(x);
        writer.writeInt(z);
        writer.writeByte(y);
    }

    @Override
    protected int getLength() {
        return 10;
    }
}
