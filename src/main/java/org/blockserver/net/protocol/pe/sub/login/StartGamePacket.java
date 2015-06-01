package org.blockserver.net.protocol.pe.sub.login;

import org.blockserver.io.BinaryWriter;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;

import java.io.IOException;

/**
 * MC_START_GAME_PACKET
 */
public class StartGamePacket extends PeDataPacket{
    public int seed;
    public int generator;
    public int gamemode;
    public long entityID;
    public int spawnX;
    public int spawnY;
    public int spawnZ;
    public float x;
    public float y;
    public float z;

    @Override
    protected void _encode(BinaryWriter writer) throws IOException {
        writer.writeByte(MC_START_GAME_PACKET);
        writer.writeInt(seed);
        writer.writeInt(generator);
        writer.writeInt(gamemode);
        writer.writeLong(entityID);
        writer.writeInt(spawnX);
        writer.writeInt(spawnY);
        writer.writeInt(spawnZ);
        writer.writeFloat(x);
        writer.writeFloat(y);
        writer.writeFloat(z);
    }

    @Override
    protected int getLength() {
        return 43;
    }
}
