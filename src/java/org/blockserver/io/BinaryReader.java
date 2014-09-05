package org.blockserver.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Locale;

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
		falloc(2);
		return readString(2);
	}
	public String readString(int lenLen) throws IOException{
		falloc(lenLen);
		int length = (int) readNat(lenLen);
		falloc(length);
		return new String(read(length));
	}
	public byte readByte() throws IOException{
		falloc(1);
		return (byte) is.read();
	}
	public short readShort() throws IOException{
		falloc(2);
		return (short) readNat(2);
	}
	public int readTriad() throws IOException{
		falloc(3);
		return (int) readNat(3);
	}
	public int readInt() throws IOException{
		falloc(4);
		return (int) readNat(4);
	}
	public long readLong() throws IOException{
		falloc(8);
		return readNat(8);
	}
	public float readFloat() throws IOException{
		falloc(4);
		ByteBuffer bb = ByteBuffer.wrap(read(4));
		return bb.getFloat();
	}
	public double readDouble() throws IOException{
		falloc(8);
		ByteBuffer bb = ByteBuffer.wrap(read(8));
		return bb.getDouble();
	}
	public byte[] read(int length) throws IOException{
		falloc(length);
		byte[] buffer = new byte[length];
		is.read(buffer, 0, length);
		return buffer;
	}
	public long readNat(int length) throws IOException{
		falloc(length);
		return BinaryUtils.read(read(length), 0, length);
	}

	@SuppressWarnings("unchecked")
	public <T> T readType(Class<T> t, List<Object> args) throws IllegalArgumentException, IOException{
		if(t.equals(Byte.class)){
			return (T) (Byte) readByte();
		}
		if(t.equals(Short.class)){
			return (T) (Short) readShort();
		}
		if(t.equals(Integer.class)){
			return (T) (Integer) readInt();
		}
		if(t.equals(Long.class)){
			return (T) (Long) readLong();
		}
		if(t.equals(Float.class)){
			return (T) (Float) readFloat();
		}
		if(t.equals(Double.class)){
			return (T) (Double) readDouble();
		}
		if(t.equals(String.class) || t.equals(CharSequence.class)){
			return (T) (String) readString();
		}
		return handleUnknownType(t, args);
	}
	protected <T> T handleUnknownType(Class<T> t, List<Object> args) throws IllegalArgumentException, IOException{
		throw new IllegalArgumentException(String.format(Locale.US,
				"Unknown type %s", t.getSimpleName()));
	}

	protected void falloc(int l) throws IOException{
		int lack = l - is.available();
		if(lack > 0){
			throw getUEOFException(lack);
		}
	}
	protected IOException getUEOFException(int needed){
		return new IOException(String.format("Unexpected end of file: %d more bytes expected", needed));
	}
}
