package org.blockserver.net.protocol.pe.sub.v27;

import org.blockserver.io.BinaryReader;
import org.blockserver.io.BinaryWriter;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;

import java.io.IOException;

/**
 * MC_SET_HEALTH_PACKET for protocol 27
 */
public class SetHealthPacketV27 extends PeDataPacket{
    public int health;

    @Override
    protected void _encode(BinaryWriter writer) throws IOException {
        writer.writeByte(MC_SET_HEALTH_PACKET);
        writer.writeInt(health);
    }

    @Override
    protected void _decode(BinaryReader reader) throws IOException {
        reader.readByte();
        health = reader.readInt();
    }

    @Override
    protected int getLength() {
        return 5;
    }
}
