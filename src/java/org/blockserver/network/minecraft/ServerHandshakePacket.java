package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;

import org.blockserver.utility.Utils;

public class ServerHandshakePacket implements BaseDataPacket {
	private ByteBuffer buffer;
	
	public static byte PID = PacketsID.SERVER_HANDSHAKE;
	public int port;
	public long sessionID;
	
	public ServerHandshakePacket(int port, long session){
		this.port = port;
		this.sessionID = session;
	}
	
	public void encode(){
		buffer = ByteBuffer.allocate(96);
		buffer.put(PID);
		buffer.put(new byte[] { 0x04, 0x3f, 0x57, (byte) 0xfe }); //Cookie
		buffer.put((byte) 0xcd); //Security flags
		buffer.putShort((short) this.port);
		this.putDataArray();
		buffer.put(new byte[] { 0x00, 0x00 });
		buffer.putLong(sessionID);
		buffer.put(new byte[] { 0x00, 0x00, 0x00, 0x00, 0x04, 0x44, 0x0b, (byte) 0xa9 });
	}
	
	private void putDataArray() {
        byte[] unknown1 = new byte[] { (byte) 0xf5, (byte) 0xff, (byte) 0xff, (byte) 0xf5 };
        byte[] unknown2 = new byte[] { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff };

        buffer.put(Utils.putTriad(unknown1.length));
        buffer.put(unknown1);

        for (int i = 0; i < 9; i++){
        	buffer.put(Utils.putTriad(unknown2.length));
        	buffer.put(unknown2);
        }
	}

	public void decode(){}
	
	public ByteBuffer getBuffer(){
		return this.buffer;
	}

}
