package org.blockserver.network.raknet;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.blockserver.network.RaknetIDs;
import org.blockserver.network.minecraft.BaseDataPacket;
import org.blockserver.utility.Utils;

public class CustomPacket extends BaseDataPacket{
	public byte packetID;
	public int sequenceNumber;
	public List<InternalPacket> packets;

	public CustomPacket(byte[] data){
		bb = ByteBuffer.wrap(data);
		packets = new ArrayList<InternalPacket>();
	}
	public CustomPacket(){
		packetID = RaknetIDs.DATA_PACKET_4;
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
		packetID = bb.get();
		sequenceNumber = Utils.getLTriad(bb.array(), bb.position());
		bb.position(bb.position() + 3);

		byte[] data = new byte[bb.capacity() - 4];
		bb.get(data);
		packets = Arrays.asList(InternalPacket.fromBinary(data));
	}
	@Override
	public void encode(){
		bb= ByteBuffer.allocate(getLength());
		bb.put(this.packetID);
		bb.put(Utils.putLTriad(sequenceNumber));
		for(InternalPacket pck: packets){
			bb.put(pck.toBinary());
		}
	}
}
