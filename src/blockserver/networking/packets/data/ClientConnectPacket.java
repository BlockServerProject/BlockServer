package blockserver.networking.packets.data;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

import blockserver.core.BlockServerThread;
import blockserver.core.Player;
import blockserver.networking.packet.base.BaseDataPacket;

public class ClientConnectPacket extends BaseDataPacket{
	private CustomPacket rawPacket;
	private Player player;
	private BlockServerThread server;
	
	protected ByteBuffer buffer;
	protected ByteBuffer payload;
	
	public ClientConnectPacket(CustomPacket pkt, Player player, BlockServerThread server){
		this.rawPacket = pkt;
		this.player = player;
		this.server = server;
		this.buffer = pkt.buffer;
		this.payload = pkt.payload;

	}

	@Override
	public Player getClient() {
		// TODO Auto-generated method stub
		return player;
	}

	@Override
	public InetAddress getAddress() {
		// TODO Auto-generated method stub
		return rawPacket.getAddress();
	}

	@Override
	public int getPort() {
		// TODO Auto-generated method stub
		return rawPacket.getPort();
	}

	@Override
	public DatagramPacket getPacket() {
		// TODO Auto-generated method stub
		return rawPacket.getRawPacket();
	}

	@Override
	public ByteBuffer getPacketData() {
		byte[] dataPacket = decodePacket();
		ByteBuffer realPacket = ByteBuffer.wrap(dataPacket);
		return realPacket;
	}

	protected byte[] decodePacket() {
		byte[] data = null;
		byte EID = buffer.get();
		short pktLength = 0;
		byte[] count; //Unknown use
		
		switch(EID){
		case 0x00:
			//DATA
			pktLength = buffer.getShort();
			data = buffer.get(new byte[pktLength]).array();
			break;
		case 0x40:
			//TODO: Work on this
			pktLength = buffer.getShort();
			count = buffer.get(new byte[3]).array();
			data = buffer.get(new byte[pktLength]).array();
			break;
		case 0x60:
			//TODO: Work on this
			pktLength = buffer.getShort();
			count = buffer.get(new byte[3]).array();
			buffer.get(new byte[4]).array(); //Unknown
			data = buffer.get(new byte[pktLength]).array();
			break;
		default:
			server.getLogger().severe("(DataHandler): Recived an unsupported Encapsulation ID: "+EID);
		}
		System.out.println("Length is: " + pktLength);
		return data;
	}

}
