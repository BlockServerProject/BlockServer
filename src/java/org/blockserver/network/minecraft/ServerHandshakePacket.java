package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;

import org.blockserver.utility.Utils;

public class ServerHandshakePacket extends BaseDataPacket{
	public int port;
	public long sessionID;
	
	public ServerHandshakePacket(int port, long session){
		this.port = port;
		this.sessionID = session;
	}

	@Override
	public void encode(){
		bb= ByteBuffer.allocate(96);
		bb.put(SERVER_HANDSHAKE);
		bb.put(new byte[]{
				0x04, 0x3f, 0x57, (byte) 0xfe
				}); //Cookie
		bb.put((byte) 0xcd); //Security flags
		bb.putShort((short) port);
		putDataArray();
		bb.put(new byte[]{
				0x00, 0x00});
		bb.putLong(sessionID);
		bb.put(new byte[]{
				0x00, 0x00, 0x00, 0x00, 0x04, 0x44, 0x0b, (byte) 0xa9
				});
	}

	private void putDataArray(){
		byte[] unknown1 = new byte[]{
				(byte) 0xf5, (byte) 0xff, (byte) 0xff, (byte) 0xf5
		};
		byte[] unknown2 = new byte[]{
				(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff
		};

		bb.put(Utils.putTriad(unknown1.length));
		bb.put(unknown1);

		for (int i = 0; i < 9; i++){
			bb.put(Utils.putTriad(unknown2.length));
			bb.put(unknown2);
		}
	}

	@Override
	public void decode(){}
}
