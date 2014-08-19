package net.blockserver.utility;

public enum Command {

	FREE("Shows the available space of memory."),
	HELP("Shows available commands."),
	STOP("Stops the server.");

	private String description;

	private Command(String description){
        	this.description = description;
	}

	public String getDescription(){
        	return this.description;
	}

}
