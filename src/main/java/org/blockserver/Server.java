package org.blockserver;

import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.blockserver.net.bridge.NetworkBridgeManager;
import org.blockserver.net.bridge.UDPBridge;
import org.blockserver.net.protocol.ProtocolManager;
import org.blockserver.net.protocol.ProtocolSession;
import org.blockserver.net.protocol.pe.PeProtocol;
import org.blockserver.net.protocol.pe.sub.v20.PeSubprotocolV20;
import org.blockserver.player.Player;
import org.blockserver.player.PlayerDatabase;
import org.blockserver.player.PlayerLoginInfo;
import org.blockserver.ticker.ServerTicker;
import org.blockserver.ticker.Task;
import org.blockserver.ui.ConsoleListener;
import org.blockserver.ui.ConsoleOut;
import org.blockserver.ui.InputStreamConsoleIn;
import org.blockserver.ui.Logger;
import org.blockserver.utils.Position;

public class Server{
	private InetAddress address;
	private int port;
	private String serverName;
	private ServerTicker ticker;
	private Logger logger;
	private ConsoleListener consoleListener;
	private final ArrayList<Runnable> shutdownRuns = new ArrayList<>();
	private NetworkBridgeManager bridges;
	private ProtocolManager protocols;
	private PlayerDatabase playerDb;
	private HashMap<SocketAddress, Player> players = new HashMap<>();
	private int currentEntityID = -1;
	private Position spawnPosition = new Position(0, 64, 0); //DUMMY

	public String getServerName(){
		return serverName;
	}
	public Position getSpawnPosition() {
		return spawnPosition;
	}
	public ServerTicker getTicker(){
		return ticker;
	}
	/**
	 * Get the server logger for wrapping the {@linkplain ConsoleOut} passed in constructor
	 * @return {@linkplain Logger} wrapping a {@linkplain ConsoleOut}
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
	 * Adds {@code function} to an {@linkplain ArrayList} such that
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
	public PlayerDatabase getPlayerDatabase(){
		return playerDb;
	}
	public Collection<Player> getPlayers(){
		return players.values();
	}

	/**
	 * Package internal constructor used in {@linkplain ServerBuilder#build()} internally
	 * @see ServerBuilder#build()
	 * @param address IP address that the MCPE server is run on
	 * @param port the port that the MCPE server is run on
	 * @param out the console output, a.k.a. the logger, of the server
	 * @param playerDb the database to use for players
	 */
	Server(InetAddress address, int port, String serverName, ConsoleOut out, PlayerDatabase playerDb){
		Thread.currentThread().setName("BlockServerPE");
		this.address = address;
		this.port = port;
		this.serverName = serverName;
		logger = new Logger(out);
		consoleListener = new ConsoleListener(new InputStreamConsoleIn(System.in, false));
		consoleListener.tick();
		ticker = new ServerTicker(this, 50);
		protocols = new ProtocolManager(this);
		bridges = new NetworkBridgeManager(this);
		this.playerDb = playerDb;
		registerModules();
	}
	private void registerModules(){
		logger.info("Registering modules...");
		PeProtocol pocket = new PeProtocol(this);
		protocols.addProtocol(pocket);
		bridges.addBridge(new UDPBridge(bridges));
		pocket.getSubprotocols().registerSubprotocol(new PeSubprotocolV20(this));
		logger.info("Modules registered!");
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
		System.out.println("IS CALLED FROM SERVER!");
	}

	public Player newSession(ProtocolSession session){
		Player player = new Player(session, new PlayerLoginInfo());
		players.put(session.getAddress(), player);
		return player;
	}
	public Player newSession(ProtocolSession session, PlayerLoginInfo info){
		Player player = new Player(session, info);
		players.put(session.getAddress(), player);
		return player;
	}

	public int getNextEntityID(){
		return currentEntityID++;
	}
}
