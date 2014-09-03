package org.blockserver.io.nbt;

import java.io.IOException;

public class ShortTag extends NamedTag{
	private short v = 0;
	public void setValue(short v){
		this.v = v;
	}
	public short getValue(){
		return v;
	}

	@Override
	public void write(NBTWriter writer) throws IOException{
		writer.writeShort(v);
	}
	@Override
	public void read(NBTReader reader) throws IOException{
		v = reader.readShort();
	}
}
