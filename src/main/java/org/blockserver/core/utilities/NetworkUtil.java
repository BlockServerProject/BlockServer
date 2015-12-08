package org.blockserver.core.utilities;

import java.net.InetAddress;
import java.net.UnknownHostException;

public final class NetworkUtil {
    private NetworkUtil() {

    }

    public static InetAddress getLocalHost() {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }
}
