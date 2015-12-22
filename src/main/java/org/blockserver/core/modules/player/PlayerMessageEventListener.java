package org.blockserver.core.modules.player;

import org.blockserver.core.event.EventListener;
import org.blockserver.core.events.MessageHandleEvent;
import org.blockserver.core.message.Message;

/**
 * Created by atzei on 12/21/2015.
 */
public class PlayerMessageEventListener<T extends Message> extends EventListener<T, MessageHandleEvent>{
    private PlayerModule module;

    public PlayerMessageEventListener(PlayerModule module) {
        this.module = module;
    }

    @Override
    public void onEvent(MessageHandleEvent event) {
        module.onMessage(event);
    }
}
