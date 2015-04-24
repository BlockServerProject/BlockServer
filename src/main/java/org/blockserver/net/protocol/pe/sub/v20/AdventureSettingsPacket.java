package org.blockserver.net.protocol.pe.sub.v20;

import org.blockserver.io.BinaryWriter;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;

import java.io.IOException;

/**
 * MC_ADVENTURE_SETTINGS_PACKET (0x
 */
public class AdventureSettingsPacket extends PeDataPacket{
    public byte[] flags = new byte[] {0x20};

    public AdventureSettingsPacket(byte[] buffer) {
        super(buffer);
    }

    public AdventureSettingsPacket(){
        super(new byte[] {MC_ADVENTURE_SETTINGS});
    }

    @Override
    protected void _encode(BinaryWriter writer) throws IOException {
        writer.writeByte(MC_ADVENTURE_SETTINGS);
        writer.write(flags);
    }

    @Override
    protected int getLength() {
        return 1 + flags.length;
    }
}
