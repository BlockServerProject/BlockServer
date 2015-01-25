package org.blockserver.net.protocol.pe.sub.gen;

import org.blockserver.io.BinaryWriter;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;

import java.io.IOException;

public class McpeSetHealthPacket extends PeDataPacket{

    public byte health;

    public McpeSetHealthPacket(int health){
        super(new byte[] {MC_SET_HEALTH_PACKET});
        this.health = (byte) health;
    }

    @Override
    protected void _encode(BinaryWriter writer) throws IOException {
        writer.writeByte(MC_SET_HEALTH_PACKET);
        writer.writeByte(health);
    }

    public int getLength(){
        return 2;
    }
}
