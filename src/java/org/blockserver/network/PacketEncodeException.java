package org.blockserver.network;

public class PacketEncodeException extends RuntimeException{
	private static final long serialVersionUID = -7582017283053003465L;
	public PacketEncodeException() {
		super("An Exception happened while encoding a packet.");
	}
	public PacketEncodeException(String message) {
		super(message);
	}
	public PacketEncodeException(byte pid){
		super(String.format("An Exception happened while encoding a packet with the ID of: %d", pid));
	}
}
