package net.blockserver.network.data;

import net.blockserver.utility.Utils;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class InternalPacket
{
    public byte[] packet;

    public byte reliability;
    public boolean hasSplit;
    public int messageIndex;
    public int orderIndex;
    public byte orderChannel;
    public int splitCount;
    public short splitID;
    public int splitIndex;

    public static InternalPacket[] fromBinary(byte[] buffer)
    {
        ByteBuffer bb = ByteBuffer.wrap(buffer);
        InternalPacket pck = new InternalPacket();
        ArrayList<InternalPacket> list = new ArrayList<>();

        while(bb.position() < bb.capacity()) {
            byte flag = bb.get();
            pck.reliability = (byte) (flag >> 5);
            pck.hasSplit = (flag & 0b00010000) == 16;
            int length = ((bb.getShort() + 7) >> 3);

            if (pck.reliability == 2 || pck.reliability == 3 || pck.reliability == 4 || pck.reliability == 6 || pck.reliability == 7) {
                pck.messageIndex = Utils.getLTriad(buffer, bb.position());
                bb.position(bb.position() + 3);
            }

            if (pck.reliability == 1 || pck.reliability == 3 || pck.reliability == 4 || pck.reliability == 7) {
                pck.orderIndex = Utils.getLTriad(buffer, bb.position());
                pck.orderChannel = bb.get();
            }

            if (pck.hasSplit) {
                pck.splitCount = bb.getInt();
                pck.splitID = bb.getShort();
                pck.splitIndex = bb.getInt();
            }

            pck.packet = new byte[length];
            bb.get(pck.packet);
            list.add(pck);
        }

        return list.toArray(new InternalPacket[list.size()]);
    }



    public ByteBuffer toBinary()
    {
        ByteBuffer bb = ByteBuffer.allocate(3 + this.packet.length + (this.messageIndex > -1 ? 3 : 0) + (this.orderIndex > -1 ? 4 : 0)  + (this.hasSplit ? 10 : 0));
        bb.put((byte)((this.reliability << 5) ^ (this.hasSplit ? 0b0001 : 0x00))); // Reliability Level: Reliable, hasSplit=false
        bb.putShort((short)(this.packet.length << 3));

        if (this.reliability == 2 || this.reliability == 3 || this.reliability == 4 || this.reliability == 6 || this.reliability == 7) {
            bb.put(Utils.putLTriad(this.messageIndex));
        }

        if (this.reliability == 1 || this.reliability == 3 || this.reliability == 4 || this.reliability == 7) {
            bb.put(Utils.putLTriad(this.orderIndex));
            bb.put(this.orderChannel);
        }

        if (this.hasSplit) {
            this.splitCount = bb.getInt();
            this.splitID = bb.getShort();
            this.splitIndex = bb.getInt();
        }

        bb.put(this.packet);
        return bb;
    }
}
