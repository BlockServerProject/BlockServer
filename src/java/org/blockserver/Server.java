package org.blockserver;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.blockserver.chat.ChatManager;
import org.blockserver.cmd.CommandManager;
import org.blockserver.entity.EntityTypeManager;
import org.blockserver.level.Level;
import org.blockserver.level.format.LevelProviderManager;
import org.blockserver.level.format.LevelProviderType;
import org.blockserver.level.format.bsl.BSLLevelProviderType;
import org.blockserver.level.generator.GenerationSettings;
import org.blockserver.level.generator.GeneratorManager;
import org.blockserver.network.PacketHandler;
import org.blockserver.player.Player;
import org.blockserver.player.PlayerDatabase;
import org.blockserver.scheduler.Scheduler;
import org.blockserver.utility.MinecraftVersion;
import org.blockserver.utility.ServerLogger;
import org.blockserver.utility.Utils;

public class Server implements Context{
	private static Server instance = null;

	private ConsoleCommandHandler cmdHandler = null;
	private CommandManager cmdMgr;
	private ChatManager chatMgr;
	private EntityTypeManager entityTypeMgr;
	private LevelProviderManager levelProviderMgr;
	private GeneratorManager generatorMgr;
	private boolean isNextChatMgrFirst = true;
	private ServerLogger logger;
	private Scheduler scheduler;
	private PacketHandler packetHandler;
	public PlayerDatabase playerDb;

	private Map<String, Player> players;
	private Map<String, Level> levels;

	private boolean stopped= false;
	private final MinecraftVersion MCVERSION;
	private String VERSION = "0.1 - DEV";
	private String serverName;
	private String serverip;
	private short serverPort;
	private int maxPlayers;

	private File worldsDir;
	private File playersDir;
	private File pluginsDir;
	private String defaultLevel;

	private boolean serverRunning = false;
	private long startTime;
	private long serverID;
	private GenerationSettings defaultLevelGenSet;
	private String motd;

	/**
	 * <p>Get an instance of the currently running server, (Broken).</p>
	 * 
	 * @return an instance of Server
	 */
	public static synchronized Server getInstance(){
		return instance;
	}
	/**
	 * <p>Set the static instance of the currently running server, (Broken).<br>
	 * 
	 * Do <b>not</b> call this method externally.</p>
	 * @param i the server object to set
	 */
	public static void setInstance(Server i){
		instance = i;
	}

