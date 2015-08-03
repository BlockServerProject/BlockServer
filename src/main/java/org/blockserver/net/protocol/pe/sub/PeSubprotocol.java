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
package org.blockserver.net.protocol.pe.sub;

import org.blockserver.Server;
import org.blockserver.net.internal.request.InternalRequest;
import org.blockserver.net.protocol.pe.PeProtocolConst;
import org.blockserver.player.Player;

public abstract class PeSubprotocol implements PeProtocolConst{
	public abstract Server getServer();
	public abstract int getSubprotocolVersionId();
	public abstract String getSubprotocolName();
	public void readDataPacket(byte[] pk, Player handler){
		PeDataPacket dp = getDataPacketByBuffer(pk);
		dp.decode(pk);
		InternalRequest req = toInternalRequest(dp);
		handler.handleRequest(req);
	}
	protected abstract InternalRequest toInternalRequest(PeDataPacket dp);
	public abstract PeDataPacket getDataPacketByBuffer(byte[] buffer);
}
