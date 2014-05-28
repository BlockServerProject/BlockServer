package blockserver.networking.packet.base;

public interface BasePacket {
	
	String getIP();
	
	int getPort();
	
	byte getPacketID();
	
	byte[] getBuffer();

}
