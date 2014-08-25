package net.blockserver.io.nbt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CompoundTag extends NamedTag{
	private Map<String, Tag> tags = new HashMap<String, Tag>(0);
	public CompoundTag(){}
	public void write(NBTWriter writer) throws IOException{
		for(Tag tag: tags.values()){
			writer.writeTag(tag);
		}
		writer.writeTag(new EndTag());
	}
	public void read(NBTReader reader) throws IOException{
		Tag tag;
		while((tag = reader.readTag()) instanceof NamedTag){
			tags.put(((NamedTag) tag).getName(), (NamedTag) tag);
		}
	}
}
