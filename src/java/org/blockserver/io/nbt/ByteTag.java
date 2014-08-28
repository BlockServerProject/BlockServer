package org.blockserver.io.nbt;

import java.io.IOException;

public class ByteTag extends NamedTag{
	private byte b = 0;
	public ByteTag(){}
	public void setValue(byte b){
		this.b = b;
	}

	@Override
	public void write(NBTWriter writer) throws IOException{
		writer.writeByte(b);
	}
	@Override
	public void read(NBTReader reader) throws IOException{
		b = reader.readByte();
	}
}
