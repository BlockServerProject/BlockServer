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

import org.blockserver.core.event.EventListener;
import org.blockserver.core.events.MessageHandleEvent;
import org.blockserver.core.message.Message;

/**
 * Created by atzei on 12/21/2015.
 */
public class PlayerMessageEventListener<T extends Message> extends EventListener<T, MessageHandleEvent> {
    private PlayerModule module;

    public PlayerMessageEventListener(PlayerModule module) {
        this.module = module;
    }

    @Override
    public void onEvent(MessageHandleEvent event) {
        module.onMessage(event);
    }
}
