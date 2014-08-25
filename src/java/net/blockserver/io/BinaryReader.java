package net.blockserver.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class BinaryReader implements Closeable{
	protected InputStream is;
	public BinaryReader(InputStream is){
		this.is = is;
	}
	@Override
	public void close() throws IOException{
		is.close();
	}
	public String readString() throws IOException{
		return readString(2);
	}
	public String readString(int lenLen) throws IOException{
		int length = (int) readNat(lenLen);
		return new String(read(length));
	}
	public byte readByte() throws IOException{
		return (byte) is.read();
	}
	public short readShort() throws IOException{
		return (short) readNat(2);
	}
	public int readTriad() throws IOException{
		return (int) readNat(3);
	}
	public int readInt() throws IOException{
		return (int) readNat(4);
	}
	public long readLong() throws IOException{
		return readNat(8);
	}
	public float readFloat() throws IOException{
		ByteBuffer bb = ByteBuffer.wrap(read(4));
		return bb.getFloat();
	}
	public double readDouble() throws IOException{
		ByteBuffer bb = ByteBuffer.wrap(read(8));
		return bb.getDouble();
	}
	public byte[] read(int length) throws IOException{
		byte[] buffer = new byte[length];
		is.read(buffer, 0, length);
		return buffer;
	}
	public long readNat(int length) throws IOException{
		return BinaryUtils.read(read(length), 0, length);
	}
}
