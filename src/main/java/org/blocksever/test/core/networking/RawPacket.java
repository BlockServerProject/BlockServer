package org.blocksever.test.core.networking;

/**
 * Created by Exerosis.
 */
public class RawPacket {
    private String testMessage;

    public RawPacket(String testMessage) {
        this.testMessage = testMessage;
    }

    @Override
    public String toString() {
        return testMessage;
    }
}