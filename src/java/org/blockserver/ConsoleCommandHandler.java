package org.blockserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.blockserver.cmd.CommandIssuer;

/**
 * <p>The thread to watch and issue console commands.</p>
 */
public class ConsoleCommandHandler extends Thread implements CommandIssuer{
	private Server server;
	protected boolean running;

	/**
	 * <p>Constructs a new <code>ConsoleCommandHandler</code> instance.</p>
	 * @param server
	 */
	public ConsoleCommandHandler(Server server){
		this.server = server;
		setName("ConsoleHandler");
	}

	@Override
	public void run(){
		running = true;
		server.getLogger().info("Console Handler started.");
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		while(running){
			try{
				String input = console.readLine();
				if(input != null){
					input = input.trim();
					if(!input.isEmpty()){
						server.getLogger().info("Dispatching command: %s", input);
						sudoCommand(input.trim());
					}
				}
			}
			catch(IOException e){
				server.getLogger().error("IOException in ConsoleCommand Thread:");
				e.printStackTrace();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * Stop listening for new commands.
	 * @throws Exception 
	 */
	public void end() throws Exception{
		running = false;
		join();
	}

	@Override
	public void sendMessage(String msg){
		server.getLogger().info("[CMD] %s", msg);
	}
	@Override
	public void sudoCommand(String line){
		server.getCmdManager().runCommand(this, line.trim());
	}
	@Override
	public int getHelpLines(){
		return Integer.MAX_VALUE;
	}
	@Override
	public Server getServer(){
		return server;
	}
	@Override
	public String getEOL(){
		return System.getProperty("line.separator", "\n");
	}
}
