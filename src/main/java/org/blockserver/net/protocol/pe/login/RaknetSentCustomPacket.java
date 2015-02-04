package org.blockserver.net.protocol.pe.login;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.blockserver.net.bridge.NetworkBridge;
import org.blockserver.net.protocol.pe.PeProtocolConst;
import org.blockserver.utils.Utils;

public class RaknetSentCustomPacket implements PeProtocolConst{
	public ArrayList<SentEncapsulatedPacket> packets = new ArrayList<>(1);
	public int seqNumber;
	private int length = 4;
	public RaknetSentCustomPacket(int seqNumber){
		this.seqNumber = seqNumber;
	}
	public RaknetSentCustomPacket(){
		
	}
	public void send(NetworkBridge bridge, SocketAddress addr){
		length = 4;
		for(SentEncapsulatedPacket pk: packets){
			length += pk.getLength();
		}
		ByteBuffer bb = ByteBuffer.allocate(length);
		bb.put(RAKNET_CUSTOM_PACKET_DEFAULT);
		Utils.writeLTriad(seqNumber, bb);
		for(SentEncapsulatedPacket pk: packets){
			pk.append(bb);
		}
		bridge.send(bb.array(), addr);
	}
	
	public int getLength(){
		int len = 4;
		for(SentEncapsulatedPacket pk: packets){
			len += pk.getLength();
		}
		return len;
	}
	public static class SentEncapsulatedPacket{
		private byte[] buffer;
		private byte reliability;
		public int messsageIndex;
		public int orderIndex;
		public byte orderChannel;
		private boolean hasSplit = false;
		private int splitCount;
		private short splitId;
		private int splitIndex;
		public SentEncapsulatedPacket(byte[] buffer, byte reliability){
			this.buffer = buffer;
			this.reliability = reliability;
		}
		public void split(int count, short id, int index){
			hasSplit = true;
			splitCount = count;
			splitId = id;
			splitIndex = index;
		}
		public void append(ByteBuffer bb){
			bb.put((byte) (reliability << 5));
			bb.putShort((short) (buffer.length << 3));
			if(Utils.inArray(reliability, RAKNET_HAS_MESSAGE_RELIABILITIES)){
				Utils.writeLTriad(messsageIndex, bb);
			}
			if(Utils.inArray(reliability, RAKNET_HAS_ORDER_RELIABILITIES)){
				Utils.writeLTriad(orderIndex, bb);
				bb.put(orderChannel);
			}
			if(hasSplit){
				bb.putInt(splitCount);
				bb.putShort(splitId);
				bb.putInt(splitIndex);
			}
			bb.put(buffer);
		}
		public int getLength(){
			return 3 + buffer.length +
					(Utils.inArray(reliability,
							RAKNET_HAS_MESSAGE_RELIABILITIES) ? 3:0) +
					(Utils.inArray(reliability,
							RAKNET_HAS_ORDER_RELIABILITIES) ? 4:0) +
					(hasSplit ? 10:0);
		}
	}
}
