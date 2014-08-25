package net.blockserver.io;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class BinaryWriter implements Flushable, Closeable{
	protected OutputStream os;
	public BinaryWriter(OutputStream os){
		this.os = os;
	}
	public void flush() throws IOException{
		os.flush();
	}
	public void close() throws IOException{
		os.close();
	}
	public void writeString(String string) throws IOException{
		writeString(string, 2);
	}
	public void writeString(String string, int lenLen) throws IOException{
		write(string.length(), lenLen);
		os.write(string.getBytes());
	}
	public void writeByte(byte b) throws IOException{
		os.write(b);
	}
	public void writeShort(short s) throws IOException{
		write(s, 2);
	}
	public void writeTriad(int t) throws IOException{
		write(t, 3);
	}
	public void writeInt(int i) throws IOException{
		write(i, 4);
	}
	public void writeLong(long l) throws IOException{
		write(l, 8);
	}
	public void writeFloat(float f) throws IOException{
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.putFloat(f);
		os.write(bb.array());
	}
	public void writeDouble(double d) throws IOException{
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.putDouble(d);
		os.write(bb.array());
	}
	public void write(long x, int len) throws IOException{
		os.write(BinaryUtils.write(x, len));
	}
	public void writeBytes(byte[] bytes) throws IOException{
		os.write(bytes);
	}
	public void writeNat(int oneByte) throws IOException{
		os.write(oneByte);
	}
}