	/**
	 * <p>Get the server scheduler instance.</p>
	 * 
	 * @return the server scheduler
	 */
	public Scheduler getScheduler(){
		return scheduler;
	}
	/**
	 * <p>Get the server logger instance.</p>
	 * 
	 * @return the server logger
	 */
	public ServerLogger getLogger(){
		return logger;
	}
	/**
	 * <p>Get the server's Minecraft version.</p>
	 * 
	 * @return an instance of <code>MinecraftVeresion</code> that describes 
	 *   the server's compatible Minecraft version.
	 */
	public MinecraftVersion getMinecraftVersion(){
		return MCVERSION;
	}
	/**
	 * <p>Get the server's IP listening to, most often <code>0.0.0.0</code>.</p>
	 * 
	 * @return the server's listening IP as string.
	 */
	public String getServerIP(){
		return serverip;
	}
	/**
	 * <p>Get the server's name visible to clients.</p>
	 * 
	 * @return the server's name visible to clients.
	 */
	public String getServerName(){
		return serverName;
	}
	/**
	 * <p>Get the current BlockServer version.</p>
	 * 
	 * @return the current BlockServer version.
	 */
	public String getVersion(){
		return VERSION;
	}
	/**
	 * <p>Get the port the server is running on.</p>
	 * 
	 * @return the port the server is running on.
	 */
	public short getServerPort(){
		return serverPort;
	}
	/**
	 * <p>Get the maximum player limit.</p>
	 * 
	 * @return the maximum player limit.
	 */
	public int getMaxPlayers(){
		return maxPlayers;
	}
	/**
	 * <p>Get the server ID visible to clients.</p>
	 * 
	 * @return the server ID visible to clients.
	 */
	public long getServerID(){
		return serverID;
	}
	/**
	 * <p>Get the server's start time timestamp in milliseconds.</p>
	 * 
	 * @return the server's start time timestamp in milliseconds.
	 */
	public long getStartTime(){
		return startTime;
	}
	/**
	 * <p>Check if the server is still running.</p>
	 * 
	 * @return <code>true</code> if <code>run()</code> has been called and
	 *   <code>stop()</code> has not been called.
	 */
	public boolean isRunning(){
		return serverRunning;
	}
	public boolean isStopped(){
		return stopped;
	}
	/**
	 * <p>Get a Player object by the player address.</p<
	 * 
	 * @param ip - the IP the player connects with
	 * @param port - the port the player connects with
	 * @return the Player object that connects with this address, or <code>null</code> if not found.
	 */
	public Player getPlayer(String ip, int port){
		return players.get(ip + Integer.toString(port));
	}
	public Player[] getPlayers(String regex){
		List<Player> ps = new ArrayList<Player>(1);
		for(Player player: players.values()){
			if(player.getName().matches(regex)){
				ps.add(player);
			}
		}
		return Utils.toArray(ps, Player.class);
	}
	/**
	 * <p>Get the folder where worlds are saved in.</p>
	 * 
	 * @return the folder where worlds are saved in.
	 */
	public File getWorldsDir(){
		return worldsDir;
	}
	/**
	 * <p>Get the folder where data of players are saved in.</p>
	 * 
	 * @return the folder where data of players are saved in.
	 */
	public File getPlayersDir(){
		return playersDir;
	}
	/**
	 * <p>Get the folder where plugins are loaded from and whose data are saved in.</p>
	 * @return the folder where plugins are loaded from and whose data are saved in.
	 */
	public File getPluginsDir(){
		return pluginsDir;
	}
	/**
	 * <p>Get the <code>PlayerDatabase</code> instance that the server is using to save player data.</p>
	 * 
	 * @return the <code>PlayerDatabase</code> instance that the server
	 *   is using to save player data.
	 */
	public PlayerDatabase getPlayerDatabase(){
		return playerDb;
	}
	/**
	 * <p>Get the default level of the server.</p>
	 * 
	 * @return the default level of the server.
	 */
	public Level getDefaultLevel() {
		return levels.get(defaultLevel);
	}
	/**
	 * <p>Get a level by its name, and load and/or generate it if necessary and required.</p>
	 * 
	 * @param name - the name of the level to get
	 * @param load - whether to load the level if it hasn't been loaded
	 * @param generate - whether to generate the level if it hasn't been generated
	 * @return the <code>Level</code> object or <code>null</code> if not possible.
	 */
	public Level getLevel(String name, boolean load, boolean generate){
		if(levels.containsKey(name)){
			return levels.get(name);
		}
		if(load){
			boolean success = loadLevel(name, generate);
			if(success){
				return levels.get(name);
			}
		}
		return null;
	}
	/**
	 * <p>Get the server command manager.</p>
	 * 
	 * @return the <code>CommandManager</code> instance that manages server commands.
	 */
	public CommandManager getCmdManager(){
		return cmdMgr;
	}
	public GenerationSettings getDefaultLevelGenerationSettings(){
		return defaultLevelGenSet;
	}
	public ChatManager getChatMgr(){
		return chatMgr;
	}
	public void setChatMgr(Class<? extends ChatManager> type) throws ReflectiveOperationException{
		setChatMgr(type.newInstance());
	}
	public void setChatMgr(ChatManager chatMgr){
		this.chatMgr = chatMgr;
		chatMgr.initialize(this);
		if(!isNextChatMgrFirst){
			logger.warning("Using new ChatManager: %s", chatMgr.getClass().getSimpleName());
		}
		isNextChatMgrFirst = false;
	}
	public EntityTypeManager getEntityTypeMgr(){
		return entityTypeMgr;
	}
	public void setEntityTypeMgr(EntityTypeManager entityTypeMgr){
		this.entityTypeMgr = entityTypeMgr;
	}
	public LevelProviderManager getLevelProviderMgr(){
		return levelProviderMgr;
	}
	public GeneratorManager getGeneratorMgr(){
		return generatorMgr;
	}
	/**
	 * <p>Add a player to the list of online players.</p>
	 * 
	 * @param player the <code>Player</code> object to add
	 */
	public void addPlayer(Player player){
		players.put(player.getIP() + Integer.toString(player.getPort()), player);
	}
	public void removePlayer(Player player){
		String addr = player.getIP() + Integer.toString(player.getPort());
		players.remove(addr);
	}
	public boolean isPlayerConnected(Player player){
		String addr = player.getIP() + Integer.toString(player.getPort());
		if(players.containsKey(addr)){
			return true;
		}
		else{
			return false;
		}
	}
	public int getPlayersConnected(){
		return players.size();
	}
	public Collection<Player> getConnectedPlayers(){
		return players.values();
	}
	
	public String getMOTD(){
		return motd;
	}

