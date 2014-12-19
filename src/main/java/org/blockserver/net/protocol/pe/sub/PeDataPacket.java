package org.blockserver.net.protocol.pe.sub;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.blockserver.io.BinaryReader;
import org.blockserver.io.BinaryUtils;
import org.blockserver.io.BinaryWriter;
import org.blockserver.net.protocol.pe.PeProtocolConst;

public abstract class PeDataPacket implements PeProtocolConst{
	public abstract byte getPid();
	public final void decode(byte[] buffer){
		try{
			_decode(new BinaryReader(new ByteArrayInputStream(buffer), BinaryUtils.LITTLE_ENDIAN));
		}
		catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	protected void _decode(BinaryReader reader) throws IOException{
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
