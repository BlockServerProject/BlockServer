package org.blockserver.net.protocol.pe.raknet;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

/**
 * RakNet NetworkChannel enum for the orderChannel property in InternalPacket.
 */
public enum NetworkChannel {
    /**
     * Normal Channel for unimportant things.
     */
    CHANNEL_NONE(0),
    /**
     * Channel only used when it matters.
     */
    CHANNEL_PRIORITY(1),
    /**
     * Channel for Chunk Sending.
     */
    CHANNEL_WORLD_CHUNKS(2),
    /**
     * Channel for Movements.
     */
    CHANNEL_MOVEMENT(3),
    /**
     * Channel for block updates or explosions.
     */
    CHANNEL_BLOCKS(4),
    /**
     * Channel for entity, level, or tile entity events.
     */
    CHANNEL_WORLD_EVENTS(5),
    /**
     * Channel for Entity spawn/despawn.
     */
    CHANNEL_ENTITY_SPAWNING(6),
    /**
     * Channel for chat and text.
     */
    CHANNEL_TEXT(7),

    CHANNEL_END(31);

    private byte channel;

    private NetworkChannel(int channel){
        this.channel = (byte) channel;
    }

    public static NetworkChannel fromByte(byte channel){
        List<NetworkChannel> types = Arrays.asList(NetworkChannel.values());
        for(NetworkChannel type : types){
            if(type.getAsByte() == channel){
                return type;
            }
        }
        return null;
    }

    /**
     * Get the channel type as a byte.
     * @return The channel as a byte.
     */
    public byte getAsByte(){
        return channel;
    }
}
