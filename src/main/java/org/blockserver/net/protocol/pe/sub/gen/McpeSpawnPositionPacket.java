package org.blockserver.net.protocol.pe.sub.gen;

import org.blockserver.io.BinaryWriter;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;
import org.blockserver.utils.Position;

import java.io.IOException;

public class McpeSpawnPositionPacket extends PeDataPacket{

    public int x;
    public int z;
    public byte y;

    public McpeSpawnPositionPacket(Position spawnPosition){
        super(new byte[] { MC_SPAWN_POSITION_PACKET});
        x = (int) spawnPosition.getX();
        z = (int) spawnPosition.getZ();
        y = (byte) spawnPosition.getY();
    }

    @Override
    protected void _encode(BinaryWriter writer) throws IOException {
        writer.writeByte(MC_SPAWN_POSITION_PACKET);
        writer.writeInt(x);
        writer.writeInt(z);
        writer.writeByte(y);
    }

    public int getLength(){
        return 10;
    }
}