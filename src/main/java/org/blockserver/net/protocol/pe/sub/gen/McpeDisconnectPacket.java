package org.blockserver.net.protocol.pe.sub.gen;

import org.blockserver.io.BinaryReader;
import org.blockserver.io.BinaryWriter;
import org.blockserver.net.protocol.pe.sub.PeDataPacket;

import java.io.IOException;

/**
 * An Implemenation of a MCPE Disconnect Packet.
 * Created by jython234 on 1/13/2015.
 */
public class McpeDisconnectPacket extends PeDataPacket{

    /**
     * The reason for the disconnect.
     */
    public String reason;

    /**
     * Constructor for DisconnectPacket(0x15).
     * @param reason The reason for disconnect. Currently not supported.
     */
    public McpeDisconnectPacket(String reason){
        super(new byte[] { MC_DISCONNECT });
        this.reason = reason;
    }

    public McpeDisconnectPacket(byte[] buffer){
        super(new byte[] {buffer[0]});
    }

    @Override
    protected void _encode(BinaryWriter writer) throws IOException {
        writer.writeByte(MC_DISCONNECT);
    }

    @Override
    protected void _decode(BinaryReader reader) throws IOException{
        reader.readByte(); //PID
    }

    @Override
    public int getLength(){
        return 1;
    }

}
