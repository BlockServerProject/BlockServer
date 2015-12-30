/*
* This file is part of BlockServer.
*
* BlockServer is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* BlockServer is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License
* along with BlockServer.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.blockserver.core.modules.player;

import lombok.Getter;
import org.blockserver.core.Server;
import org.blockserver.core.event.MessageEventListener;
import org.blockserver.core.events.MessageHandleEvent;
import org.blockserver.core.message.MessageInPlayerLogin;
import org.blockserver.core.module.Module;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Module that handles players.
 */
public class PlayerModule extends Module {
    @Getter private final Set<Player> players = Collections.unmodifiableSet(Collections.synchronizedSet(new HashSet<>()));
    private MessageEventListener<MessageInPlayerLogin> listener;

    public PlayerModule(Server server) {
        super(server);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        listener = new MessageEventListener<MessageInPlayerLogin>() {
            @Override
            public void onEvent(MessageHandleEvent<MessageInPlayerLogin> event) {
                Player player = new Player(getServer(), event.getMessage().getAddress()); //TODO: Fire PlayerCreatedEvent
                players.add(player);
            }
        }.register(MessageInPlayerLogin.class, getServer());
    }

    @Override
    public void onDisable() {
        super.onDisable();
        listener.unregister(getServer());
    }
}