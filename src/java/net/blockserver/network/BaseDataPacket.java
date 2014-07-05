package net.blockserver.network;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

public abstract class BaseDataPacket{
	
	public abstract void decode();
	public abstract void encode();
	
}
