package org.blockserver.net.protocol.pe.login;

import org.blockserver.net.protocol.pe.PeProtocolConst;

import java.nio.ByteBuffer;

/**
 * Created by jython234 on 2/8/2015.
 */
public class LoginStatusPacket implements EncapsulatedLoginPacket, PeProtocolConst{
    public int status;
    private ByteBuffer bb;
    @Override
    public void encode() {
        bb = ByteBuffer.allocate(5);
        bb.put(MC_LOGIN_STATUS_PACKET);
        bb.putInt(status);
    }

    @Override
    public void decode() { throw new UnsupportedOperationException("Not implemented."); }

    @Override
    public ByteBuffer getBuffer() {
        return bb;
    }
}
