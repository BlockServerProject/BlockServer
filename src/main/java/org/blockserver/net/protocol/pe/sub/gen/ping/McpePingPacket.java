package org.blockserver.net.protocol.pe.sub.gen.ping;

import org.blockserver.io.BinaryReader;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;

import java.io.IOException;

/**
 * Represents a Ping Packet(0x00).
 */
public class McpePingPacket extends PeDataPacket{

    public long pingID;

    public McpePingPacket(byte[] buffer){
        super(buffer);
    }

    @Override
    protected void _decode(BinaryReader reader) throws IOException {
        reader.readByte(); //PID
        pingID = reader.readLong();
    }

    @Override
    public int getLength(){
        return 9;
    }
}
