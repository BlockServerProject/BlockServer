package org.blockserver.net.protocol;

import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.blockserver.net.bridge.NetworkBridge;

/**
 * Represents a generic packet with data.
 */
public class WrappedPacket{
	private ByteBuffer bb;
	private SocketAddress addr = null;
	private NetworkBridge bridge;
	public Socket socket = null;
	/**
	 * Create a new WrappedPacket with a buffer, an address, and a network bridge.
	 * @param buffer The Packet Buffer.
	 * @param addr The packet's address.
	 * @param bridge The packet's network bridge.
	 */
	public WrappedPacket(byte[] buffer, SocketAddress addr, NetworkBridge bridge){
		bb = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN);
		this.addr = addr;
		this.bridge = bridge;
	}
	/**
	 * Create a new WrappedPacket with a buffer, a socket, and a network bridge.
	 * @param buffer The Packet Buffer.
	 * @param socket The packet's socket.
	 * @param bridge The packet's network bridge.
	 */
	public WrappedPacket(byte[] buffer, Socket socket, NetworkBridge bridge){
		bb = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN);
		this.socket = socket;
		this.bridge = bridge;
	}
	/**
	 * Get this packet's buffer in the form of a ByteBuffer.
	 * @return The ByteBuffer.
	 */
	public ByteBuffer bb(){
		return bb;
	}
	/**
	 * Get this packet's address.
	 * @return The address.
	 */
	public SocketAddress getAddress(){
		return addr;
	}
	/**
	 * Get this packet's network bridge.
	 * @return The NetworkBridge.
	 */
	public NetworkBridge getBridge(){
		return bridge;
	}
}
