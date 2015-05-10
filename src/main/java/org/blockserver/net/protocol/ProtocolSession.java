package org.blockserver.net.protocol;

import java.net.SocketAddress;

import org.blockserver.Server;
import org.blockserver.net.internal.response.InternalResponse;

/**
 * Base for all ProtocolSessions.
 */
public interface ProtocolSession{
	/**
	 * Gives a packet for this session to handle.
	 * @param pk The WrappedPacket.
	 */
	public void handlePacket(WrappedPacket pk);

	/**
	 * Get this ProtocolSession's client address.
	 * @return The client address.
	 */
	public SocketAddress getAddress();

	/**
	 * Send a packet to this ProtocolSession's client.
	 * @param buffer The packet buffer.
	 */
	public void sendPacket(byte[] buffer);

	/**
	 * Send a response to the ProtocolSession's client. <br>
	 *     The ProtocolSession will wrap it into a packet.
	 * @param response The response.
	 */
	public void sendResponse(InternalResponse response);

	/**
	 * Close this session by disconnecting the client.
	 * @param reason Reason for the closure.
	 */
	public void closeSession(String reason);

	/**
	 * Get the server this ProtocolSession is running under.
	 * @return The Server.
	 */
	public Server getServer();
}
