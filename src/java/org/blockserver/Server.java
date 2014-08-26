package org.blockserver;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.blockserver.cmd.CommandManager;
import org.blockserver.level.Level;
import org.blockserver.network.PacketHandler;
import org.blockserver.player.Player;
import org.blockserver.player.PlayerDatabase;
import org.blockserver.scheduler.Scheduler;
import org.blockserver.utility.MinecraftVersion;
import org.blockserver.utility.ServerLogger;

public class Server implements Context{
	private static Server instance = null;
	private ConsoleCommandHandler cmdHandler = null;
	private CommandManager cmdMgr;
	private ServerLogger logger = new ServerLogger();
	private Scheduler scheduler;
	private PacketHandler packetHandler;
	private Map<String, Player> players;
	private Map<String, Level> levels;

	private final MinecraftVersion MCVERSION;
	private String VERSION = "0.1 - DEV";
	private String serverName;
	private String serverip;
	private int serverPort;
	private int maxPlayers;
	public PlayerDatabase playerDb;
	private File worldsDir;
	private File playersDir;
	private String defaultLevel;

	private boolean serverRunning;
	private long startTime;
	private long serverID;

	public static synchronized Server getInstance(){
		return instance;
	}
	public static void setInstance(Server i){
		instance = i;
	}

	public Scheduler getScheduler(){
		return scheduler;
	}
	public ServerLogger getLogger(){
		return logger;
	}
	public MinecraftVersion getMinecraftVersion(){
		return MCVERSION;
	}
	public String getServerIP(){
		return serverip;
	}
	public String getServerName(){
		return serverName;
	}
	public String getVersion(){
		return VERSION;
	}
	public int getServerPort(){
		return serverPort;
	}
	public int getMaxPlayers(){
		return maxPlayers;
	}
	public long getServerID(){
		return serverID;
	}
	public long getStartTime(){
		return startTime;
	}
	public boolean isRunning(){
		return serverRunning;
	}
	public Player getPlayer(String ip, int port){
		return players.get(ip + Integer.toString(port));
	}
	public File getWorldsDir(){
		return worldsDir;
	}
	public File getPlayersDir(){
		return playersDir;
	}
	public PlayerDatabase getPlayerDatabase(){
		return playerDb;
	}
	public Level getDefaultLevel() {
		return levels.get(defaultLevel);
	}
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

	public CommandManager getCmdManager(){
		return cmdMgr;
	}

	public void addPlayer(Player player){
		players.put(player.getIP() + Integer.toString(player.getPort()), player);
	}

	public Server(String name, String ip, int port, int maxPlayers, MinecraftVersion version,
			String defaultLevel, Class<? extends PlayerDatabase> dbType,
			File worldsDir, File playersDir) throws Exception{
		Thread.currentThread().setName("ServerThread");
		setInstance(this);
		startTime = System.currentTimeMillis();
		serverip = ip;
		serverPort = port;
		serverName = "MCCPP;Demo;" + name;
		this.maxPlayers = maxPlayers;
		MCVERSION = version;
		serverID = new Random().nextLong();
		players = new HashMap<String, Player>(players);
		this.playersDir = playersDir;
		worldsDir.mkdirs();
		this.worldsDir = worldsDir;
		int cnt = worldsDir.list(new RootDirectoryFilter(worldsDir)).length;
		levels = new HashMap<String, Level>(cnt);
		boolean success = loadLevel(defaultLevel, true);
		if(!success){
//			throw new RuntimeException("Unable to generate default level");
		}
		scheduler = new Scheduler();// Minecraft default Ticks Per Seconds(20)
		packetHandler = new PacketHandler(this);
		cmdHandler = new ConsoleCommandHandler(this);
		cmdMgr = new CommandManager();
		playerDb = dbType.newInstance();
	}

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

	public void run(){
		serverRunning = true;
		try{
			logger.info("Starting server on: *:" + serverPort + ", implementing " + MinecraftVersion.versionToString(MCVERSION));
			logger.info("This is version " + VERSION);
			scheduler.Start();
			logger.info("Server Scheduler Started...");
			playerDb.init();
			packetHandler.start();
			cmdHandler.start();
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
	public void stop() throws Exception{
		serverRunning = false;
		scheduler.Stop();
		packetHandler.Stop();
		cmdHandler.end();
	}

	@Override
	public boolean isEnabled(){
		return true;
	}

	public void sendPacket(byte[] buffer, String ip, int port){
		try{
			packetHandler.sendPacket(buffer, ip, port);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public boolean loadLevel(String name, boolean generate) {
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
	public boolean generateLevel(String name) {
		// TODO Auto-generated method stub
		return false;
	}

}
