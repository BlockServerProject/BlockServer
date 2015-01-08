package org.blockserver.net.protocol.pe.login;

import java.nio.ByteBuffer;

public interface EncapsulatedLoginPacket {
	
	void encode();
	void decode();
	
	ByteBuffer getBuffer();

}
