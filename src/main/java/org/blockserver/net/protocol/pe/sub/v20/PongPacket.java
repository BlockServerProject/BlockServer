package org.blockserver.net.protocol.pe.sub.v20;

/**
 * MC_PLAY_PONG (0x03)
 */
public class PongPacket extends PingPacket{
    @Override
    public byte getPid() {
        return MC_PLAY_PONG;
    }
}
