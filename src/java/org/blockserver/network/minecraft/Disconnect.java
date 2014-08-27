package org.blockserver.network.minecraft;


import java.nio.ByteBuffer;

public class Disconnect implements BaseDataPacket {

    private ByteBuffer bb;

    public Disconnect()
    {
        bb = ByteBuffer.allocate(1);
    }

    public Disconnect(byte[] buffer)
    {
        bb = ByteBuffer.wrap(buffer);
    }

    public void encode()
    {
        bb.put(PacketsID.DISCONNECT);
    }

    public void decode()
    {
        if(bb.get() != PacketsID.DISCONNECT)
        {
            throw new RuntimeException(String.format("Trying to decode Disconnect packet and received: %02X", bb.array()[0]));
        }
    }

    public ByteBuffer getBuffer() {
        return this.bb;
    }
}
