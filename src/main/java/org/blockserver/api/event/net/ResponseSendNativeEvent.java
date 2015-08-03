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
package org.blockserver.api.event.net;

import org.blockserver.api.NativeEvent;
import org.blockserver.net.internal.response.InternalResponse;
import org.blockserver.player.Player;

public class ResponseSendNativeEvent extends NativeEvent{
	private Player player;
	private InternalResponse response;

	public ResponseSendNativeEvent(Player player, InternalResponse response){
		this.player = player;
		this.response = response;
	}

	public Player getPlayer(){
		return player;
	}

	public InternalResponse getResponse(){
		return response;
	}

	public void setResponse(InternalResponse response){
		this.response = response;
	}
}
