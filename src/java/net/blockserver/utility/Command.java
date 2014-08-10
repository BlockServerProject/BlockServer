package net.blockserver.utility;

public enum Command {
	FREE(0),
	STOP(1);
	
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
		else{
			return null;
		}
	}
	
	public static String getCommandName(Command cmd){
		if(cmd == cmd.FREE){
			return "free";
		}
		else if(cmd == cmd.STOP){
			return "stop";
		}
		else{
			return null;
		}
	}
	
	

}
