package org.blockserver.player;

import org.blockserver.utils.Listener;

public class InternalListener{
	public final static int FORMAT_BOLD = 0x00000001,
			FORMAT_ITALIC = 0x00000002,
			FORMAT_UNDERLINED = 0x00000004,
			FORMAT_STRIKETHROUGH = 0x00000008,
			FORMAT_OBFUSCATED = 0x00000010;
	public void onChatReceived(String text, int formatFlags, char color, Listener<Player> clickEvent, Listener<Player> hoverEvent){
		// TODO
	}
}
