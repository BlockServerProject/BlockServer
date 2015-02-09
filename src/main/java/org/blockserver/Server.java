package org.blockserver;

import java.io.File;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.blockserver.api.API;
import org.blockserver.api.impl.DummyAPI;
import org.blockserver.cmd.CommandManager;
import org.blockserver.level.LevelManager;
import org.blockserver.level.LevelSaveException;
import org.blockserver.level.impl.dummy.DummyLevel;
import org.blockserver.level.impl.levelDB.DBLevel;
import org.blockserver.module.ModuleLoadException;
import org.blockserver.module.ModuleLoader;
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
	private final ArrayList<Runnable> shutdownRuns = new ArrayList<>();
	private NetworkBridgeManager bridges;
	private ProtocolManager protocols;
	private PlayerDatabase playerDb;
	private HashMap<SocketAddress, Player> players = new HashMap<>();
	private CommandManager cmdMgr;
	private ConsoleListener consoleListener;
	private int currentEntityID = 1; // TODO migrate this to somewhere more proper
	@Deprecated
	private Position spawnPosition = new Position(0, 64, 0); // TODO DUMMY
	private LevelManager lvlManager;
	private API api = new DummyAPI();
	private File modulesLocation;

	public String getServerName(){
		return serverName;
	}
	public Position getSpawnPosition() {
		return lvlManager.getLevelImplemenation().getSpawnPosition();
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
	public void addShutdownFunction(Runnable function){
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
	public CommandManager getCmdMgr(){
		return cmdMgr;
	}
	public ConsoleListener getConsoleListener(){
		return consoleListener;
	}

	/**
	 * Package internal constructor used in {@linkplain ServerBuilder#build()} internally
	 * @see ServerBuilder#build()
	 * @param address IP address that the MCPE server is run on
	 * @param port the port that the MCPE server is run on
	 * @param out the console output, a.k.a. the logger, of the server
	 * @param playerDb the database to use for players
	 */
	Server(InetAddress address, int port, String serverName, ConsoleOut out, PlayerDatabase playerDb, File modulePath){
		Thread.currentThread().setName("BlockServerPE");
		this.address = address;
		this.port = port;
		this.modulesLocation = modulePath;
		this.serverName = serverName;
		logger = new Logger(out);
		ticker = new ServerTicker(this, 50);
		protocols = new ProtocolManager(this);
		bridges = new NetworkBridgeManager(this);
		this.playerDb = playerDb;
		cmdMgr = new CommandManager(this);
		consoleListener = new ConsoleListener(this, InputStreamConsoleIn.fromConsole());
		registerModules();
		loadLevel();
	}
	private void registerModules(){
		ModuleLoader loader = new ModuleLoader(this, modulesLocation);
		try {
			loader.run();
		} catch(ModuleLoadException e){
			logger.error("Failed to load modules: ModuleLoadException.");
			logger.trace("ModuleLoadException: "+e.getMessage());
			e.printStackTrace();
		}
	}
	private void loadLevel(){
		//TODO: Implement LevelDB worlds
		lvlManager = new LevelManager(this);
		//lvlManager.setLevelImpl(new DummyLevel(new Position(0, 64, 0)));
		lvlManager.setLevelImpl(new DBLevel(new File("world"), this, new Position(0, 64, 0)));
		addShutdownFunction(new Runnable() {
			@Override
			public void run() {
				try {
					lvlManager.getLevelImplemenation().saveLevel();
				} catch (LevelSaveException e) {
					e.printStackTrace();
				}
			}
		});
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
		consoleListener.close(false);
		ticker.stop();
		for(Runnable r: shutdownRuns){
			r.run();
		}
	}

	public void setAPI(API api){
		this.api = api;
	}

	public API getAPI(){
		return this.api;
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

	public LevelManager getLevelManager(){
		return lvlManager;
	}

	public int getNextEntityID(){
		return currentEntityID++;
	}
}
