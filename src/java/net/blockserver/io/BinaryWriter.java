package net.blockserver.io;

import java.io.*; // TODO let Eclipse do this

public class BinaryWriter implements Closeable, Flushable{
	protected OutputStream os;
	public BinaryWriter(OutputStream os){
		this.os = os;
	}
	public void flush(){
		os.flush();
	}
	public void close(){
		os.close();
	}
	public void writeString(String string){
		writeString(string, 2);
	}
	public void writeString(String string, int lenLen){
		write(string.length(), lenLen);
		os.write(string.getBytes());
	}
	public void writeByte(byte b){
		os.write((int) b);
	}
	public void writeShort(short s){
		write(s, 2);
	}
	public void writeTriad(int i){
		write(i, 3);
	}
	public void writeInt(int i){
		write(i, 4);
	}
	public void writeLong(long l){
		write(i, 8);
	}
	public void write(long x, int len){
		os.write(BinaryUtils.write(x, len));
	}
}
