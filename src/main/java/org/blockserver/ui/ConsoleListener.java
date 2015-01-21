package org.blockserver.ui;

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
				String[] args = line.split(" ");
				
				String commandLabel = args[0];
				if(commandLabel.equals(CommandType.STOP)) {
					// Code here
				}
				// TODO add all commands
			}
		}
	}
}
