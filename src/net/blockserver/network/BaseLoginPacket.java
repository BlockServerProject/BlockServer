package net.blockserver.network;

import net.blockserver.utility.Utils;

import java.nio.ByteBuffer;

public abstract class BaseLoginPacket extends BasePacket {
	
	public static byte[] getMAGIC(){
		return Utils.hexStringToByteArray("0x00ffff00fefefefefdfdfdfd12345678");
	}
	
	public abstract ByteBuffer getResponse(String serverName);

}
