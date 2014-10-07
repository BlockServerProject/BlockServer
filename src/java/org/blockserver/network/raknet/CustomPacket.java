package org.blockserver.network.raknet;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.blockserver.network.RaknetsID;
import org.blockserver.network.minecraft.BaseDataPacket;
import org.blockserver.utility.Utils;

public class CustomPacket implements BaseDataPacket {
	protected ByteBuffer buffer;
	public byte PacketID;
	public int sequenceNumber;
	public List<InternalPacket> packets;

	public CustomPacket(byte[] data){
		buffer = ByteBuffer.wrap(data);
		packets = new ArrayList<InternalPacket>();
	}
	public CustomPacket(){
		PacketID = RaknetsID.DATA_PACKET_4;
		packets = new ArrayList<InternalPacket>();
	}

	public int getLength(){
		int length = 4; // PacketID + sequence number
		for(InternalPacket pck: this.packets){
			length += pck.getLength();
		}
		return length;
	}
	
	@Override
	public void decode(){
		PacketID = buffer.get();
		sequenceNumber = Utils.getLTriad(this.buffer.array(), this.buffer.position());
		buffer.position(buffer.position() + 3);

		byte[] data = new byte[buffer.capacity() - 4];
		buffer.get(data);
		packets = Arrays.asList(InternalPacket.fromBinary(data));
	}
	@Override
	public void encode(){
		buffer = ByteBuffer.allocate(getLength());
		buffer.put(this.PacketID);
		buffer.put(Utils.putLTriad(sequenceNumber));
		for(InternalPacket pck: packets){
			buffer.put(pck.toBinary());
		}
	}
	@Override
	public ByteBuffer getBuffer(){
		return buffer;
	}
}
