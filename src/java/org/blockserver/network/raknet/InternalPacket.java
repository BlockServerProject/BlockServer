package org.blockserver.network.raknet;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.blockserver.utility.Utils;

public class InternalPacket{
	public byte[] buffer;
	public byte reliability;
	public boolean hasSplit;
	public int messageIndex = -1;
	public int orderIndex = -1;
	public byte orderChannel = (byte)0xff;
	public int splitCount = -1;
	public short splitID = -1;
	public int splitIndex = -1;

	public static InternalPacket[] fromBinary(byte[] buffer){
		ByteBuffer bb = ByteBuffer.wrap(buffer);
		ArrayList<InternalPacket> list = new ArrayList<>();
		while(bb.position() < bb.capacity()) {
			InternalPacket pck = new InternalPacket();
			byte flag = bb.get();
			pck.reliability = (byte) (flag >> 5);
			pck.hasSplit = (flag & 0b00010000) == 16;
			int length = ((bb.getShort() + 7) >> 3); // The Length is in bits, so Bits to Bytes conversion
			if(pck.reliability == 2 || pck.reliability == 3 || pck.reliability == 4 || pck.reliability == 6 || pck.reliability == 7){
				pck.messageIndex = Utils.getLTriad(buffer, bb.position());
				bb.position(bb.position() + 3);
			}
			if(pck.reliability == 1 || pck.reliability == 3 || pck.reliability == 4 || pck.reliability == 7){
				pck.orderIndex = Utils.getLTriad(buffer, bb.position());
				bb.position(bb.position() + 3);
				pck.orderChannel = bb.get();
			}
			if(pck.hasSplit){
				pck.splitCount = bb.getInt();
				pck.splitID = bb.getShort();
				pck.splitIndex = bb.getInt();
			}
			pck.buffer = new byte[length];
			bb.get(pck.buffer);
			list.add(pck);
		}

		return (InternalPacket[]) list.toArray();
	}

	public int getLength(){
		return 3 + buffer.length + (messageIndex != -1 ? 3:0) + (orderIndex != -1 ? 4:0) +  (hasSplit ? 10:0);
	}

	public byte[] toBinary(){
		ByteBuffer bb = ByteBuffer.allocate(getLength());
		bb.put((byte) ((reliability << 5) ^ (hasSplit ? 0b0001 : 0x00)));
		bb.putShort((short) (buffer.length << 3));
		if(reliability == 0x02 || reliability == 0x03 || reliability == 0x04 || reliability == 0x06 || reliability == 0x07){
			bb.put(Utils.putLTriad(this.messageIndex));
		}
		if(reliability == 0x01 || reliability == 0x03 || reliability == 0x04 || reliability == 0x07){
			bb.put(Utils.putLTriad(this.orderIndex));
			bb.put(this.orderChannel);
		}
		if(hasSplit){
			bb.putInt(splitCount);
			bb.getShort(splitID);
			bb.putInt(splitIndex);
		}
		bb.put(buffer);
		return bb.array();
	}
}
