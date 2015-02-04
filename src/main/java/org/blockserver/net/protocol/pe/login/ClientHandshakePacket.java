package org.blockserver.net.protocol.pe.login;

import java.nio.ByteBuffer;

import org.blockserver.net.protocol.pe.PeProtocolConst;
import org.blockserver.utils.Utils;

/**
 * An implementation of a ClientHandshakePacket(0x13).
 * @author jython234
 *
 */
public class ClientHandshakePacket implements PeProtocolConst, EncapsulatedLoginPacket {
	private ByteBuffer bb;
	
	/**
	 * Constructor for ClientHandshakePacket
	 * @param bb The ByteBuffer containing raw packet data.
	 */
	public ClientHandshakePacket(ByteBuffer bb){
		this.bb = bb;
	}
	
	@Override
	public void encode(){ }
	
	@Override
	public void decode(){
		bb.getInt(); //Cookie
		bb.getShort(); //Server Port
		dataArray1();
		dataArray2();
		bb.getShort(); //Timestamp
		bb.getLong(); //Session 1
		bb.getLong(); //Session 2
	}
	
	@Override
	public ByteBuffer getBuffer(){
		return bb;
	}
	
	private void dataArray1(){
		bb.get(new byte[(int) bb.get()]);
	}
	
	private void dataArray2(){
		for(byte b: new byte[9]){ //Repeat nine times
			int l = Utils.readLTriad(bb);
			bb.get(new byte[l]);
		}
	}

}
