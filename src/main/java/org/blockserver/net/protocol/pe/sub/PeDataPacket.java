package org.blockserver.net.protocol.pe.sub;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.blockserver.io.BinaryReader;
import org.blockserver.io.BinaryUtils;
import org.blockserver.io.BinaryWriter;
import org.blockserver.net.protocol.pe.PeProtocolConst;
import org.blockserver.net.protocol.pe.raknet.NetworkChannel;

public abstract class PeDataPacket implements PeProtocolConst{
	private byte pid;
	private NetworkChannel channel = NetworkChannel.CHANNEL_NONE;
	public final void decode(byte[] buffer){
		pid = buffer[0];
		try{
			_decode(new BinaryReader(new ByteArrayInputStream(buffer), BinaryUtils.BIG_ENDIAN));
		}catch(IOException e){
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
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	protected void _encode(BinaryWriter writer) throws IOException{
		throw new RuntimeException(getClass().getSimpleName() + " cannot be encoded");
	}
	protected abstract int getLength();
	public byte getPid(){
		return pid;
	}

	public NetworkChannel getChannel() {
		return channel;
	}

	public void setChannel(NetworkChannel channel) {
		this.channel = channel;
	}
}
