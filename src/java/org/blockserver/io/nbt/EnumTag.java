package org.blockserver.io.nbt;

import java.io.IOException;
import java.util.*; // TODO, Mr. Eclipse, ping.

public class EnumTag extends NamedTag implements Iterable<Tag>{
	private List<Tag> tags = new ArrayList<Tag>();
	private TagType type = null;

	@Override
	public void write(NBTWriter writer) throws IOException{
		if(type instanceof TagType){
			writer.writeByte((byte) type.getID());
		}
		else if(tags.size() > 0){
			writer.writeByte((byte) TagType.getType(((Tag[]) tags.toArray())[0]).getID());
		}
		else{
			writer.writeByte((byte) 0);
		}
		writer.writeInt(tags.size());
		for(Tag tag: tags){
			tag.write(writer);
		}
	}
	@Override
	public void read(NBTReader reader) throws IOException{
		type = TagType.getType(reader.readByte());
		int len = reader.readInt(); // phew... nearly got it into read-once-a-loop.
		for(int i = 0; i < len; i++){
			Tag tag = type.newInstance();
			tag.read(reader);
			tags.add(tag);
		}
	}

	public Iterator<Tag> iterator(){
		return tags.iterator();
	}
	public Tag get(int offset){
		try{
			return tags.get(offset);
		}
		catch(IndexOutOfBoundsException e){
			return null;
		}
	}
	public boolean isset(int offset){
		return tags.contains(offset);
	}
	public boolean set(int offset, Tag tag){
		if(isset(offset)){
			tags.set(offset, tag);
			return true;
		}
		return false;
	}
	public void add(Tag tag){
		tags.add(tag);
	}
	public void add(int offset, Tag tag){
		tags.add(offset, tag);
	}
	public void setAll(Collection<Tag> all){
		tags.clear();
		tags.addAll(all);
	}
}
