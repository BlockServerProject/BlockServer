package org.blockserver.network.protocol.pocket.subprotocol;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.blockserver.Server;
import org.blockserver.io.BinaryReader;
import org.blockserver.io.BinaryUtils;
import org.blockserver.network.protocol.pocket.raknet.RaknetReceivedCustomPacket.ReceivedEncapsulatedPacket;

public abstract class PocketSubprotocol{
	public abstract Server getServer();
	public abstract int getSubprotocolVersionId();
	public abstract String getSubprotocolName();
	public void readDataPacket(ReceivedEncapsulatedPacket pk){
		try{
			@SuppressWarnings("resource")
			BinaryReader reader = new BinaryReader(new ByteArrayInputStream(pk.buffer), BinaryUtils.LITTLE_ENDIAN);
			byte pid = reader.readByte();
			PocketDataPacket dp = getDataPacketByPid(pid);
			handleDataPacket(dp);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	protected abstract void handleDataPacket(PocketDataPacket dp);
	public abstract PocketDataPacket getDataPacketByPid(byte pid);
}
