package org.blocksever.test.core.networking;

/**
 * Created by Exerosis.
 */
public interface NetworkDispatcher {
    void dispatch(RawPacket packet);
}
