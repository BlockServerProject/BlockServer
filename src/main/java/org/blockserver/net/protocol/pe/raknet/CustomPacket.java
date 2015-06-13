package org.blockserver.net.protocol.pe.raknet;

import org.blockserver.utils.Utils;
import static org.blockserver.net.protocol.pe.PeProtocolConst.*;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * CUSTOM PACKET (0x80-0x8F)
 */
public class CustomPacket extends RakNetPacket{
    public int sequenceNumber;
    public List<InternalPacket> packets = new ArrayList<>();

    @Override
    protected void _encode(ByteBuffer bb) {
        bb.put(RAKNET_CUSTOM_PACKET_DEFAULT);
        Utils.writeLTriad(sequenceNumber, bb);
        for(InternalPacket packet: packets){
            bb.put(packet.encode());
        }
    }

    @Override
    protected void _decode(ByteBuffer bb) {
        bb.get();
        sequenceNumber = Utils.readLTriad(bb);
        while(bb.hasRemaining()){
            InternalPacket p = new InternalPacket();
            p.decode(bb);
            packets.add(p);
        }
    }

    @Override
    public int getLength() {
        int len = 4;
        for(InternalPacket packet : packets){
            len += packet.getLength();
        }
        return len;
    }

    /**
     * Represents an InternalPacket contained in a CustomPacket
     */
    public static class InternalPacket extends RakNetPacket{
        public byte[] buffer;
        public byte reliability;
        public boolean hasSplit;
        public int messageIndex = -1;
        public int orderIndex = -1;
        public byte orderChannel = (byte)0xff;
        public int splitCount = -1;
        public short splitID = -1;
        public int splitIndex = -1;

        @Override
        protected void _encode(ByteBuffer bb){
            bb.put((byte) ((reliability << 5) | (hasSplit ? 0b00010000 : 0)));
            bb.putShort((short) (buffer.length << 3)); //Length is in bits
            if(reliability == 0x02 || reliability == 0x03 || reliability == 0x04 || reliability == 0x06 || reliability == 0x07){
                bb.put(Utils.writeLTriad(this.messageIndex));
            }
            if(reliability == 0x01 || reliability == 0x03 || reliability == 0x04 || reliability == 0x07){
                bb.put(Utils.writeLTriad(this.orderIndex));
                bb.put(this.orderChannel);
            }
            if(hasSplit){
                bb.putInt(splitCount);
                bb.putShort(splitID);
                bb.putInt(splitIndex);
            }
            bb.put(buffer);
        }

        @Override
        protected void _decode(ByteBuffer bb){
            byte flag = bb.get();
            reliability = (byte) (flag >> 5);
            hasSplit = (flag & 0b00010000) == 16;
            int length = ((bb.getShort() + 7) >> 3); //Length is in bits
            if(reliability == 0x02 || reliability == 0x03 || reliability == 0x04 || reliability == 0x06 || reliability == 0x07){
                messageIndex = Utils.readLTriad(bb);
            }
            if(reliability == 0x01 || reliability == 0x03 || reliability == 0x04 || reliability == 0x07){
                orderIndex = Utils.readLTriad(bb);
                orderChannel = bb.get();
            }
            if(hasSplit){
                splitCount = bb.getInt();
                splitID = bb.getShort();
                splitIndex = bb.getInt();
            }
            buffer = new byte[length];
            bb.get(buffer);
        }

        @Override
        public int getLength() {
            return 3 + buffer.length + (messageIndex != -1 ? 3:0) + (orderIndex != -1 ? 4:0) +  (hasSplit ? 10:0);
        }
    }
}
