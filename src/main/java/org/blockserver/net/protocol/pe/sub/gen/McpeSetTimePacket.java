package org.blockserver.net.protocol.pe.sub.gen;

import org.blockserver.io.BinaryWriter;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;

import java.io.IOException;

public class McpeSetTimePacket extends PeDataPacket {

    public int time;

    public McpeSetTimePacket(int time){
        super(new byte[] {MC_SET_TIME_PACKET});
        this.time = time;
    }

    @Override
    protected void _encode(BinaryWriter writer) throws IOException {
        writer.writeByte(MC_SET_TIME_PACKET);
        writer.writeInt(time);
        writer.writeByte((byte) 0x80);
    }

    public int getLength(){
        return 5;
    }
}
