package net.blockserver.network.minecraft;


import java.nio.ByteBuffer;

public class LoginPacket implements BaseDataPacket{

    public String username;
    public int protocol;
    public int protocol2;
    public int clientID;
    public String loginData;

    private ByteBuffer bb;
    public LoginPacket(byte[] buffer)
    {
        bb = ByteBuffer.wrap(buffer);
    }


    public void encode() {

    }

    public void decode() {
        if(this.bb.get() != PacketsID.LOGIN)
        {
            throw new RuntimeException(String.format("Trying to decode packet LoginPacket and received %02X.", this.bb.array()[0]));
        }


        this.username = this.getString();
        this.protocol = this.bb.getInt();
        this.protocol2 = this.bb.getInt();
        this.clientID = this.bb.getInt();
        this.loginData = this.getString();
    }

    public String getString()
    {
        byte[] text = new byte[this.bb.getShort()];
        this.bb.get(text);
        return new String(text);
    }

    public ByteBuffer getBuffer() {
        return this.bb;
    }
}
