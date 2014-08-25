package net.blockserver.io.nbt;

public abstract class Tag{
	public abstract void write(NBTWriter writer);
	public abstract void read(NBTReader reader);
}
