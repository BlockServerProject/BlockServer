package org.blockserver.ui;

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
			// TODO dispatch command
		}
	}
}
