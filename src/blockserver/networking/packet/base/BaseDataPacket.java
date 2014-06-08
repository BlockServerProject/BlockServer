package blockserver.networking.packet.base;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

import blockserver.core.Player;

public abstract class BaseDataPacket {
	
	public abstract Player getClient();
	public abstract InetAddress getAddress();
	public abstract int getPort();
	public abstract DatagramPacket getPacket();
	public abstract ByteBuffer getPacketData();
	
	protected abstract byte[] decodePacket();

}
