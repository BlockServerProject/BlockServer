package blockserver.networking.packet;

import java.net.DatagramPacket;

public interface BasePacket {
	
	String getIP();
	
	int getPort();
	
	byte getPacketID();
	
	byte[] getBuffer();

}
