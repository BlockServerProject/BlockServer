package org.blockserver.cmd;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.blockserver.Server;

public class CommandManager{
	private Server server;
	private Map<String, Command> cmds;

	public CommandManager(Server server){
		this.server = server;
		registerDefaults();
	}

	private void registerDefaults(){
		cmds = new HashMap<String, Command>(0);
		
	}
	public boolean registerAll(Command[] cmds){
		boolean ret = true;
		for(Command cmd: cmds){
			ret = ret && registerCommand(cmd);
		}
		return ret;
	}
	public boolean registerCommand(Command command){
		if(cmds.containsKey(command.getName())){
			return false;
		}
		cmds.put(command.getName(), command);
		return true;
	}

	public void runCommand(CommandIssuer issuer, String line){
		String[] args = line.split(" ");
		if(cmds.containsKey(args[0])){
			String[] fargs = {};
			for(int i = 1; i < args.length; i++){
				fargs[i - 1] = args[i];
			}
			if(args[0].equals("help")){
				// TODO more details
				StringBuilder b = new StringBuilder("Available commands: ");
				for(String name: cmds.keySet()){
					b.append(name);
					b.append(", ");
				}
				issuer.sendMessage(b.toString());
			}
			else{
				Command cmd = cmds.get(args[0]);
				if(cmd != null){
					String result = cmds.get(args[0]).run(issuer, fargs);
					result = result == null ? "" : result;
					if(result.length() > 0){
						issuer.sendMessage(result);
					}
				}
				else{
					issuer.sendMessage(String.format(Locale.US, "Command /%s doesn't exist!", args[0]));
				}
			}
		}
	}
	public Server getServer(){
		return server;
	}
}
