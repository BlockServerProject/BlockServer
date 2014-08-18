package net.blockserver.network.minecraft;


import net.blockserver.utility.Utils;

import java.nio.ByteBuffer;

public class ClientHandShakePacket implements BaseDataPacket {


    public int cookie;
    public byte security;
    public short port;
    public short timestamp;
    public long session;
    public long session2;

    private ByteBuffer bb;
    public ClientHandShakePacket(byte[] buffer)
    {
        this.bb = ByteBuffer.wrap(buffer);
    }

    public void encode() {

    }

    public void decode() {
        this.bb.position(0);
        if(this.bb.get() != PacketsID.CLIENT_HANDSHAKE)
        {
            throw new RuntimeException(String.format("Trying to decode packet ClientHandShake and received %02X.", bb.array()[0]));
        }

        this.cookie = this.bb.getInt();
        this.security = this.bb.get();
        this.port = this.bb.getShort();
        this.bb.get(new byte[(int)bb.get()]);
        this.getDataArray(9);
        this.timestamp = bb.getShort();
        this.session2 = bb.getLong();
        this.session = bb.getLong();

    }

    public byte[] getDataArray(int len)
    {
        for(int i = 0; i < len; i++)
        {
            int l = Utils.getTriad(bb);
            this.bb.get(new byte[l]);
        }
        return new byte[0];
    }

    public ByteBuffer getBuffer() {
        return this.bb;
    }
}

