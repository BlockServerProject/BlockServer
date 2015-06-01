package org.blockserver.net.protocol.pe.sub.v20;

import org.blockserver.io.BinaryWriter;
import org.blockserver.level.ILevel;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;

import java.io.IOException;

/**
 * MC_SET_TIME_PACKET
 */
public class SetTimePacket extends PeDataPacket{
    public int time;
    public boolean started = true;

    @Override
    protected void _encode(BinaryWriter writer) throws IOException {
        writer.writeByte(MC_SET_TIME_PACKET);
        writer.writeInt((time / ILevel.TIME_FULL) * 19200);
        writer.writeByte((byte) (started == true ? 0x80 : 0x00));
    }

    @Override
    protected int getLength() {
        return 6;
    }
}
