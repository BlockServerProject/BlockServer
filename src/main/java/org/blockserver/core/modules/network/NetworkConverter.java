package org.blockserver.core.modules.network;

import org.blockserver.core.modules.message.Message;

/**
 * A NetworkConverter converts {@linkplain RawPacket}s to {@linkplain Message}s and
 * in reverse.
 *
 * @author BlockServer Team
 * @see RawPacket
 * @see Message
 */
public interface NetworkConverter {

    /**
     * Converts a {@linkplain Message} to a {@linkplain RawPacket}
     *
     * @param packet The {@linkplain RawPacket} to be converted.
     * @return The converted {@linkplain Message}, if converted, null if not.
     */
    Message convertPacket(RawPacket packet);

    /**
     * Converts a {@linkplain RawPacket} to a {@linkplain Message}
     *
     * @param message The {@linkplain Message} to be converted.
     * @return The converted {@linkplain RawPacket}, if converted, null if not.
     */
    RawPacket convertMessage(Message message);
}
