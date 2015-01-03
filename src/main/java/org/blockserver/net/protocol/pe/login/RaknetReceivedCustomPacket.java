package org.blockserver.net.protocol.pe.login;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import org.blockserver.net.protocol.pe.PeProtocolConst;
import org.blockserver.utils.AntiSpam;
import org.blockserver.utils.Utils;

public class RaknetReceivedCustomPacket implements PeProtocolConst{
	public int seqNumber;
	public ArrayList<ReceivedEncapsulatedPacket> packets = new ArrayList<>(1);
	public RaknetReceivedCustomPacket(ByteBuffer bb){
		seqNumber = Utils.readLTriad(bb);
		while(bb.hasRemaining()){
			packets.add(new ReceivedEncapsulatedPacket(bb));
		}
	}
	public static class ReceivedEncapsulatedPacket{
		public byte reliability;
		public boolean hasSplit;
		public int messageIndex = -1;
		public int orderIndex = -1;
		public byte orderChannel = (byte) 0xFF;
		public int splitCount = -1;
		public short splitId = -1;
		public int splitIndex = -1;
		public byte[] buffer;
		public ReceivedEncapsulatedPacket(ByteBuffer bb){
			bb.order(ByteOrder.BIG_ENDIAN);
			byte flag = bb.get();
			reliability = (byte) (flag >> 5);
			hasSplit = (flag & 0x10) == 0x10;
			final short _length = bb.getShort();
			final int length = (_length & 0x0000FFF8) >> 3;
			AntiSpam.act(new Runnable(){
				@Override
				public void run(){
					System.out.println(_length + ", " + length);
				}
			}, "raknet custom packet length", 2000);
			if(Utils.inArray(reliability, RAKNET_HAS_MESSAGE_RELIABILITIES)){
				messageIndex = Utils.readLTriad(bb);
			}
			if(Utils.inArray(reliability, RAKNET_HAS_ORDER_RELIABILITIES)){
				orderIndex = Utils.readLTriad(bb);
				orderChannel = bb.get();
			}
			if(hasSplit){
				splitCount = bb.getInt();
				splitId = bb.getShort();
				splitIndex = bb.getInt();
			}
			buffer = new byte[length];
			bb.get(buffer);
		}
	}
}
