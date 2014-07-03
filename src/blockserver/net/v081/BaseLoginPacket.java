package blockserver.net.v081;

import java.nio.ByteBuffer;

import blockserver.Utils;

public abstract class BaseLoginPacket extends BasePacket {
	
	public static byte[] getMAGIC(){
		return Utils.hexStringToByteArray("0x00ffff00fefefefefdfdfdfd12345678");
	}
	
	public abstract ByteBuffer getResponse(String serverName);

}
