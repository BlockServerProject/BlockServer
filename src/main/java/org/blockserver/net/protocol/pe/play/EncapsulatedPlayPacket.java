package org.blockserver.net.protocol.pe.play;

import java.nio.ByteBuffer;

public interface EncapsulatedPlayPacket {
	
	void encode();
	void decode();
	
	ByteBuffer getBuffer();

}
