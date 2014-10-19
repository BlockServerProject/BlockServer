package org.blockserver.network;

public class PacketDecodeException extends RuntimeException{
	private static final long serialVersionUID = -5231794410367375106L;
	public PacketDecodeException() {
		super("An Exception happened while processing a packet.");
	}
	public PacketDecodeException(String message) {
		super(message);
	}
	public PacketDecodeException(byte pid){
		super(String.format("An Exception happened while processing a packet with the ID of: %d", pid));
	}
}
