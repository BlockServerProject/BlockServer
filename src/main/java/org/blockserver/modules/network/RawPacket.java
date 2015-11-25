package org.blockserver.modules.network;

import lombok.Getter;
import lombok.Setter;
import org.blockserver.core.event.Cancellable;

import java.net.InetAddress;

/**
 * Represents a packet recieved or ready to be sent in byte form.
 *
 * @author BlockServer Team
 */
public class RawPacket implements Cancellable {
    @Getter @Setter private byte[] buffer;
    @Getter @Setter private InetAddress address;

    public RawPacket(byte[] buffer, InetAddress address) {
        this.buffer = buffer;
        this.address = address;
    }
}
