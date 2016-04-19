package org.blocksever.test.core.networking;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Exerosis.
 */
public class NetworkManager {
    private final Set<NetworkDispatcher> dispatchers = new HashSet<>();

    public NetworkManager() {
    }

    public void registerDispatcher(NetworkDispatcher dispatcher){
        dispatchers.add(dispatcher);
    }

    public void dispatch(RawPacket packet){
        for (NetworkDispatcher dispatcher : dispatchers) {
            dispatcher.dispatch(packet);
        }
    }

    public Set<NetworkDispatcher> getDispatchers() {
        return Collections.unmodifiableSet(dispatchers);
    }
}