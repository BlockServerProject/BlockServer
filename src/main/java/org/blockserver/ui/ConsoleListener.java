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
