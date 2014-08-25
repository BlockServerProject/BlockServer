package net.blockserver.io.nbt;

import java.io.IOException;

public class ByteArrayTag extends NamedTag{
	private byte[] v = new byte[0];
	public byte[] getValue(){
		return v;
	}
	public void setValue(byte[] v){
		this.v = v;
	}
	public void write(NBTWriter writer) throws IOException{
		writer.writeInt(v.length);
		writer.writeBytes(v);
	}
	public void read(NBTReader reader) throws IOException{
		int len = reader.readInt();
		v = new byte[len];
		for(int i = 0; i < len; i++){
			v[i] = reader.readByte();
		}
	}
}
