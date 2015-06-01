package org.blockserver.net.protocol.pe.sub.v20;

import org.blockserver.io.BinaryReader;
import org.blockserver.io.BinaryWriter;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;

import java.io.IOException;

/**
 * MC_SET_HEALTH_PACKET
 */
public class SetHealthPacketV20 extends PeDataPacket{
    public byte health;

    @Override
    protected void _encode(BinaryWriter writer) throws IOException {
        writer.writeByte(MC_SET_HEALTH_PACKET);
        writer.writeByte(health);
    }

    @Override
    protected void _decode(BinaryReader reader) throws IOException {
        reader.readByte();
        health = reader.readByte();
    }

    @Override
    protected int getLength() {
        return 2;
    }
}
