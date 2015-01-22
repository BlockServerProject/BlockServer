package org.blockserver.ui;

import java.util.ArrayList;
import java.util.Arrays;

import org.blockserver.command.Command;
import org.blockserver.command.CommandMessage;
import org.blockserver.command.CommandType;

public class ConsoleListener {
	
	private ConsoleIn in;
	
	public ConsoleListener(ConsoleIn in){
		this.in = in;
	}
	public void tick(){
		while(true){
			String line = in.read();
			if(!line.isEmpty()){
				// Get raw command args
				String[] rawCommandArgs = line.split(" ");
				// Get command
				CommandType command = Command.getCommandType(rawCommandArgs[0]);
				
				// Remove command from arguments
				ArrayList<String> argsArray = new ArrayList<String>(Arrays.asList(rawCommandArgs));
				argsArray.remove(0);
				
				// Create arguments
				String[] args = argsArray.toArray(new String[argsArray.size()]);
				
				if(command.equals(CommandType.HELP) || command.equals(CommandType.ALT_HELP)) {
					System.out.println("This command is not yet finished!");
				}
				else {
					System.out.println(CommandMessage.UnknownCommand);
				}
				// TODO add all commands
			}
			else {
				return;
			}
		}
	}
}
