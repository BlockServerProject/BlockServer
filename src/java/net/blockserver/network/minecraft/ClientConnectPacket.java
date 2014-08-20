package net.blockserver.network.minecraft;

import java.nio.ByteBuffer;

public class ClientConnectPacket implements BaseDataPacket{
	private ByteBuffer buffer;

	public long clientID;
	public long session;
	public byte unknown;
	
	public ClientConnectPacket(byte[] data){
		this.buffer = ByteBuffer.wrap(data);
	}
	
	public void decode(){
        if(this.buffer.get() != PacketsID.CLIENT_CONNECT) { return; }

        this.clientID = this.buffer.getLong();
        this.session = this.buffer.getLong();
        this.unknown = this.buffer.get();
    }
	
	public void encode() {}
	
	public ByteBuffer getBuffer(){
		return buffer;
	}

}
