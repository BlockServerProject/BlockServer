package blockserver.net;

import java.net.DatagramPacket;

public abstract class BasePacketHandler {
	
	public abstract void handlePacket(DatagramPacket packet);

}
