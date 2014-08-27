package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;

public interface BaseDataPacket{
	public void encode();
	public void decode();
	public ByteBuffer getBuffer();
}
