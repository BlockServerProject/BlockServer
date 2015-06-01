package org.blockserver.net.protocol.pe.sub.v27;

import org.blockserver.io.BinaryReader;
import org.blockserver.io.BinaryWriter;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;

import java.io.IOException;

/**
 * MC_PLAY_STATUS_PACKET (0x83)
 */
public class PlayStatusPacket extends PeDataPacket{
    public static final int LOGIN_SUCCESS = 0;
    public static final int LOGIN_FAILED_CLIENT = 1;
    public static final int LOGIN_FAILED_SERVER = 2;
    public static final int PLAYER_SPAWN = 3;

    public int status;

    @Override
    protected void _encode(BinaryWriter writer) throws IOException {
        writer.writeByte(MC_LOGIN_STATUS_PACKET); //Same PID as the old packet
        writer.writeInt(status);
    }

    @Override
    protected int getLength() {
        return 5;
    }
}
