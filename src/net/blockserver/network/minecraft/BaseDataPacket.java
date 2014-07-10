package net.blockserver.network.minecraft;

import java.nio.ByteBuffer;

public interface BaseDataPacket {
	
	void encode();
	void decode();
	
	ByteBuffer getBuffer();

}
