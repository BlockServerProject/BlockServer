package org.blockserver.io.nbt;

import java.io.IOException;
import java.io.InputStream;

import org.blockserver.io.BinaryReader;

public class NBTReader extends BinaryReader{
	public NBTReader(InputStream is){
		super(is);
	}
	public Tag readTag() throws IOException{
		TagType type = TagType.getType(readByte());
		Tag tag = type.newInstance(type.isNamed() ? readString():null);
		tag.read(this);
		return tag;
	}
}
