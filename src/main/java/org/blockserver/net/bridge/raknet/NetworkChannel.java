package org.blockserver.net.bridge.raknet;

/**
 * Represents a NetworkChannel.
 */
public enum NetworkChannel {
    CHANNEL_NONE((byte) 0),
    CHANNEL_PRIORITY((byte) 1),
    CHANNEL_WORLD_CHUNKS((byte) 2),
    CHANNEL_MOVEMENT((byte) 3),
    CHANNEL_BLOCKS((byte) 4),
    CHANNEL_WORLD_EVENTS((byte) 5),
    CHANNEL_ENTITY_SPAWNING((byte) 6),
    CHANNEL_TEXT((byte) 7),
    CHANNEL_END((byte) 31);

    private byte channel;

    NetworkChannel(byte channel){
        this.channel = channel;
    }

    public byte getAsByte(){
        return channel;
    }
}
