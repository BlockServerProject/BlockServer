package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;

public abstract class BaseDataPacket implements PacketIDs{
	protected ByteBuffer bb;

	public abstract void encode();
	public abstract void decode();
	public byte[] getBuffer(){
		return bb.array();
	}
}
