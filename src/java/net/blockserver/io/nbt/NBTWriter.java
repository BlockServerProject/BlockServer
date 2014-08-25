package net.blockserver.io.nbt;

import net.blockserver.io.BinaryWriter;

public class NBTWriter extends BinaryWriter{
	public NBTWriter(OutputStream os){
		super(os);
	}
	public void writeTag(Tag tag){
		write(tag.getID());
		if(tag instanceof NamedTag){
			writeString(tag.getName());
		}
		tag.write(this);
	}
}
