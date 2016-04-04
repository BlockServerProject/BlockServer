package org.blockserver.core;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main run class for BlockServer
 */
public class run {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger("BlockServer");

        Server server = new Server(new InetSocketAddress("0.0.0.0", 19132), logger);
        server.setRunning(true);
        server.run();
    }
}
