package org.blockserver.io.nbt;

import java.io.IOException;

public class DoubleTag extends NamedTag{
	private double v = 0;
	public void setValue(double v){
		this.v = v;
	}
	public double getValue(){
		return v;
	}

	@Override
	public void write(NBTWriter writer) throws IOException{
		writer.writeDouble(v);
	}
	@Override
	public void read(NBTReader reader) throws IOException{
		v = reader.readDouble();
	}
}
