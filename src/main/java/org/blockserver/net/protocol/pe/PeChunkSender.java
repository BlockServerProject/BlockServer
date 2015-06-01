package org.blockserver.net.protocol.pe;

/**
 * Pocket Edition Chunk sender.
 */
public abstract class PeChunkSender extends Thread{
    private RakNetProtocolSession session;
    public PeChunkSender(RakNetProtocolSession session){
        this.session = session;
    }
    public abstract void run();

    public RakNetProtocolSession getSession() {
        return session;
    }
}
