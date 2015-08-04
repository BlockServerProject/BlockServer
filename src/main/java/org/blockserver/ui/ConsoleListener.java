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
package org.blockserver.ui;

import org.blockserver.Server;
import org.blockserver.cmd.CommandIssuer;

public class ConsoleListener{
	private Server server;
	private ConsoleIn in;
	private CommandIssuer.ConsoleIssuer console;

	public ConsoleListener(Server server, ConsoleIn in){
		this.server = server;
		this.in = in;
		console = new CommandIssuer.ConsoleIssuer(server);
	}
	public void tick(){
		while(true){
			String line = in.read();
			if(line == null){
				return;
			}
			line = line.trim();
			if(line.isEmpty()){
				return;
			}
			server.getCmdMgr().run(console, line);
		}
	}
	public void close(boolean emergency){
		in.close(emergency);
	}
}
