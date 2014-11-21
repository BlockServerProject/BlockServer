package org.blockserver.network.protocol.pocket.subprotocol;

import java.io.ByteArrayInputStream;

import org.blockserver.io.BinaryReader;
import org.blockserver.utils.ByteBufferWriter;

public abstract class PocketDataPacket{
	protected ByteBufferWriter bw;
	protected BinaryReader br;
	public final void decode(byte[] array){
		br = new BinaryReader(new ByteArrayInputStream(array));
		decode();
	}
	protected void decode(){
		throw new UnsupportedOperationException(getClass().getSimpleName() + " cannot be decoded!");
	}
	public final byte[] encode(){
		_encode();
		return bw.getBuffer();
	}
	protected void _encode(){
		throw new UnsupportedOperationException(getClass().getSimpleName() + " cannot be encoded!");
	}
}
