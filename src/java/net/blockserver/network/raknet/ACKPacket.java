package net.blockserver.network.raknet;

import net.blockserver.network.RaknetsID;

public class ACKPacket extends AcknowledgePacket {
    public byte getPID() {
        return RaknetsID.ACK;
    }

    public ACKPacket(int[] numbers)
    {
        this.sequenceNumbers = numbers;
    }
}
