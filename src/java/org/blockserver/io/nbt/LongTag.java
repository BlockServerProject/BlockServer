package org.blockserver.io.nbt;

import java.io.IOException;

public class LongTag extends NamedTag{
	private long v = 0;
	public void setValue(long v){
		this.v = v;
	}
	public long getValue(){
		return v;
	}

	@Override
	public void write(NBTWriter writer) throws IOException{
		writer.writeLong(v);
	}
	@Override
	public void read(NBTReader reader) throws IOException{
		v = reader.readLong();
	}
}
