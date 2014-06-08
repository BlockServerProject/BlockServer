package blockserver.networking.packets.data;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

import blockserver.core.BlockServerThread;
import blockserver.core.Player;

public class CustomPacket {
	private DatagramPacket rawPacket;
	private Player player;
	private BlockServerThread server;
	
	protected ByteBuffer buffer;
	protected byte CPID;
	protected byte[] count;
	protected ByteBuffer payload;
	
	public CustomPacket(DatagramPacket pkt, Player player, BlockServerThread server){
		this.rawPacket = pkt;
		this.player = player;
		this.server = server;
		this.buffer = ByteBuffer.wrap(pkt.getData());
		this.CPID = buffer.get();
		this.count = buffer.get(new byte[3]).array();
		
		this.payload = buffer.get(new byte[pkt.getLength() - 4]);
		
	}

	protected InetAddress getAddress() {
		return rawPacket.getAddress();
	}

	protected int getPort() {
		return rawPacket.getPort();
	}
	
	protected DatagramPacket getRawPacket(){
		return rawPacket;
	}
	

}
