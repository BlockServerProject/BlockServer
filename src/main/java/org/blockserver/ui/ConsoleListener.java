package org.blockserver.ui;

import org.blockserver.command.Command;
import org.blockserver.command.CommandMessage;
import org.blockserver.command.CommandType;

public class ConsoleListener{
	
	private ConsoleIn in;
	
	public ConsoleListener(ConsoleIn in){
		this.in = in;
	}
	public void tick(){
		while(true){
			String line = in.read();
			if(line.isEmpty()){
				return;
			}
			else {
				String[] commandArgs = line.split(" ");
				CommandType command = Command.getCommandType(commandArgs[0]);
				if(command.equals(CommandType.HELP) || command.equals(CommandType.ALT_HELP)) {
					System.out.println("This command is not yet finished!");
				}
				else {
					System.out.println(CommandMessage.UnknownCommand);
				}
				// TODO add all commands
			}
		}
	}
}
