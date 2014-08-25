package net.blockserver.io.nbt;

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
	// TODO redirect methods for EnumTag.tags
}
