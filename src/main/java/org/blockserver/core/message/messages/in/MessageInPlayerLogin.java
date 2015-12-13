package org.blockserver.core.message.messages.in;

import org.blockserver.core.message.messages.in.player.MessagePlayer;
import org.blockserver.core.module.modules.player.Player;

/**
 * Written by Exerosis!
 */
public class MessageInPlayerLogin extends MessagePlayer {
    public MessageInPlayerLogin(Player player) {
        super(player);
    }
}
