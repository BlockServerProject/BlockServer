package blockserver.core;
import java.io.IOException;
import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.util.Arrays;

import blockserver.networking.packet.base.BaseDataPacket;
import blockserver.networking.packet.base.BasePacket;
import blockserver.networking.packets.data.ClientConnectPacket;
import blockserver.networking.packets.data.CustomPacket;

public class DataHandler{
	private BlockServerThread server;
	private DatagramPacket rawPacket;
	private Player player;
	
	
	public DataHandler(DatagramPacket packet, BlockServerThread server, Player player){
		this.server = server;
		this.rawPacket = packet;
		this.player = player;
		
	}
	
	private void splitPacket(){
		ByteBuffer b = ByteBuffer.wrap(rawPacket.getData());
		b.get(); //PID
		byte[] count = new byte[3];
		b.get(count); //Send ACK
		sendACK(count);
		
		int length = rawPacket.getLength() -4;
		byte[] data = new byte[length];
		
		for(int pos = 4;pos < length; pos++){
			data[pos] = rawPacket.getData()[pos];
		}
		System.out.println(Arrays.toString(data));
		
		
	}
	
	private void sendACK(byte[] count) {
		// TODO Auto-generated method stub
		ByteBuffer response = ByteBuffer.allocate(7);
		response.put((byte) 0xc0); //PID for ACK
		response.putShort((short) 1); //One packet received
		response.put((byte) 1); 
		response.put((byte)0x01);
		response.put(count);
		
		DatagramPacket r = new DatagramPacket(response.array(), response.capacity());
		r.setAddress(rawPacket.getAddress());
		r.setPort(rawPacket.getPort());
		try {
			server.socket.send(r);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public boolean handleData(){
		byte[] data = rawPacket.getData();
		ByteBuffer bb = ByteBuffer.wrap(data);
		byte pid = bb.get();
		//splitPacket();
		
		BaseDataPacket packet;
		CustomPacket customPacket = new CustomPacket(rawPacket, player, server);
		switch(pid){
		case (byte) 0x84:
			//Apears to be ReadyPacket (0x84)
			//TODO: Working on this
			packet = new ClientConnectPacket(customPacket, player, server);
			ByteBuffer payload = packet.getPacketData();
			System.out.println(Arrays.toString(payload.array()));
			break;
			
		default:
			server.getLogger().warning("Unimplemented packet recived: "+pid);
			break;
			
		}
		return true; //Placeholder
		
	}
	

}
