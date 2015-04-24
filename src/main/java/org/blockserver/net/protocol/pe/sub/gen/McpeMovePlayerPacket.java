package org.blockserver.net.protocol.pe.sub.gen;

import org.blockserver.io.BinaryReader;
import org.blockserver.io.BinaryWriter;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;

import java.io.IOException;

/**
 * MC_MOVE_PLAYER_PACKET (0x95)
 */
public class McpeMovePlayerPacket extends PeDataPacket{
    public int entityId;
    public float x;
    public float y;
    public float z;
    public float yaw;
    public float pitch;
    public float bodyYaw;
    public byte teleport = 0x00;

    public McpeMovePlayerPacket(byte[] buffer) {
        super(buffer);
    }

    public McpeMovePlayerPacket(){
        super(new byte[] {MC_MOVE_PLAYER_PACKET});
    }

    @Override
    protected void _encode(BinaryWriter writer) throws IOException {
        writer.writeByte(MC_MOVE_PLAYER_PACKET);
        writer.writeInt(entityId);
        writer.writeFloat(x);
        writer.writeFloat(y);
        writer.writeFloat(z);
        writer.writeFloat(yaw);
        writer.writeFloat(pitch);
        writer.writeFloat(bodyYaw);
        writer.writeByte(teleport);
    }

    @Override
    protected void _decode(BinaryReader reader) throws IOException {
        reader.readByte(); //PID
        entityId = reader.readInt();
        x = reader.readFloat();
        y = reader.readFloat();
        z = reader.readFloat();
        yaw = reader.readFloat();
        pitch = reader.readFloat();
        bodyYaw = reader.readFloat();
        teleport = reader.readByte();
    }

    @Override
    protected int getLength() {
        return 30;
    }
}
