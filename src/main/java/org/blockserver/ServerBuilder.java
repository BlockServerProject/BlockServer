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
package org.blockserver;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.blockserver.player.PlayerDatabase;
import org.blockserver.ui.ConsoleOut;

public class ServerBuilder{
	private InetAddress address;
	private int port = -1;
	private String serverName = null;
	private ConsoleOut out = null;
	private File includePath = null;
	private PlayerDatabase playerDb = null;
	private File modulePath = null;
	public ServerBuilder(){
		try{
			address = InetAddress.getByName("localhost");
		}catch(UnknownHostException e){
			e.printStackTrace();
		}
	}

	public ServerBuilder setAddress(InetAddress ip){
		address = ip;
		return this;
	}
	public ServerBuilder setPort(int port){
		this.port = port;
		return this;
	}
	public ServerBuilder setServerName(String name){
		serverName = name;
		return this;
	}
	public ServerBuilder setConsoleOut(ConsoleOut out){
		this.out = out;
		return this;
	}
	public ServerBuilder setIncludePath(File dir){
		includePath = dir;
		dir.mkdirs();
		return this;
	}
	public ServerBuilder setPlayerDatabase(PlayerDatabase db){
		playerDb = db;
		return this;
	}
	public ServerBuilder setModulePath(File path){
		modulePath = path;
		return this;
	}

	public Server build(){
		validate(port != -1, "port");
		validate(serverName != null, "serverName");
		validate(out != null, "out");
		validate(includePath != null, "includePath");
		validate(playerDb != null, "playerDb");
		validate(modulePath != null, "modulePath");
		return new Server(address, port, serverName, out, playerDb, modulePath);
	}
	private void validate(boolean bool, String field){
		if(!bool){
			throw new IllegalStateException("Field ServerBuilder." + field
					+ " not initialized!");
		}
	}
}
