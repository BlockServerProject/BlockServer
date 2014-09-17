package org.blockserver.network;

@SuppressWarnings("serial")
public class PacketDecodeException extends RuntimeException {
	public PacketDecodeException() {
		super("An Exception happened while processing a packet.");
	}
	public PacketDecodeException(String message) {
		super(message);
	}
	public PacketDecodeException(byte pid){
		super("An Exception happened while processing a packet with the ID of: "+pid);
	}
}
