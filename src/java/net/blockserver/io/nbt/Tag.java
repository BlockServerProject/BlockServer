package net.blockserver.io.nbt;

import java.io.IOException;

public abstract class Tag{
	public abstract void write(NBTWriter writer) throws IOException;
	public abstract void read(NBTReader reader) throws IOException;
}
