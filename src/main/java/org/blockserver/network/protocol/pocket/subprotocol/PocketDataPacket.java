package org.blockserver.network.protocol.pocket.subprotocol;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.blockserver.io.BinaryReader;
import org.blockserver.io.BinaryWriter;
import org.blockserver.network.protocol.pocket.PocketProtocolConstants;

public abstract class PocketDataPacket implements PocketProtocolConstants{
	public void decode(BinaryReader reader){
		throw new RuntimeException(getClass().getSimpleName() + " cannot be decoded");
	}
	public final byte[] encode(){
		try{
			BinaryWriter writer = new BinaryWriter(new ByteArrayOutputStream(getLength()));
			_encode(writer);
			ByteArrayOutputStream os = (ByteArrayOutputStream) writer.getOutputStream();
			return os.toByteArray();
		}
		catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	protected void _encode(BinaryWriter writer) throws IOException{
		throw new RuntimeException(getClass().getSimpleName() + " cannot be encoded");
	}
	protected abstract int getLength();
}
