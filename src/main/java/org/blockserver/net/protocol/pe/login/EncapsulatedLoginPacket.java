package org.blockserver.net.protocol.pe.login;

import java.nio.ByteBuffer;

/**
 * An interface to set the methods an implemenation of an Encapsulated Login Packet must have.
 */
public interface EncapsulatedLoginPacket{
	
	/**
	 * Encodes the packet by using all the values given, and putting them into packet form.
	 */
	void encode();
	/**
	 * Decodes the packet by setting all the packet values using the ByteBuffer given. Please note that the implementation may ignore unneeded, or unused fields.
	 */
	void decode();
	/**
	 * Gets the ByteBuffer used in the encoding/decoding process.
	 * @return The ByteBuffer.
	 */
	ByteBuffer getBuffer();

}
