package org.blockserver;

/**
 * Main class for the core.
 *
 * @author BlockServer team
 */
public class  run {

    public static void main(String[] args) {
        Server server = new Server();
        server.setRunning(true);
        server.run();
    }

}
