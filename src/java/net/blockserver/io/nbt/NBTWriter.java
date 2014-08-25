package net.blockserver.io.nbt;

import java.io.IOException;
import java.io.OutputStream;

import net.blockserver.io.BinaryWriter;

public class NBTWriter extends BinaryWriter{
	public NBTWriter(OutputStream os){
		super(os);
	}
	public void writeTag(Tag tag) throws IOException{
		
		if(tag instanceof NamedTag){
			writeString(((NamedTag) tag).getName());
		}
		tag.write(this);
	}
}
