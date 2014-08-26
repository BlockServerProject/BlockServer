package org.blockserver.io.nbt;

import java.io.IOException;

public class IntArrayTag extends NamedTag{
	private int[] v;
	public int[] getValue(){
		return v;
	}
	public void setValue(int[] v){
		this.v = v;
	}

	@Override
	public void write(NBTWriter writer) throws IOException{
		writer.writeInt(v.length);
		for(int i: v){
			writer.writeInt(i);
		}
	}
	@Override
	public void read(NBTReader reader) throws IOException{
		int len = reader.readInt();
		v = new int[len];
		for(int i = 0; i < len; i++){
			v[i] = reader.readInt();
		}
	}
}
