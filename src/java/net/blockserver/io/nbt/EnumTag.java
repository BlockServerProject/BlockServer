package net.blockserver.io.nbt;

import java.util.*; // TODO, Mr. Eclipse, ping.

public class EnumTag extends NamedTag implementa Iterable{
	private List<? extends Tag> tags = new ArrayList<? extends Tag>();
	pricate TagType type = null;
	public Enum(){}
	public void write(NBTWriter writer){
		if(type instanceof TagType){
			writer.writeByte(type.getID());
		}
		else if(tags.size() > 0){
			writer.writeByte(((Tag[]) tags.toArray())[0].
		}
		else{
			writer.writeByte(0);
		}
		writer.writeInt(tags.size());
		for(Tag tag: tags){
			tag.write(writer);
		}
	}
	public void read(NBTReader reader){
		type = TagType.getType(reader.readByte());
		for(int i = 0; i < reader.getInt(); i++){
			Tag tag = type.newInstance();
			tag.read(reader);
			tags.add(tag);
		}
	}
	public Iterator<? extends Tag> iterator(){
		return tags.iterator();
	}
	// TODO redirect methods for EnumTag.tags
}
