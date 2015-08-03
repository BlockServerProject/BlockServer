package org.blockserver.net.protocol.pe.sub;

/**
 * Created by jython234 on 8/3/2015.
 */
public class UnknownDataPacket extends PeDataPacket{
    private byte[] buffer;

    public UnknownDataPacket(byte[] buffer){
        this.buffer = buffer;
    }

    @Override
    protected int getLength() {
        return buffer.length;
    }

    @Override
    public byte getPID() {
        return buffer[0];
    }
}
