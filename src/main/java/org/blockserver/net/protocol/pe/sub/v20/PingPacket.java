package org.blockserver.net.protocol.pe.sub.v20;

import org.blockserver.io.BinaryReader;
import org.blockserver.io.BinaryWriter;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;

import java.io.IOException;

/**
 * MC_PLAY_PING (0x00)
 */
public class PingPacket extends PeDataPacket{
    public long pingID;

    @Override
    public byte getPid(){ return MC_PLAY_PING; }

    @Override
    protected void _encode(BinaryWriter writer) throws IOException {
        writer.writeByte(getPid());
        writer.writeLong(pingID);
    }

    @Override
    protected void _decode(BinaryReader reader) throws IOException {
        reader.readByte();
        pingID = reader.readLong();
    }

    @Override
    protected int getLength() {
        return 9;
    }
}
