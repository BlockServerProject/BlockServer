package org.blockserver.network;

public class PacketEncodeException extends RuntimeException {

	public PacketEncodeException() {
		super("An Exception happened while encoding a packet.");
	}

	public PacketEncodeException(String message) {
		super(message);
	}
	
	public PacketEncodeException(byte pid){
		super("An Exception happened while encoding a packet with the ID of: "+pid);
	}

}
