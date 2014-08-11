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
        this.protocol = this.bb.getInt();//12
        this.protocol2 = this.bb.getInt();//16
        this.clientID = this.bb.getInt();//20
        this.loginData = this.getString();//22
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
