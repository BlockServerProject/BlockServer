package net.blockserver.utility;

public enum Command {
	FREE(0),
	STOP(1),
	HELP(2);
	
	private int id;
	
	private Command(int cmdId){
		this.id = cmdId;
	}
	
	public static Command getCommandByName(String name){
		if(name.equalsIgnoreCase("free")){
			return Command.FREE;
		}
		else if(name.equalsIgnoreCase("stop")){
			return Command.STOP;
		}
		else if(name.equalsIgnoreCase("help")){
			return Command.HELP;
		}
		else{
			return null;
		}
	}
	
	public static String getCommandName(Command cmd){
		if(cmd == FREE){
			return "free";
		}
		else if(cmd == STOP){
			return "stop";
		}
		else if(cmd == HELP){
			return "help";
		}
		else{
			return null;
		}
	}
	
	

}
