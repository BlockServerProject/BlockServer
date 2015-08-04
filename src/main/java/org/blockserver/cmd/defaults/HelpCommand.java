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
package org.blockserver.cmd.defaults;

import org.blockserver.Server;
import org.blockserver.cmd.Command;
import org.blockserver.cmd.CommandIssuer;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class HelpCommand extends Command{
	private Server server;
	public HelpCommand(Server server){
		this.server = server;
	}
	@Override
	public String getName(){
		return "help";
	}
	@Override
	public String getDescription(CommandIssuer issuer){
		return "Show all available commands";
	}
	@Override
	public String getUsage(CommandIssuer issuer){
		return "/help [page] [lines per page]";
	}
	@Override
	public String run(CommandIssuer issuer, ArrayList<String> args){
		if(args.size() == 0){
			args.add("1");
		}
		if(args.size() == 1){
			args.add("5");
		}
		try{
			int page = Integer.parseInt(args.remove(0));
			int lines = Integer.parseInt(args.remove(0));
			TreeMap<String, Command> fullMap = server.getCmdMgr().getAll();
			TreeMap<String, Command> map = new TreeMap<>();
			fullMap.entrySet().stream()
					.filter(entry -> entry.getValue().canUse(issuer))
					.forEach(entry -> map.put(entry.getKey(), entry.getValue()));
			int size = map.size();
			int max = (size - 1) / lines + 1;
			page = Math.max(1, Math.min(max, page));
			int start = (page - 1) * lines;
			int cur = -1;
			issuer.tell("Showing help page %d of %d", page, max);
			for(Map.Entry<String, Command> entry : map.entrySet()){
				if(++cur >= start){
					if(cur - start < lines){
						issuer.tell("/%s: %s", entry.getKey(), entry.getValue().getDescription(issuer));
					}
					else{
						break;
					}
				}
			}
		}catch(NumberFormatException e){
			return "Usage: " + getUsage(issuer);
		}
		return null;
	}
}
