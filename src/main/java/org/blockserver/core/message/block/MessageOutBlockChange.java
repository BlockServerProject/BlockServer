package org.blockserver.core.message.block;

import org.blockserver.core.message.player.MessagePlayer;
import org.blockserver.core.modules.player.Player;
import org.blockserver.core.modules.world.Block;

/**
 * Written by Exerosis!
 */
public class MessageOutBlockChange extends MessagePlayer {
    public MessageOutBlockChange(Player player, Block... blocks) {
        super(player);
    }
}
