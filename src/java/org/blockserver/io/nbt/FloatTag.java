package org.blockserver.io.nbt;

import java.io.IOException;

public class FloatTag extends NamedTag{
	private float v = 0;
	public void setValue(float v){
		this.v = v;
	}
	public float getValue(){
		return v;
	}

	@Override
	public void write(NBTWriter writer) throws IOException{
		writer.writeFloat(v);
	}
	@Override
	public void read(NBTReader reader) throws IOException{
		v = reader.readFloat();
	}
}
