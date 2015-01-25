package org.blockserver.net.protocol.pe.sub.gen;

import org.blockserver.io.BinaryWriter;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;

import java.io.IOException;

public class McpeSetDifficultyPacket extends PeDataPacket{

    public int difficulty;

    public McpeSetDifficultyPacket(int difficulty){
        super(new byte[] {MC_SET_DIFFICULTY_PACKET});
        this.difficulty = difficulty;
    }

    @Override
    protected void _encode(BinaryWriter writer) throws IOException {
        writer.writeByte(MC_SET_DIFFICULTY_PACKET);
        writer.writeInt(difficulty);
    }

    public int getLength(){
        return 5;
    }
}
