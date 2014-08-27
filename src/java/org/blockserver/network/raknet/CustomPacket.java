package org.blockserver.network.raknet;

import org.blockserver.network.RaknetsID;
import org.blockserver.network.minecraft.BaseDataPacket;
import org.blockserver.utility.Utils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomPacket implements BaseDataPacket {
	
	protected ByteBuffer buffer;

    public byte PacketID;
    public int SequenceNumber;

    public List<InternalPacket> packets;

	public CustomPacket(byte[] data){
		this.buffer = ByteBuffer.wrap(data);
        this.packets = new ArrayList<InternalPacket>();
	}

    public CustomPacket() {
        this.PacketID = RaknetsID.DATA_PACKET_4;
        this.packets = new ArrayList<InternalPacket>();
    }


    public int getLength()
    {
        int length = 4; // PacketID + sequence number

        for (InternalPacket pck : this.packets)
        {
            length += pck.getLength();
        }
        return length;
    }
	
	public void decode() {
		this.PacketID = this.buffer.get();
        this.SequenceNumber = Utils.getLTriad(this.buffer.array(), this.buffer.position());
        this.buffer.position(this.buffer.position() + 3);

        byte[] data = new byte[this.buffer.capacity() - 4];
        buffer.get(data);
	    this.packets = Arrays.asList(InternalPacket.fromBinary(data));

	}

	public void encode() {
        this.buffer = ByteBuffer.allocate(this.getLength());
        this.buffer.put(this.PacketID);
        this.buffer.put(Utils.putLTriad(this.SequenceNumber));

        for (InternalPacket pck : this.packets)
        {
            this.buffer.put(pck.toBinary());
        }
    }
	
	public ByteBuffer getBuffer(){
		return buffer;
	}

}