	/**
	 * <p>Construct a new instance of the server.
	 *   <code>Server.setInstance()</code> is called in this constructor,
	 *   so using this constructor automatically changes the static instance
	 *   <code>Server.instance</code> to this new instance.
	 * 
	 * @param name - name of the server visible to clients
	 * @param ip - IP to run the server on, most often equal to "0.0.0.0"
	 * @param port - port to run the server on
	 * @param maxPlayers - the maximum number of players to allow connected
	 * @param version - the Minecraft version the server is compatible with
	 * @param motd - the message to send to the player when he logins
	 * @param defaultLevel - the default level name
	 * @param defaultLevelGenSet - the default level generation settings
	 * @param dbType - the player database class to use as player database
	 * @param worldsDir - the {@link File} directory to save worlds in
	 * @param playersDir - the {@link File} directory to save player data in, if used by <code>dbType</code>
	 * @param pluginsDir - the {@link File} directory to save plugins data in and load from
	 * @throws Exception
	 */
	public Server(String name, String ip, short port, int maxPlayers, MinecraftVersion version,
			String motd, String defaultLevel, GenerationSettings defaultLevelGenSet,
			Class<? extends ChatManager> chatMgrType, Class<? extends PlayerDatabase> dbType,
			Class<? extends EntityTypeManager> entityTypeMgrType,
			File worldsDir, File playersDir) throws Exception{
		Thread.currentThread().setName("ServerThread");
		setInstance(this);
		logger = new ServerLogger();
		startTime = System.currentTimeMillis();
		serverip = ip;
		serverPort = port;
		serverName = name;
		this.maxPlayers = maxPlayers;
		MCVERSION = version;
		this.motd = motd;
		serverID = new Random().nextLong();
		players = new HashMap<String, Player>(maxPlayers);
		this.playersDir = playersDir;
		worldsDir.mkdirs();
		this.worldsDir = worldsDir;
		int cnt = worldsDir.list(new RootDirectoryFilter(worldsDir)).length;
		levels = new HashMap<String, Level>(cnt);
		this.defaultLevelGenSet = defaultLevelGenSet;
		this.defaultLevel = defaultLevel;
		boolean success = loadLevel(defaultLevel, true);
		if(!success){
//			throw new RuntimeException("Unable to generate default level");
		}
		scheduler = new Scheduler(this);// Minecraft default Ticks Per Seconds(20)
		packetHandler = new PacketHandler(this);
		cmdHandler = new ConsoleCommandHandler(this);
		cmdMgr = new CommandManager(this);
		setChatMgr(chatMgrType.newInstance()); // gracefully throw out the exception to the one who asked for it :P
		setEntityTypeMgr(entityTypeMgrType.newInstance());
		Collection<LevelProviderType<?>> coll = new ArrayList<LevelProviderType<?>>(1);
		coll.add(new BSLLevelProviderType());
		levelProviderMgr = new LevelProviderManager(coll, this);
		generatorMgr = new GeneratorManager();
		playerDb = dbType.newInstance();
	}
	/**
	 * A file filter that filters out any non-directories and non-root directories
	 */
	private class RootDirectoryFilter implements FilenameFilter{
		private File dir;
		public RootDirectoryFilter(File dir){
			this.dir = dir;
		}
		@Override
		public boolean accept(File dir, String name){
			if(dir.equals(this.dir)){
				File d = new File(dir, name);
				return d.isDirectory();
			}
			return false;
		}
	}

	/**
	 * <p>Actually start the server and other threads.</p>
	 */
	public void run(){
		serverRunning = true;
		try{
			logger.info("This is version " + VERSION);
			scheduler.Start();
			logger.info("Server Scheduler Started...");
			playerDb.init(this);
			packetHandler.start();
			cmdHandler.start();
			logger.info("Started server on: *:" + serverPort + ", implementing " + MinecraftVersion.versionToString(MCVERSION));
		}
		catch(SocketException e){
			logger.fatal("COULD NOT BIND TO PORT - Maybe another server is running on %d?", serverPort);
			logger.fatal("Shutting down server due to error.");
			System.exit(1);
		}
		catch(IOException e){
			int time = (int) (System.currentTimeMillis() - this.startTime);
			logger.warning("IOException at: " + time + " ms");
		}
		catch(Exception e){
			int time = (int) (System.currentTimeMillis() - this.startTime);
			logger.warning("Exception at: " + time + " ms");
			logger.warning(e.getMessage());
		}
	}
	/**
	 * <p>Stop the server, actually making the current tick the last tick of the server.</p>
	 * 
	 * @throws Exception
	 */
	public void stop() throws Exception{
		for(Player player: players.values()){
			player.close("server is stopping");
		}
		serverRunning = false;
		scheduler.end();
		packetHandler.end();
		cmdHandler.end();
		stopped = true;
	}

	@Override
	public boolean isEnabled(){
		return true;
	}
	@Override
	public String getIdentifier(){
		return "org.blockserver";
	}

	public void sendPacket(byte[] buffer, String ip, int port){
		try{
			packetHandler.sendPacket(buffer, ip, port);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * <p>Load a level.</p>
	 * 
	 * @param name - the name of the level to load
	 * @param generate - whether to try to generate the level if it doesn't exist
	 * @return whether the level is now loaded.
	 */
	public boolean loadLevel(String name, boolean generate){
		File dir = new File(getWorldsDir(), name);
		if(dir.isDirectory()){
			// TODO Auto-generated method stub
			return false;
		}
		if(generate){
			return generateLevel(name);
		}
		return false;
	}
	/**
	 * <p>Generate a level.</p>
	 * 
	 * @param name
	 * @return whether the level is successfully generated.
	 */
	public boolean generateLevel(String name){
		generateLevel(name, getDefaultLevelGenerationSettings());
		return false;
	}
	/**
	 * <p>Generate a level.</p>
	 * 
	 * @param name
	 * @param settings the settings to generate a level
	 * @return whether the level is successfully generated.
	 */
	public boolean generateLevel(String name, GenerationSettings settings){
		// TODO Auto-generated method stub
		return false;
	}
}
