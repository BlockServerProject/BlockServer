package org.blockserver.core;

import lombok.Getter;
import org.slf4j.Logger;

import java.net.InetSocketAddress;

/**
 * The main server implementation.
 *
 * @author BlockServer Team
 */
public class Server {
    @Getter private final Logger logger;

    public Server(InetSocketAddress bindAddress, Logger logger) {
        this.logger = logger;
    }
}
