package net.blockserver.network.raknet;

public class NACKPacket extends AcknowledgePacket {
    @Override
    public byte getPID() {
        return 0;
    }

    public NACKPacket(int[] numbers)
    {
        this.sequenceNumbers = numbers;
    }

    public NACKPacket(byte[] buffer)
    {
        this.buffer = buffer;
    }
}
