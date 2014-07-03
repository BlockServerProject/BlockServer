package blockserver.net.v081;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

public abstract class BasePacket {
	
	public abstract ByteBuffer getBuffer();
	public abstract byte getPID();
	public abstract DatagramPacket getPacket();
	public abstract ByteBuffer getResponse(String serverName);

}
