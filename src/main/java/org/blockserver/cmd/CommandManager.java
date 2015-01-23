package org.blockserver.cmd;

import org.blockserver.Server;
import org.blockserver.cmd.defaults.HelpCommand;

import java.util.*;

public class CommandManager{
	private Server server;
	private TreeMap<String, Command> cmds = new TreeMap<>();
	public CommandManager(Server server){
		this.server = server;
		registerDefaults();
	}
	public boolean register(Command cmd){
		String name = normalize(cmd.getName());
		if(cmds.containsKey(name)){
			server.getLogger().warning("Attempt to register cmd '%s' twice", name);
			server.getLogger().trace(new Throwable("Attempt to register a cmd twice"));
			return false;
		}
		cmds.put(name, cmd);
		return true;
	}
	public boolean unregister(String name){
		return cmds.remove(normalize(name)) != null;
	}
	public TreeMap<String, Command> getAll(){
		return cmds;
	}
	public void run(CommandIssuer issuer, String msg){
		ArrayList<String> args = new ArrayList<>(Arrays.asList(msg.split(" ")));
		if(args.isEmpty()){
			return;
		}
		String cmdName = normalize(args.remove(0));
		Command cmd = cmds.get(cmdName);
		if(cmd == null){
			issuer.tell("Unknown cmd /%s. Use /help to get a list of all commands.", cmdName);
			return;
		}
		String response = cmd.run(issuer, args);
		if(response != null){
			issuer.tell(response);
		}
	}
	private String normalize(String cmdName){
		return cmdName.toLowerCase(Locale.ENGLISH);
	}
	private void registerDefaults(){
		register(new HelpCommand(server));
	}
	public Server getServer(){
		return server;
	}
}
