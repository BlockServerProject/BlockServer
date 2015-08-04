/**
 * This file is part of BlockServer.
 *
 * BlockServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BlockServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BlockServer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.blockserver.player;

import org.blockserver.utils.Listener;

public class InternalListener{
	public final static int FORMAT_BOLD = 0x00000001,
			FORMAT_ITALIC = 0x00000002,
			FORMAT_UNDERLINED = 0x00000004,
			FORMAT_STRIKETHROUGH = 0x00000008,
			FORMAT_OBFUSCATED = 0x00000010; // TODO: migrate these to somewhere more proper?
	public void onChatReceived(String text, int formatFlags, char color, Listener<Player> clickEvent, Listener<Player> hoverEvent){
		// TODO
	}
}
