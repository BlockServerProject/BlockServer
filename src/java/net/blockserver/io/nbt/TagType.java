package net.blockserver.io.nbt;

public enum TagType{ // /me silently curses java.lang.*
	END(0, EndTag.class){
		public boolean isNamed(){
			return false;
		}
	},
	BYTE(1, ByteTag.class),
	SHORT(2, ShortTag.class),
	INT(3, IntTag.class),
	LONG(4, LongTag.class),
	FLOAT(5, FloatTag.class),
	DOUBLE(6, DoubleTag.class),
	BYTE_ARRAY(7, ByteArrayTag.class),
	STRING(8, StringTag.class),
	ENUM(9, EnumTag.class),
	COMPOUND(10, CompoundTag.class),
	INT_ARRAY(11, IntArrayTag.class);
	private int id;
	private Class<? extends Tag> c;
	private TagType(int id, Class<? extends Tag> c){
		this.id = id;
		this.c = c;
	}
	public int getID(){
		return id;
	}
	public Class<? extends Tag> getC(){
		return c;
	}
	public Tag newInstance(){
		return newInstance(null);
	}
	public Tag newInstance(String name){
		Tag tag = null;
		try{
			tag = c.newInstance();
			if(tag instanceof NamedTag){
				((NamedTag) tag).setName(name);
			}
		}
		catch(InstantiationException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(IllegalAccessException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // how dare you throw an InstantiateException!
		return tag;
	}
	public boolean isNamed(){
		return true;
	}
	public static TagType getType(int id){
		return values()[id];
	}
	public static TagType getType(Tag tag){
		for(TagType type: values()){
			if(type.getC().getName().equals(tag.getClass().getName())){
				return type;
			}
		}
		throw new RuntimeException(String.format("Unknown tag type: %s", tag.getClass().getName()));
	}
}
