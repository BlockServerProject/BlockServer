package org.blockserver;

import java.io.IOException;

import org.blockserver.cmd.CommandIssuer;

/**
 * <p>The thread to watch and issue console commands.</p>
 */
public class ConsoleCommandHandler extends Thread implements CommandIssuer{
	private Server server;
	private ConsoleCommandSource source;
	protected boolean running;

	/**
	 * <p>Constructs a new <code>ConsoleCommandHandler</code> instance.</p>
	 * @param server
	 */
	public ConsoleCommandHandler(Server server, ConsoleCommandSource source){
		this.server = server;
		this.source = source;
		setName("ConsoleHandler");
	}

	@Override
	public void run(){
		running = true;
		server.getLogger().info("Console Handler started.");
		while(running){
			try{
				String input = source.readLine();
				if(input != null){
					input = input.trim();
					if(input.length() > 0){
						sudoCommand(input);
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
	public void sendChat(String msg){
		server.getLogger().info("[CMD] %s", msg);
	}
	@Override
	public void sudoCommand(String line){
		server.getCmdManager().queueCommand(this, line.trim());
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
