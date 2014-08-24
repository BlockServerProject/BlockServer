package net.blockserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.blockserver.cmd.CommandIssuer;

public class ConsoleCommandHandler extends Thread implements CommandIssuer{
	private Server server;
	protected boolean running;

	public ConsoleCommandHandler(Server server){
		this.server = server;
		setName("ConsoleCommandHandler");
	}

	public void run(){
		server.getLogger().info("Console Handler started.");
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		while(running){
			try{
				String input = console.readLine();
				sudoCommand(input.trim());
			}
			catch (IOException e){
				this.server.getLogger().error("IOException in ConsoleCommand Thread:");
				e.printStackTrace();
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	public void Start(){
		running = true;
		start();
	}
	public void Stop(){
		running = false;
	}

	@Override
	public void sendMessage(String msg) {
		server.getLogger().info("[CMD] %s", msg);
	}

	@Override
	public void sudoCommand(String line) {
		server.getCmdManager().runCommand(this, line.trim());
	}

}
