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

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.blockserver.Server;

public class PeDataPacketParser{
	private HashMap<Byte, Class<? extends PeDataPacket>> packets = new HashMap<>();
	private Server server;
	public PeDataPacketParser(Server server){
		this.server = server;
	}
	public void add(Byte pid, Class<? extends PeDataPacket> clazz){
		packets.put(pid, clazz);
	}
	public PeDataPacket parsePacket(final byte[] buffer){
		try{
			//PeDataPacket pk = packets.get(buffer[0]).newInstance(buffer);
			PeDataPacket pk;
			try{
				if(packets.containsKey(buffer[0])){
					pk = packets.get(buffer[0]).getConstructor(byte[].class).newInstance(new Object[]{buffer});
				}else{
					pk = new UnknownDataPacket(buffer);
				}
			}catch(InvocationTargetException | NoSuchMethodException e){
				e.printStackTrace();
				return null;
			}
			pk.decode(buffer);
			return pk;
		}catch(NullPointerException e){
			server.getLogger().warning("Unknown data packet ID %d", (int) buffer[0]);
			return null;
		}catch(InstantiationException | IllegalAccessException e){
			e.printStackTrace();
			return null;
		}
	}
}
