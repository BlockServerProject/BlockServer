package org.blockserver.network.raknet;

import org.blockserver.network.BasePacket;

import java.nio.ByteBuffer;

public abstract class BaseLoginPacket extends BasePacket {
	
	public byte[] getMAGIC(){
		return new byte[] { (byte)0x00, (byte)0xff, (byte)0xff, (byte)0x00, (byte)0xfe,
                            (byte)0xfe, (byte)0xfe, (byte)0xfe, (byte)0xfd, (byte)0xfd,
                            (byte)0xfd, (byte)0xfd, (byte)0x12, (byte)0x34, (byte)0x56, (byte)0x78 };
	}
	
	public abstract ByteBuffer getResponse();

}
