package org.blockserver.io.nbt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CompoundTag extends NamedTag implements Iterable<Tag>{
	private Map<String, Tag> tags = new HashMap<String, Tag>(0);
	@Override
	public void write(NBTWriter writer) throws IOException{
		for(Tag tag: tags.values()){
			writer.writeTag(tag);
		}
		writer.writeTag(new EndTag());
	}
	@Override
	public void read(NBTReader reader) throws IOException{
		Tag tag;
		while((tag = reader.readTag()) instanceof NamedTag){
			tags.put(((NamedTag) tag).getName(), (NamedTag) tag);
		}
	}

	public boolean isset(String name){
		return tags.containsKey(name);
	}
	public void set(String name, Tag tag){
		tags.put(name, tag);
	}
	public Tag get(String name){
		return tags.get(name);
	}
	public void unset(String name){
		tags.remove(name);
	}
	public Iterator<Tag> iterator(){
		return tags.values().iterator();
	}
}
