package net.blockserver.io.nbt;

import java.io.IOException;

public class IntTag extends NamedTag{
	private int v = 0;
	public void setValue(int v){
		this.v = v;
	}
	public int getValue(){
		return v;
	}
	public void write(NBTWriter writer) throws IOException{
		writer.writeInt(v);
	}
	public void read(NBTReader reader) throws IOException{
		v = reader.readInt();
	}
}
