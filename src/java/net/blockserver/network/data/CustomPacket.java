package net.blockserver.network.data;

import net.blockserver.Server;
import net.blockserver.utility.Utils;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class CustomPacket implements BaseDataPacket {
	
	protected ByteBuffer buffer;

    public byte PacketID = (byte)0x84;
    public int SequenceNumber;

    public InternalPacket[] packets;

	public CustomPacket(byte[] data){
		this.buffer = ByteBuffer.wrap(data);
	}

    public CustomPacket()
    {
        this.PacketID = (byte)0x84;
    }
	
	
	public void decode() {
		this.PacketID = this.buffer.get();
        this.SequenceNumber = Utils.getLTriad(this.buffer.array(), this.buffer.position());
        this.buffer.position(this.buffer.position() + 3);

        byte[] data = new byte[this.buffer.capacity() - 4];
        buffer.get(data);
	    this.packets = InternalPacket.fromBinary(data);

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

	public void encode() {
        this.buffer = ByteBuffer.allocate(this.getLength());
        this.buffer.put(this.PacketID);
        this.buffer.put(Utils.putLTriad(this.SequenceNumber));

        for (InternalPacket pck : this.packets)
        {
            this.buffer.put(pck.toBinary());
        }
    }

}
