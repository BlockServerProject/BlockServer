package org.blockserver.net.protocol.pe.raknet;


import org.apache.commons.lang3.NotImplementedException;

import java.nio.ByteBuffer;

/**
 * Base class for all RakNet packets.
 */
public abstract class RakNetPacket {

    public final byte[] encode(){
        ByteBuffer bb = ByteBuffer.allocate(getLength());
        _encode(bb);
        return bb.array();
    }

    public final void decode(byte[] buffer){
        ByteBuffer bb = ByteBuffer.wrap(buffer);
        _decode(bb);
    }

    public final void decode(ByteBuffer bb){
        _decode(bb);
    }

    protected void _encode(ByteBuffer bb){
        throw new NotImplementedException("Not implemented: encode");
    }

    protected void _decode(ByteBuffer bb){
        throw new NotImplementedException("Not implemented: decode");
    }

    public abstract int getLength();
}
