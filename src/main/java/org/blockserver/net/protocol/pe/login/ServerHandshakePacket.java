package org.blockserver.net.protocol.pe.login;

import java.nio.ByteBuffer;

import org.blockserver.net.protocol.pe.PeProtocolConst;
import org.blockserver.utils.Utils;

/**
 * An implemenation of a ServerHandshakePacket(0x10).
 * @author jython234
 *
 */
public class ServerHandshakePacket implements PeProtocolConst, EncapsulatedLoginPacket {
	private ByteBuffer bb;
	private byte[] cookie = new byte[] {0x04, 0x3f, 0x56, (byte) 0xfe};
	private byte securityFlags = (byte) 0xcd;
	private byte[] unknown1 = new byte[] {0x00, 0x00};
	private byte[] unknown2 = new byte[] {0x00, 0x00, 0x00, 0x00, 0x04, 0x44, 0x0b, (byte) 0xa9};
	
	private long session;
	private short serverPort;
	
	/**
	 * Constructor for ServerHandshakePacket
	 * @param session The session long recieved in ClientConnect(0x09).
	 * @param serverPort The server's port.
	 */
	public ServerHandshakePacket(long session, short serverPort){
		bb = ByteBuffer.allocate(96);
		this.session = session;
		this.serverPort = serverPort;
	}
	
	@Override
	public void encode(){
		bb.put(MC_SERVER_HANDSHAKE);
		bb.put(cookie);
		bb.put(securityFlags);
		bb.putShort(serverPort);
		dataArray();
		bb.put(unknown1);
		bb.putLong(session);
		bb.put(unknown2);
	}
	
	@Override
	public void decode() { }
	
	@Override
	public ByteBuffer getBuffer(){
		return bb;
	}
	
	private void dataArray(){
		byte[] unknown1 = new byte[] { (byte) 0xf5, (byte) 0xff, (byte) 0xff, (byte) 0xf5 };
        byte[] unknown2 = new byte[] { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff };
        Utils.writeLTriad(unknown1.length, bb);
       	bb.put(unknown1);
        for (int i = 0; i < 9; i++)
        {
            Utils.writeLTriad(unknown2.length, bb);
            bb.put(unknown2);
        }
	}

}
