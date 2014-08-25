package net.blockserber.io.nbt;

import java.util.HashMap;
import java.util.Map;

public class CompoundTag extends NamedTag{
	private Map<String, ? extends Tag> tags = new HashMap<String, ? extends Tag>(0);
	public CompoundTag(){}
	public void write(NBTWriter writer){
	}
	public void read(NBTReader reader){
		Tag tag;
		while((tag = reader.readTag()) instanceof NamedTag){
			tags.put(tag.getName(), tag);
		}
	}
	public void write(NBTWriter writer){
		for(Tag tag: tags.values()){
			writer.writeTag(tag);
		}
		writer.write(new EndTag());
	}
}
