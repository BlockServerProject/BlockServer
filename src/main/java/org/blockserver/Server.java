package org.blockserver;

import java.net.InetAddress;
import java.util.ArrayList;

import org.blockserver.network.bridge.NetworkBridgeManager;
import org.blockserver.network.protocol.ProtocolManager;
import org.blockserver.ticker.ServerTicker;
import org.blockserver.ticker.Task;
import org.blockserver.ui.ConsoleOut;
import org.blockserver.ui.Logger;

public class Server{
	private InetAddress address;
	private int port;
	private String serverName;
	private ServerTicker ticker;
	private Logger logger;
	private ArrayList<Runnable> shutdownRuns = new ArrayList<Runnable>();
	private NetworkBridgeManager bridges;
	private ProtocolManager protocols = new ProtocolManager(this);

	public String getServerName(){
		return serverName;
	}
	public ServerTicker getTicker(){
		return ticker;
	}
	/**
	 * Get the server logger for wrapping the {@linkplain ConsoleOut} passed in constructor
	 * @return {@linkplain ServerLogger} wrapping a {@linkplain ConsoleOut}
	 */
	public Logger getLogger(){
		return logger;
	}
	public NetworkBridgeManager getBridges(){
		return bridges;
	}
	public ProtocolManager getProtocols(){
		return protocols;
	}
	/**
	 * Adds <code>function</code> to an {@linkplain ArrayList} such that
	 * it will be run when the server stops (without uncatchable crashing).<br>
	 * This method is thread-safe.
	 * @param function the {@linkplain Runnable} to be run
	 */
	public void registerShutdownFunction(Runnable function){
		synchronized(shutdownRuns){
			shutdownRuns.add(function);
		}
	}
	/**
	 * Get the IP the server's default bridge(s) is/are running on, usually localhost
	 * @return the default IP
	 */
	public InetAddress getAddress(){
		return address;
	}
	/**
	 * Get the port the server's default bridge(s) is/are running on, by default 19132
	 * @return the default port
	 */
	public int getPort(){
		return port;
	}

	/**
	 * Package internal constructor used in {@linkplain ServerBuilder#build()} internally
	 * @see ServerBuilder#build()
	 * @param address
	 * @param port
	 * @param out
	 */

	Server(InetAddress address, int port, String serverName, ConsoleOut out){
		Thread.currentThread().setName("BlockServerPE-" + serverName);
		this.address = address;
		this.port = port;
		this.serverName = serverName;
		logger = new Logger(out);
		ticker = new ServerTicker(this, 50);
		bridges = new NetworkBridgeManager(this);
	}
	/**
	 * Start the server operation. This method blocks until the server is stopped.
	 */
	public void start(){
		ticker.start();
	}
	/**
	 * Stop the server. This calls {@linkplain Task#onFinalize()} on all
	 * {@linkplain Task}s in {@linkplain ServerTicker}.tasks
	 */
	public void stop(){
		ticker.stop();
		for(Runnable r: shutdownRuns){
			r.run();
		}
	}
}
