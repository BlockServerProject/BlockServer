package org.blockserver.net.protocol.pe.sub.v20;

import org.blockserver.io.BinaryReader;
import org.blockserver.io.BinaryWriter;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;

import java.io.IOException;

/**
 * MC_SET_DIFFICULTY_PACKET
 */
public class SetDifficultyPacket extends PeDataPacket{
    public int difficulty;

    @Override
    protected void _encode(BinaryWriter writer) throws IOException {
        writer.writeByte(MC_SET_DIFFICULTY_PACKET);
        writer.writeInt(difficulty);
    }

    @Override
    protected void _decode(BinaryReader reader) throws IOException {
        reader.readByte();
        difficulty = reader.readInt();
    }

    @Override
    protected int getLength() {
        return 5;
    }
}
