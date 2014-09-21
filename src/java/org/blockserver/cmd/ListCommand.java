package org.blockserver.cmd;

import java.util.Collection;
import java.util.List;

import org.blockserver.Server;
import org.blockserver.player.Player;

public class ListCommand extends Command {
	public String getName() {
		return "list";
	}

	@Override
	public String run(CommandIssuer issuer, List<String> args) {
		Server server = issuer.getServer();
		Collection<Player> players = server.getConnectedPlayers();
		StringBuilder builder = new StringBuilder();
		builder.append(players.size());
		builder.append('/');
		builder.append(server.getMaxPlayers());
		builder.append(String.format(" %s online:", players.size() >= 2 ? "players":"player"));
		builder.append(issuer.getEOL());
		for(Player p: server.getConnectedPlayers()){
			builder.append(p.getName());
			builder.append(", ");
		}
		return builder.toString().substring(0, builder.length() - 2);
	}
}
