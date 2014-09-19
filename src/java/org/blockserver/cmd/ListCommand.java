package org.blockserver.cmd;

import java.util.ArrayList;
import java.util.List;

import org.blockserver.Server;
import org.blockserver.player.Player;

public class ListCommand extends Command {
	
	public String getName() {
		return "list";
	}

	@Override
	public String run(CommandIssuer issuer, List<String> args) {
		String returnString = "There are ";
		Server server = issuer.getServer();
		ArrayList<Player> players = server.getConnectedPlayers();
		returnString = returnString + "["+players.size()+"/"+server.getMaxPlayers()+"] players online: ";
		for(int i = 0; i < players.size(); i++){
			returnString = returnString + players.get(i).getName()+" ";
		}
		return returnString;
	}

}
