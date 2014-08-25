package net.blockserver.io.nbt;

class ByteTag extends NamedTag{
	private byte b = 0;
	public ByteTag(){}
	public void setValue(byte b){
		this.b = b;
	}
	public void write(NBTWriter writer){
		writer.writeByte(b);
	}
	public void read(NBTReader reader){
		writer.readByte(b);
	}
}
