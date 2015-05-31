package org.blockserver.net.protocol.pe.sub.login;

import org.blockserver.io.BinaryWriter;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;
import org.blockserver.utils.Utils;

import java.io.IOException;

/**
 * MC_SERVER_HANDSHAKE (0x10)
 */
public class ServerHandshake extends PeDataPacket{
    public short serverPort;
    public long sessionID;

    @Override
    protected void _encode(BinaryWriter writer) throws IOException {
        writer.writeByte(MC_SERVER_HANDSHAKE);
        writer.write(new byte[] {0x04, 0x3f, 0x57, (byte) 0xfe});
        writer.writeByte((byte) 0xcd);
        writer.writeShort(serverPort);
        dataArray1(writer);
        writer.write(new byte[] {0x00, 0x00});
        writer.writeLong(sessionID);
        writer.write(new byte[] {0x00, 0x00, 0x00, 0x00, 0x04, 0x44, 0x0b, (byte) 0xa9});
    }

    private void dataArray1(BinaryWriter writer) throws IOException {
        byte[] unknown1 = new byte[] {(byte) 0xf5, (byte) 0xff, (byte) 0xff, (byte) 0xf5};
        byte[] unknown2 = new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff};
        writer.write(Utils.writeLTriad(unknown1.length));
        writer.write(unknown1);
        for (int i = 0; i < 9; i++)
        {
            writer.write(Utils.writeLTriad(unknown2.length));
            writer.write(unknown2);
        }
    }

    @Override
    protected int getLength() {
        return 96;
    }
}
