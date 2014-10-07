package org.blockserver.cmd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
		registerCommand(new HelpCommand());
		registerCommand(new StopCommand());
		registerCommand(new ListCommand());
	}
	public boolean registerAll(Command[] cmds){
		boolean ret = true;
		for(Command cmd: cmds){
			ret = ret && registerCommand(cmd);
		}
		return ret;
	}
	public boolean registerCommand(Command command){
		if(cmds.containsKey(command.getName().toLowerCase(Locale.US))){
			return false;
		}
		cmds.put(command.getName().toLowerCase(Locale.US), command);
		return true;
	}

	public void runCommand(CommandIssuer issuer, String line){
		String[] arrayArgs = line.split(" ");
		List<String> args = new ArrayList<String>(arrayArgs.length);
		for(String arg: arrayArgs){
			args.add(arg);
		}
		String cmdStr = null;
		try{
			cmdStr = args.remove(0);
		}
		catch(IndexOutOfBoundsException e){
			return;
		}
		Command cmd = cmds.get(cmdStr.toLowerCase(Locale.US));
		if(cmd == null){
			cmd = cmds.get("help");
			args.clear();
		}
		CharSequence cs = cmd.run(issuer, args);
		if(cs != null){
			String msg = cs.toString();
			if(msg.trim().length() > 0){
				issuer.sendChat(msg);
			}
		}
	}

	public Server getServer(){
		return server;
	}
	public Map<String, Command> getCommands(){
		return cmds;
	}
}
